package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GouvernanceIndicateurRule {
    static final String GOUV_INDICATEUR_API_PREFIX = "/gouvernance-indicateur";
    static final String GOUV_INDICATEUR_ID = "/{gouvernanceIndicateurId}";

    @Bean
    public SecurityRule addIndicateurGouvernance() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(GOUV_INDICATEUR_API_PREFIX)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.ADD_GOUV_INDICATEURS)
                .hasPermission(SecurityPermissions.READ_PROJECT_ESG)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllIndicateurGouvernanceProject() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(GOUV_INDICATEUR_API_PREFIX + GOUV_INDICATEUR_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.READ_GOUV_INDICATEURS)
//                .hasPermission(SecurityPermissions.ADD_GOUV_INDICATEURS)
                .hasPermission(SecurityPermissions.READ_PROJECT_ESG)
//                .hasPermission(SecurityPermissions.EDIT_GOUV_INDICATEURS)
//                .hasPermission(SecurityPermissions.DELETE_GOUV_INDICATEURS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readGouvernanceIndicateur() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(GOUV_INDICATEUR_API_PREFIX)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.READ_GOUV_INDICATEURS)
//                .hasPermission(SecurityPermissions.ADD_GOUV_INDICATEURS)
                .hasPermission(SecurityPermissions.READ_PROJECT_ESG)
//                .hasPermission(SecurityPermissions.EDIT_GOUV_INDICATEURS)
//                .hasPermission(SecurityPermissions.DELETE_GOUV_INDICATEURS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateIndicateurGouvernance() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(GOUV_INDICATEUR_API_PREFIX + GOUV_INDICATEUR_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.EDIT_GOUV_INDICATEURS)
                .hasPermission(SecurityPermissions.READ_PROJECT_ESG)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteIndicateurGouvernance() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(GOUV_INDICATEUR_API_PREFIX + GOUV_INDICATEUR_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.DELETE_GOUV_INDICATEURS)
                .hasPermission(SecurityPermissions.READ_PROJECT_ESG)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

}
