package com.webgram.dgpsn.security.rules;

import com.webgram.dgpsn.security.SecurityPermissions;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AtelierRule {

    static final String ATELIER_API_PREFIX = "/ateliers";



    static final String ATELIER_ID = "/{atelierId}";
    static final String ATELIER_ALL = "/all";

    @Bean
    public SecurityRule atelierAdd() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(ATELIER_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_ATELIER)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAtelier() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(ATELIER_API_PREFIX + ATELIER_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_ATELIER)
                .hasPermission(SecurityPermissions.ADD_ATELIER)
                .hasPermission(SecurityPermissions.EDIT_ATELIER)
                .hasPermission(SecurityPermissions.DELETE_ATELIER)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllAtelierByProject() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(ATELIER_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_ATELIER)
                .hasPermission(SecurityPermissions.ADD_ATELIER)
                .hasPermission(SecurityPermissions.EDIT_ATELIER)
                .hasPermission(SecurityPermissions.DELETE_ATELIER)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateAtelier() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(ATELIER_API_PREFIX + ATELIER_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_ATELIER)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteAtelier() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(ATELIER_API_PREFIX + ATELIER_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_ATELIER)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllAtelier() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(ATELIER_API_PREFIX + ATELIER_ALL)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_ATELIER)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}