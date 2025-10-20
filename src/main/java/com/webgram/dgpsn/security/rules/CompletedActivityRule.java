package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompletedActivityRule {
    static final String COMPLETED_ACTIVITY_API_PREFIX = "/completedActivity";
    static final String COMPLETED_ACTIVITY_ID = "/{completedActivityId}";

    @Bean
    public SecurityRule createCompletedActivity() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(COMPLETED_ACTIVITY_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_COMPLETED_ACTIVITY)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readCompletedActivity() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(COMPLETED_ACTIVITY_API_PREFIX + COMPLETED_ACTIVITY_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_COMPLETED_ACTIVITY)
                .hasPermission(SecurityPermissions.ADD_COMPLETED_ACTIVITY)
                .hasPermission(SecurityPermissions.EDIT_COMPLETED_ACTIVITY)
                .hasPermission(SecurityPermissions.DELETE_COMPLETED_ACTIVITY)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllCompletedActivity() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(COMPLETED_ACTIVITY_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_COMPLETED_ACTIVITY)
                .hasPermission(SecurityPermissions.ADD_COMPLETED_ACTIVITY)
                .hasPermission(SecurityPermissions.EDIT_COMPLETED_ACTIVITY)
                .hasPermission(SecurityPermissions.DELETE_COMPLETED_ACTIVITY)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateCompletedActivity() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(COMPLETED_ACTIVITY_API_PREFIX + COMPLETED_ACTIVITY_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_COMPLETED_ACTIVITY)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule deleteCompletedActivity() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(COMPLETED_ACTIVITY_API_PREFIX + COMPLETED_ACTIVITY_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_COMPLETED_ACTIVITY)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

}
