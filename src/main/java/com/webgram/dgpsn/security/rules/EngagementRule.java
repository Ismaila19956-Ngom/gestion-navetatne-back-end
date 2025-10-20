package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EngagementRule {
    static final String ENGAGEMENT_API_PREFIX = "/engagement";
    static final String ENGAGEMENT_ID = "/{engagementId}";
    static final String ENGAGEMENT_MULTIPLE = "/multiple";

    @Bean
    public SecurityRule createEngagement() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(ENGAGEMENT_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_ENGAGEMENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule createMultipleEngagements() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(ENGAGEMENT_API_PREFIX + ENGAGEMENT_MULTIPLE)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_ENGAGEMENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readEngagement() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(ENGAGEMENT_API_PREFIX + ENGAGEMENT_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_ENGAGEMENT)
                .hasPermission(SecurityPermissions.ADD_ENGAGEMENT)
                .hasPermission(SecurityPermissions.EDIT_ENGAGEMENT)
                .hasPermission(SecurityPermissions.DELETE_ENGAGEMENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllEngagements() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(ENGAGEMENT_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_ENGAGEMENT)
                .hasPermission(SecurityPermissions.ADD_ENGAGEMENT)
                .hasPermission(SecurityPermissions.EDIT_ENGAGEMENT)
                .hasPermission(SecurityPermissions.DELETE_ENGAGEMENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateEngagement() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(ENGAGEMENT_API_PREFIX + ENGAGEMENT_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_ENGAGEMENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteEngagement() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(ENGAGEMENT_API_PREFIX + ENGAGEMENT_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_ENGAGEMENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}