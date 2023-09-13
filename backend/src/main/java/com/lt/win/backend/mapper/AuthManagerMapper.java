package com.lt.win.backend.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface AuthManagerMapper {
    Map<String, Object> queryUserRule(@Param("map") Map<String, Object> map);

    void updateUserRule(@Param("map") Map<String, Object> map);

    String queryRulesByRoleId(@Param("map") Map<String, Object> map);

    Map<String, Object> queryRulesByUid(@Param("map") Map<String, Object> map);

    List<Map<String, Object>> queryAllRule(@Param("map") Map<String, Object> map);

    List<Map<String, Object>> queryMenuId();

    List<Map<String, Object>> queryAllRuleTitle(@Param("map") Map<String, Object> map);

    void insertRole(@Param("map") Map<String, Object> map);

    void updateRole(@Param("map") Map<String, Object> map);

    void deleteRole(@Param("map") Map<String, Object> map);

    Map<String, Object> queryRepair(@Param("map") Map<String, Object> map);
}
