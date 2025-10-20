package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EvaluationPerformanceRule {
    static final String EVALUATION_PERFORMANCE_API_PREFIX = "/evaluationfinancieres";
    static final String EVALUATION_PERFORMANCE_ID = "/{evaluationfinanciereId}";
    static final String EXPORT_PREFIX = "/export";
    static final  String EVALUATION_ENTREPRISE_BY_ID ="/entreprise/{entrepriseId}";

    
    @Bean
    public SecurityRule createPerformance() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern( EVALUATION_PERFORMANCE_API_PREFIX)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.ADD_EVALUATION_PERFORMANCE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readPerformance() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern( EVALUATION_PERFORMANCE_API_PREFIX + EVALUATION_PERFORMANCE_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.READ_EVALUATION_PERFORMANCE)
//                .hasPermission(SecurityPermissions.ADD_EVALUATION_PERFORMANCE)
//                .hasPermission(SecurityPermissions.EDIT_EVALUATION_PERFORMANCE)
//                .hasPermission(SecurityPermissions.DELETE_EVALUATION_PERFORMANCE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readPerformanceByEntrepriseId() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern( EVALUATION_PERFORMANCE_API_PREFIX +  EVALUATION_ENTREPRISE_BY_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.READ_EVALUATION_PERFORMANCE)
//                .hasPermission(SecurityPermissions.ADD_EVALUATION_PERFORMANCE)
//                .hasPermission(SecurityPermissions.EDIT_EVALUATION_PERFORMANCE)
//                .hasPermission(SecurityPermissions.DELETE_EVALUATION_PERFORMANCE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }


    @Bean
    public SecurityRule readAllPerformance() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(EVALUATION_PERFORMANCE_API_PREFIX )
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.READ_EVALUATION_PERFORMANCE)
//                .hasPermission(SecurityPermissions.ADD_EVALUATION_PERFORMANCE)
//                .hasPermission(SecurityPermissions.EDIT_EVALUATION_PERFORMANCE)
//                .hasPermission(SecurityPermissions.DELETE_EVALUATION_PERFORMANCE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule exportEvaluationPerformance() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(EVALUATION_PERFORMANCE_API_PREFIX + EXPORT_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EXPORT_AGENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    
    @Bean
    public SecurityRule updatePerformance() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(EVALUATION_PERFORMANCE_API_PREFIX + EVALUATION_PERFORMANCE_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.EDIT_EVALUATION_PERFORMANCE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule deletePerformance() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(EVALUATION_PERFORMANCE_API_PREFIX + EVALUATION_PERFORMANCE_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.DELETE_EVALUATION_PERFORMANCE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}
