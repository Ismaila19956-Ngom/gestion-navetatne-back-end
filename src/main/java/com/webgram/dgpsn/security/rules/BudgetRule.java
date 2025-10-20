package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BudgetRule {
    static final String  BUDGET_API_PREFIX = "/budget";
    static final String BUDGET_ID = "/{budgetId}";
//    static final String BUDGET_IMPORT = "/import";
//    static final String BUDGET_EXPORT = "/export";

    static final String BUDGET_TOTAL = "/totalBudget";

    @Bean
    public SecurityRule AddBudget() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(BUDGET_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_BUDGET)
                .hasPermission(SecurityPermissions.READ_PROJECT_FINANCEMENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule viewBudget() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(BUDGET_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_PROJECT_FINANCEMENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

//    @Bean
//    public SecurityRule readBudget() {
//        return SecurityRule.builder()
//                .httpMethod(HttpMethod.GET)
//                .apiPattern(BUDGET_API_PREFIX + BUDGET_ID)
//                .build()
//                .condition()
//                .hasPermission(SecurityPermissions.READ_BUDGET)
//                .hasPermission(SecurityPermissions.ADD_BUDGET)
//                .hasPermission(SecurityPermissions.EDIT_BUDGET)
//                .hasPermission(SecurityPermissions.DELETE_BUDGET)
//                .hasPermission(SecurityPermissions.ALL_ACCESS)
//                .end();
//    }

    @Bean
    public SecurityRule readAllBudgetByActivity() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(BUDGET_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_BUDGET)
                .hasPermission(SecurityPermissions.ADD_BUDGET)
                .hasPermission(SecurityPermissions.EDIT_BUDGET)
                .hasPermission(SecurityPermissions.DELETE_BUDGET)
                .hasPermission(SecurityPermissions.READ_PROJECT_FINANCEMENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateBudget() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(BUDGET_API_PREFIX + BUDGET_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_BUDGET)
                .hasPermission(SecurityPermissions.READ_PROJECT_FINANCEMENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteBudget() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(BUDGET_API_PREFIX + BUDGET_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_BUDGET)
                .hasPermission(SecurityPermissions.READ_PROJECT_FINANCEMENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readTotalBudget() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(BUDGET_API_PREFIX + BUDGET_TOTAL)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_PROJECT_FINANCEMENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)

                .end();
    }

//    @Bean
//    public SecurityRule importMilestone() {
//        return SecurityRule.builder()
//                .httpMethod(HttpMethod.POST)
//                .apiPattern(BUDGET_API_PREFIX + BUDGET_IMPORT)
//                .build()
//                .condition()
//                .hasPermission(SecurityPermissions.IMPORT_BUDGET)
//                .hasPermission(SecurityPermissions.ALL_ACCESS)
//                .end();
//    }
//
//    @Bean
//    public SecurityRule exportMilestone() {
//        return SecurityRule.builder()
//                .httpMethod(HttpMethod.GET)
//                .apiPattern(BUDGET_API_PREFIX + BUDGET_EXPORT)
//                .build()
//                .condition()
//                .hasPermission(SecurityPermissions.EXPORT_BUDGET)
//                .hasPermission(SecurityPermissions.ALL_ACCESS)
//                .end();
//    }
}
