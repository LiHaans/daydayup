package com.golaxy.service;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;

/**
 * @Auther: lihang
 * @Date: 2021-06-16 14:54
 * @Description:
 */
public class MybatisPlusGenerator {
    public static void main(String[] args) {
        // 需要构建一个 代码自动生成器 对象
        AutoGenerator mpg = new AutoGenerator();
        // 配置策略
        // 1、全局配置
        GlobalConfig gc = new GlobalConfig();
        // 先得到当前工程目录
        String projectPath = System.getProperty("user.dir")+"\\mybatisplus-generator";
        // 是maven项目的结构，就是工程目录 + /src/main/java
        gc.setOutputDir(projectPath+"/src/main/java");
        // 设置生成文件的作者信息
        gc.setAuthor("lihang");
        //当代码生成完成之后是否打开代码所在的文件夹
        gc.setOpen(false);
        gc.setFileOverride(false); // 是否覆盖
        gc.setServiceName("%sService"); // 去Service的I前缀
        gc.setIdType(IdType.UUID);  // ID生成规则
        gc.setDateType(DateType.ONLY_DATE);
        gc.setSwagger2(true);  // 实体属性 Swagger2 注解
        mpg.setGlobalConfig(gc); // 将上述的全局配置注入
        //2、设置数据源
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:postgresql://10.208.63.3:5432/big_data_governance");
                dsc.setDriverName("org.postgresql.Driver");
        dsc.setUsername("postgres");
        dsc.setPassword("Ictsoft@123");
        dsc.setDbType(DbType.POSTGRE_SQL);
        mpg.setDataSource(dsc);
        //3、包的配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName("cn");  //pc.setModuleName("sys");
        pc.setParent("golaxy");    // 设置父级包名
        pc.setEntity("entity");       // 实体类包名
        pc.setMapper("mapper");       // mapper包名
        pc.setService("service");      // service包名
        pc.setController("controller");    // controller包名
        pc.setXml("mapper.mapper");
        // 业务接口的实现类包
        pc.setServiceImpl("service.impl");

        mpg.setPackageInfo(pc);
        //4、策略配置
        StrategyConfig strategy = new StrategyConfig();

        //要设置生成哪些表 如果不设置就是生成所有的表
        strategy.setInclude("tool_choreography_task", "tool_manager_info");
                strategy.setNaming(NamingStrategy.underline_to_camel);  //设置字段和表名的是否把下划线完成驼峰命名规则
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);

        //设置生成的实体类继承的父类
        //strategy.setSuperEntityClass("com.sxt.BaseEntity");

        // 公共父类
        //strategy.setSuperControllerClass("com.sxt.BaseController");

        // 写于父类中的公共字段
        //strategy.setSuperEntityColumns("person_id","person_name");

        strategy.setEntityLombokModel(true); // 自动lombok；
        strategy.setLogicDeleteFieldName("is_deleted");
        // 自动填充配置
        TableFill gmtCreate = new TableFill("create_time", FieldFill.INSERT);
        TableFill gmtModified = new TableFill("modified_time",
                FieldFill.INSERT_UPDATE);
        ArrayList<TableFill> tableFills = new ArrayList<>();
        tableFills.add(gmtCreate);
        tableFills.add(gmtModified);
        strategy.setTableFillList(tableFills);
        // 乐观锁
        //strategy.setVersionFieldName("version");

        //strategy.setTablePrefix(pc.getModuleName() + "_");

        //是否生成resetController
        strategy.setRestControllerStyle(true);
        strategy.setControllerMappingHyphenStyle(true); // localhost:8080/hello_id_2
        mpg.setStrategy(strategy);
        mpg.execute(); //执行
    }

}
