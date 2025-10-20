package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MilesToneRule {
    static final String  MILESTONE_API_PREFIX = "/milestones";
    static final String MILESTONE_ID = "/{milestoneId}";
    static final String MILESTONE_IMPORT = "/import";
    static final String MILESTONE_EXPORT = "/export";

    @Bean
    public SecurityRule MilestoneDTOaddMilestoneToProject() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(MILESTONE_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_MILESTONE)
                .hasPermission(SecurityPermissions.READ_PROJECT_ACTIVITY_MONITORING)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)

                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllMilestoneByProject() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(MILESTONE_API_PREFIX + MILESTONE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_MILESTONE)
                .hasPermission(SecurityPermissions.ADD_MILESTONE)
                .hasPermission(SecurityPermissions.EDIT_MILESTONE)
                .hasPermission(SecurityPermissions.DELETE_MILESTONE)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .hasPermission(SecurityPermissions.READ_PROJECT_ACTIVITY_MONITORING)
                .end();
    }

    @Bean
    public SecurityRule readMilestones() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(MILESTONE_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_MILESTONE)
                .hasPermission(SecurityPermissions.ADD_MILESTONE)
                .hasPermission(SecurityPermissions.EDIT_MILESTONE)
                .hasPermission(SecurityPermissions.DELETE_MILESTONE)
                .hasPermission(SecurityPermissions.READ_PROJECT_ACTIVITY_MONITORING)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateMilestone() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(MILESTONE_API_PREFIX + MILESTONE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_MILESTONE)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .hasPermission(SecurityPermissions.READ_PROJECT_ACTIVITY_MONITORING)
                .end();
    }

    @Bean
    public SecurityRule deleteMilestone() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(MILESTONE_API_PREFIX + MILESTONE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_MILESTONE)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .hasPermission(SecurityPermissions.READ_PROJECT_ACTIVITY_MONITORING)
                .end();
    }
    @Bean
    public SecurityRule importMilestone() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(MILESTONE_API_PREFIX + MILESTONE_IMPORT)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.IMPORT_MILESTONE)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .hasPermission(SecurityPermissions.READ_PROJECT_ACTIVITY_MONITORING)
                .end();
    }

    @Bean
    public SecurityRule exportMilestone() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(MILESTONE_API_PREFIX + MILESTONE_EXPORT)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EXPORT_MILESTONE)
                .hasPermission(SecurityPermissions.READ_PROJECT_ACTIVITY_MONITORING)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule viewMilestoneToActivity() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(MILESTONE_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_PROJECT_ACTIVITY_MONITORING)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}
