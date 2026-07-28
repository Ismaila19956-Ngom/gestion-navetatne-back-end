package sn.naavetane.backend.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import sn.naavetane.backend.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StructureRule {
    static final String STRUCTURE_PREFIX = "/structures";

    static final String EXPORT_EXCEL_PREFIX = "/export";
    static final String STRUCTURE_ID = "/{structureId}";

//    static  final String REVIEW_DOWNLOAD_FILE="/{id}/_download";
//
//   static final String REVIEW_IMPORT ="/import";
//   static final String REVIEW_EXPORT ="/export";



    @Bean
    public SecurityRule createStructure() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(STRUCTURE_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_STRUCTURE)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.READ_TDB_RESUME)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }


    @Bean
    public SecurityRule readStructure() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(STRUCTURE_PREFIX + STRUCTURE_ID )
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_STRUCTURE)
                .hasPermission(SecurityPermissions.READ_STRUCTURE)
                .hasPermission(SecurityPermissions. EDIT_STRUCTURE)
                .hasPermission(SecurityPermissions.DELETE_STRUCTURE)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readStructures() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(STRUCTURE_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_STRUCTURE)
                .hasPermission(SecurityPermissions.READ_STRUCTURE)
                .hasPermission(SecurityPermissions. EDIT_STRUCTURE)
                .hasPermission(SecurityPermissions.DELETE_STRUCTURE)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.READ_TDB_RESUME)
                .hasPermission(SecurityPermissions.READ_PTBA)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateStructure() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(STRUCTURE_PREFIX + STRUCTURE_ID )
                .build()
                .condition()
                .hasPermission(SecurityPermissions. EDIT_STRUCTURE)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.READ_TDB_RESUME)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule exportStructure() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(STRUCTURE_PREFIX + EXPORT_EXCEL_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.READ_TDB_RESUME)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }


    @Bean
    public SecurityRule deleteStructure() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(STRUCTURE_PREFIX + STRUCTURE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_STRUCTURE)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.READ_TDB_RESUME)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

   

  

 
}
