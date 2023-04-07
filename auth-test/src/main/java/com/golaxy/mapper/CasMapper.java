package com.golaxy.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@DS("auth")
@Mapper
public interface CasMapper {


    /**
     *  根据appId查询所有资源
     * @param appId
     * @return
     */
    @MapKey("id")
    Map<String, HashMap<String, String>> getAllResourceByAppId(@Param("appId") String appId);

    /**
     *  查询子资源
     * @param appId
     * @param pid
     * @return
     */
    List getAllResourceByAppIdAndPid(@Param("appId") String appId, @Param("pid") String pid);

    List<Map<String, Object>> getAllResourceByAppIdAndId(@Param("appId") String appId, @Param("ids") List<String> ids);

    void insertBatch(@Param("list") List<Map<String, Object>> allList);
}
