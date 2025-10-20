package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompletionRateRule {
    static final String COMPLETION_RATE_API_PREFIX = "/completion-rates";
    static final String COMPLETION_RATE_ID = "/{completionRateId}";
    static final String MANAGEMENT_UNIT_ID = "/{managementUnitId}";
    static final String YEAR = "/{year}";

    @Bean
    public SecurityRule createCompletionRate() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(COMPLETION_RATE_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_COMPLETIONRATE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.READ_PROJECT_ACTIVITY_MONITORING)
                .end();
    }

    @Bean
    public SecurityRule readCompletionRate() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(COMPLETION_RATE_API_PREFIX + COMPLETION_RATE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_COMPLETIONRATE)
                .hasPermission(SecurityPermissions.ADD_COMPLETIONRATE)
                .hasPermission(SecurityPermissions.EDIT_COMPLETIONRATE)
                .hasPermission(SecurityPermissions.DELETE_COMPLETIONRATE)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.READ_PROJECT_ACTIVITY_MONITORING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllCompletionRate() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(COMPLETION_RATE_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_COMPLETIONRATE)
                .hasPermission(SecurityPermissions.ADD_COMPLETIONRATE)
                .hasPermission(SecurityPermissions.EDIT_COMPLETIONRATE)
                .hasPermission(SecurityPermissions.DELETE_COMPLETIONRATE)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.READ_PROJECT_ACTIVITY_MONITORING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateCompletionRate() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(COMPLETION_RATE_API_PREFIX + COMPLETION_RATE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_COMPLETIONRATE)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.READ_PROJECT_ACTIVITY_MONITORING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule deleteCompletionRate() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(COMPLETION_RATE_API_PREFIX + COMPLETION_RATE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_COMPLETIONRATE)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.READ_PROJECT_ACTIVITY_MONITORING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readCompletionRateByManagementUnit() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(COMPLETION_RATE_API_PREFIX + MANAGEMENT_UNIT_ID + YEAR)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_COMPLETIONRATE)
                .hasPermission(SecurityPermissions.ADD_COMPLETIONRATE)
                .hasPermission(SecurityPermissions.EDIT_COMPLETIONRATE)
                .hasPermission(SecurityPermissions.DELETE_COMPLETIONRATE)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.READ_PROJECT_ACTIVITY_MONITORING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule viewCompletionRate() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(COMPLETION_RATE_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .hasPermission(SecurityPermissions.READ_PROJECT_ACTIVITY_MONITORING)
                .end();
    }

}
