package com.lt.win.backend.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lt.win.backend.io.dto.AgentDto;
import com.lt.win.backend.io.dto.UserGroupParameter;
import com.lt.win.backend.io.dto.UserGroupParameter.DeleteUserGroupReqDto;
import com.lt.win.backend.io.dto.UserGroupParameter.SaveOrUpdateUserGroupReqDto;
import com.lt.win.backend.io.dto.UserGroupParameter.UserGroupListReqDto;
import com.lt.win.backend.io.dto.UserGroupParameter.UserGroupListResDto;
import com.lt.win.backend.mapper.AdminPermissionMapper;
import com.lt.win.backend.mapper.AuthManagerMapper;
import com.lt.win.service.cache.KeyConstant;
import com.lt.win.service.cache.redis.AdminCache;
import com.lt.win.service.common.Constant;
import com.lt.win.service.exception.BusinessException;
import com.lt.win.service.io.dto.BaseParams;
import com.lt.win.service.thread.ThreadHeaderLocalData;
import com.lt.win.utils.*;
import com.lt.win.utils.*;
import com.lt.win.utils.components.pagination.ReqPage;
import com.lt.win.utils.components.pagination.ResPage;
import com.lt.win.utils.components.response.CodeInfo;
import com.lt.win.dao.generator.po.*;
import com.lt.win.dao.generator.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static com.alibaba.fastjson.JSON.parseObject;
import static com.alibaba.fastjson.JSON.toJSONString;

/**
 * @Description :
 * @Date 2020/3/13 20:22
 * @Created by WELLS
 */
@Slf4j
@Component
public class AuthManagerServiceImpl {

    @Autowired
    private AuthManagerMapper authManagerMapper;
    @Resource
    private JedisUtil jedisUtil;
    @Resource
    private UserService userServiceImpl;
    @Resource
    private AuthAdminGroupService authAdminGroupServiceImpl;
    @Resource
    private AdminCache adminCache;
    @Resource
    private AuthGroupAccessService authGroupAccessServiceImpl;
    @Resource
    private AuthGroupService authGroupServiceImpl;
    @Resource
    private AdminService adminServiceImpl;
    @Resource
    private AdminPermissionMapper adminPermissionMapper;
    @Resource
    private AdminPermissionServiceImpl adminPermissionServiceImpl;

    /**
     * 标识：菜单树-true;权限树-false
     */
    private ThreadLocal<Boolean> isTransPerm = new ThreadLocal<Boolean>();
    /**
     * 父节点
     */
    public static final ThreadLocal<List<String>> PID_LIST = new ThreadLocal<>();

    /**
     * 用户补充菜单列表查询
     *
     * @param map
     * @return
     */
    public JSONObject queryUserRule(Map<String, Object> map) {
        String uid = Optional.ofNullable(map.get("uid")).orElse("").toString();
        JSONObject jo = new JSONObject();
        if (StringUtils.isBlank(uid)) {
            log.error("uid is blank");
            jo.put("msg", "uid不能为空");
            jo.put("msg_zh", "uid is null");
            return jo;
        }
        Map<String, Object> resultMap = authManagerMapper.queryUserRule(map);
        return DataTransformaUtils.transformaJO(resultMap);
    }

    /*修改用户维度授权模块*/
    public JSONObject updateUserRule(Map<String, Object> map) {
        String uid = Optional.ofNullable(map.get("uid")).orElse("").toString();
        JSONObject jo = new JSONObject();
        if (StringUtils.isBlank(uid)) {
            log.error("uid is blank");
            jo.put("msg", "uid不能为空");
            jo.put("msg_zh", "uid is null");
            return jo;
        }
        try {
            authManagerMapper.updateUserRule(map);
            jo.put("msg_zh", GlobalVariableUtils.UPDATE_SUCCESS);
        } catch (Exception e) {
            jo.put("msg", e.getMessage());
            jo.put("msg_zh", GlobalVariableUtils.UPDATE_FAIL);
        }
        return jo;
    }


