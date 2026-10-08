package com.tutorial.trading.config;

import java.beans.BeanProperty;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestClient;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableWebSecurity
public class ApiConfig {
    
@Value("${coingecko.api.url}")
    private String baseUrl;



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
 http.cors(cors->{});
        http.csrf(e->e.disable());
        // http.authorizeHttpRequests(req->
        //     req.anyRequest().authenticated());
        return http.build();
    }
    
    

    @Bean
    public RestClient restClient(){


        return RestClient.builder()
                   .baseUrl(baseUrl)
                   .defaultHeader("accept","application/json")
                   .build();
    }
    //    @Bean
    // public WebMvcConfigurer corsConfigurer() {
    //     return new WebMvcConfigurer() {

    //         @Override
    //         public void addCorsMappings(CorsRegistry registry) {

    //             registry.addMapping("/**")
    //                     .allowedOrigins("http://localhost:4200")
    //                     .allowedMethods("*")
    //                     .allowedHeaders("*");


    //         }
    //     };
    // }
    @Bean
CorsConfigurationSource corsConfigurationSource(){

    CorsConfiguration config = new CorsConfiguration();

    config.setAllowCredentials(true);
    config.addAllowedOrigin("http://localhost:4200");
    config.addAllowedHeader("*");
    config.addAllowedMethod("*");

    UrlBasedCorsConfigurationSource source =
        new UrlBasedCorsConfigurationSource();

    source.registerCorsConfiguration("/**", config);

    return source;
}

}
