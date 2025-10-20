package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConditionnalityRule {
    static final String CONDITIONALITY_API_PREFIX = "/conditionnalities";
    static final String CONDITIONALITY_ID = "/{conditionnalityId}";
    static final String IMPORT_PREFIX = "/import";
    static final String EXPORT_PREFIX = "/export";

    @Bean
    public SecurityRule createConditionnality() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(CONDITIONALITY_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_CONDITIONALITY)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readConditionnality() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(CONDITIONALITY_API_PREFIX + CONDITIONALITY_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_CONDITIONALITY)
                .hasPermission(SecurityPermissions.ADD_CONDITIONALITY)
                .hasPermission(SecurityPermissions.EDIT_CONDITIONALITY)
                .hasPermission(SecurityPermissions.DELETE_CONDITIONALITY)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllConditionnalities() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(CONDITIONALITY_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_CONDITIONALITY)
                .hasPermission(SecurityPermissions.ADD_CONDITIONALITY)
                .hasPermission(SecurityPermissions.EDIT_CONDITIONALITY)
                .hasPermission(SecurityPermissions.DELETE_CONDITIONALITY)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateConditionnality() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(CONDITIONALITY_API_PREFIX + CONDITIONALITY_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_CONDITIONALITY)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule deleteConditionnality() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(CONDITIONALITY_API_PREFIX + CONDITIONALITY_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_CONDITIONALITY)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule importConditionnality() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(CONDITIONALITY_API_PREFIX + IMPORT_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.IMPORT_CONDITIONALITY)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule exportConditionnality() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(CONDITIONALITY_API_PREFIX + EXPORT_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EXPORT_CONDITIONALITY)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

}
