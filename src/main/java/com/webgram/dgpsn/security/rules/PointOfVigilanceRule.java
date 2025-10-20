package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PointOfVigilanceRule {
    static final String POINT_OF_VIGILANCE_API_PREFIX = "/PointOfVigilances";
    static final String POINT_OF_VIGILANCE_ID = "/{pointOfVigilanceId}";
    @Bean
    public SecurityRule createPointOfVigilance() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(POINT_OF_VIGILANCE_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_POINT_OF_VIGILANCE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readPointOfVigilance() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(POINT_OF_VIGILANCE_API_PREFIX + POINT_OF_VIGILANCE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_POINT_OF_VIGILANCE)
                .hasPermission(SecurityPermissions.ADD_POINT_OF_VIGILANCE)
                .hasPermission(SecurityPermissions.EDIT_POINT_OF_VIGILANCE)
                .hasPermission(SecurityPermissions.DELETE_POINT_OF_VIGILANCE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllPointOfVigilances() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(POINT_OF_VIGILANCE_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_POINT_OF_VIGILANCE)
                .hasPermission(SecurityPermissions.ADD_POINT_OF_VIGILANCE)
                .hasPermission(SecurityPermissions.EDIT_POINT_OF_VIGILANCE)
                .hasPermission(SecurityPermissions.DELETE_POINT_OF_VIGILANCE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updatePointOfVigilance() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(POINT_OF_VIGILANCE_API_PREFIX + POINT_OF_VIGILANCE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_POINT_OF_VIGILANCE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule deletePointOfVigilance() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(POINT_OF_VIGILANCE_API_PREFIX + POINT_OF_VIGILANCE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_POINT_OF_VIGILANCE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}
