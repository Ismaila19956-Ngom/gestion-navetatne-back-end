package com.webgram.dgpsn.security.rules;

import com.webgram.dgpsn.security.SecurityPermissions;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WorkflowValidationHistoriqueRule {
    static final String WORKFLOW_HISTORIQUE_API_PREFIX = "/workflow-validation-historiques";
    static final String WORKFLOW_HISTORIQUE_ID = "/{historiqueId}";

    @Bean
    public SecurityRule createWorkflowHistorique() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(WORKFLOW_HISTORIQUE_API_PREFIX)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.VALIDATE_FLUX_TRESORERIE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readWorkflowHistorique() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(WORKFLOW_HISTORIQUE_API_PREFIX + WORKFLOW_HISTORIQUE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_WORKFLOW_HISTORIQUE)
                .hasPermission(SecurityPermissions.ADD_WORKFLOW_HISTORIQUE)
                .hasPermission(SecurityPermissions.EDIT_WORKFLOW_HISTORIQUE)
                .hasPermission(SecurityPermissions.DELETE_WORKFLOW_HISTORIQUE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllWorkflowHistorique() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(WORKFLOW_HISTORIQUE_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_WORKFLOW_HISTORIQUE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateWorkflowHistorique() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(WORKFLOW_HISTORIQUE_API_PREFIX + WORKFLOW_HISTORIQUE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_WORKFLOW_HISTORIQUE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule deleteWorkflowHistorique() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(WORKFLOW_HISTORIQUE_API_PREFIX + WORKFLOW_HISTORIQUE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_WORKFLOW_HISTORIQUE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

}
