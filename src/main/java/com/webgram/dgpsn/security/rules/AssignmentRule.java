package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AssignmentRule {
    static final String ASSIGNMENT_API_PREFIX = "/assignments";
    static final String ASSIGNMENT_ID = "/{assignmentId}";
    static final String CREATE_ASSIGNMENT_ACTIVITY = "/assignmentActivity";
    static final String ASSIGNMENT_ACTIVITY = "/assignmentActivity";
//    static final String ASSIGNMENT_ACTIVITY_ID = "/{assignmentActivityId}";


    static final String DOWNLOAD_PREFIX = "/_download";
    static final String IMPORT_API_PREFIX = "/import";
    static final String EXPORT_API_PREFIX = "/export";

    @Bean
    public SecurityRule createAssignment() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(ASSIGNMENT_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_ASSIGNMENT)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.READ_PROJECT_ACTIVITY_MONITORING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule createAssignmentActivity() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(ASSIGNMENT_API_PREFIX + CREATE_ASSIGNMENT_ACTIVITY)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_ASSIGNMENT)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.READ_PROJECT_ACTIVITY_MONITORING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAssignment() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(ASSIGNMENT_API_PREFIX + ASSIGNMENT_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_ASSIGNMENT)
                .hasPermission(SecurityPermissions.ADD_ASSIGNMENT)
                .hasPermission(SecurityPermissions.EDIT_ASSIGNMENT)
                .hasPermission(SecurityPermissions.DELETE_ASSIGNMENT)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.READ_PROJECT_ACTIVITY_MONITORING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllAssignments() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(ASSIGNMENT_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_ASSIGNMENT)
                .hasPermission(SecurityPermissions.ADD_ASSIGNMENT)
                .hasPermission(SecurityPermissions.EDIT_ASSIGNMENT)
                .hasPermission(SecurityPermissions.DELETE_ASSIGNMENT)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.READ_PROJECT_ACTIVITY_MONITORING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateAssignment() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(ASSIGNMENT_API_PREFIX + ASSIGNMENT_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_ASSIGNMENT)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.READ_PROJECT_ACTIVITY_MONITORING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateAssignmentActivity() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(ASSIGNMENT_API_PREFIX + ASSIGNMENT_ACTIVITY+ ASSIGNMENT_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_ASSIGNMENT)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.READ_PROJECT_ACTIVITY_MONITORING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule deleteAssignment() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(ASSIGNMENT_API_PREFIX +ASSIGNMENT_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_ASSIGNMENT)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.READ_PROJECT_ACTIVITY_MONITORING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule importAssignement() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(ASSIGNMENT_API_PREFIX + IMPORT_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.IMPORT_ASSIGNMENT)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.READ_PROJECT_ACTIVITY_MONITORING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule exportAssignment() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(ASSIGNMENT_API_PREFIX + EXPORT_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EXPORT_ASSIGNMENT)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.READ_PROJECT_ACTIVITY_MONITORING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule readFileAssignment() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(ASSIGNMENT_API_PREFIX+ ASSIGNMENT_ID + DOWNLOAD_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_ASSIGNMENT)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.READ_PROJECT_ACTIVITY_MONITORING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule viewAssignment() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(ASSIGNMENT_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_ASSIGNMENT)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.READ_PROJECT_ACTIVITY_MONITORING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}
