package com.lt.win.backend.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.lt.win.backend.io.dto.AgentDto;
import com.lt.win.backend.io.dto.UserGroupParameter;
import com.lt.win.backend.io.dto.UserGroupParameter.DeleteUserGroupReqDto;
import com.lt.win.backend.io.dto.UserGroupParameter.SaveOrUpdateUserGroupReqDto;
import com.lt.win.backend.io.dto.UserGroupParameter.UserGroupListReqDto;
import com.lt.win.backend.io.dto.UserGroupParameter.UserGroupListResDto;
import com.lt.win.backend.service.impl.AdminPermissionServiceImpl;
import com.lt.win.backend.service.impl.AuthManagerServiceImpl;
import com.lt.win.utils.components.pagination.ReqPage;
import com.lt.win.utils.components.pagination.ResPage;
import com.lt.win.utils.components.response.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @Description :
 * @Date 2020/3/13 20:15
 * @Created by WELLS
 */

@Slf4j
@RestController
@ApiSupport(author = "wells")
@RequestMapping("/v1/authManager")
@Api(tags = "权限管理", value = "AuthManagerController")
public class AuthManagerController {
    @Autowired
    AuthManagerServiceImpl authManagerServiceImpl;
    @Autowired
    AdminPermissionServiceImpl adminPermissionServiceImpl;

    @PostMapping(value = "/adminList")
    @ApiOperation(value = "管理信息列表查询")
    public Result<Object> adminList(@Validated @RequestBody Map<String, Object> map) {
        JSONObject jsonObject = adminPermissionServiceImpl.adminList(map);
        return Result.ok(jsonObject);
    }

    @PostMapping(value = "/getauthGroup")
    @ApiOperation(value = "角色列表查询")
    public Result<Object> getauthGroup(@Validated @RequestBody Map<String, Object> map) {
        return Result.ok(adminPermissionServiceImpl.getauthGroup(map));
    }

    @PostMapping(value = "/addAdmin")
    @ApiOperation(value = "添加管理信息")
    public Result<Object> addAdmin(@Validated @RequestBody Map<String, Object> map) {
        JSONObject jsonObject = adminPermissionServiceImpl.addAdmin(map);
        return Result.ok(jsonObject);
    }

    @PostMapping(value = "/updateAdmin")
    @ApiOperation(value = "修改管理信息")
    public Result<Object> updateAdmin(@Validated @RequestBody Map<String, Object> map) {
        JSONObject jsonObject = adminPermissionServiceImpl.updateAdmin(map);
        return Result.ok(jsonObject);
    }

    @PostMapping(value = "/deteleAdmin")
    @ApiOperation(value = "删除管理信息")
    public Result<Object> deteleAdmin(@Validated @RequestBody Map<String, Object> map) {
        JSONObject jsonObject = adminPermissionServiceImpl.deteleAdmin(map);
        return Result.ok(jsonObject);
    }

    @PostMapping(value = "/getAdminByUsername")
    @ApiOperation(value = "校验用户名")
    public Result<Object> getAdminByUsername(@Validated @RequestBody Map<String, Object> map) {
        JSONObject jsonObject = adminPermissionServiceImpl.getAdminByUsername(map);
        return Result.ok(jsonObject);
    }

    @PostMapping(value = "/ruleList")
    @ApiOperation(value = "查询权限配置")
    public Result<Object> ruleList(@Validated @RequestBody Map<String, Object> map) {
        JSONObject jsonObject = adminPermissionServiceImpl.ruleList(map);
        return Result.ok(jsonObject);
    }

    @PostMapping(value = "/insertRule")
    @ApiOperation(value = "添加权限配置")
    public Result<Object> insertRule(@Validated @RequestBody Map<String, Object> map) {
        JSONObject jsonObject = adminPermissionServiceImpl.insertRule(map);
        return Result.ok(jsonObject);
    }

    @PostMapping(value = "/updateRule")
    @ApiOperation(value = "修改权限配置")
    public Result<Object> updateRule(@Validated @RequestBody Map<String, Object> map) {
        JSONObject jsonObject = adminPermissionServiceImpl.updateRule(map);
        return Result.ok(jsonObject);
    }

    @PostMapping(value = "/deleteRule")
    @ApiOperation(value = "删除权限配置")
    public Result<Object> deleteRule(@Validated @RequestBody Map<String, Object> map) {
        JSONObject jsonObject = adminPermissionServiceImpl.deleteRule(map);
        return Result.ok(jsonObject);
    }

    @PostMapping(value = "/loadAuthRuleAuthManager")
    @ApiOperation(value = "根据uid加载对应角色的授权模块列表")
    public Result<Object> loadAuthRule(@Validated @RequestBody Map<String, Object> map) {
        JSONObject jsonObject = authManagerServiceImpl.loadAuthRule(map);
        return Result.ok(jsonObject);
    }

