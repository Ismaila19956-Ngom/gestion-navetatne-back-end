package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TdrRule {
    static final String API_PREFIX = "/tdr";
    static final String API_ID = "/{id}";

    @Bean
    public SecurityRule createTdrRule() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(API_PREFIX)
                .build()
                .condition()
                 .hasPermission(SecurityPermissions.ADD_TDR)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    
    @Bean
    public SecurityRule readTdrRule() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(API_PREFIX + "/**")
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_TDR)
                .hasPermission(SecurityPermissions.ADD_TDR)
                .hasPermission(SecurityPermissions.EDIT_TDR)
                .hasPermission(SecurityPermissions.DELETE_TDR)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    
    @Bean
    public SecurityRule updateTdrRule() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(API_PREFIX + API_ID)
                .build()
                .condition()
                 .hasPermission(SecurityPermissions.EDIT_TDR)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    
    @Bean
    public SecurityRule deleteTdrRule() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(API_PREFIX + API_ID)
                .build()
                .condition()
                 .hasPermission(SecurityPermissions.DELETE_TDR)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}