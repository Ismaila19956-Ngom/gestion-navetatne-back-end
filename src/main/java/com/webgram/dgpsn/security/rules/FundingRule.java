package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FundingRule {
    static final String  Funding_API_PREFIX = "/fundings";
    static final String Funding_ID = "/{fundingId}";
    static final String Funding_IMPORT = "/import";
    static final String Funding_EXPORT = "/export/excel";

    @Bean
    public SecurityRule createFunding() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(Funding_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_FUNDING)
                .hasPermission(SecurityPermissions.READ_PROJECT_FINANCEMENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readFunding() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(Funding_API_PREFIX + Funding_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_FUNDING)
                .hasPermission(SecurityPermissions.ADD_FUNDING)
                .hasPermission(SecurityPermissions.EDIT_FUNDING)
                .hasPermission(SecurityPermissions.DELETE_FUNDING)
                .hasPermission(SecurityPermissions.READ_PROJECT_FINANCEMENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readFundings() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(Funding_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_FUNDING)
                .hasPermission(SecurityPermissions.ADD_FUNDING)
                .hasPermission(SecurityPermissions.EDIT_FUNDING)
                .hasPermission(SecurityPermissions.DELETE_FUNDING)
                .hasPermission(SecurityPermissions.READ_PROJECT_FINANCEMENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateFunding() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(Funding_API_PREFIX + Funding_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_FUNDING)
                .hasPermission(SecurityPermissions.READ_PROJECT_FINANCEMENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule deleteFunding() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(Funding_API_PREFIX + Funding_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_FUNDING)
                .hasPermission(SecurityPermissions.READ_PROJECT_FINANCEMENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule importFunding() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(Funding_IMPORT)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.IMPORT_FUNDING)
                .hasPermission(SecurityPermissions.READ_PROJECT_FINANCEMENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule exportFunding() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(Funding_API_PREFIX + Funding_EXPORT)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EXPORT_FUNDING)
                .hasPermission(SecurityPermissions.READ_PROJECT_FINANCEMENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}
