package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BudgetPassationRule {
    static final String BUDGET_PASSATION_API_PREFIX = "/budgetPassation";
    static final String BUDGET_PASSATION_ID = "/{budgetPassationId}";
    static final String BUDGET_PASSATION_MULTIPLE = "/multiple";
    static final String BUDGET_PASSATION_EXPORT = "/export/year";
    static final String BUDGET_PASSATION_YEAR = "/{year}";


    @Bean
    public SecurityRule createBudgetPassation() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(BUDGET_PASSATION_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_BUDGET_PASSATION)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule getBudgetPassationWithEngagementsExcel() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(BUDGET_PASSATION_API_PREFIX  + BUDGET_PASSATION_EXPORT + BUDGET_PASSATION_YEAR)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_BUDGET_PASSATION)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule createMultipleBudgetPassations() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(BUDGET_PASSATION_API_PREFIX + BUDGET_PASSATION_MULTIPLE)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_BUDGET_PASSATION)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readBudgetPassation() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(BUDGET_PASSATION_API_PREFIX + BUDGET_PASSATION_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_BUDGET_PASSATION)
                .hasPermission(SecurityPermissions.ADD_BUDGET_PASSATION)
                .hasPermission(SecurityPermissions.EDIT_BUDGET_PASSATION)
                .hasPermission(SecurityPermissions.DELETE_BUDGET_PASSATION)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllBudgetPassations() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(BUDGET_PASSATION_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_BUDGET_PASSATION)
                .hasPermission(SecurityPermissions.ADD_BUDGET_PASSATION)
                .hasPermission(SecurityPermissions.EDIT_BUDGET_PASSATION)
                .hasPermission(SecurityPermissions.DELETE_BUDGET_PASSATION)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateBudgetPassation() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(BUDGET_PASSATION_API_PREFIX + BUDGET_PASSATION_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_BUDGET_PASSATION)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteBudgetPassation() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(BUDGET_PASSATION_API_PREFIX + BUDGET_PASSATION_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_BUDGET_PASSATION)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}