    @PostMapping(value = "/queryAllRuleTitleManager")
    @ApiOperation(value = "获取权限分配全量树结构")
    public Result<Object> queryAllRuleTitle(@Validated @RequestBody Map<String, Object> map) {
        JSONObject jsonObject = authManagerServiceImpl.queryAllRuleTitle(map);
        return Result.ok(jsonObject);
    }

    @PostMapping(value = "/queryHasAuthByRoleIdAuthManager")
    @ApiOperation(value = "根据roleId获取当前角色已授权的id列表")
    public Result<Object> queryHasAuthByRoleId(@Validated @RequestBody Map<String, Object> map) {
        String[] strArr = authManagerServiceImpl.queryHasAuthByRoleId(map);
        return Result.ok(strArr);
    }

    @PostMapping(value = "/insertRoleAuthManager")
    @ApiOperation(value = "新增角色")
    public Result<Object> insertRole(@Validated @RequestBody Map<String, Object> map) {
        JSONObject jsonObject = authManagerServiceImpl.insertRole(map);
        return Result.ok(jsonObject);
    }

    @PostMapping(value = "/updateRoleAuthManager")
    @ApiOperation(value = "修改角色")
    public Result<Object> updateRole(@Validated @RequestBody Map<String, Object> map) {
        JSONObject jsonObject = authManagerServiceImpl.updateRole(map);
        return Result.ok(jsonObject);
    }

    @PostMapping(value = "/deleteRoleAuthManager")
    @ApiOperation(value = "删除角色")
    public Result<Object> deleteRole(@Validated @RequestBody Map<String, Object> map) {
        JSONObject jsonObject = authManagerServiceImpl.deleteRole(map);
        return Result.ok(jsonObject);
    }

    @PostMapping(value = "/queryHasAuthByUidAuthManager")
    @ApiOperation(value = "根据uid获取当前角色已授权的id列表")
    public Result<Object> queryHasAuthByUid(@Validated @RequestBody Map<String, Object> map) {
        String[] strArr = authManagerServiceImpl.queryHasAuthByUid(map);
        return Result.ok(strArr);
    }

    @PostMapping(value = "/updateUserRule")
    @ApiOperation(value = "修改用户维度授权模块")
    public Result<Object> updateUserRule(@Validated @RequestBody Map<String, Object> map) {
        JSONObject jsonObject = authManagerServiceImpl.updateUserRule(map);
        return Result.ok(jsonObject);
    }

    @PostMapping(value = "/queryUserRule")
    @ApiOperation(value = "用户补充菜单列表查询")
    public Result<Object> queryUserRule(@Validated @RequestBody Map<String, Object> map) {
        JSONObject jsonObject = authManagerServiceImpl.queryUserRule(map);
        return Result.ok(jsonObject);
    }

    @PostMapping(value = "/agentList")
    @ApiOperation(value = "代理用户列表", notes = "代理用户列表")
    public Result<List<AgentDto.AgentListResDto>> agentList() {
        return Result.ok(authManagerServiceImpl.agentList());
    }


    @PostMapping(value = "/userGroupList")
    @ApiOperation(value = "用户组列表", notes = "用户组列表")
    public Result<ResPage<UserGroupListResDto>> userGroupList(@Valid @RequestBody ReqPage<UserGroupListReqDto> reqDto) {
        return Result.ok(authManagerServiceImpl.userGroupList(reqDto));
    }

    @PostMapping(value = "/saveOrUpdateUserGroup")
    @ApiOperation(value = "新增或修改用户组", notes = "新增或修改用户组")
    public Result<Boolean> saveOrUpdateUserGroup(@Valid @RequestBody SaveOrUpdateUserGroupReqDto reqDto) {
        return Result.ok(authManagerServiceImpl.saveOrUpdateUserGroup(reqDto));
    }

    @PostMapping(value = "/deleteUserGroup")
    @ApiOperation(value = "删除用户组", notes = "删除用户组")
    public Result<Boolean> deleteUserGroup(@Valid @RequestBody DeleteUserGroupReqDto reqDto) {
        return Result.ok(authManagerServiceImpl.deleteUserGroup(reqDto));
    }

    @PostMapping(value = "/allAdminGroupList")
    @ApiOperation(value = "用户组下拉框", notes = "用户组下拉框")
    public Result<List<UserGroupParameter.AllAdminGroupListResDto>> allAdminGroupList() {
        return Result.ok(authManagerServiceImpl.allAdminGroupList());
    }

    @PostMapping(value = "/allRoleList")
    @ApiOperation(value = "角色下拉框", notes = "角色下拉框")
    public Result<List<UserGroupParameter.AllRoleListResDto>> allRoleList() {
        return Result.ok(authManagerServiceImpl.allRoleList());
    }

    @PostMapping(value = "/generateGoogleSecret")
    @ApiOperation(value = "生成谷歌秘钥", notes = "生成谷歌秘钥")
    public Result<UserGroupParameter.GenerateGoogleSecretDto> generateGoogleSecret() {
        return Result.ok(authManagerServiceImpl.generateGoogleSecret());
    }
}
