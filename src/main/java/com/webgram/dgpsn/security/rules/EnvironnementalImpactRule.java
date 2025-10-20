package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EnvironnementalImpactRule {
    static final String  ENV_IMPACT_API_PREFIX = "/environmental-impact";
    static final String ENV_IMPACT_ID = "/{environmentalImpactId}";

    @Bean
    public SecurityRule addImpactEnvironmental() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(ENV_IMPACT_API_PREFIX)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.ADD_ENV_IMPACTS)
                .hasPermission(SecurityPermissions.READ_PROJECT_ESG)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllImpactEnvironmentalProject() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(ENV_IMPACT_API_PREFIX + ENV_IMPACT_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.READ_ENV_IMPACTS)
                .hasPermission(SecurityPermissions.READ_PROJECT_ESG)
//                .hasPermission(SecurityPermissions.ADD_ENV_IMPACTS)
//                .hasPermission(SecurityPermissions.EDIT_ENV_IMPACTS)
//                .hasPermission(SecurityPermissions.DELETE_ENV_IMPACTS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readEnvironnementImpact() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(ENV_IMPACT_API_PREFIX)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.READ_ENV_IMPACTS)
                .hasPermission(SecurityPermissions.READ_PROJECT_ESG)
//                .hasPermission(SecurityPermissions.ADD_ENV_IMPACTS)
//                .hasPermission(SecurityPermissions.EDIT_ENV_IMPACTS)
//                .hasPermission(SecurityPermissions.DELETE_ENV_IMPACTS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateImpactEnvironnemental() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(ENV_IMPACT_API_PREFIX + ENV_IMPACT_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_PROJECT_ESG)
//                .hasPermission(SecurityPermissions.EDIT_ENV_IMPACTS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteImpactEnvironnemental() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(ENV_IMPACT_API_PREFIX + ENV_IMPACT_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_PROJECT_ESG)
//                .hasPermission(SecurityPermissions.DELETE_ENV_IMPACTS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule viewImpactEnvironmental() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(ENV_IMPACT_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_PROJECT_ESG)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

}
