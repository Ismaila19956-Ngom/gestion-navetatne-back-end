package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ImpactsAndObjectiveRule {
    static final String API_PREFIX = "/impactsAndObjectives";
    static final String ID = "/{impactsAndObjectiveId}";

    @Bean
    public SecurityRule createImpactsAndObjective() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_IMPACTSANDOBJECTIVE)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readImpactsAndObjective() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(API_PREFIX + ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_IMPACTSANDOBJECTIVE)
                .hasPermission(SecurityPermissions.ADD_IMPACTSANDOBJECTIVE)
                .hasPermission(SecurityPermissions.EDIT_IMPACTSANDOBJECTIVE)
                .hasPermission(SecurityPermissions.DELETE_IMPACTSANDOBJECTIVE)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllImpactsAndObjectives() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_IMPACTSANDOBJECTIVE)
                .hasPermission(SecurityPermissions.ADD_IMPACTSANDOBJECTIVE)
                .hasPermission(SecurityPermissions.EDIT_IMPACTSANDOBJECTIVE)
                .hasPermission(SecurityPermissions.DELETE_IMPACTSANDOBJECTIVE)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateImpactsAndObjective() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(API_PREFIX + ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_IMPACTSANDOBJECTIVE)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule deleteImpactsAndObjective() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(API_PREFIX + ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_IMPACTSANDOBJECTIVE)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

}
