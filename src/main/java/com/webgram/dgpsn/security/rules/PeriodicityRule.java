package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PeriodicityRule {
    static final String PERIODICITY_API_PREFIX = "/periodicities";
    static final String PERIODICITY_ID = "/{periodicityId}";
    
    @Bean
    public SecurityRule createPeriodicity() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(PERIODICITY_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_PERIODICITY)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readPeridicity() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(PERIODICITY_API_PREFIX + PERIODICITY_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_PERIODICITY)
                .hasPermission(SecurityPermissions.ADD_PERIODICITY)
                .hasPermission(SecurityPermissions.EDIT_PERIODICITY)
                .hasPermission(SecurityPermissions.DELETE_PERIODICITY)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllPeriodicity() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(PERIODICITY_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_PERIODICITY)
                .hasPermission(SecurityPermissions.ADD_PERIODICITY)
                .hasPermission(SecurityPermissions.EDIT_PERIODICITY)
                .hasPermission(SecurityPermissions.DELETE_PERIODICITY)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updatePeriodicity() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(PERIODICITY_API_PREFIX + PERIODICITY_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_PERIODICITY)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule deletePeriodicity() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(PERIODICITY_API_PREFIX + PERIODICITY_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_PERIODICITY)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}
