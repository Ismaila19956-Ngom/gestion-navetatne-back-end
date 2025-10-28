package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LigneBudgetaireRule {
    static final String LIGNE_BUDGETAIRE_API_PREFIX = "/ligne-budgetaire";
    static final String LIGNE_BUDGETAIRE_ID = "/{ligneBudgetaireId}";
    static final String LIGNE_BUDGETAIRE_MULTIPLE = "/multiple";

    @Bean
    public SecurityRule addLigneBudgetaire() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(LIGNE_BUDGETAIRE_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_LIGNE_BUDGETAIRE)
                .hasPermission(SecurityPermissions.READ_PLAN_COMPTABLE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule addMultipleLigneBudgetaire() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(LIGNE_BUDGETAIRE_API_PREFIX + LIGNE_BUDGETAIRE_MULTIPLE)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_LIGNE_BUDGETAIRE)
                .hasPermission(SecurityPermissions.READ_PLAN_COMPTABLE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllLigneBudgetaire() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(LIGNE_BUDGETAIRE_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_LIGNE_BUDGETAIRE)
                .hasPermission(SecurityPermissions.ADD_LIGNE_BUDGETAIRE)
                .hasPermission(SecurityPermissions.READ_PLAN_COMPTABLE)
                .hasPermission(SecurityPermissions.EDIT_LIGNE_BUDGETAIRE)
                .hasPermission(SecurityPermissions.DELETE_LIGNE_BUDGETAIRE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateLigneBudgetaire() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(LIGNE_BUDGETAIRE_API_PREFIX + LIGNE_BUDGETAIRE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_LIGNE_BUDGETAIRE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteLigneBudgetaire() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(LIGNE_BUDGETAIRE_API_PREFIX + LIGNE_BUDGETAIRE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_LIGNE_BUDGETAIRE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}