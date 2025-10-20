package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FundingConfigRule {
    static final String  FUNDINGCONFIG_API_PREFIX = "/fundingConfig";
    static final String  FUNDINGCONFI_ID = "/{fundingConfigId}";


    @Bean
    public SecurityRule AddFindingConfig() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(FUNDINGCONFIG_API_PREFIX )
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_FUNDING_CONFIG)
                .hasPermission(SecurityPermissions.READ_PROJECT_FINANCEMENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }


    @Bean
    public SecurityRule readAllFindingConfigByActivity() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(FUNDINGCONFIG_API_PREFIX )
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_FUNDING_CONFIG)
                .hasPermission(SecurityPermissions.ADD_FUNDING_CONFIG)
                .hasPermission(SecurityPermissions.EDIT_FUNDING_CONFIG)
                .hasPermission(SecurityPermissions.DELETE_FUNDING_CONFIG)
                .hasPermission(SecurityPermissions.READ_PROJECT_FINANCEMENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateFundingConfig() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(FUNDINGCONFIG_API_PREFIX +  FUNDINGCONFI_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_FUNDING_CONFIG)
                .hasPermission(SecurityPermissions.READ_PROJECT_FINANCEMENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteFundingConfig() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(FUNDINGCONFIG_API_PREFIX +  FUNDINGCONFI_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_FUNDING_CONFIG)
                .hasPermission(SecurityPermissions.READ_PROJECT_FINANCEMENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

}
