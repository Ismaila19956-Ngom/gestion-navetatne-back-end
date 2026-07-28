package sn.naavetane.backend.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import sn.naavetane.backend.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SourceRule {
    static final String SOURCE_PREFIX = "/sources";
    static final String SOURCE_ID = "/{sourceId}";

//    static  final String REVIEW_DOWNLOAD_FILE="/{id}/_download";
//
//   static final String REVIEW_IMPORT ="/import";
//   static final String REVIEW_EXPORT ="/export";



    @Bean
    public SecurityRule createSource() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(SOURCE_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_SOURCE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readSource() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(SOURCE_PREFIX + SOURCE_ID )
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_SOURCE)
                .hasPermission(SecurityPermissions.READ_SOURCE)
                .hasPermission(SecurityPermissions. EDIT_SOURCE)
                .hasPermission(SecurityPermissions.DELETE_SOURCE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readSources() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(SOURCE_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_SOURCE)
                .hasPermission(SecurityPermissions.READ_SOURCE)
                .hasPermission(SecurityPermissions. EDIT_SOURCE)
                .hasPermission(SecurityPermissions.DELETE_SOURCE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateSource() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(SOURCE_PREFIX + SOURCE_ID )
                .build()
                .condition()
                .hasPermission(SecurityPermissions. EDIT_SOURCE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

   

  

 
}
