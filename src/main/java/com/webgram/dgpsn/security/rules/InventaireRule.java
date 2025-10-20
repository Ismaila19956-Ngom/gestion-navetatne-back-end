package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InventaireRule {
    static final String INVENTAIRE_API_PREFIX = "/inventaire";
    static final String INVENTAIRE_ID = "/{inventaireId}";
    static final String INVENTAIRE_MULTIPLE = "/multiple";

    @Bean
    public SecurityRule createInventaire() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(INVENTAIRE_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_INVENTAIRE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule createMultipleInventaires() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(INVENTAIRE_API_PREFIX + INVENTAIRE_MULTIPLE)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_INVENTAIRE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readInventaire() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(INVENTAIRE_API_PREFIX + INVENTAIRE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_INVENTAIRE)
                .hasPermission(SecurityPermissions.ADD_INVENTAIRE)
                .hasPermission(SecurityPermissions.EDIT_INVENTAIRE)
                .hasPermission(SecurityPermissions.DELETE_INVENTAIRE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllInventaires() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(INVENTAIRE_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_INVENTAIRE)
                .hasPermission(SecurityPermissions.ADD_INVENTAIRE)
                .hasPermission(SecurityPermissions.EDIT_INVENTAIRE)
                .hasPermission(SecurityPermissions.DELETE_INVENTAIRE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateInventaire() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(INVENTAIRE_API_PREFIX + INVENTAIRE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_INVENTAIRE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteInventaire() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(INVENTAIRE_API_PREFIX + INVENTAIRE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_INVENTAIRE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}