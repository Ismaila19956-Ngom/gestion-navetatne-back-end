package com.webgram.dgpsn.security.rules;

import com.webgram.dgpsn.security.SecurityPermissions;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CaracteristiqueRule {
    static final String CARACTERISTIQUE_API_PREFIX = "/caracteristiqueRecrutements";
    static final String CARACTERISTIQUE_ID = "/{caracterietiqueId}";
    static final String DOWNLOAD_PREFIX = "/_download";
    static final String IMPORT_PREFIX = "/import";
    static final String EXPORT_PREFIX = "/export";
    static final String FILTER_NOT_IN_USER = "/notInUser";


    @Bean
    public SecurityRule createCaracteristiqueRecrutement() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(CARACTERISTIQUE_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_CARACTERISTIQUE)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readCaracteristiqueRecrutement() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(CARACTERISTIQUE_API_PREFIX + CARACTERISTIQUE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_CARACTERISTIQUE)
                .hasPermission(SecurityPermissions.ADD_CARACTERISTIQUE)
                .hasPermission(SecurityPermissions.EDIT_CARACTERISTIQUE)
                .hasPermission(SecurityPermissions.DELETE_CARACTERISTIQUE)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllCaracteristiqueRecrutement() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(CARACTERISTIQUE_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_CARACTERISTIQUE)
                .hasPermission(SecurityPermissions.ADD_CARACTERISTIQUE)
                .hasPermission(SecurityPermissions.EDIT_CARACTERISTIQUE)
                .hasPermission(SecurityPermissions.DELETE_CARACTERISTIQUE)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateCaracteristiqueRecrutement() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(CARACTERISTIQUE_API_PREFIX + CARACTERISTIQUE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_CARACTERISTIQUE)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule deleteCaracteristiqueRecrutement() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(CARACTERISTIQUE_API_PREFIX + CARACTERISTIQUE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_CARACTERISTIQUE)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule importCaracteristiqueRecrutement() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(CARACTERISTIQUE_API_PREFIX + IMPORT_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.IMPORT_CARACTERISTIQUE)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule exportCaracteristiqueRecrutement() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(CARACTERISTIQUE_API_PREFIX + EXPORT_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EXPORT_CARACTERISTIQUE)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule readFileCaracteristiqueRecrutement() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(CARACTERISTIQUE_API_PREFIX + CARACTERISTIQUE_ID + DOWNLOAD_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_FILE_CARACTERISTIQUE)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readCaracteristiqueRecrutementNotInUser() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(CARACTERISTIQUE_API_PREFIX + FILTER_NOT_IN_USER)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_CARACTERISTIQUE)
                .hasPermission(SecurityPermissions.ADD_CARACTERISTIQUE)
                .hasPermission(SecurityPermissions.EDIT_CARACTERISTIQUE)
                .hasPermission(SecurityPermissions.DELETE_CARACTERISTIQUE)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

}
