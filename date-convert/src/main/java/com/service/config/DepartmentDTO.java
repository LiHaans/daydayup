package com.service.config;

import lombok.Data;


/**
 * @Auther: liuysh
 * @Date: 2020/5/7 13:46
 * @Description:
 */
@Data
public class DepartmentDTO {
    //重电使用的参数















    //陈旧的将使用的部分上移
    private  String dwh;//单位号/学院号
    private  String dwmc;// 单位名称

    private  String zyh;// 专业号
    private  String zymc;// 专业名称

    private  String bh; // 班号
    private  String bj; // 班级

    private  String xbm; //性别码
    private  String xb; //性别(男女)
    private  String xbName; //性别(男女)
    private  String mzm; // 民族码
    private  String mz; // 民族
    private  String mzName; // 民族

    private  String xh; //学号
    private  String xm; //姓名
    private  String name;
    private  String rxnf;

    private String sznj; // 所在年级

    private String lymc; //楼宇名称
    private String szlc; //所在楼层
    private String fjh; //房间号
    private String apmac; //ap的mac地址

    private String startTime;
    private String endTime;
    private String recentMonth; //近几个月，参数

    private String jdFlag; // 绩点群体 参数为：优秀、良好、中等、一般、较差


    private String studenttype; //贫困生的类型
    private String expand2sftk; //就业数据是否特困
    private String topNum;

    private String xn; //学年

    private String province;
    private String city;
    private String zzmmm; // 政治面貌码
    private String zzmm; // 政治面貌

    private String bynd;// 毕业年度
    private String jsdwlsbm; //就业单位隶属部门（毕业去向省份）
    private String jsdw; //接收单位
    private String byqx; //毕业去向




    private String xqhId;//校区id
    private String xqh;//校区id 离校去向使用
    private String xq;//校区ap使用
    private String pyccm;//培养层次码
    private String xqm;//学期


}
