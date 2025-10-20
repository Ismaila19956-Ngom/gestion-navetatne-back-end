package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UnitRule {
    static final String UNIT_PREFIX = "/units";
    static final String UNIT_ID = "/{unitId}";

    @Bean
    public SecurityRule createUnit() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(UNIT_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_UNIT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readUnit() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(UNIT_PREFIX + UNIT_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_UNIT)
                .hasPermission(SecurityPermissions.ADD_UNIT)
                .hasPermission(SecurityPermissions.EDIT_UNIT)
                .hasPermission(SecurityPermissions.DELETE_UNIT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readUnits() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(UNIT_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_UNIT)
                .hasPermission(SecurityPermissions.ADD_UNIT)
                .hasPermission(SecurityPermissions.EDIT_UNIT)
                .hasPermission(SecurityPermissions.DELETE_UNIT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateUnit() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(UNIT_PREFIX + UNIT_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_UNIT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule deleteUnit() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(UNIT_PREFIX + UNIT_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_UNIT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

}
