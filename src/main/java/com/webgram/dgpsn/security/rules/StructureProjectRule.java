package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StructureProjectRule {
    static final String SUPERVISION_EXECUTION_PREFIX = "/partner-project";
    static final String SUPERVISION_EXECUTION_STRUCTURE_ID = "/{partnerProjectId}";
    static final String SUPERVISION_EXECUTION_STRUCTURE_ID_POST ="/many";

//    static  final String REVIEW_DOWNLOAD_FILE="/{id}/_download";
//
//   static final String REVIEW_IMPORT ="/import";
   static final String REPORTING_BY_STRUCTURE ="/reportingByStructure";
    static final String STRUCTURE_ID ="/{structureId}";



    @Bean
    public SecurityRule addPartnerToProject() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(SUPERVISION_EXECUTION_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_SUPERVISION_EXECUTION_STRUCTURE)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readPartnerProject() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern( SUPERVISION_EXECUTION_PREFIX + SUPERVISION_EXECUTION_STRUCTURE_ID )
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_SUPERVISION_EXECUTION_STRUCTURE)
                .hasPermission(SecurityPermissions.READ_SUPERVISION_EXECUTION_STRUCTURE)
                .hasPermission(SecurityPermissions. EDIT_SUPERVISION_EXECUTION_STRUCTURE)
                .hasPermission(SecurityPermissions.DELETE_SUPERVISION_EXECUTION_STRUCTURE)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllPartnerByProject() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(SUPERVISION_EXECUTION_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_SUPERVISION_EXECUTION_STRUCTURE)
                .hasPermission(SecurityPermissions.READ_SUPERVISION_EXECUTION_STRUCTURE)
                .hasPermission(SecurityPermissions. EDIT_SUPERVISION_EXECUTION_STRUCTURE)
                .hasPermission(SecurityPermissions.DELETE_SUPERVISION_EXECUTION_STRUCTURE)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updatePartnerProject() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(SUPERVISION_EXECUTION_PREFIX + SUPERVISION_EXECUTION_STRUCTURE_ID )
                .build()
                .condition()
                .hasPermission(SecurityPermissions. EDIT_SUPERVISION_EXECUTION_STRUCTURE)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deletePartnerToProject() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(SUPERVISION_EXECUTION_PREFIX + SUPERVISION_EXECUTION_STRUCTURE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_SUPERVISION_EXECUTION_STRUCTURE)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule getReportingByStructrucure() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(SUPERVISION_EXECUTION_PREFIX+REPORTING_BY_STRUCTURE+STRUCTURE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_TDB_RESUME)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.READ_TDB_RESUME)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

  

 
}
