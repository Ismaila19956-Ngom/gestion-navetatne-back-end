package com.webgram.dgpsn.security.rules;

import com.webgram.dgpsn.security.SecurityPermissions;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WorkflowStepValidationRule {
    static final String WORKFLOW_STEP_VALIDATION_API_PREFIX = "/workflow-step-validations";
    static final String WORKFLOW_STEP_VALIDATION_ID = "/{workflowStepValidationId}";
    static final String WORKFLOW_CONFI_USER = "/config-user";

    @Bean
    public SecurityRule createWorkflowStepValidation() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(WORKFLOW_STEP_VALIDATION_API_PREFIX)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.ADD_WORKFLOW_STEP_VALIDATION)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readWorkflowStepValidation() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(WORKFLOW_STEP_VALIDATION_API_PREFIX + WORKFLOW_STEP_VALIDATION_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.READ_AFFECTATION)
//                .hasPermission(SecurityPermissions.READ_WORKFLOW_STEP_VALIDATION)
//                .hasPermission(SecurityPermissions.ADD_WORKFLOW_STEP_VALIDATION)
//                .hasPermission(SecurityPermissions.EDIT_WORKFLOW_STEP_VALIDATION)
//                .hasPermission(SecurityPermissions.DELETE_WORKFLOW_STEP_VALIDATION)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllWorkflowStepValidation() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(WORKFLOW_STEP_VALIDATION_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateWorkflowStepValidation() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(WORKFLOW_STEP_VALIDATION_API_PREFIX + WORKFLOW_STEP_VALIDATION_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.EDIT_WORKFLOW_STEP_VALIDATION)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteWorkflowStepValidation() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(WORKFLOW_STEP_VALIDATION_API_PREFIX + WORKFLOW_STEP_VALIDATION_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.DELETE_WORKFLOW_STEP_VALIDATION)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readWorkflowUsers() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(WORKFLOW_STEP_VALIDATION_API_PREFIX + WORKFLOW_CONFI_USER)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

}
