package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PollutionManagerRule {
    static final String POLLUTION_API_PREFIX = "/pollution-manager";
    static final String POLLUTION_ID = "/{pollutionId}";
    static final String UPDATE_STATUT = "/{id}/statut";

    @Bean
    public SecurityRule createPollutionManager() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(POLLUTION_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_POLLUTION)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllPollutionManager() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(POLLUTION_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_POLLUTION)
                .hasPermission(SecurityPermissions.ADD_POLLUTION)
                .hasPermission(SecurityPermissions.EDIT_POLLUTION)
                .hasPermission(SecurityPermissions.DELETE_POLLUTION)
                .hasPermission(SecurityPermissions.VALIDATION_POLLUTION)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readPollutionManager() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(POLLUTION_API_PREFIX + POLLUTION_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_POLLUTION)
                .hasPermission(SecurityPermissions.ADD_POLLUTION)
                .hasPermission(SecurityPermissions.EDIT_POLLUTION)
                .hasPermission(SecurityPermissions.DELETE_POLLUTION)
                .hasPermission(SecurityPermissions.VALIDATION_POLLUTION)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updatePollutionManager() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(POLLUTION_API_PREFIX + POLLUTION_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_POLLUTION)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deletePollutionManager() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(POLLUTION_API_PREFIX + POLLUTION_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_POLLUTION)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
        }


    @Bean
    public SecurityRule updateStatutMangement() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern( POLLUTION_API_PREFIX + UPDATE_STATUT)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .hasPermission(SecurityPermissions.VALIDATION_POLLUTION)
                .end();
    }

}
