package com.house;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

public class MyGenerator {
    public static void main(String[] args) {
        //创建代码生成器
        AutoGenerator autoGenerator = new AutoGenerator();

        //策略配置
        //1.全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        //代码输出位置
        String projectPath = System.getProperty("user.dir");
        globalConfig.setOutputDir(projectPath+"/order/src/main/java");
        //设置作者信息
        globalConfig.setAuthor("zpj");
        //生成完毕之后不打开资源管理器
        globalConfig.setOpen(false);
        //是否覆盖原来的内容
        globalConfig.setFileOverride(true);
        //设置服务名称
        globalConfig.setServiceName("%sService");
        //设置全局主键生成策略
        globalConfig.setIdType(IdType.AUTO);

        autoGenerator.setGlobalConfig(globalConfig);

        //数据源设置***
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDriverName("com.mysql.jdbc.Driver");
        dataSourceConfig.setUrl("jdbc:mysql:///house?characterEncoding=UTF-8");
        dataSourceConfig.setUsername("root");
        dataSourceConfig.setPassword("zpj523123");

        autoGenerator.setDataSource(dataSourceConfig);

        //包设置
        PackageConfig packageConfig = new PackageConfig();
        //模块名称***
        //packageConfig.setModuleName("demo04");
        //父包***
        packageConfig.setParent("com.house");
        //实体类
        packageConfig.setEntity("entity");
        //服务层
        packageConfig.setService("service");
        //控制层
        packageConfig.setController("controller");
        //持久层
        packageConfig.setMapper("mapper");

        autoGenerator.setPackageInfo(packageConfig);

        //策略配置
        StrategyConfig sc = new StrategyConfig();
        //****需要生成的映射的表名
        sc.setInclude("house");
        //驼峰/划线命名转换
        sc.setNaming(NamingStrategy.underline_to_camel);
        sc.setColumnNaming(NamingStrategy.underline_to_camel);
        //是否为实体启动Lombok
        sc.setEntityLombokModel(true);
        //是否成成RestFul风格的控制器
        sc.setRestControllerStyle(true);

        autoGenerator.setStrategy(sc);

        //执行
        autoGenerator.execute();
    }
}
