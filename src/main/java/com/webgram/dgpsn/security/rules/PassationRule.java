package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PassationRule {
    static final String PASSATION_API_PREFIX = "/passation";
    static final String PASSATION_ID = "/{passationId}";
    static final String PASSATION_MULTIPLE = "/multiple";

    @Bean
    public SecurityRule createPassations() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(PASSATION_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_PASSATION)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule createMultiplePassations() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(PASSATION_API_PREFIX + PASSATION_MULTIPLE)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_PASSATION)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readPassationss() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(PASSATION_API_PREFIX + PASSATION_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_PASSATION)
                .hasPermission(SecurityPermissions.ADD_PASSATION)
                .hasPermission(SecurityPermissions.EDIT_PASSATION)
                .hasPermission(SecurityPermissions.DELETE_PASSATION)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllPassations() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(PASSATION_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_PASSATION)
                .hasPermission(SecurityPermissions.ADD_PASSATION)
                .hasPermission(SecurityPermissions.EDIT_PASSATION)
                .hasPermission(SecurityPermissions.DELETE_PASSATION)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updatePassations() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(PASSATION_API_PREFIX + PASSATION_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_PASSATION)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deletePassations() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(PASSATION_API_PREFIX + PASSATION_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_PASSATION)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}