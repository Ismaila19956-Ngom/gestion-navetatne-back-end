package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FicheVisiteRule {
    static final String FICHE_VISITE_API_PREFIX = "/ficheVisite";
    static final String FICHE_VISITE_ID = "/{ficheVisiteId}";
    static final String FICHE_VISITE_MULTIPLE = "/multiple";

    @Bean
    public SecurityRule createFicheVisite() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(FICHE_VISITE_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_FICHE_VISITE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule createMultipleFicheVisites() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(FICHE_VISITE_API_PREFIX + FICHE_VISITE_MULTIPLE)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_FICHE_VISITE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readFicheVisite() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(FICHE_VISITE_API_PREFIX + FICHE_VISITE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_FICHE_VISITE)
                .hasPermission(SecurityPermissions.ADD_FICHE_VISITE)
                .hasPermission(SecurityPermissions.EDIT_FICHE_VISITE)
                .hasPermission(SecurityPermissions.DELETE_FICHE_VISITE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllFicheVisites() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(FICHE_VISITE_API_PREFIX + "/allFicheVisites")
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_FICHE_VISITE)
                .hasPermission(SecurityPermissions.ADD_FICHE_VISITE)
                .hasPermission(SecurityPermissions.EDIT_FICHE_VISITE)
                .hasPermission(SecurityPermissions.DELETE_FICHE_VISITE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateFicheVisite() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(FICHE_VISITE_API_PREFIX + FICHE_VISITE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_FICHE_VISITE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteFicheVisite() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(FICHE_VISITE_API_PREFIX + FICHE_VISITE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_FICHE_VISITE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}