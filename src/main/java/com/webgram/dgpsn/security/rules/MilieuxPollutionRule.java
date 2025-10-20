package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MilieuxPollutionRule {
    static final String MILIEUX_POLLUTION_API_PREFIX = "/pollution-milieux";
    static final String MILIEUX_POLLUTION_ID = "/{milieuxId}";
    static final String UPDATE_STATUT = "/{id}/statut";

    @Bean
    public SecurityRule createMilieuxPollution() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(MILIEUX_POLLUTION_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_POLLUTION)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllMilieuxPollution() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(MILIEUX_POLLUTION_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_POLLUTION)
                .hasPermission(SecurityPermissions.ADD_POLLUTION)
                .hasPermission(SecurityPermissions.EDIT_POLLUTION)
                .hasPermission(SecurityPermissions.DELETE_POLLUTION)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readMilieuxPollution() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(MILIEUX_POLLUTION_API_PREFIX + MILIEUX_POLLUTION_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_POLLUTION)
                .hasPermission(SecurityPermissions.ADD_POLLUTION)
                .hasPermission(SecurityPermissions.EDIT_POLLUTION)
                .hasPermission(SecurityPermissions.DELETE_POLLUTION)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateMilieuxPollution() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(MILIEUX_POLLUTION_API_PREFIX + MILIEUX_POLLUTION_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_POLLUTION)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteMilieuxPollution() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(MILIEUX_POLLUTION_API_PREFIX + MILIEUX_POLLUTION_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_POLLUTION)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
        }

    @Bean
    public SecurityRule updateStatut() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern( MILIEUX_POLLUTION_API_PREFIX + UPDATE_STATUT)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

}
