package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GouvernanceConformitesRule {
    static final String GOUV_CONFORMITE_API_PREFIX = "/gouvernance-conformites-reglementaire";
    static final String GOUV_CONFORMITE_ID = "/{gouvernanceConformiteId}";

    @Bean
    public SecurityRule addConformiteGouvernance() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(GOUV_CONFORMITE_API_PREFIX)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.ADD_GOUV_CONFORMITES)
                .hasPermission(SecurityPermissions.READ_PROJECT_ESG)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllConformiteGouvernanceProject() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(GOUV_CONFORMITE_API_PREFIX + GOUV_CONFORMITE_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.READ_GOUV_CONFORMITES)
                .hasPermission(SecurityPermissions.READ_PROJECT_ESG)
//                .hasPermission(SecurityPermissions.ADD_GOUV_CONFORMITES)
//                .hasPermission(SecurityPermissions.EDIT_GOUV_CONFORMITES)
//                .hasPermission(SecurityPermissions.DELETE_GOUV_CONFORMITES)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readGouvernanceConformite() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(GOUV_CONFORMITE_API_PREFIX)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.READ_GOUV_CONFORMITES)
                .hasPermission(SecurityPermissions.READ_PROJECT_ESG)
//                .hasPermission(SecurityPermissions.ADD_GOUV_CONFORMITES)
//                .hasPermission(SecurityPermissions.EDIT_GOUV_CONFORMITES)
//                .hasPermission(SecurityPermissions.DELETE_GOUV_CONFORMITES)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateConformiteGouvernance() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(GOUV_CONFORMITE_API_PREFIX + GOUV_CONFORMITE_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.EDIT_GOUV_CONFORMITES)
                .hasPermission(SecurityPermissions.READ_PROJECT_ESG)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteConformiteGouvernance() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(GOUV_CONFORMITE_API_PREFIX + GOUV_CONFORMITE_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.DELETE_GOUV_CONFORMITES)
                .hasPermission(SecurityPermissions.READ_PROJECT_ESG)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

}
