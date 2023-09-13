package com.lt.win.backend.service.impl;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lt.win.backend.mapper.AdminPermissionMapper;
import com.lt.win.backend.mapper.AuthManagerMapper;
import com.lt.win.dao.generator.po.Admin;
import com.lt.win.dao.generator.po.AuthAdminGroup;
import com.lt.win.dao.generator.po.AuthGroup;
import com.lt.win.dao.generator.po.AuthGroupAccess;
import com.lt.win.dao.generator.service.AdminService;
import com.lt.win.dao.generator.service.AuthAdminGroupService;
import com.lt.win.dao.generator.service.AuthGroupAccessService;
import com.lt.win.dao.generator.service.AuthGroupService;
import com.lt.win.service.cache.KeyConstant;
import com.lt.win.service.cache.redis.AdminCache;
import com.lt.win.service.common.Constant;
import com.lt.win.service.exception.BusinessException;
import com.lt.win.service.io.dto.BaseParams;
import com.lt.win.service.thread.ThreadHeaderLocalData;
import com.lt.win.utils.*;
import com.lt.win.utils.components.response.CodeInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.*;
import java.util.stream.Collectors;

import static com.alibaba.fastjson.JSON.parseArray;
import static com.lt.win.utils.I18nUtils.getLocale;

/**
 * @Description :
 * @Date 2020/3/16 10:28
 * @Created by WELLS
 */
@Slf4j
@Service
public class AdminPermissionServiceImpl {
    @Resource
    private JedisUtil jedisUtil;
    @Autowired
    private AdminPermissionMapper adminPermissionMapper;
    @Autowired
    private AdminService adminServiceImpl;
    @Resource
    private AuthAdminGroupService authAdminGroupServiceImpl;
    @Autowired
    private AuthManagerMapper authManagerMapper;
    @Autowired
    private AdminCache adminCache;
    @Autowired
    private AuthGroupService authGroupServiceImpl;
    @Autowired
    private AuthManagerServiceImpl authManagerServiceImpl;
    @Autowired
    private AuthGroupAccessService authGroupAccessServiceImpl;

    public static final String TITLE = "title";

    /**
     * 管理信息列表查询
     */
    public JSONObject adminList(Map<String, Object> parMap) {
        BaseParams.HeaderInfo headerInfo = ThreadHeaderLocalData.HEADER_INFO_THREAD_LOCAL.get();
        var admin = adminCache.getAdminInfoById(headerInfo.getId());
        var adminGroupMap = authAdminGroupServiceImpl.list().stream().collect(Collectors.toMap(AuthAdminGroup::getId, AuthAdminGroup::getTitle));
        var set = new HashSet<Integer>();
        set.add(headerInfo.id);
        var adminList = adminServiceImpl.lambdaQuery().eq(Admin::getStatus, 10).list();
        var pidMap = adminList.stream().collect(Collectors.groupingBy(Admin::getParent));
        getAdminSelfChild(pidMap, headerInfo.id, set);
        List<Map<String, Object>> list = adminPermissionMapper.adminList(parMap);
        if (!CollectionUtils.isEmpty(list)) {
            list = list.stream()
                    //超级可见全部，系统查看除超级管理员，其他可见自身与下级用户
                    .filter(x -> isVisible(admin, Integer.parseInt(x.get("adminGroupId") + "")) || set.contains(Integer.parseInt(x.get("id") + "")))
                    .map(user -> {
                        var adminGroupId = user.get("adminGroupId") + "";

                        var parentId = user.get("parent") + "";
                        var parentAdmin = adminCache.getAdminInfoById(Integer.parseInt(parentId));
                        var parentName = Constant.SUPER_ADMIN.equals(Optional.ofNullable(parentAdmin).map(x -> x.getAdminGroupId() + "").orElse("")) ?
                                "--" : Optional.ofNullable(parentAdmin).map(Admin::getUsername).orElse("");
                        user.put("parent_username", parentName);

                        var operateId = user.get("operateId") + "";
                        var operateAdmin = adminCache.getAdminInfoById(Integer.parseInt(operateId));
                        var operateName = Constant.SUPER_ADMIN.equals(Optional.ofNullable(operateAdmin).map(x -> x.getAdminGroupId() + "").orElse("")) ?
                                "--" : Optional.ofNullable(operateAdmin).map(Admin::getUsername).orElse("");

                        user.put("operateName", operateName);
                        user.put("adminGroupName", adminGroupMap.getOrDefault(Integer.parseInt(adminGroupId), ""));
                        return user;
                    })
                    .collect(Collectors.toList());
        }
        JSONArray jarray = parseArray(JSON.toJSONString(list));
        return DataTransformaUtils.jAtransformaPage(parMap, jarray);
    }

