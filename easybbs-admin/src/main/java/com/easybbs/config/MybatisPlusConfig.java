package com.easybbs.config;

import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import org.springframework.context.annotation.Bean;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisPlusConfig {
    private final Integer MAX_PAGE_SIZE = 300;
    @Bean
    public PaginationInterceptor pageInterceptor() {
        PaginationInterceptor pageInterceptor = new PaginationInterceptor();
        pageInterceptor.setLimit(MAX_PAGE_SIZE);
        pageInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
        return pageInterceptor;
    }
}
