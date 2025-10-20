package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PreparationRule {
    static final String PREPARATION_API_PREFIX = "/preparations";
    static final String PREPARATION_ID = "/{preparationId}";
    static final String IMPORT_PREFIX = "/import";
    static final String EXPORT_PREFIX = "/export";

    @Bean
    public SecurityRule createPreparation() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(PREPARATION_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_PREPARATION)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readPreparation() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(PREPARATION_API_PREFIX + PREPARATION_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_PREPARATION)
                .hasPermission(SecurityPermissions.ADD_PREPARATION)
                .hasPermission(SecurityPermissions.EDIT_PREPARATION)
                .hasPermission(SecurityPermissions.DELETE_PREPARATION)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllPreparations() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(PREPARATION_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_PREPARATION)
                .hasPermission(SecurityPermissions.ADD_PREPARATION)
                .hasPermission(SecurityPermissions.EDIT_PREPARATION)
                .hasPermission(SecurityPermissions.DELETE_PREPARATION)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updatePreparation() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(PREPARATION_API_PREFIX + PREPARATION_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_PREPARATION)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule deletePreparation() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(PREPARATION_API_PREFIX + PREPARATION_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_PREPARATION)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule importPreparation() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(PREPARATION_API_PREFIX + IMPORT_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.IMPORT_PREPARATION)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule exportPreparation() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(PREPARATION_API_PREFIX + EXPORT_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EXPORT_PREPARATION)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

}
