package com.lt.win.backend.io.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author : Wells
 * @Date : 2020-12-18 4:00 下午
 * @Description : 用户组参数
 */
public interface UserGroupParameter {
    @Data
    @ApiModel(value = "userGroupListReqDto", description = "用户组列表请求实例")
    class UserGroupListReqDto {
        @ApiModelProperty(name = "title", value = "用户组名称")
        private String title;
    }

    @Data
    @ApiModel(value = "userGroupListResDto", description = "用户组列表响应实例")
    class UserGroupListResDto {
        @ApiModelProperty(name = "id", value = "ID")
        private Integer id;
        @ApiModelProperty(name = "title", value = "用户组名称")
        private String title;
        @ApiModelProperty(name = "operateName", value = "操作人")
        private String operateName;
        @ApiModelProperty(name = "rules", value = "菜单ID集合")
        private String rules;
        @ApiModelProperty(name = "updatedAt", value = "操作时间")
        private Integer updatedAt;
    }

    @Data
    @ApiModel(value = "SaveOrUpdateUserGroupReqDto", description = "新增与修改用户组响应实例")
    class SaveOrUpdateUserGroupReqDto {
        @ApiModelProperty(name = "id", value = "ID")
        private Integer id;
        @ApiModelProperty(name = "title", value = "用户组名称")
        private String title;
        @ApiModelProperty(name = "rules", value = "菜单ID集合")
        private String rules;
    }

    @Data
    @ApiModel(value = "DeleteUserGroupReqDto", description = "删除用户组响应实例")
    class DeleteUserGroupReqDto {
        @NotNull(message = "ID不能为空")
        @ApiModelProperty(name = "id", value = "ID")
        private Integer id;
    }

    @Data
    @ApiModel(value = "AllAdminGroupListResDto", description = "用户组下拉框响应实例")
    class AllAdminGroupListResDto {
        @ApiModelProperty(name = "id", value = "ID")
        private Integer id;
        @ApiModelProperty(name = "title", value = "用户组名称")
        private String title;
    }

    @Data
    @ApiModel(value = "", description = "全部角色响应实例")
    class AllRoleListResDto {
        @ApiModelProperty(name = "id", value = "ID")
        private Integer id;
        @ApiModelProperty(name = "title", value = "角色名称")
        private String title;
        @ApiModelProperty(name = "adminGroupId", value = "角色ID")
        private Integer adminGroupId;
    }

    @Data
    @Builder
    @ApiModel(value = "", description = "谷歌秘钥响应请求实例")
    class GenerateGoogleSecretDto {
        @ApiModelProperty(name = "secret", value = "秘钥")
        private String secret;

    }
}
