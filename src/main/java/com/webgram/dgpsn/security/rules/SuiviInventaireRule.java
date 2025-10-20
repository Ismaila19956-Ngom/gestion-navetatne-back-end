package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SuiviInventaireRule {
    static final String SUIVI_INVENTAIRE_API_PREFIX = "/suiviInventaire";
    static final String SUIVI_INVENTAIRE_ID = "/{suiviInventaireId}";
    static final String SUIVI_INVENTAIRE_MULTIPLE = "/multiple";

    @Bean
    public SecurityRule createSuiviInventaire() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(SUIVI_INVENTAIRE_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_SUIVI_INVENTAIRE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule createMultipleSuiviInventaires() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(SUIVI_INVENTAIRE_API_PREFIX + SUIVI_INVENTAIRE_MULTIPLE)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_SUIVI_INVENTAIRE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readSuiviInventaire() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(SUIVI_INVENTAIRE_API_PREFIX + SUIVI_INVENTAIRE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_SUIVI_INVENTAIRE)
                .hasPermission(SecurityPermissions.ADD_SUIVI_INVENTAIRE)
                .hasPermission(SecurityPermissions.EDIT_SUIVI_INVENTAIRE)
                .hasPermission(SecurityPermissions.DELETE_SUIVI_INVENTAIRE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllSuiviInventaires() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(SUIVI_INVENTAIRE_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_SUIVI_INVENTAIRE)
                .hasPermission(SecurityPermissions.ADD_SUIVI_INVENTAIRE)
                .hasPermission(SecurityPermissions.EDIT_SUIVI_INVENTAIRE)
                .hasPermission(SecurityPermissions.DELETE_SUIVI_INVENTAIRE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateSuiviInventaire() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(SUIVI_INVENTAIRE_API_PREFIX + SUIVI_INVENTAIRE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_SUIVI_INVENTAIRE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteSuiviInventaire() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(SUIVI_INVENTAIRE_API_PREFIX + SUIVI_INVENTAIRE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_SUIVI_INVENTAIRE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}