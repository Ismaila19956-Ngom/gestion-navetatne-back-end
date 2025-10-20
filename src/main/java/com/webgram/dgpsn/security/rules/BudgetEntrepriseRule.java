package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BudgetEntrepriseRule {
    static final String BUDGETS_API_PREFIX = "/budgets";
    static final String BUDGETS_ID = "/{budgetId}";
    static final String BUDGETS_EXPORT = "/export";


    @Bean
    public SecurityRule AddBudgetEntreprise() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(BUDGETS_API_PREFIX)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.ADD_AG)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllBudgetEntreprises() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(BUDGETS_API_PREFIX)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.READ_AG)
//                .hasPermission(SecurityPermissions.ADD_AG)
//                .hasPermission(SecurityPermissions.EDIT_AG)
//                .hasPermission(SecurityPermissions.DELETE_AG)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readBudgetEntreprise() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(BUDGETS_API_PREFIX + BUDGETS_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.READ_AG)
//                .hasPermission(SecurityPermissions.ADD_AG)
//                .hasPermission(SecurityPermissions.EDIT_AG)
//                .hasPermission(SecurityPermissions.DELETE_AG)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateBudgetEntreprise() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(BUDGETS_API_PREFIX + BUDGETS_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.EDIT_AG)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteBudgetEntreprise() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(BUDGETS_API_PREFIX + BUDGETS_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.DELETE_AG)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }


    @Bean
    public SecurityRule exportBudgetEntreprise() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(BUDGETS_API_PREFIX + BUDGETS_EXPORT)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.EXPORT_AG)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}
