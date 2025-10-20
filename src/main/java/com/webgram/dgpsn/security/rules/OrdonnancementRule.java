package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrdonnancementRule {
    static final String ORDONNANCEMENT_API_PREFIX = "/ordonnancement";
    static final String ORDONNANCEMENT_ID = "/{ordonnancementId}";
    static final String ORDONNANCEMENT_MULTIPLE = "/multiple";

    @Bean
    public SecurityRule createOrdonnancement() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(ORDONNANCEMENT_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_ORDONNANCEMENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule createMultipleOrdonnancements() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(ORDONNANCEMENT_API_PREFIX + ORDONNANCEMENT_MULTIPLE)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_ORDONNANCEMENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readOrdonnancement() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(ORDONNANCEMENT_API_PREFIX + ORDONNANCEMENT_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_ORDONNANCEMENT)
                .hasPermission(SecurityPermissions.ADD_ORDONNANCEMENT)
                .hasPermission(SecurityPermissions.EDIT_ORDONNANCEMENT)
                .hasPermission(SecurityPermissions.DELETE_ORDONNANCEMENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllOrdonnancements() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(ORDONNANCEMENT_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_ORDONNANCEMENT)
                .hasPermission(SecurityPermissions.ADD_ORDONNANCEMENT)
                .hasPermission(SecurityPermissions.EDIT_ORDONNANCEMENT)
                .hasPermission(SecurityPermissions.DELETE_ORDONNANCEMENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateOrdonnancement() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(ORDONNANCEMENT_API_PREFIX + ORDONNANCEMENT_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_ORDONNANCEMENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteOrdonnancement() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(ORDONNANCEMENT_API_PREFIX + ORDONNANCEMENT_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_ORDONNANCEMENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}