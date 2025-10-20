package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EchangeHistoryRule {
    static final String ECHANGE_API_PREFIX = "/echangeHistories";
    static final String ECHANGE_ID = "/{echangeId}";
    static final String ECHANGE_RATE = "/rate";

    
    @Bean
    public SecurityRule createEchange() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(ECHANGE_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_ECHANGE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readEchange() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(ECHANGE_API_PREFIX + ECHANGE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_ECHANGE)
                .hasPermission(SecurityPermissions.ADD_ECHANGE)
                .hasPermission(SecurityPermissions.EDIT_ECHANGE)
                .hasPermission(SecurityPermissions.DELETE_ECHANGE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllEchange() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(ECHANGE_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_ECHANGE)
                .hasPermission(SecurityPermissions.ADD_ECHANGE)
                .hasPermission(SecurityPermissions.EDIT_ECHANGE)
                .hasPermission(SecurityPermissions.DELETE_ECHANGE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readRateByCashAndDate() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(ECHANGE_API_PREFIX + ECHANGE_RATE)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_ECHANGE)
                .hasPermission(SecurityPermissions.ADD_ECHANGE)
                .hasPermission(SecurityPermissions.EDIT_ECHANGE)
                .hasPermission(SecurityPermissions.DELETE_ECHANGE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateEchange() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(ECHANGE_API_PREFIX + ECHANGE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_ECHANGE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule deleteEchange() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(ECHANGE_API_PREFIX + ECHANGE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_CASH)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}
