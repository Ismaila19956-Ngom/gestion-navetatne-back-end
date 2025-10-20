package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FundingExecutionRule {
    static final String  FUNDING_EXECUTION_API_PREFIX = "/fundingConfig";
    static final String  FUNDING_EXECUTION_ID = "/{fundingConfigId}";


    @Bean
    public SecurityRule AddFindingExecution() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(FUNDING_EXECUTION_API_PREFIX )
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_FUNDING_EXECUTION)
                .hasPermission(SecurityPermissions.READ_PROJECT_FINANCEMENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule viewFindingExecution() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(FUNDING_EXECUTION_API_PREFIX )
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_PROJECT_FINANCEMENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }


    @Bean
    public SecurityRule readAllFindingExecutionByActivity() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(FUNDING_EXECUTION_API_PREFIX )
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_FUNDING_EXECUTION)
                .hasPermission(SecurityPermissions.ADD_FUNDING_EXECUTION)
                .hasPermission(SecurityPermissions.EDIT_FUNDING_EXECUTION)
                .hasPermission(SecurityPermissions.DELETE_FUNDING_EXECUTION)
                .hasPermission(SecurityPermissions.READ_PROJECT_FINANCEMENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateFundingExecution() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(FUNDING_EXECUTION_API_PREFIX +  FUNDING_EXECUTION_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_FUNDING_EXECUTION)
                .hasPermission(SecurityPermissions.READ_PROJECT_FINANCEMENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteFundingExecution() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(FUNDING_EXECUTION_API_PREFIX +  FUNDING_EXECUTION_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_FUNDING_EXECUTION)
                .hasPermission(SecurityPermissions.READ_PROJECT_FINANCEMENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

}