    /**
     * 根据uid加载对应角色的授权模块列表
     *
     * @param map
     * @return
     */
    public JSONObject loadAuthRule(Map<String, Object> map) {
        String uid = map.getOrDefault("uid", "").toString();
        if (StringUtils.isBlank(uid)) {
            JSONObject jo = new JSONObject();
            jo.put("msg", "uid is null");
            jo.put("msg_zh", "请传入uid");
            return jo;
        }
        var permissionJson = jedisUtil.hget(Constant.PERMISSION_LIST, uid);
        if (StringUtils.isNotEmpty(permissionJson)) {
            return parseObject(permissionJson);
        }
        /*获取该用户已授权的模块列表*/
        Map<String, Object> repairMap = authManagerMapper.queryRepair(map);
        String rules = "";
        if (repairMap != null) {
            String menu_id = Optional.ofNullable(repairMap.get("menu_id")).orElse("").toString();
            String[] rulesArray = menu_id.split(",");
            /*   String[] rulesArray = queryRulesByUid(map);*/
            StringBuffer sb = new StringBuffer("");
            for (int i = 0, length = rulesArray.length; i < length; i++) {
                if (i == length - 1) {
                    sb.append(rulesArray[i]);
                } else {
                    sb.append(rulesArray[i]).append(",");
                }
            }
            rules = sb.toString();
        }
        if (StringUtils.isBlank(rules)) {
            throw new BusinessException(CodeInfo.NO_AUTH_TO_GET_RULES);
        }
        map.put("rules", rules);
        List<Map<String, Object>> list = authManagerMapper.queryAllRule(map);
        JSONObject root = new JSONObject();
        root.put("id", 0);
        JSONArray ja = DataTransformaUtils.transformaJA(list);
        isTransPerm.set(true);
        JSONObject children;
        try {
            PID_LIST.set(new ArrayList<>());
            children = getChildren(root, ja);
        } finally {
            isTransPerm.remove();
        }
        jedisUtil.hset(Constant.PERMISSION_LIST, uid, toJSONString(children));
        return children;
    }

    /**
     * 获取权限分配全量树结构
     *
     * @param map
     * @return
     */
    public JSONObject queryAllRuleTitle(Map<String, Object> map) {
        BaseParams.HeaderInfo headerInfo = ThreadHeaderLocalData.HEADER_INFO_THREAD_LOCAL.get();
        var admin = adminCache.getAdminInfoById(headerInfo.getId());
        var ruleList = new ArrayList<String>();
        var adminGroupId = Optional.ofNullable(map.get("adminGroupId")).map(x -> Integer.parseInt(x + "")).orElse(admin.getAdminGroupId());
        var groupId = Optional.ofNullable(map.get("groupId")).map(x -> Integer.parseInt(x + "")).orElse(0);
        var adminGroup = authAdminGroupServiceImpl.getById((adminGroupId));
        ruleList.addAll(Arrays.asList(Optional.ofNullable(adminGroup).map(AuthAdminGroup::getRules).orElse("")
                .split(",")));
        if (groupId != 0) {
            var group = authGroupServiceImpl.getById(groupId);
            ruleList.clear();
            ruleList.addAll(Arrays.asList(Optional.ofNullable(group).map(AuthGroup::getRules).orElse("")
                    .split(",")));
        }
        List<Map<String, Object>> list = authManagerMapper.queryAllRuleTitle(map);
        list = list.stream().filter(x ->
                (adminGroupId == 0 && groupId == 0) || ruleList.contains(x.get("id") + ""))
                .collect(Collectors.toList());
        JSONObject root = new JSONObject();
        root.put("id", 0);
        JSONArray ja = DataTransformaUtils.transformaJA(list);
        isTransPerm.set(false);
        JSONObject children;
        try {
            PID_LIST.set(new ArrayList<>());
            children = getChildren(root, ja);
        } finally {
            isTransPerm.remove();
        }
        return children;
    }

