package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ValueIndicatorRule {
    static final String PROGRESS_TRACKING_INDICATOR_API_PREFIX = "/value-indicators";
    static final String PROGRESS_TRACKING_INDICATOR_ID = "/{valueIndicatorId}";
    static final String LAST_PREFIX = "/last";
    static final String DOWNLOAD_PREFIX = "/_download";
    static final String IMPORT_PREFIX = "/import";
    static final String EXPORT_PREFIX = "/export/excel";

    @Bean
    public SecurityRule createValueIndicator() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(PROGRESS_TRACKING_INDICATOR_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_PROGRESS_TRACKING_INDICATOR)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readValueIndicator() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(PROGRESS_TRACKING_INDICATOR_API_PREFIX + PROGRESS_TRACKING_INDICATOR_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_PROGRESS_TRACKING_INDICATOR)
                .hasPermission(SecurityPermissions.ADD_PROGRESS_TRACKING_INDICATOR)
                .hasPermission(SecurityPermissions.EDIT_PROGRESS_TRACKING_INDICATOR)
                .hasPermission(SecurityPermissions.DELETE_PROGRESS_TRACKING_INDICATOR)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readValueIndicators() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(PROGRESS_TRACKING_INDICATOR_API_PREFIX + LAST_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_PROGRESS_TRACKING_INDICATOR)
                .hasPermission(SecurityPermissions.ADD_PROGRESS_TRACKING_INDICATOR)
                .hasPermission(SecurityPermissions.EDIT_PROGRESS_TRACKING_INDICATOR)
                .hasPermission(SecurityPermissions.DELETE_PROGRESS_TRACKING_INDICATOR)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllValueIndicators() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(PROGRESS_TRACKING_INDICATOR_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_PROGRESS_TRACKING_INDICATOR)
                .hasPermission(SecurityPermissions.ADD_PROGRESS_TRACKING_INDICATOR)
                .hasPermission(SecurityPermissions.EDIT_PROGRESS_TRACKING_INDICATOR)
                .hasPermission(SecurityPermissions.DELETE_PROGRESS_TRACKING_INDICATOR)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateValueIndicator() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(PROGRESS_TRACKING_INDICATOR_API_PREFIX + PROGRESS_TRACKING_INDICATOR_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_PROGRESS_TRACKING_INDICATOR)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule deleteValueIndicator() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(PROGRESS_TRACKING_INDICATOR_API_PREFIX + PROGRESS_TRACKING_INDICATOR_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_PROGRESS_TRACKING_INDICATOR)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule importValueIndicator() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(PROGRESS_TRACKING_INDICATOR_API_PREFIX + IMPORT_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.IMPORT_PROGRESS_TRACKING_INDICATOR)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule export() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(PROGRESS_TRACKING_INDICATOR_API_PREFIX + EXPORT_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EXPORT_PROGRESS_TRACKING_INDICATOR)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule readFileValueIndicator() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(PROGRESS_TRACKING_INDICATOR_API_PREFIX + PROGRESS_TRACKING_INDICATOR_ID + DOWNLOAD_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_FILE_PROGRESS_TRACKING_INDICATOR)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}
