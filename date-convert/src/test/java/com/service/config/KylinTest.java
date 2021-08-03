package com.service.config;


import org.junit.Test;
import org.junit.platform.commons.util.StringUtils;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

/**
 * @Auther: lihang
 * @Date: 2021-07-27 22:47
 * @Description:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class KylinTest {

    @Resource
    private JdbcTemplate kylinTemplate;

    @Test
    public void studentsGroupCounts() throws Exception {
        /*String dpl = "search * from dwd_jw_o_xsjbxx where xsdqztmc='在读'    ";
        dpl = DepartMentUtils.appendDepartMent(dpl, departmentDTO);
        dpl += " | count(1) as sl ";
        List<DplResult> list = dPLService.getDPLResult(dpl);*/
        String sql = "select count(*) from dwd_jw_o_xsjbxx where xsdqztmc='在读' ";
        //DepartMentUtils.appendSqlByDepartment(sql ,departmentDTO);
        Long count = kylinTemplate.queryForObject(sql, Long.class);
        List<DplResult> list = new ArrayList<>();
        DplResult result = new DplResult();
        result.put("sl",count==null?0:count);
        list.add(result);
        list.toString();
    }


    @Test
    public void studentsGroupSexCounts() throws Exception {
        /*String dpl = "search * from dwd_jw_o_xsjbxx where xsdqztmc='在读'  and xbmc is not null ";
        dpl = DepartMentUtils.appendDepartMent(dpl, departmentDTO);
        dpl += " | count(1) as sl group by xbmc,xbdm ";
        List<DplResult> list = dPLService.getDPLResult(dpl);
        return null;*/
        String sql = "select xbmc,xbdm,count(*) sl from dwd_jw_o_xsjbxx where xsdqztmc='在读'  and xbmc is not null group by xbmc,xbdm";
        List<Map<String, Object>> list = kylinTemplate.queryForList(sql);
        List<DplResult> dplResults = transformResult(list);
        System.out.println(dplResults);
    }

    @Test
    public void studentsGroupNationCounts() throws IOException {
        /*String dpl = "search * from dwd_jw_o_xsjbxx where xsdqztmc='在读' and  mzmc is not null ";
        dpl = DepartMentUtils.appendDepartMent(dpl, departmentDTO);
        dpl += " | count(1) as sl group by mzmc,mzdm ";
        List<DplResult> list = dPLService.getDPLResult(dpl);
        return list;*/
        String sql = "select mzmc,mzdm,count(*) sl from dwd_jw_o_xsjbxx where xsdqztmc='在读' and  mzmc is not null  group by mzmc,mzdm ";
        List<Map<String, Object>> list = kylinTemplate.queryForList(sql);
        List<DplResult> dplResults = transformResult(list);
        System.out.println(dplResults);
    }
    @Test
    public void studentsGroupStudentTypeCounts() throws IOException {
        /*String dpl = "search * from dwd_jw_o_xsjbxx where xsdqztmc='在读'   ";
        dpl = DepartMentUtils.appendDepartMent(dpl, departmentDTO);
        dpl += " | count(1) as sl group by xz ";
        List<DplResult> list = dPLService.getDPLResult(dpl);
        return list;*/
        String sql = "select xz,count(*) sl from dwd_jw_o_xsjbxx where xsdqztmc='在读' group by xz ";
        List<Map<String, Object>> list = kylinTemplate.queryForList(sql);
        List<DplResult> dplResults = transformResult(list);
        System.out.println(dplResults);
    }
    @Test
    public void studentsGroupPoliticalCounts() throws IOException {
       /* String dpl = "search * from dwd_jw_o_xsjbxx where xsdqztmc='在读'   ";
        dpl = DepartMentUtils.appendDepartMent(dpl, departmentDTO);
        dpl += "   | count(1) as sl group by zzmmmc,zzmmdm ";
        List<DplResult> list = dPLService.getDPLResult(dpl);
        return list;*/
        String sql = "select zzmmmc,zzmmdm,count(*) sl from dwd_jw_o_xsjbxx where xsdqztmc='在读' group by zzmmmc,zzmmdm ";
        List<Map<String, Object>> list = kylinTemplate.queryForList(sql);
        List<DplResult> dplResults = transformResult(list);
        System.out.println(dplResults);
    }
    @Test
    public void studentsGroupProvinceCounts() throws IOException {
        /*String dpl = "search * from dwd_jw_o_xsjbxx where xsdqztmc='在读'   ";
        dpl = DepartMentUtils.appendDepartMent(dpl, departmentDTO);
        if(StringUtils.isNotBlank(departmentDTO.getProvince())){
            dpl += " | count(1) as sl group by  city ";
        }else {
            dpl += " | count(1) as sl group by  province ";
        }
        List<DplResult> list = dPLService.getDPLResult(dpl);
        return list;*/
        String group = "province";

        String sql = "select "+ group +",count(*) sl from dwd_jw_o_xsjbxx where xsdqztmc='在读' group by "+ group;
        List<Map<String, Object>> list = kylinTemplate.queryForList(sql);
        List<DplResult> dplResults = transformResult(list);
        System.out.println(dplResults);
    }

    public static Map<String, Object> transformUpperCase(Map<String, Object> orgMap) {
        Map<String, Object> resultMap = new HashMap<>();
        if (orgMap == null || orgMap.isEmpty()) {
            return resultMap;
        }
        Set<String> keySet = orgMap.keySet();
        for (String key : keySet) {
            String newKey = key.toUpperCase();
            resultMap.put(newKey, orgMap.get(key));
        }
        return resultMap;
    }

    // 将map的Key全部转换为小写
    public static List<DplResult> transformResult(List<Map<String, Object>> list) {
        List<DplResult> result = new ArrayList<>();
        for (Map<String, Object> map : list) {
            DplResult dplResult = new DplResult();
            if (map == null || map.isEmpty()) {
                result.add(dplResult);
            }else {
                Set<String> keySet = map.keySet();
                for (String key : keySet) {
                    String newKey = key.toLowerCase();
                    dplResult.put(newKey, map.get(key));
                }
                result.add(dplResult);
            }

        }
        return result;
    }

    @Test
    public void getPopularConsumePortion() throws IOException {
        /*String dpl = "search * from dws_ykt_lsmxxx  where xh=xh    ";
        if(StringUtils.isNotBlank(departmentDTO.getStartTime())){
            dpl+=" and jysj>='"+departmentDTO.getStartTime()+"-01 00:00:00' ";
        }
        if(StringUtils.isNotBlank(departmentDTO.getEndTime())){
            dpl+=" and jysj<='"+departmentDTO.getEndTime()+"-28 00:00:00' ";
        }

        dpl = DepartMentUtils.appendDepartMent(dpl, departmentDTO);
        dpl += "  |sum(-jyje) as nums group by shmc    order by nums desc limit 10";
        List<DplResult> list = dPLService.getDPLResult(dpl);
*/
        DepartmentDTO dto = new DepartmentDTO();
        dto.setStartTime("2021-01");
        dto.setEndTime("2021-07");
        String sql = "select shmc, -sum(jyje) as nums from dws_ykt_lsmxxx where xh=xh ";
        if(StringUtils.isNotBlank(dto.getStartTime())){
            sql+=" and jysj>='"+dto.getStartTime()+"-01 00:00:00' ";
        }
        if(StringUtils.isNotBlank(dto.getEndTime())){
            sql+=" and jysj<='"+dto.getEndTime()+"-28 00:00:00' ";
        }
        //sql = DepartMentUtils.appendDepartMent(sql, departmentDTO);

        sql += " group by shmc order by nums desc limit 10 ";
        List<Map<String, Object>> list = kylinTemplate.queryForList(sql);
        List<DplResult> dplResults = transformResult(list);
        System.out.println(dplResults);
    }

}