    /**
     * 根据uid获取当前角色已授权的id列表
     *
     * @param map
     * @return
     */
    public String[] queryHasAuthByUid(Map<String, Object> map) {
        String uid = Optional.ofNullable(map.get("uid")).orElse("").toString();
        if (StringUtils.isBlank(uid)) {
            return null;
        }
        Map<String, Object> repairMap = authManagerMapper.queryRepair(map);
        String menu_id = Optional.ofNullable(repairMap.get("menu_id")).orElse("").toString();
        String[] split = menu_id.split(",");
        List<Map<String, Object>> list = authManagerMapper.queryMenuId();
        Set<String> set = new HashSet<>();
        for (Map<String, Object> item : list) {
            set.add(String.valueOf(item.get("pid")));
        }
        ArrayList<String> roleIds = new ArrayList<>();
        for (String roleId : split) {
            /*叶节点*/
            if (!set.contains(roleId)) {
                roleIds.add(roleId);
            }
        }
        return roleIds.toArray(new String[roleIds.size()]);
    }

    /**
     * 根据roleId获取当前角色已授权的id列表
     *
     * @param map
     * @return
     */
    public String[] queryHasAuthByRoleId(Map<String, Object> map) {
        String id = map.getOrDefault("id", "").toString();
        if (StringUtils.isBlank(id)) {
            JSONObject jo = new JSONObject();
            jo.put("msg", "id must be not null");
            jo.put("msg_zh", "请传入id");
            return null;
        }
        ArrayList<String> roleIds = new ArrayList<>();
        String rules = authManagerMapper.queryRulesByRoleId(map);
        if (rules != null) {
            String[] split = rules.split(",");
            List<Map<String, Object>> list = authManagerMapper.queryMenuId();
            Set<String> set = new HashSet<>();
            for (Map<String, Object> item : list) {
                set.add(String.valueOf(item.get("pid")));
            }
            for (String roleId : split) {
                /*叶节点*/
                if (!set.contains(roleId)) {
                    roleIds.add(roleId);
                }
            }
        }

        return roleIds.toArray(new String[roleIds.size()]);
    }

    /**
     * 新增角色
     *
     * @param map
     * @return
     */
    public JSONObject insertRole(Map<String, Object> map) {
        var title = map.get("title") + "";
        var count = authGroupServiceImpl.lambdaQuery()
                .eq(AuthGroup::getTitle, title)
                .count();
        if (count > 0) {
            throw new BusinessException(CodeInfo.ROLE_ALREADY_EXIST);
        }
        JSONObject jo = new JSONObject();
        try {
            String rules = Optional.ofNullable(map.get("rules")).orElse("").toString();
            if (StringUtils.isNotBlank(rules)) {
                String[] arr = rules.split(",");
                int[] ints = Arrays.asList(arr).stream().mapToInt(e -> Integer.parseInt(e)).sorted().toArray();
                StringBuffer sb = new StringBuffer("");
                for (int i = 0, length = ints.length; i < length; i++) {
                    if (i == length - 1) {
                        sb.append(ints[i]);
                    } else {
                        sb.append(ints[i]).append(",");
                    }
                }
                rules = sb.toString();
                map.put("rules", rules);
            }
            BaseParams.HeaderInfo headerInfo = ThreadHeaderLocalData.HEADER_INFO_THREAD_LOCAL.get();
            var authGroupAccess = authGroupAccessServiceImpl.getOne(new LambdaQueryWrapper<AuthGroupAccess>()
                            .eq(AuthGroupAccess::getUid, headerInfo.getId())
                    , false);
            map.put("pid", Optional.ofNullable(authGroupAccess).map(AuthGroupAccess::getGroupId).orElse(0));
            map.put("parent", headerInfo.getId());
            map.put("operate_id", headerInfo.getId());
            authManagerMapper.insertRole(map);
            jo.put("msg_zh", GlobalVariableUtils.INSERT_SUCCESS);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            jo.put("msg", e.getMessage());
            jo.put("msg_zh", GlobalVariableUtils.INSERT_FAIL);
        }
        return jo;
    }

