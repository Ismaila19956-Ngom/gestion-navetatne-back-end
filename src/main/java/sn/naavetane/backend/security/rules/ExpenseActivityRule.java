package sn.naavetane.backend.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import sn.naavetane.backend.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExpenseActivityRule {
    static final String  EXPENSE_ACTIVITY_API_PREFIX = "/depense";
    static final String EXPENSE_ACTIVITY_ID = "/{depenseId}";


    @Bean
    public SecurityRule etapeActivityAddToMission() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(EXPENSE_ACTIVITY_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_EXPENSE_ACTIVITY)
                .hasPermission(SecurityPermissions.READ_PROJECT_ACTIVITY_MONITORING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readEtapeActivity() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(EXPENSE_ACTIVITY_API_PREFIX + EXPENSE_ACTIVITY_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_EXPENSE_ACTIVITY)
                .hasPermission(SecurityPermissions.ADD_EXPENSE_ACTIVITY)
                .hasPermission(SecurityPermissions.EDIT_EXPENSE_ACTIVITY)
                .hasPermission(SecurityPermissions.DELETE_EXPENSE_ACTIVITY)
                .hasPermission(SecurityPermissions.READ_PROJECT_ACTIVITY_MONITORING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllEtapeActivityByProject() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(EXPENSE_ACTIVITY_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_EXPENSE_ACTIVITY)
                .hasPermission(SecurityPermissions.ADD_EXPENSE_ACTIVITY)
                .hasPermission(SecurityPermissions.EDIT_EXPENSE_ACTIVITY)
                .hasPermission(SecurityPermissions.DELETE_EXPENSE_ACTIVITY)
                .hasPermission(SecurityPermissions.READ_PROJECT_ACTIVITY_MONITORING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateEtapeActivity() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(EXPENSE_ACTIVITY_API_PREFIX + EXPENSE_ACTIVITY_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_EXPENSE_ACTIVITY)
                .hasPermission(SecurityPermissions.READ_PROJECT_ACTIVITY_MONITORING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteEtapeActivity() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(EXPENSE_ACTIVITY_API_PREFIX + EXPENSE_ACTIVITY_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_EXPENSE_ACTIVITY)
                .hasPermission(SecurityPermissions.READ_PROJECT_ACTIVITY_MONITORING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule viewActivityAddToMission() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(EXPENSE_ACTIVITY_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_PROJECT_ACTIVITY_MONITORING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }


}
