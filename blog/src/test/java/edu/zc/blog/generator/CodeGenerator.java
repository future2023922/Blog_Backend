package edu.zc.blog.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;


/**
 * @author: keeplooking
 * @since: 2021/12/30 - 21:51
 */
public class CodeGenerator {

        public static void main(String[] args) {
            // 代码生成器
            AutoGenerator mpg = new AutoGenerator();

            // 全局配置
            GlobalConfig gc = new GlobalConfig();
            String projectPath = System.getProperty("user.dir");
            System.out.println(projectPath);
            gc.setOutputDir(projectPath + "/blog/src/main/java");
            gc.setAuthor("keeplooking");
            gc.setServiceName("%sService");	//去掉Service接口的首字母I
            gc.setOpen(false);
            gc.setSwagger2(true); //实体属性 Swagger2 注解
            mpg.setGlobalConfig(gc);
            gc.setFileOverride(false); //重新生成时文件是否覆盖

            // 数据源配置
            DataSourceConfig dsc = new DataSourceConfig();
            dsc.setUrl("jdbc:mysql://localhost:3306/informationAggregationSite?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=Asia/Shanghai");
            // dsc.setSchemaName("public");
            dsc.setDriverName("com.mysql.cj.jdbc.Driver");
            dsc.setUsername("root");
            dsc.setPassword("rootroot");
            dsc.setDbType(DbType.MYSQL);
            mpg.setDataSource(dsc);

            // 包配置
            PackageConfig pc = new PackageConfig();
            pc.setModuleName("blog");
            pc.setParent("edu.zc");
            pc.setController("controller");
            pc.setEntity("entity");
            pc.setService("service");
            pc.setMapper("mapper");
            mpg.setPackageInfo(pc);

            // 策略配置
            StrategyConfig strategy = new StrategyConfig();
            strategy.setInclude("bookmark");
            strategy.setNaming(NamingStrategy.underline_to_camel);//数据库表映射到实体的命名策略
            strategy.setTablePrefix(pc.getModuleName() + "_"); //生成实体时去掉表前缀

            strategy.setColumnNaming(NamingStrategy.underline_to_camel);//数据库表字段映射到实体的命名策略
            strategy.setEntityLombokModel(true); // lombok 模型 @Accessors(chain = true) setter链式操作

            strategy.setRestControllerStyle(true); //restful api风格控制器
            strategy.setControllerMappingHyphenStyle(true); //url中驼峰转连字符

            mpg.setStrategy(strategy);

            // 6、执行
            mpg.execute();
        }

}
