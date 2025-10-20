package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QueryRule {
    static final String QUERY_API_PREFIX = "/queries";
    static final String QUERY_ID = "/{queryId}";

    static  final String QUERY_DOWNLOAD_FILE="/{id}/_download";

   static final String QUERY_IMPORT ="/import";
   static final String QUERY_EXPORT ="/export";



    @Bean
    public SecurityRule createQuery() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(QUERY_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_QUERY)
                .hasPermission(SecurityPermissions.READ_PROJECT_ACTIVITY_MONITORING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readQuery() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(QUERY_API_PREFIX + QUERY_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_QUERY)
                .hasPermission(SecurityPermissions.ADD_QUERY)
                .hasPermission(SecurityPermissions.EDIT_QUERY)
                .hasPermission(SecurityPermissions.DELETE_QUERY)
                .hasPermission(SecurityPermissions.READ_PROJECT_ACTIVITY_MONITORING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readQuerys() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(QUERY_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_QUERY)
                .hasPermission(SecurityPermissions.ADD_QUERY)
                .hasPermission(SecurityPermissions.EDIT_QUERY)
                .hasPermission(SecurityPermissions.DELETE_QUERY)
                .hasPermission(SecurityPermissions.READ_PROJECT_ACTIVITY_MONITORING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateQuery() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(QUERY_API_PREFIX + QUERY_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_QUERY)
                .hasPermission(SecurityPermissions.READ_PROJECT_ACTIVITY_MONITORING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule QuerysImport() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(QUERY_API_PREFIX + QUERY_IMPORT)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.IMPORT_QUERY)
                .hasPermission(SecurityPermissions.READ_PROJECT_ACTIVITY_MONITORING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule QuerysExport() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(QUERY_API_PREFIX + QUERY_EXPORT)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EXPORT_QUERY)
                .hasPermission(SecurityPermissions.READ_PROJECT_ACTIVITY_MONITORING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteQuery() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(QUERY_API_PREFIX + QUERY_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_QUERY)
                .hasPermission(SecurityPermissions.READ_PROJECT_ACTIVITY_MONITORING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule downloadQuerysFile() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(QUERY_API_PREFIX + QUERY_DOWNLOAD_FILE)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_QUERY)
                .hasPermission(SecurityPermissions.READ_PROJECT_ACTIVITY_MONITORING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule viewQuery() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(QUERY_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_PROJECT_ACTIVITY_MONITORING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

}
