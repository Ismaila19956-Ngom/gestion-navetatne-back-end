package com.webgram.dgpsn.security.rules;

import com.webgram.dgpsn.security.SecurityPermissions;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DirectionRule {
    static final String DIRECTION_API_PREFIX = "/directions";
    static final String DIRECTION_ID = "/{directionId}";
    static final String ORGANIGRAMME = "/organigramme";

    @Bean
    public SecurityRule createDirection() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(DIRECTION_API_PREFIX)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.ADD_DIRECTION)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readDirection() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(DIRECTION_API_PREFIX + DIRECTION_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.READ_AFFECTATION)
//                .hasPermission(SecurityPermissions.READ_DIRECTION)
//                .hasPermission(SecurityPermissions.ADD_DIRECTION)
//                .hasPermission(SecurityPermissions.EDIT_DIRECTION)
//                .hasPermission(SecurityPermissions.DELETE_DIRECTION)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllDirection() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(DIRECTION_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule loadOrganigramme() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(DIRECTION_API_PREFIX + ORGANIGRAMME)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateDirection() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(DIRECTION_API_PREFIX + DIRECTION_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.EDIT_DIRECTION)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule deleteDirection() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(DIRECTION_API_PREFIX + DIRECTION_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.DELETE_DIRECTION)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

}