    /**
     * 修改角色
     *
     * @param map
     * @return
     */
    public JSONObject updateRole(Map<String, Object> map) {
        String id = map.getOrDefault("id", "").toString();
        if (StringUtils.isBlank(id)) {
            JSONObject jo = new JSONObject();
            jo.put("msg", "id must be not null");
            jo.put("msg_zh", "请传入id");
            return jo;
        }
        JSONObject jo = new JSONObject();
        try {
            String rules = Optional.ofNullable(map.get("rules")).orElse("").toString();
            if (StringUtils.isNotBlank(rules)) {
                String[] arr = rules.split(",");
                int[] ints = Arrays.asList(arr).stream().mapToInt(e -> Integer.parseInt(e)).sorted().toArray();
                StringBuffer sb = new StringBuffer("");
                for (int i = 0, length = ints.length; i < length; i++) {
                    if (i == length - 1) {
                        sb.append(ints[i]);
                    } else {
                        sb.append(ints[i]).append(",");
                    }
                }
                rules = sb.toString();
                map.put("rules", rules);
            }
            BaseParams.HeaderInfo headerInfo = ThreadHeaderLocalData.HEADER_INFO_THREAD_LOCAL.get();
            map.put("operateId", headerInfo.id);
            authManagerMapper.updateRole(map);
            jo.put("msg_zh", GlobalVariableUtils.UPDATE_SUCCESS);
        } catch (Exception e) {
            log.error("", e);
            jo.put("msg", e.getMessage());
            jo.put("msg_zh", GlobalVariableUtils.UPDATE_FAIL);
        }
        jedisUtil.del(Constant.PERMISSION_LIST);
        return jo;
    }

    /**
     * 删除角色
     *
     * @param map
     * @return
     */
    public JSONObject deleteRole(Map<String, Object> map) {
        String id = map.getOrDefault("id", "").toString();
        if (StringUtils.isBlank(id)) {
            JSONObject jo = new JSONObject();
            jo.put("msg", "id must be not null");
            jo.put("msg_zh", "请传入id");
            return jo;
        }
        var count = authGroupAccessServiceImpl.lambdaQuery()
                .eq(AuthGroupAccess::getGroupId, Integer.parseInt(id)).count();
        if (count > 0) {
            throw new BusinessException(CodeInfo.ROLE_DELETE_VERIFICATION);
        }
        JSONObject jo = new JSONObject();
        try {
            authManagerMapper.deleteRole(map);
            authGroupAccessServiceImpl.remove(new LambdaQueryWrapper<AuthGroupAccess>()
                    .eq(AuthGroupAccess::getGroupId, id));
            jo.put("msg_zh", GlobalVariableUtils.DELETE_SUCCESS);
        } catch (Exception e) {
            jo.put("msg", e.getMessage());
            jo.put("msg_zh", GlobalVariableUtils.DELETE_FAIL);
        }
        jedisUtil.del(Constant.PERMISSION_LIST);
        return jo;
    }


    /**
     * 递归获得子节点
     *
     * @param root
     * @param ja
     * @return
     */

    public JSONObject getChildren(JSONObject root, JSONArray ja) {
        JSONArray children = new JSONArray();
        JSONArray button = new JSONArray();
        boolean isButton = false;
        for (int i = 0; i < ja.size(); i++) {
            JSONObject jo = (JSONObject) ja.get(i);
            if (String.valueOf(jo.get("pid")).equals(String.valueOf(root.get("id")))) {
                /*判断树的按钮节点是否使用button作为数组名称,isButton为true，数组形成使用button*/
                if (isTransPerm.get() && StringUtils.equals("1", String.valueOf(jo.get("isRaceMenu")))) {
                    isButton = true;
                }
                /*按钮没有子元素,button一定是叶子节点,直接放入button*/
                if (isButton) {
                    button.add(jo);
                    isButton = false;
                } else {
                    PID_LIST.get().add(String.valueOf(root.get("id")));
                    /*非button节点，继续递归，是否有子元素*/
                    children.add(getChildren(jo, ja));
                }
            }
        }
        if (!CollectionUtils.isEmpty(children)) {
            root.put("children", children);
        }
        if (!CollectionUtils.isEmpty(button)) {
            root.put("button", button);
        }
        return root;
    }

