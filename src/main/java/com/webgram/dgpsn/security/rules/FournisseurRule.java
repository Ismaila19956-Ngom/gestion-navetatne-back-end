package com.webgram.dgpsn.security.rules;

import com.webgram.dgpsn.security.SecurityPermissions;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FournisseurRule {
    static final String FOURNISSEUR_API_PREFIX = "/fournisseurs";
    static final String FOURNISSEUR_ID = "/{id}";

    @Bean
    public SecurityRule createFournisseur() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(FOURNISSEUR_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_FOURNISSEUR)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readFournisseur() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(FOURNISSEUR_API_PREFIX + FOURNISSEUR_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_FOURNISSEUR)
                .hasPermission(SecurityPermissions.ADD_FOURNISSEUR)
                .hasPermission(SecurityPermissions.EDIT_FOURNISSEUR)
                .hasPermission(SecurityPermissions.DELETE_FOURNISSEUR)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllFournisseurs() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(FOURNISSEUR_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_FOURNISSEUR)
                .hasPermission(SecurityPermissions.ADD_FOURNISSEUR)
                .hasPermission(SecurityPermissions.EDIT_FOURNISSEUR)
                .hasPermission(SecurityPermissions.DELETE_FOURNISSEUR)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateFournisseur() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(FOURNISSEUR_API_PREFIX + FOURNISSEUR_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_FOURNISSEUR)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteFournisseur() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(FOURNISSEUR_API_PREFIX + FOURNISSEUR_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_FOURNISSEUR)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}