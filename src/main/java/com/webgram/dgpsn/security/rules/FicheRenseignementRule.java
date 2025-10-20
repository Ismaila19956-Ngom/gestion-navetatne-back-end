package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FicheRenseignementRule {
    static final String FICHE_RENSEIGNEMENT_API_PREFIX = "/ficheRenseignement";
    static final String FICHE_RENSEIGNEMENT_ID = "/{ficheRenseignementId}";
    static final String FICHE_RENSEIGNEMENT_MULTIPLE = "/multiple";

    @Bean
    public SecurityRule createFicheRenseignement() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(FICHE_RENSEIGNEMENT_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_FICHE_RENSEIGNEMENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule createMultipleFicheRenseignements() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(FICHE_RENSEIGNEMENT_API_PREFIX + FICHE_RENSEIGNEMENT_MULTIPLE)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_FICHE_RENSEIGNEMENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readFicheRenseignement() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(FICHE_RENSEIGNEMENT_API_PREFIX + FICHE_RENSEIGNEMENT_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_FICHE_RENSEIGNEMENT)
                .hasPermission(SecurityPermissions.ADD_FICHE_RENSEIGNEMENT)
                .hasPermission(SecurityPermissions.EDIT_FICHE_RENSEIGNEMENT)
                .hasPermission(SecurityPermissions.DELETE_FICHE_RENSEIGNEMENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllFicheRenseignements() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(FICHE_RENSEIGNEMENT_API_PREFIX + "/allFicheRenseignements")
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_FICHE_RENSEIGNEMENT)
                .hasPermission(SecurityPermissions.ADD_FICHE_RENSEIGNEMENT)
                .hasPermission(SecurityPermissions.EDIT_FICHE_RENSEIGNEMENT)
                .hasPermission(SecurityPermissions.DELETE_FICHE_RENSEIGNEMENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateFicheRenseignement() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(FICHE_RENSEIGNEMENT_API_PREFIX + FICHE_RENSEIGNEMENT_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_FICHE_RENSEIGNEMENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteFicheRenseignement() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(FICHE_RENSEIGNEMENT_API_PREFIX + FICHE_RENSEIGNEMENT_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_FICHE_RENSEIGNEMENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}