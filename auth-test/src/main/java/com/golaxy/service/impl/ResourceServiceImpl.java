package com.golaxy.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.golaxy.mapper.CasMapper;
import com.golaxy.service.ResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author lihang
 */
@Service
@Slf4j
public class ResourceServiceImpl implements ResourceService {
    @Resource
    CasMapper casMapper;


    public static final String newAppId = "4bae96940032961c23975354e332b24f";

    @Override
    public void copyResource() {
        String oldAppId = "b40fd5c1575f87a40eb0c8c2a1303740";
        String newAppId = "4bae96940032961c23975354e332b24f";

        // 重新计算pid 查询数据库所有数据
        Map<String, HashMap<String, String>> all = casMapper.getAllResourceByAppId(oldAppId);

        Set<String> ids = all.keySet();
        HashMap<String, String> idMapping = new HashMap<>();
        for (String id : ids) {
            idMapping.put(id, UUID.randomUUID().toString().replace("-", ""));
        }

        List<String> childIds = new ArrayList<>();
        childIds.add("bb43360991de866afb55765b0bcecf42");
        childIds.add("7f559ff8df6bf3448fb6cf1082d6de36");



        List<Map<String, Object>> allList = new ArrayList<>();
        List<Map<String, Object>> list = casMapper.getAllResourceByAppIdAndId(oldAppId, childIds);

        String pid = "fcd89bb2af8dd4d00ed7ff1b13c38b61";
        String pids = "0,fcd89bb2af8dd4d00ed7ff1b13c38b61,";

        for (Map<String, Object> map : list) {
            String id = map.get("id").toString();
            map.put("pids", pids);
            getResources(map, oldAppId, id, idMapping, allList);
            map.put("id", idMapping.get(id).toString());
            map.put("pid", pid);
            map.put("app_id", newAppId);
            allList.add(map);
        }

        log.info(JSONObject.toJSONString(allList));

        casMapper.insertBatch(allList);


    }

    public void getResources(Map<String, Object> map, String appId, String pid, HashMap<String, String> idMapping, List<Map<String, Object>> allList) {

        String pids = map.get("pids").toString();
        List<Map<String, Object>> list = casMapper.getAllResourceByAppIdAndPid(appId, pid);

        //map.put("child", list);
        for (Map<String, Object> childMap : list) {
            String id = childMap.get("id").toString();
            getResources(childMap, appId, id, idMapping, allList);
            childMap.put("pids", pids + idMapping.get(pid).toString() + ",");
            childMap.put("pid", idMapping.get(pid).toString());
            childMap.put("id", idMapping.get(id).toString());
            childMap.put("app_id", newAppId);
            allList.add(childMap);
        }
    }
}