    /**
     * 获取下级uid
     *
     * @param pidMap 分组Map
     * @param pid    父类ID
     * @param ids    下级集合
     * @return
     */
    public HashSet<Integer> getAdminSelfChild(Map<Integer, List<Admin>> pidMap, Integer pid, HashSet<Integer> ids) {
        var list = pidMap.get(pid);
        if (!CollectionUtils.isEmpty(list)) {
            ids.add(pid);
            ids.addAll(list.stream().map(Admin::getId).collect(Collectors.toList()));
            list.forEach(admin -> getAdminSelfChild(pidMap, admin.getId(), ids));
        }
        return ids;
    }

    /**
     * 校验用户名
     */
    public JSONObject getAdminByUsername(Map<String, Object> parMap) {
        Map<String, Object> map = adminPermissionMapper.getAdminByUsername(parMap);
        if (map != null && !map.isEmpty()) {
            throw new BusinessException(CodeInfo.ACCOUNT_EXISTS);
        }
        return DataTransformaUtils.transformaJO(map);
    }

    /**
     * 角色列表查询
     */
    public JSONObject getauthGroup(Map<String, Object> parMap) {
        BaseParams.HeaderInfo headerInfo = ThreadHeaderLocalData.HEADER_INFO_THREAD_LOCAL.get();
        var admin = adminCache.getAdminInfoById(headerInfo.getId());
        var authGroupAccess = authGroupAccessServiceImpl.getOne(new LambdaQueryWrapper<AuthGroupAccess>()
                        .eq(AuthGroupAccess::getUid, admin.getId())
                , false);
        var set = new HashSet<Integer>();
        if (Objects.nonNull(authGroupAccess)) {
            set.add(authGroupAccess.getGroupId());
            var groupList = authGroupServiceImpl.lambdaQuery().eq(AuthGroup::getStatus, 1).list();
            var pidMap = groupList.stream().collect(Collectors.groupingBy(AuthGroup::getPid));
            getGroupSelfChild(pidMap, authGroupAccess.getGroupId(), set);
        }
        List<Map<String, Object>> list = adminPermissionMapper.getauthGroup(parMap);
        var relist = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)) {
            //获取全量树的PID
            authManagerServiceImpl.queryAllRuleTitle(new HashMap<>());
            relist.addAll(list.stream().filter(x ->
                    //超级可见全部，系统查看除超级管理员，其他可见自身与下级用户
                    isVisible(admin, Integer.parseInt(x.get("adminGroupId") + "")) || set.contains(Integer.parseInt(x.get("id") + "")))
                    .map(group -> {
                        var operateId = group.get("operateId") + "";
                        var operateAdmin = adminCache.getAdminInfoById(Integer.parseInt(operateId));
                        var operateName = Constant.SUPER_ADMIN.equals(Optional.ofNullable(operateAdmin).map(x -> x.getAdminGroupId() + "").orElse("")) ?
                                "--" : Optional.ofNullable(operateAdmin).map(Admin::getUsername).orElse("");
                        group.put("operateName", operateName);
                        var rules = group.get("rules");
                        if (Objects.nonNull(rules)) {
                            var ruleList = Arrays.asList(String.valueOf(rules).split(",")).stream()
                                    .filter(ele -> !AuthManagerServiceImpl.PID_LIST.get().contains(ele))
                                    .collect(Collectors.toList());
                            group.put("rules", StringUtils.join(ruleList, ","));
                        }
                        return group;
                    }).collect(Collectors.toList()));
        }
        JSONArray jarray = parseArray(JSON.toJSONString(relist));
        return DataTransformaUtils.jAtransformaPage(parMap, jarray);
    }

    /**
     * 获取下级uid
     *
     * @param pidMap 分组Map
     * @param pid    父类ID
     * @param ids    下级集合
     * @return
     */
    public HashSet<Integer> getGroupSelfChild(Map<Integer, List<AuthGroup>> pidMap, Integer pid, HashSet<Integer> ids) {
        var list = pidMap.get(pid);
        if (!CollectionUtils.isEmpty(list)) {
            ids.add(pid);
            ids.addAll(list.stream().map(AuthGroup::getId).collect(Collectors.toList()));
            list.forEach(admin -> getGroupSelfChild(pidMap, admin.getId(), ids));
        }
        return ids;
    }

    /**
     * 添加管理信息
     *
     * @param parMap
     * @return
     */
    @Transactional
    public JSONObject addAdmin(Map<String, Object> parMap) {
        getAdminByUsername(parMap);
        JSONObject jo = new JSONObject();
        BaseParams.HeaderInfo headerInfo = ThreadHeaderLocalData.HEADER_INFO_THREAD_LOCAL.get();
        //username, password_hash, api_token, parent, status
        String api_token = UUID.randomUUID().toString().replace("-", "") + "_" + System.currentTimeMillis() / 1000;
        try {
            String password_hash = parMap.getOrDefault("password_hash", "").toString();
            parMap.put("password_hash", BCrypt.with(BCrypt.Version.VERSION_2Y).hashToString(13, password_hash.toCharArray()));
        } catch (Exception e) {
            log.info("密码解析异常" + e.getMessage());
        }
        parMap.put("api_token", api_token);
        parMap.put("parent", headerInfo.getId());
        parMap.put("status", "10");
        parMap.put("operate_id", headerInfo.getId());
        var group = authGroupServiceImpl.getById(Integer.parseInt(parMap.get("group_id") + ""));
        parMap.put("adminGroupId", group.getAdminGroupId());

        adminPermissionMapper.addAdmin(parMap);
        Map<String, Object> map = adminPermissionMapper.getAdminByUsername(parMap);
        String uid = String.valueOf(map.get("id"));
        parMap.put("uid", uid);
        adminPermissionMapper.addAuthGroupAccess(parMap);//加入字段uid,group_id
        jo.put("msg_zh", GlobalVariableUtils.INSERT_SUCCESS);
        return jo;
    }

    /**
     * 修改管理信息
     */
    @Transactional
    public JSONObject updateAdmin(Map<String, Object> parMap) {
        JSONObject jo = new JSONObject();
        //验证修改的用户是否存在
        var count = adminServiceImpl.lambdaQuery()
                .ne(Admin::getId, parMap.get("uid"))
                .eq(Admin::getUsername, parMap.get("username"))
                .count();
        if (count > 0) {
            throw new BusinessException(CodeInfo.ACCOUNT_EXISTS);
        }
        BaseParams.HeaderInfo headerInfo = ThreadHeaderLocalData.HEADER_INFO_THREAD_LOCAL.get();
        //password_hash不传参默认为""
        String password_hash = parMap.get("password_hash") == null || parMap.get("password_hash").toString().length() == 0 ? "" :
                BCrypt.with(BCrypt.Version.VERSION_2Y).hashToString(13, parMap.get("password_hash").toString().toCharArray());
        parMap.put("password_hash", password_hash);
        //修改自身密码
        var originalPassword = parMap.get("originalPassword");
        if (Objects.nonNull(originalPassword)) {
            var admin = adminServiceImpl.getById(headerInfo.id);
            if (Boolean.FALSE.equals(PasswordUtils.validatePasswordHash(originalPassword.toString(), admin.getPasswordHash()))) {
                throw new BusinessException(CodeInfo.PASSWORD_INVALID);
            }
            parMap.put("uid", headerInfo.id);
            adminPermissionMapper.updateAdmin(parMap);
            jo.put("msg_zh", GlobalVariableUtils.UPDATE_SUCCESS);
            return jo;
        }
        parMap.put("api_token", TextUtils.generateApiToken());
        parMap.put("operate_id", headerInfo.getId());
        if (Objects.nonNull(parMap.get("role_id"))) {
            var group = authGroupServiceImpl.getById(Integer.parseInt(parMap.get("role_id") + ""));
            parMap.put("admin_group_id", group.getAdminGroupId() + "");
        }
        adminPermissionMapper.updateAdmin(parMap);
        if (Objects.isNull(parMap.get("group_id"))) {
            Map<String, Object> accessMap = new HashMap<>();
            accessMap.put("uid", parMap.get("uid"));
            accessMap.put("group_id", parMap.get("role_id"));
            adminPermissionMapper.addAuthGroupAccess(accessMap);
        } else if (Objects.isNull(parMap.get("role_id"))) {
            adminPermissionMapper.deleteAdmin(parMap);
        } else {
            adminPermissionMapper.updateGroupAccess(parMap);
        }
        if (StringUtils.isNotEmpty(password_hash)) {
            jedisUtil.hdel(KeyConstant.ADMIN_TOKEN_HASH, parMap.get("uid") + "");
        }
        //删除用户的补充菜单
        String update_flag = Optional.ofNullable(parMap.get("update_flag")).orElse("").toString();
        //1为发生改变，0为未发生改变
        if ("1".equals(update_flag)) {
            Map<String, Object> userRuleMap = new HashMap<>();
            userRuleMap.put("uid", parMap.get("uid"));
            userRuleMap.put("menuId", "");
            authManagerMapper.updateUserRule(userRuleMap);
        }
        jo.put("msg_zh", GlobalVariableUtils.UPDATE_SUCCESS);
        jedisUtil.del(Constant.PERMISSION_LIST);
        jedisUtil.hdel(KeyConstant.ADMIN_INFO_ID_HASH, parMap.get("uid") + "");
        return jo;
    }


    /**
     * 删除管理信息
     *
     * @param parMap
     * @return
     */
    @Transactional
    public JSONObject deteleAdmin(Map<String, Object> parMap) {
        JSONObject jo = new JSONObject();
        parMap.put("status", "0");
        adminPermissionMapper.updateAdminStatus(parMap);
        authGroupAccessServiceImpl.remove(new LambdaQueryWrapper<AuthGroupAccess>()
                .eq(AuthGroupAccess::getUid, Integer.parseInt(parMap.get("uid") + "")));
        // 删除token
        jedisUtil.hdel(KeyConstant.ADMIN_TOKEN_HASH, parMap.get("uid") + "");
        jo.put("msg_zh", GlobalVariableUtils.DELETE_SUCCESS);
        return jo;
    }

    /**
     * 查询权限配置
     *
     * @param parMap
     * @return
     */
    @Transactional
    public JSONObject ruleList(Map<String, Object> parMap) {
        List<Map<String, Object>> list = new ArrayList<>();
        Locale locale = getLocale();
        if (parMap.get(TITLE) != null && !locale.toString().equals("zh_CN")) {
            var titleList = new I18nUtils().resolveCode(locale, String.valueOf(parMap.get(TITLE)));
            parMap.remove(TITLE);
            if (!titleList.isEmpty()) {
                List<Map<String, Object>> authlist = adminPermissionMapper.ruleList(parMap);
                for (Object j : titleList) {
                    List<Map<String, Object>> collect = authlist.stream().filter(x -> x.containsValue(j)).collect(Collectors.toList());
                    if (!collect.isEmpty()) {
                        list.add(collect.get(0));
                    }
                }
            }
        } else {
            list = adminPermissionMapper.ruleList(parMap);
        }
        //通过角色组过滤数据
        BaseParams.HeaderInfo headerInfo = ThreadHeaderLocalData.HEADER_INFO_THREAD_LOCAL.get();
        var admin = adminServiceImpl.getById(headerInfo.getId());
        var roleGroup = authAdminGroupServiceImpl.getById(admin.getAdminGroupId());
        var rules = Arrays.asList(Optional.ofNullable(roleGroup).map(AuthAdminGroup::getRules).orElse("").split(","));
        list = list.stream().filter(x ->
                admin.getAdminGroupId() == 0 || rules.contains(x.get("id") + "")).collect(Collectors.toList());
        JSONArray jarray = parseArray(JSON.toJSONString(list));
        return DataTransformaUtils.jAtransformaPage(parMap, jarray);
    }

    /**
     * 添加权限配置
     */
    public JSONObject insertRule(Map<String, Object> parMap) {
        JSONObject jo = new JSONObject();
        //icon, menu_name, title, pid
        // parMap.put("menu_name", parMap.get("controller_name") + "/" + parMap.get("method_name"));
        // parMap.put("status",1);
        adminPermissionMapper.insertRule(parMap);
        jo.put("msg_zh", GlobalVariableUtils.INSERT_SUCCESS);
        return jo;
    }

    /**
     * 修改权限配置
     *
     * @param parMap
     * @return
     */
    public JSONObject updateRule(Map<String, Object> parMap) {
        JSONObject jo = new JSONObject();
        //icon, menu_name, title, pid
        //parMap.put("menu_name", parMap.get("controller_name") + "/" + parMap.get("method_name"));
        adminPermissionMapper.updateRule(parMap);
        jo.put("msg_zh", GlobalVariableUtils.UPDATE_SUCCESS);
        jedisUtil.del(Constant.PERMISSION_LIST);
        return jo;
    }

    /**
     * 删除权限配置
     *
     * @param parMap
     * @return
     */
    @Transactional
    public JSONObject deleteRule(Map<String, Object> parMap) {
        JSONObject jo = new JSONObject();
        List<Map<String, Object>> groupList = adminPermissionMapper.getauthGroup(parMap);
        for (int i = 0; i < groupList.size(); i++) {
            String rules = String.valueOf(groupList.get(i).get("rules"));
            List<String> ruleList = Arrays.asList(rules.split(","));
            String newRule = StringUtils.join(ruleList.toArray(), ",");
            Map<String, Object> groupMap = new HashMap<>();
            groupMap.put("id", String.valueOf(groupList.get(i).get("id")));
            groupMap.put("rules", newRule);
            adminPermissionMapper.updateAuthGroup(groupMap);
        }
        parMap.put("status", "0");
        adminPermissionMapper.deleteRule(parMap);
        jo.put("msg_zh", GlobalVariableUtils.DELETE_SUCCESS);
        return jo;
    }


    /**
     * 超级管理员与系统管理员对数据可见判断
     * 超级管理员
     * 1.所有的数据都可见
     * 系统管理员
     * 1.过滤超级管理员，其他的数据都都可见
     * 2.系统管理员定义-》第一级的用户组，用户组的pid为0
     *
     * @param adminGroupId 用户组ID
     * @return boolean
     */
    public boolean isVisible(Admin admin, Integer adminGroupId) {
        if (admin.getAdminGroupId() != 0) {
            var adminGroup = adminCache.getAuthAdminGroupById(admin.getAdminGroupId());
            return Optional.ofNullable(adminGroup).map(x -> x.getPid() == 0).orElse(false) && adminGroupId != 0
                    || admin.getAdminGroupId().equals(adminGroupId);
        }
        return admin.getAdminGroupId() == 0;
    }
}
