package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ManagementUnitRule {
    static final String PROJECT_API_PREFIX = "/management-unit";
    static final String PROJECT_API_EXPORT_EXCEL = "/export/excel";
    static final String PROJECT_API_EXPORT_PROJET_EXCEL = "/exportProjets/excel";

    static final String PROJECT_API_EXPORT_PDF = "/export/pdf";
    static final String PROJECT_ID = "/{managementUnitId}";
    static final String PROJECT_API_UPLOAD = "/upload-image";
    static final String PUBLISH_UNPUBLISH = "/publishUnpublish";
    static final String PUBLISHED = "/publish";
    static final String STATISTICS = "/statistics";
    static final String TREE = "/tree";
    static final String ADD_NODE = "/add";
    static final String PARENT_ID = "/{parentId}";
    static final String ALL = "/all";
    static final String PTBA = "/{managementUnitId}/ptba";
    static final String BUDGET_TREE = "/tree/budget/{budgetId}";

    @Bean
    public SecurityRule readTreeManagementUnitByBudgetId() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(PROJECT_API_PREFIX + BUDGET_TREE)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule generatePtba() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(PROJECT_API_PREFIX + PTBA)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .hasPermission(SecurityPermissions.READ_PROJECT_ACTIVITY_MONITORING)
                .hasPermission(SecurityPermissions.EXPORT_PROJECT)
                .end();
    }

    @Bean
    public SecurityRule createProjet() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(PROJECT_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_PROJECT_ACTIVITY_MONITORING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readPorjet() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(PROJECT_API_PREFIX + PROJECT_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .hasPermission(SecurityPermissions.READ_PROJECT_ACTIVITY_MONITORING)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .end();
    }

    @Bean
    public SecurityRule readAllProject() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(PROJECT_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .hasPermission(SecurityPermissions.READ_PROJECT)
                .hasPermission(SecurityPermissions.ADD_PROJECT)
                .hasPermission(SecurityPermissions.EDIT_PROJECT)
                .hasPermission(SecurityPermissions.IMPORT_PROJECT)
                .hasPermission(SecurityPermissions.DELETE_PROJECT)
                .hasPermission(SecurityPermissions.READ_PROJECT_ACTIVITY_MONITORING)
                .hasPermission(SecurityPermissions.EXPORT_PROJECT )
                .end();
    }

    @Bean
    public SecurityRule updateProjet() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(PROJECT_API_PREFIX + PROJECT_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .hasPermission(SecurityPermissions.READ_PROJECT_ACTIVITY_MONITORING)
                .hasPermission(SecurityPermissions.EDIT_PROJECT)
                .end();
    }

    @Bean
    public SecurityRule exportProject() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(PROJECT_API_PREFIX + PROJECT_API_EXPORT_EXCEL)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .hasPermission(SecurityPermissions.READ_PROJECT_ACTIVITY_MONITORING)
                .hasPermission(SecurityPermissions.EXPORT_PROJECT)
                .end();
    }

//    @Bean
//    public SecurityRule getTreeView() {
//        return SecurityRule.builder()
//                .httpMethod(HttpMethod.GET)
//                .apiPattern(PROJECT_API_PREFIX + LIST_TREE)
//                .build()
//                .condition()
//                .hasPermission(SecurityPermissions.ALL_ACCESS)
//                .hasPermission(SecurityPermissions.READ_PROJECT_ACTIVITY_MONITORING)
//                .hasPermission(SecurityPermissions.EXPORT_PROJECT)
//                .end();
//    }

    @Bean
    public SecurityRule exportProjets() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(PROJECT_API_PREFIX + PROJECT_API_EXPORT_PROJET_EXCEL)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .hasPermission(SecurityPermissions.EXPORT_PROJECT)
                .end();
    }

    @Bean
    public SecurityRule exportPdfProject() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(PROJECT_API_PREFIX + PROJECT_API_EXPORT_PDF)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .hasPermission(SecurityPermissions.READ_PROJECT_ACTIVITY_MONITORING)
                .hasPermission(SecurityPermissions.EXPORT_PROJECT)
                .end();
    }

    @Bean
    public SecurityRule deleteProjet() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(PROJECT_API_PREFIX + PROJECT_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .hasPermission(SecurityPermissions.READ_PROJECT_ACTIVITY_MONITORING)
                .hasPermission(SecurityPermissions.DELETE_PROJECT)
                .end();
    }

    @Bean
    public SecurityRule uploadImageProjet() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(PROJECT_API_PREFIX + PROJECT_API_UPLOAD + PROJECT_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_PROJECT_ACTIVITY_MONITORING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .hasPermission(SecurityPermissions.READ_PROJECT)
                .end();
    }

    @Bean
    public SecurityRule publishOrUnpublishProjet() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(PROJECT_API_PREFIX + PUBLISH_UNPUBLISH + PROJECT_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_PROJECT_ACTIVITY_MONITORING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readPublishedProjects() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(PROJECT_API_PREFIX + PUBLISHED)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_PROJECT_ACTIVITY_MONITORING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readStatistics() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(PROJECT_API_PREFIX + PUBLISHED)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .hasPermission(SecurityPermissions.READ_PROJECT)
                .hasPermission(SecurityPermissions.ADD_PROJECT)
                .hasPermission(SecurityPermissions.EDIT_PROJECT)
                .hasPermission(SecurityPermissions.IMPORT_PROJECT)
                .hasPermission(SecurityPermissions.DELETE_PROJECT)
                .hasPermission(SecurityPermissions.READ_PROJECT_ACTIVITY_MONITORING)
                .hasPermission(SecurityPermissions.EXPORT_PROJECT )
                .end();
    }

    @Bean
    public SecurityRule viewActivity() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(PROJECT_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .hasPermission(SecurityPermissions.READ_PROJECT_ACTIVITY_MONITORING)
                .end();
    }

    @Bean
    public SecurityRule readTreeManagementUnit() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(PROJECT_API_PREFIX + TREE + PROJECT_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule addNodeToTreeManagmentUnit() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(PROJECT_API_PREFIX + TREE + ADD_NODE + PARENT_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllProjectsWithTree() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(PROJECT_API_PREFIX + TREE + ALL)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}
