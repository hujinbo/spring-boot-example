package me.hujinbo.boot.example.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.alibaba.druid.spring.boot.autoconfigure.properties.DruidStatProperties;
import com.alibaba.druid.util.Utils;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;

/**
 * 移除Druid监控页底部广告
 *
 * @author hujinbo
 */
@Configuration
@ConditionalOnWebApplication
@AutoConfigureAfter(DruidDataSourceAutoConfigure.class)
@ConditionalOnProperty(name = "spring.datasource.druid.stat-view-servlet.enabled", havingValue = "true", matchIfMissing = true)
public class RemoveDruidAdConfig {

    /**
     * 带广告的common.js路径
     */
    private final String FILE_PATH = "support/http/resources/js/common.js";

    @Bean
    public FilterRegistrationBean<Filter> removeDruidAdFilterRegistrationBean(DruidStatProperties properties) {
        // 获取web监控页面的参数
        DruidStatProperties.StatViewServlet config = properties.getStatViewServlet();
        // 获取common.js的配置路径
        String pattern = config.getUrlPattern() != null ? config.getUrlPattern() : "/druid/*";
        String commonJsPattern = pattern.replaceAll("\\*", "js/common.js");

        // 过滤common.js的请求，重新处理后用正则注释掉footer代码
        Filter filter = (request, response, chain) -> {
            chain.doFilter(request, response);
            // 重置缓冲区，响应头不会被重置
            response.resetBuffer();
            // 获取common.js
            String text = Utils.readFromResource(FILE_PATH);
            // 正则去掉banner
            text = text.replaceAll("this.buildFooter", "//this.buildFooter");
            response.getWriter().write(text);
        };

        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(filter);
        registrationBean.addUrlPatterns(commonJsPattern);

        return registrationBean;
    }

}