    /**
     * 获取角色已授权的模块的id数组
     *
     * @param map
     * @return
     */
    private String[] queryRulesByUid(Map<String, Object> map) {
        /*角色已授权模块id列表,和用户已授权id列表*/
        Map<String, Object> rulesMap = authManagerMapper.queryRulesByUid(map);
        String roleRules = Optional.ofNullable(rulesMap.get("roleRules")).orElse("").toString();
        String userRules = Optional.ofNullable(rulesMap.get("userRules")).orElse("").toString();
        String[] roleRulesArray = roleRules.split(",");
        /*中间去重,roleRulesArray转set*/
        Set<String> itemSet = new HashSet<>(Arrays.asList(roleRulesArray));

        /*匹配用户特殊授权id,正号添加，负号删除*/
        if (StringUtils.isNotBlank(userRules)) {
            String[] userRulesArray = userRules.split(",");
            for (String rule : userRulesArray) {
                if (rule.contains("-")) {
                    String absoluteRule = rule.replace("-", "");
                    if (itemSet.contains(absoluteRule)) {
                        itemSet.remove(absoluteRule);
                    }
                } else {
                    String absoluteRule = rule.replace("+", "");
                    if (!itemSet.contains(absoluteRule)) {
                        itemSet.add(rule);
                    }
                }
            }
        }
        return itemSet.stream().toArray(String[]::new);
    }

