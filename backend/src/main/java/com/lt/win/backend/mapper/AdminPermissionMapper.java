package com.lt.win.backend.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface AdminPermissionMapper {
    List<Map<String, Object>> adminList(@Param("map") Map<String, Object> map);

    List<Map<String, Object>> getauthGroup(@Param("map") Map<String, Object> map);

    void addAdmin(@Param("map") Map<String, Object> map);

    void addAuthGroupAccess(@Param("map") Map<String, Object> map);

    void updateAdmin(@Param("map") Map<String, Object> map);

    void updateGroupAccess(@Param("map") Map<String, Object> map);

    void updateAdminStatus(@Param("map") Map<String, Object> map);

    void deleteAdmin(@Param("map") Map<String, Object> map);

    Map<String, Object> getAdminByUsername(@Param("map") Map<String, Object> map);

    List<Map<String, Object>> ruleList(@Param("map") Map<String, Object> map);

    void updateRule(@Param("map") Map<String, Object> map);

    void insertRule(@Param("map") Map<String, Object> map);

    void deleteRule(@Param("map") Map<String, Object> map);

    void updateAuthGroup(@Param("map") Map<String, Object> map);

}
