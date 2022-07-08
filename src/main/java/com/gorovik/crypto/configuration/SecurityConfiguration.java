package com.gorovik.crypto.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;

@Configuration
public class SecurityConfiguration {

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        //return (web) -> web.ignoring().antMatchers("/h2-console/**");
        return (web) -> web.ignoring().anyRequest();
    }

}