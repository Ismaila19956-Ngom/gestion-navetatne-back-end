package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EvaluationFinanciereRule {
    static final String EVALUATION_FINANCE_API_PREFIX = "/evaluationperformances";
    static final String EVALUATION_FINANCE_ID = "/{evaluationperformanceId}";
    static final String EXPORT_PREFIX = "/export";
    static final  String EVALUATION_ENTREPRISE_BY_ID ="/entreprise/{entrepriseId}";

    
    @Bean
    public SecurityRule createFinance() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern( EVALUATION_FINANCE_API_PREFIX)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.ADD_EVALUATION_FINANCE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readFinance() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern( EVALUATION_FINANCE_API_PREFIX + EVALUATION_FINANCE_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.READ_EVALUATION_FINANCE)
//                .hasPermission(SecurityPermissions.ADD_EVALUATION_FINANCE)
//                .hasPermission(SecurityPermissions.EDIT_EVALUATION_FINANCE)
//                .hasPermission(SecurityPermissions.DELETE_EVALUATION_FINANCE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readByEntrepriseId() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern( EVALUATION_FINANCE_API_PREFIX +  EVALUATION_ENTREPRISE_BY_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.READ_EVALUATION_FINANCE)
//                .hasPermission(SecurityPermissions.ADD_EVALUATION_FINANCE)
//                .hasPermission(SecurityPermissions.EDIT_EVALUATION_FINANCE)
//                .hasPermission(SecurityPermissions.DELETE_EVALUATION_FINANCE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }


    @Bean
    public SecurityRule readAllFinance() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(EVALUATION_FINANCE_API_PREFIX )
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.READ_EVALUATION_FINANCE)
//                .hasPermission(SecurityPermissions.ADD_EVALUATION_FINANCE)
//                .hasPermission(SecurityPermissions.EDIT_EVALUATION_FINANCE)
//                .hasPermission(SecurityPermissions.DELETE_EVALUATION_FINANCE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule exportEvaluationperformance() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(EVALUATION_FINANCE_API_PREFIX + EXPORT_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EXPORT_AGENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateFinance() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(EVALUATION_FINANCE_API_PREFIX + EVALUATION_FINANCE_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.EDIT_EVALUATION_FINANCE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteFinance() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(EVALUATION_FINANCE_API_PREFIX + EVALUATION_FINANCE_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.DELETE_EVALUATION_FINANCE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}
