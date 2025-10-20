package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RiskRule {
    static final String RISK_PREFIX = "/risks";
    static final String RISK_ID = "/{riskId}";

//    static  final String REVIEW_DOWNLOAD_FILE="/{id}/_download";
//
   static final String RISK_IMPORT ="/import";
   static final String RISK_EXPORT ="/export/excel";



    @Bean
    public SecurityRule createRisk() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(RISK_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_REVIEW)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readRisk() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(RISK_PREFIX + RISK_ID )
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_RISK)
                .hasPermission(SecurityPermissions.READ_RISK)
                .hasPermission(SecurityPermissions. EDIT_RISK)
                .hasPermission(SecurityPermissions.DELETE_RISK)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readRisks() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(RISK_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_RISK)
                .hasPermission(SecurityPermissions.READ_RISK)
                .hasPermission(SecurityPermissions. EDIT_RISK)
                .hasPermission(SecurityPermissions.DELETE_RISK)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateRisk() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(RISK_PREFIX + RISK_ID )
                .build()
                .condition()
                .hasPermission(SecurityPermissions. EDIT_RISK)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule deleteRisk() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(RISK_PREFIX + RISK_ID )
                .build()
                .condition()
                .hasPermission(SecurityPermissions. DELETE_RISK)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }


    @Bean
    public SecurityRule importRisks() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(RISK_IMPORT)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.IMPORT_RISK)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule exportRisks() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(RISK_EXPORT)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EXPORT_RISK)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
  

 
}
