package sn.naavetane.backend.security.rules;

import sn.naavetane.backend.security.SecurityPermissions;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WorkflowRule {
    static final String WORKFLOW_API_PREFIX = "/workflows";
    static final String WORKFLOW_ID = "/{workflowId}";

    @Bean
    public SecurityRule createWorkflow() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(WORKFLOW_API_PREFIX)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.ADD_WORKFLOW)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readWorkflow() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(WORKFLOW_API_PREFIX + WORKFLOW_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.READ_AFFECTATION)
//                .hasPermission(SecurityPermissions.READ_WORKFLOW)
//                .hasPermission(SecurityPermissions.ADD_WORKFLOW)
//                .hasPermission(SecurityPermissions.EDIT_WORKFLOW)
//                .hasPermission(SecurityPermissions.DELETE_WORKFLOW)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllWorkflow() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(WORKFLOW_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateWorkflow() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(WORKFLOW_API_PREFIX + WORKFLOW_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.EDIT_WORKFLOW)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteWorkflow() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(WORKFLOW_API_PREFIX + WORKFLOW_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.DELETE_WORKFLOW)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

}
