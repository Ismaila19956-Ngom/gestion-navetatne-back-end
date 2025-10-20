package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AssignmentIssueLogRule {
    static final String ASSIGNMENT_ISSUE_LOG_API_PREFIX = "/assignmentIssuelogs";
    static final String ASSIGNMENT_ISSUE_LOG_ID = "/{assignmentIssuelogId}";
    static final String NEW_PREFIX = "/new";


    @Bean
    public SecurityRule createAssimentIssueLogs() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(ASSIGNMENT_ISSUE_LOG_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_ASSIGNMENT_ISSUE_LOG)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule createAssimentIssueLog() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(ASSIGNMENT_ISSUE_LOG_API_PREFIX + NEW_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_ASSIGNMENT_ISSUE_LOG)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAssignmentIssuelogs() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(ASSIGNMENT_ISSUE_LOG_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_ASSIGNMENT_ISSUE_LOG)
                .hasPermission(SecurityPermissions.ADD_ASSIGNMENT_ISSUE_LOG)
                .hasPermission(SecurityPermissions.EDIT_ASSIGNMENT_ISSUE_LOG)
                .hasPermission(SecurityPermissions.DELETE_ASSIGNMENT_ISSUE_LOG)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteAssignmentIssuelog() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(ASSIGNMENT_ISSUE_LOG_API_PREFIX + ASSIGNMENT_ISSUE_LOG_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_ASSIGNMENT_ISSUE_LOG)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

}
