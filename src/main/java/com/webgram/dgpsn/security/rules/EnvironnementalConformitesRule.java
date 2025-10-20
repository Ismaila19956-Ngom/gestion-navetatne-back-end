package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EnvironnementalConformitesRule {
    static final String ENV_CONFORMITE_API_PREFIX = "/environmental-conformites-reglementaire";
    static final String ENV_CONFORMITE_ID = "/{environmentalConformiteId}";

    @Bean
    public SecurityRule addConformiteEnvironmental() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(ENV_CONFORMITE_API_PREFIX)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.ADD_ENV_CONFORMITES)
                .hasPermission(SecurityPermissions.READ_PROJECT_ESG)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllConformiteEnvironmentalProject() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(ENV_CONFORMITE_API_PREFIX + ENV_CONFORMITE_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.READ_ENV_CONFORMITES)
                .hasPermission(SecurityPermissions.READ_PROJECT_ESG)
//                .hasPermission(SecurityPermissions.ADD_ENV_CONFORMITES)
//                .hasPermission(SecurityPermissions.EDIT_ENV_CONFORMITES)
//                .hasPermission(SecurityPermissions.DELETE_ENV_CONFORMITES)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readEnvironnementConformite() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(ENV_CONFORMITE_API_PREFIX)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.READ_ENV_CONFORMITES)
                .hasPermission(SecurityPermissions.READ_PROJECT_ESG)
//                .hasPermission(SecurityPermissions.ADD_ENV_CONFORMITES)
//                .hasPermission(SecurityPermissions.EDIT_ENV_CONFORMITES)
//                .hasPermission(SecurityPermissions.DELETE_ENV_CONFORMITES)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateConformiteEnvironnemental() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(ENV_CONFORMITE_API_PREFIX + ENV_CONFORMITE_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.EDIT_ENV_CONFORMITES)
                .hasPermission(SecurityPermissions.READ_PROJECT_ESG)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteConformiteEnvironnemental() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(ENV_CONFORMITE_API_PREFIX + ENV_CONFORMITE_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.DELETE_ENV_CONFORMITES)
                .hasPermission(SecurityPermissions.READ_PROJECT_ESG)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

}
