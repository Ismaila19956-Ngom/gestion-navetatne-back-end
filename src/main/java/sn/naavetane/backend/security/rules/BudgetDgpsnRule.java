package sn.naavetane.backend.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import sn.naavetane.backend.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BudgetDgpsnRule {
    static final String BUDGET_DGPSN_API_PREFIX = "/budgetGlobal";
    static final String BUDGET_DGPSN_ID = "/{budgetId}";
    static final String BUDGET_DGPSN_SYNTHESE = "/synthese";

    @Bean
    public SecurityRule addBudgetDgpsn() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(BUDGET_DGPSN_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_BUDGET)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule getSynthese() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(BUDGET_DGPSN_API_PREFIX + BUDGET_DGPSN_ID + BUDGET_DGPSN_SYNTHESE)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_BUDGET)
                .hasPermission(SecurityPermissions.ADD_BUDGET)
                .hasPermission(SecurityPermissions.EDIT_BUDGET)
                .hasPermission(SecurityPermissions.DELETE_BUDGET)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllBudgetDgpsn() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(BUDGET_DGPSN_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_BUDGET)
                .hasPermission(SecurityPermissions.ADD_BUDGET)
                .hasPermission(SecurityPermissions.EDIT_BUDGET)
                .hasPermission(SecurityPermissions.DELETE_BUDGET)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateBudgetDgpsn() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(BUDGET_DGPSN_API_PREFIX + BUDGET_DGPSN_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_BUDGET)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteBudgetDgpsn() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(BUDGET_DGPSN_API_PREFIX + BUDGET_DGPSN_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_BUDGET)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}
