package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RealisationRule {
    static final String REALISATION_API_PREFIX = "/realisation";
    static final String REALISATION_ID = "/{realisationId}";
    static final String REALISATION_MULTIPLE = "/multiple";

    @Bean
    public SecurityRule addRealisation() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(REALISATION_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_REALISATION)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule addMultipleRealisation() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(REALISATION_API_PREFIX + REALISATION_MULTIPLE)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_REALISATION)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllRealisation() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(REALISATION_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_REALISATION)
                .hasPermission(SecurityPermissions.ADD_REALISATION)
                .hasPermission(SecurityPermissions.EDIT_REALISATION)
                .hasPermission(SecurityPermissions.DELETE_REALISATION)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateRealisation() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(REALISATION_API_PREFIX + REALISATION_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_REALISATION)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteRealisation() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(REALISATION_API_PREFIX + REALISATION_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_REALISATION)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}