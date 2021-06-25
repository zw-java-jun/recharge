package com.order;

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

public class ZpjCode {
    public static void main(String[] args) {
// 需要构建一个 代码自动生成器 对象
        AutoGenerator mpg = new AutoGenerator();
// 配置策略
// 1、全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir"); // 获取用户的目录（项目路劲）
        gc.setOutputDir(projectPath + "/src/main/java"); // 输出到这个目录下
        gc.setAuthor("zpj"); // 生成作者信息
        gc.setOpen(false); // 生成完后是否打开资源管理器
        gc.setFileOverride(false); // 是否覆盖以前生成的
        gc.setServiceName("%sService"); // 去Service的I前缀
        gc.setIdType(IdType.ID_WORKER); // 默认的初始算法（3.3版本后用ASSIGN_ID）
        gc.setDateType(DateType.ONLY_DATE); // 日期类型：仅仅只是时间
        gc.setSwagger2(true); // 自动配置swagger2文档
        mpg.setGlobalConfig(gc); // 全部丢进自动配置里面
//2、设置数据源
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/user?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=Asia/Shanghai");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver"); //设置数据库连接
        dsc.setUsername("root"); // 数据库账号
        dsc.setPassword("zpj523123"); // 数据库密码
        dsc.setDbType(DbType.MYSQL); // 设置数据库类型
        mpg.setDataSource(dsc); // 全部丢进数据源配置
//3、包的配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName("order"); // 设置模块名
        pc.setParent("com.order"); // 这个模块在那个位置生成
        pc.setEntity("entity"); // 设置实体类的包名
        pc.setMapper("mapper"); // 设置mapper
        pc.setService("service"); // 设置service层
        pc.setController("controller"); // 设置controller层
        mpg.setPackageInfo(pc); // 丢进包设置里
//4、策略配置 （自动注入的字段，乐观锁）
        StrategyConfig strategy = new StrategyConfig();
        strategy.setInclude("order"); // 设置要映射的表名
        strategy.setNaming(NamingStrategy.underline_to_camel); // 设置包的命名规则 （下划线转驼峰命名）
        strategy.setColumnNaming(NamingStrategy.underline_to_camel); // 设置列的命名规则 （下划线转驼峰命名）
        strategy.setEntityLombokModel(true); // 自动lombok；
        strategy.setLogicDeleteFieldName("deleted"); // 有逻辑删除就要配置
// 自动填充配置
        TableFill gmtCreate = new TableFill("create_time", FieldFill.INSERT); // 自动创建时间
        TableFill gmtModified = new TableFill("update_time", FieldFill.INSERT_UPDATE); // 自动修改时间
        ArrayList<TableFill> tableFills = new ArrayList<>(); // 因为set需要的是一个集合
        tableFills.add(gmtCreate); // 添加进集合
        tableFills.add(gmtModified);
        strategy.setTableFillList(tableFills); // 丢进设置
// 乐观锁
        strategy.setVersionFieldName("version");
        strategy.setRestControllerStyle(true);
        strategy.setControllerMappingHyphenStyle(true); // 把参数转换成下划线连接
        mpg.setStrategy(strategy); // 丢进去
        mpg.execute(); //执行
    }
}
