package com.byb.gateway.Config;

import com.byb.gateway.Filter.CorsResponseHeaderFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import javax.annotation.PostConstruct;

import static com.byb.BaseUtil.Config.ConstantConfig.WHITE_LIST;

@Configuration
public class GatewayConfig {

    @Bean
    public CorsWebFilter corsWebFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedOriginPattern("*");
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setMaxAge(600L);

        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsWebFilter(source);
    }

    static {
        WHITE_LIST.add("/user/login");
        WHITE_LIST.add("/user/addUserByEmail");
        WHITE_LIST.add("/user/activeEmail");
        WHITE_LIST.add("/house/selectHouseAdmin");
        WHITE_LIST.add("/news/selectArticleForC");
        WHITE_LIST.add("/house/housedetail");
        WHITE_LIST.add("/house/test");
        WHITE_LIST.add("/news/getNewsGroup");
    }


    @Bean
    public CorsResponseHeaderFilter corsResponseHeaderFilter() {
        return new CorsResponseHeaderFilter();
    }

}
