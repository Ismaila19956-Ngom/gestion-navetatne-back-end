package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProlongationRule {
    static final String PROLONGATION_PREFIX = "/prolongations";
    static final String PROLONGATION_ID = "/{prolongationId}";

//    static  final String REVIEW_DOWNLOAD_FILE="/{id}/_download";
//
//   static final String REVIEW_IMPORT ="/import";
//   static final String REVIEW_EXPORT ="/export";



    @Bean
    public SecurityRule createProlongation() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(PROLONGATION_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_PROLONGATION)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readProlongation() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(PROLONGATION_PREFIX + PROLONGATION_ID )
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_PROLONGATION)
                .hasPermission(SecurityPermissions.READ_PROLONGATION)
                .hasPermission(SecurityPermissions. EDIT_PROLONGATION)
                .hasPermission(SecurityPermissions.DELETE_PROLONGATION)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readProlongations() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(PROLONGATION_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_PROLONGATION)
                .hasPermission(SecurityPermissions.READ_PROLONGATION)
                .hasPermission(SecurityPermissions. EDIT_PROLONGATION)
                .hasPermission(SecurityPermissions.DELETE_PROLONGATION)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateProlongation() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(PROLONGATION_PREFIX + PROLONGATION_ID )
                .build()
                .condition()
                .hasPermission(SecurityPermissions. EDIT_PROLONGATION)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteProlongation() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(PROLONGATION_PREFIX + PROLONGATION_ID )
                .build()
                .condition()
                .hasPermission(SecurityPermissions. DELETE_PROLONGATION)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

   

  

 
}
