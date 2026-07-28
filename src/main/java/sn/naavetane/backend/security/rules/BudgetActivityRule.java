package sn.naavetane.backend.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import sn.naavetane.backend.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BudgetActivityRule {
    static final String  BUDGET_ACTIVITY_API_PREFIX = "/budgetActivity";
    static final String BUDGET_ACTIVITY_ID = "/{budgetActivityId}";


    @Bean
    public SecurityRule budgetActivityAddToProject() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(BUDGET_ACTIVITY_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_BUDGET_ACTIVITY)
                .hasPermission(SecurityPermissions.READ_PROJECT_ACTIVITY_MONITORING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readBudgetActivity() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(BUDGET_ACTIVITY_API_PREFIX + BUDGET_ACTIVITY_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_BUDGET_ACTIVITY)
                .hasPermission(SecurityPermissions.ADD_BUDGET_ACTIVITY)
                .hasPermission(SecurityPermissions.EDIT_BUDGET_ACTIVITY)
                .hasPermission(SecurityPermissions.DELETE_BUDGET_ACTIVITY)
                .hasPermission(SecurityPermissions.READ_PROJECT_ACTIVITY_MONITORING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllBudgetActivityByProject() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(BUDGET_ACTIVITY_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_BUDGET_ACTIVITY)
                .hasPermission(SecurityPermissions.ADD_BUDGET_ACTIVITY)
                .hasPermission(SecurityPermissions.EDIT_BUDGET_ACTIVITY)
                .hasPermission(SecurityPermissions.DELETE_BUDGET_ACTIVITY)
                .hasPermission(SecurityPermissions.READ_PROJECT_ACTIVITY_MONITORING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateBudgetActivity() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(BUDGET_ACTIVITY_API_PREFIX + BUDGET_ACTIVITY_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_BUDGET_ACTIVITY)
                .hasPermission(SecurityPermissions.READ_PROJECT_ACTIVITY_MONITORING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteBudgetActivity() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(BUDGET_ACTIVITY_API_PREFIX + BUDGET_ACTIVITY_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_BUDGET_ACTIVITY)
                .hasPermission(SecurityPermissions.READ_PROJECT_ACTIVITY_MONITORING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule viewbudgetActivityAddToProjet() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(BUDGET_ACTIVITY_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_PROJECT_ACTIVITY_MONITORING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }


}
