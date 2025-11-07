package com.webgram.dgpsn.security.rules;

import com.webgram.dgpsn.security.SecurityPermissions;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PlanComptableRule {
    static final String PLAN_COMPTABLE_API_PREFIX = "/plancomptableelements";
    static final String REALISION_SOUS_COMPTE = "/realisation";
    static final String PLAN_COMPTABLE_ID = "/{id}";
    static final String PLAN_COMPTABLE_RUBRIQUE = "/rubriques";
    static final String PLAN_COMPTABLE_CLASSE = "/classes";

    @Bean
    public SecurityRule getClassesPlanComptable() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(PLAN_COMPTABLE_API_PREFIX + PLAN_COMPTABLE_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_PLAN_COMPTABLE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule createPlanComptable() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(PLAN_COMPTABLE_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_PLAN_COMPTABLE)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readPlanComptable() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(PLAN_COMPTABLE_API_PREFIX + PLAN_COMPTABLE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_PLAN_COMPTABLE)
                .hasPermission(SecurityPermissions.ADD_PLAN_COMPTABLE)
                .hasPermission(SecurityPermissions.EDIT_PLAN_COMPTABLE)
                .hasPermission(SecurityPermissions.DELETE_PLAN_COMPTABLE)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule getRealisationsBySousAltaCompteId() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(PLAN_COMPTABLE_API_PREFIX + REALISION_SOUS_COMPTE + PLAN_COMPTABLE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_PLAN_COMPTABLE)
                .hasPermission(SecurityPermissions.ADD_PLAN_COMPTABLE)
                .hasPermission(SecurityPermissions.EDIT_PLAN_COMPTABLE)
                .hasPermission(SecurityPermissions.DELETE_PLAN_COMPTABLE)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule getRubriquesByClasseId() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(PLAN_COMPTABLE_API_PREFIX + PLAN_COMPTABLE_RUBRIQUE + PLAN_COMPTABLE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_PLAN_COMPTABLE)
                .hasPermission(SecurityPermissions.ADD_PLAN_COMPTABLE)
                .hasPermission(SecurityPermissions.EDIT_PLAN_COMPTABLE)
                .hasPermission(SecurityPermissions.DELETE_PLAN_COMPTABLE)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllPlanComptable() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(PLAN_COMPTABLE_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_PLAN_COMPTABLE)
                .hasPermission(SecurityPermissions.ADD_PLAN_COMPTABLE)
                .hasPermission(SecurityPermissions.EDIT_PLAN_COMPTABLE)
                .hasPermission(SecurityPermissions.DELETE_PLAN_COMPTABLE)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updatePlanComptable() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(PLAN_COMPTABLE_API_PREFIX + PLAN_COMPTABLE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_PLAN_COMPTABLE)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deletePlanComptable() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(PLAN_COMPTABLE_API_PREFIX + PLAN_COMPTABLE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_PLAN_COMPTABLE)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}
