package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InterventionRule {
    static final String INTERVENTION_API_PREFIX = "/interventions";
    static final String INTERVENTION_ID = "/{interventionId}";

    @Bean
    public SecurityRule createIntervention() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(INTERVENTION_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateIntervention() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(INTERVENTION_API_PREFIX + INTERVENTION_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readIntervention() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(INTERVENTION_API_PREFIX + INTERVENTION_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllIntervention() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(INTERVENTION_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteIntervention() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(INTERVENTION_API_PREFIX + INTERVENTION_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}
