package com.webgram.dgpsn.security.rules;

import com.webgram.dgpsn.security.SecurityPermissions;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WorkflowStepRule {
    static final String WORKFLOW_STEP_API_PREFIX = "/workflow-steps";
    static final String WORKFLOW_STEP_ID = "/{workflowStepId}";

    @Bean
    public SecurityRule createWorkflowStep() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(WORKFLOW_STEP_API_PREFIX)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.ADD_WORKFLOW_STEP)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readWorkflowStep() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(WORKFLOW_STEP_API_PREFIX + WORKFLOW_STEP_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.READ_AFFECTATION)
//                .hasPermission(SecurityPermissions.READ_WORKFLOW_STEP)
//                .hasPermission(SecurityPermissions.ADD_WORKFLOW_STEP)
//                .hasPermission(SecurityPermissions.EDIT_WORKFLOW_STEP)
//                .hasPermission(SecurityPermissions.DELETE_WORKFLOW_STEP)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllWorkflowStep() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(WORKFLOW_STEP_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateWorkflowStep() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(WORKFLOW_STEP_API_PREFIX + WORKFLOW_STEP_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.EDIT_WORKFLOW_STEP)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteWorkflowStep() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(WORKFLOW_STEP_API_PREFIX + WORKFLOW_STEP_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.DELETE_WORKFLOW_STEP)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

}
