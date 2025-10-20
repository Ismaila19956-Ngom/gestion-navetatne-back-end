package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RejetPollutionRule {
    static final String REJETPOLLUTION_API_PREFIX = "/rejet-pollution";
    static final String REJETPOLLUTION_ID = "/{rejetId}";
    static final String UPDATE_STATUT = "/{id}/statut";

    @Bean
    public SecurityRule createRejetPollution() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(REJETPOLLUTION_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_POLLUTION)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllRejetPollution() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(REJETPOLLUTION_API_PREFIX)
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
    public SecurityRule readRejetPollution() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(REJETPOLLUTION_API_PREFIX + REJETPOLLUTION_ID)
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
    public SecurityRule updateRejetPollution() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(REJETPOLLUTION_API_PREFIX + REJETPOLLUTION_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_POLLUTION)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleterejetPollution() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(REJETPOLLUTION_API_PREFIX + REJETPOLLUTION_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_POLLUTION)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
        }

    @Bean
    public SecurityRule updateStatutRejet() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern( REJETPOLLUTION_API_PREFIX + UPDATE_STATUT)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}