    /**
     * 查询代理列表
     *
     * @return 列表
     */
    public List<AgentDto.AgentListResDto> agentList() {
        var uidList = userServiceImpl.lambdaQuery()
                .eq(User::getStatus, 10)
                .list()
                .stream()
                .map(User::getId)
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(uidList)) {
            return new ArrayList<>();
        }
        var userList = userServiceImpl.lambdaQuery()
                .in(User::getRole, List.of(1, 4))
                .in(User::getId, uidList)
                .list();
        return BeanConvertUtils.copyListProperties(userList, AgentDto.AgentListResDto::new, (user, resDto) -> {
            resDto.setAgentId(user.getId());
            resDto.setAgentName(user.getUsername());
        });
    }

    /**
     * 查询用户组列表
     */
    public ResPage<UserGroupListResDto> userGroupList(ReqPage<UserGroupListReqDto> reqDto) {
        var sortArr = reqDto.getSortField();
        var orderFlag = true;
        if (ArrayUtils.isNotEmpty(sortArr)) {
            orderFlag = !sortArr[0].equals("updatedAt");
        }
        var reqData = reqDto.getData();
        var query = new LambdaQueryWrapper<AuthAdminGroup>()
                .eq(Objects.nonNull(reqData.getTitle()), AuthAdminGroup::getTitle, reqData.getTitle())
                .orderByDesc(orderFlag, AuthAdminGroup::getUpdatedAt);
        BaseParams.HeaderInfo headerInfo = ThreadHeaderLocalData.HEADER_INFO_THREAD_LOCAL.get();
        var admin = adminCache.getAdminInfoById(headerInfo.getId());
        var adminGroupList = authAdminGroupServiceImpl.lambdaQuery().eq(AuthAdminGroup::getStatus, 1).list();
        if (admin.getAdminGroupId() != 0) {
            var set = new HashSet<Integer>();
            set.add(admin.getAdminGroupId());
            var pidMap = adminGroupList.stream().collect(Collectors.groupingBy(AuthAdminGroup::getPid));
            getAuthAdminGroupSelfChild(pidMap, admin.getAdminGroupId(), set);
            //超级可见全部，系统查看除超级管理员，其他可见自身与下级用户
            adminGroupList = adminGroupList.stream().filter(x -> adminPermissionServiceImpl.isVisible(admin, x.getId()) ||
                    set.contains(x.getId())).collect(Collectors.toList());
        }
        var idList = adminGroupList.stream().map(AuthAdminGroup::getId).collect(Collectors.toList());
        query.in(!CollectionUtils.isEmpty(idList), AuthAdminGroup::getId, idList);
        Page<AuthAdminGroup> page = authAdminGroupServiceImpl.page(reqDto.getPage(), query);
        //获取全量树的PID
        queryAllRuleTitle(new HashMap<>());
        Page<UserGroupListResDto> adminGroupPage = BeanConvertUtils.copyPageProperties(page, UserGroupListResDto::new, (source, target) -> {
            var operateAdmin = adminCache.getAdminInfoById(source.getOperateId());
            var operateName = Constant.SUPER_ADMIN.equals(Optional.ofNullable(operateAdmin).map(x -> x.getAdminGroupId() + "").orElse("")) ?
                    "--" : Optional.ofNullable(operateAdmin).map(Admin::getUsername).orElse("");
            target.setOperateName(operateName);
            var rules = source.getRules();
            if (StringUtils.isNotEmpty(rules)) {
                var ruleList = Arrays.stream(rules.split(",")).filter(ele -> !PID_LIST.get().contains(ele))
                        .collect(Collectors.toList());
                target.setRules(StringUtils.join(ruleList, ","));
            }
        });
        return ResPage.get(adminGroupPage);
    }

    /**
     * 新增与修改用户组
     */
    public boolean saveOrUpdateUserGroup(SaveOrUpdateUserGroupReqDto reqDto) {
        BaseParams.HeaderInfo headerInfo = ThreadHeaderLocalData.HEADER_INFO_THREAD_LOCAL.get();
        var admin = adminCache.getAdminInfoById(headerInfo.getId());
        //ID为空时，新增记录
        var now = DateUtils.getCurrentTime();
        var adminGroup = BeanConvertUtils.copyProperties(reqDto, AuthAdminGroup::new);
        adminGroup.setUpdatedAt(now);
        adminGroup.setOperateId(headerInfo.getId());
        Optional.ofNullable(reqDto.getTitle()).ifPresent(title -> {
            var count = authAdminGroupServiceImpl.lambdaQuery().
                    eq(AuthAdminGroup::getTitle, title).count();
            if (count > 0) {
                throw new BusinessException(CodeInfo.ADMIN_GROUP_EXIST);
            }
        });
        if (Objects.isNull(reqDto.getId())) {
            adminGroup.setPid(admin.getAdminGroupId());
            adminGroup.setParent(headerInfo.getId());
            adminGroup.setCreatedAt(now);
            return authAdminGroupServiceImpl.save(adminGroup);
        }

        //修改赋权时，同时修改角色的权限
        if (StringUtils.isNotEmpty(reqDto.getRules())) {
            var groupList = authGroupServiceImpl.lambdaQuery()
                    .eq(AuthGroup::getAdminGroupId, reqDto.getId())
                    .list();
            groupList.forEach(group -> {
                if (StringUtils.isNotEmpty(group.getRules())) {
                    var updateRules = Arrays.asList(reqDto.getRules().split(","));
                    var groupRules = Arrays.stream(group.getRules().split(","))
                            .filter(updateRules::contains)
                            .collect(Collectors.toList());
                    group.setRules(StringUtils.join(groupRules, ","));
                    jedisUtil.del(Constant.PERMISSION_LIST);
                }
            });
            authGroupServiceImpl.updateBatchById(groupList);
            jedisUtil.hdel(KeyConstant.ADMIN_GROUP_ID_HASH, reqDto.getId() + "");
        } else {
            //代理组不能修改
            if (reqDto.getId() == 2) {
                throw new BusinessException(CodeInfo.AGENT_GROUP_NO_UPDATE);
            }
        }
        return authAdminGroupServiceImpl.updateById(adminGroup);
    }

    /**
     * 删除用户组
     */
    public boolean deleteUserGroup(DeleteUserGroupReqDto reqDto) {
        var count = authGroupServiceImpl.lambdaQuery()
                .eq(AuthGroup::getStatus, 1)
                .eq(AuthGroup::getAdminGroupId, reqDto.getId()).count();
        if (count > 0) {
            throw new BusinessException(CodeInfo.ADMIN_GROUP_DELETE_VERIFICATION);
        }
        jedisUtil.hdel(KeyConstant.ADMIN_GROUP_ID_HASH, reqDto.getId() + "");
        return authAdminGroupServiceImpl.removeById(reqDto.getId());
    }

    /**
     * 获取全部的用户组
     */
    public List<UserGroupParameter.AllAdminGroupListResDto> allAdminGroupList() {
        BaseParams.HeaderInfo headerInfo = ThreadHeaderLocalData.HEADER_INFO_THREAD_LOCAL.get();
        var admin = adminCache.getAdminInfoById(headerInfo.getId());
        var adminGroupList = authAdminGroupServiceImpl.lambdaQuery().eq(AuthAdminGroup::getStatus, 1).list();
        var set = new HashSet<Integer>();
        set.add(admin.getAdminGroupId());
        var pidMap = adminGroupList.stream().collect(Collectors.groupingBy(AuthAdminGroup::getPid));
        getAuthAdminGroupSelfChild(pidMap, admin.getAdminGroupId(), set);
        //超级可见全部，系统查看除超级管理员，其他可见自身与下级用户
        adminGroupList = adminGroupList.stream().filter(x ->
                adminPermissionServiceImpl.isVisible(admin, x.getId()) || set.contains(x.getId()))
                .collect(Collectors.toList());
        return BeanConvertUtils.copyListProperties(adminGroupList, UserGroupParameter.AllAdminGroupListResDto::new);
    }

    /**
     * 获取下级uid
     *
     * @param pidMap 分组Map
     * @param pid    父类ID
     * @param ids    下级集合
     * @return
     */
    public HashSet<Integer> getAuthAdminGroupSelfChild(Map<Integer, List<AuthAdminGroup>> pidMap, Integer pid, HashSet<Integer> ids) {
        var list = pidMap.get(pid);
        if (!CollectionUtils.isEmpty(list)) {
            ids.add(pid);
            ids.addAll(list.stream().map(AuthAdminGroup::getId).collect(Collectors.toList()));
            list.forEach(admin -> getAuthAdminGroupSelfChild(pidMap, admin.getId(), ids));
        }
        return ids;
    }

    /**
     * 获取全部的角色
     */
    public List<UserGroupParameter.AllRoleListResDto> allRoleList() {
        BaseParams.HeaderInfo headerInfo = ThreadHeaderLocalData.HEADER_INFO_THREAD_LOCAL.get();
        var admin = adminCache.getAdminInfoById(headerInfo.getId());
        var list = authGroupServiceImpl.lambdaQuery().eq(AuthGroup::getStatus, 1).list();
        var authGroupAccess = authGroupAccessServiceImpl.getOne(new LambdaQueryWrapper<AuthGroupAccess>().eq(AuthGroupAccess::getUid, admin.getId())
                , false);
        var set = new HashSet<Integer>();
        if (Objects.nonNull(authGroupAccess)) {
            set.add(authGroupAccess.getGroupId());
            var pidMap = list.stream().collect(Collectors.groupingBy(AuthGroup::getPid));
            adminPermissionServiceImpl.getGroupSelfChild(pidMap, authGroupAccess.getGroupId(), set);
        }
        //超级可见全部，系统查看除超级管理员，其他可见自身与下级用户
        list = list.stream().filter(x -> adminPermissionServiceImpl.isVisible(admin, x.getAdminGroupId()) || set.contains(x.getId())).collect(Collectors.toList());
        return BeanConvertUtils.copyListProperties(list, UserGroupParameter.AllRoleListResDto::new);
    }


    /**
     * 获取googleSecret
     *
     * @return UserGroupParameter.GenerateGoogleSecretDto
     */
    public UserGroupParameter.GenerateGoogleSecretDto generateGoogleSecret() {
        return UserGroupParameter.GenerateGoogleSecretDto.builder()
                .secret(GoogleAuthenticator.getRandomSecretKey())
                .build();
    }
}
