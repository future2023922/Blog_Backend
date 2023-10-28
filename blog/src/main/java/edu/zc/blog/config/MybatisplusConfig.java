package edu.zc.blog.config;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
//import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author: keeplooking
 * @since: 2021/12/30 - 22:22
 */

@EnableTransactionManagement
@Configuration
@MapperScan("edu.zc.blog.mapper")
public class MybatisplusConfig {

    //    分页插件
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }

    /**
     * 逻辑删除插件
     */
//    @Bean
//    public ISqlInjector sqlInjector() {
//        return new LogicSqlInjector();
//    }

//    乐观锁插件
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor(){
        return new OptimisticLockerInterceptor();
    }

}

