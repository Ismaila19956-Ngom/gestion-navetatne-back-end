package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompanyEvaluationRule {
    static final String COMPANY_EVALUATION_API_PREFIX = "/companyEvaluation";
    static final String COMPANY_EVALUATION_ID = "/{companyEvaluationId}";
    static final String COMPANY_EVALUATION_MULTIPLE = "/multiple";

    @Bean
    public SecurityRule createCompanyEvaluation() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(COMPANY_EVALUATION_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_EVALUATION_STARTUP)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule createMultipleCompanyEvaluations() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(COMPANY_EVALUATION_API_PREFIX + COMPANY_EVALUATION_MULTIPLE)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_EVALUATION_STARTUP)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readCompanyEvaluation() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(COMPANY_EVALUATION_API_PREFIX + COMPANY_EVALUATION_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_EVALUATION_STARTUP)
                .hasPermission(SecurityPermissions.ADD_EVALUATION_STARTUP)
                .hasPermission(SecurityPermissions.EDIT_EVALUATION_STARTUP)
                .hasPermission(SecurityPermissions.DELETE_EVALUATION_STARTUP)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllCompanyEvaluations() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(COMPANY_EVALUATION_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_EVALUATION_STARTUP)
                .hasPermission(SecurityPermissions.ADD_EVALUATION_STARTUP)
                .hasPermission(SecurityPermissions.EDIT_EVALUATION_STARTUP)
                .hasPermission(SecurityPermissions.DELETE_EVALUATION_STARTUP)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateCompanyEvaluation() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(COMPANY_EVALUATION_API_PREFIX + COMPANY_EVALUATION_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_EVALUATION_STARTUP)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteCompanyEvaluation() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(COMPANY_EVALUATION_API_PREFIX + COMPANY_EVALUATION_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_EVALUATION_STARTUP)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}