package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class IndicatorProjetRule {
    static final String  Indicator_Project_API_PREFIX = "/indicator-project";
    static final String Indicator_Project_ID = "/{indicatorProjectId}";
    static final String Indicator_Project_IMPORT = "/import";
    static final String Indicator_Project_EXPORT = "/export";

    @Bean
    public SecurityRule addIndicatorToProject() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(Indicator_Project_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_INDICATOR)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readIndicatorToProject() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(Indicator_Project_API_PREFIX + Indicator_Project_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_INDICATOR)
                .hasPermission(SecurityPermissions.ADD_INDICATOR)
                .hasPermission(SecurityPermissions.EDIT_INDICATOR)
                .hasPermission(SecurityPermissions.DELETE_INDICATOR)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readIndicatorToProjects() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(Indicator_Project_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_INDICATOR)
                .hasPermission(SecurityPermissions.ADD_INDICATOR)
                .hasPermission(SecurityPermissions.EDIT_INDICATOR)
                .hasPermission(SecurityPermissions.DELETE_INDICATOR)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateIndicatorToProject() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(Indicator_Project_API_PREFIX + Indicator_Project_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_INDICATOR)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule deleteIndicatorToProject() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(Indicator_Project_API_PREFIX + Indicator_Project_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_INDICATOR)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule importIndicatorToProject() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(Indicator_Project_IMPORT)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.IMPORT_INDICATOR)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule exportIndicatorToProject() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(Indicator_Project_EXPORT)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EXPORT_INDICATOR)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}
