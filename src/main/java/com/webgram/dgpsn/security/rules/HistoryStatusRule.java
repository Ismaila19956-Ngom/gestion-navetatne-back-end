package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HistoryStatusRule {
    static final String  History_Status_API_PREFIX = "/histories-status";
    static final String History_Status_ID = "/{historyStatusId}";
    static final String HISTORY_STATUS_BY_CODE = "/byStatusCode";

    @Bean
    public SecurityRule createHistoryStatus() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(History_Status_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_STATUS)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readHistoriesEntities() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(History_Status_API_PREFIX + History_Status_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_STATUS)
                .hasPermission(SecurityPermissions.ADD_STATUS)
                .hasPermission(SecurityPermissions.EDIT_STATUS)
                .hasPermission(SecurityPermissions.DELETE_STATUS)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.READ_TDB_RESUME)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllHistoriesEntities() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(History_Status_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_STATUS)
                .hasPermission(SecurityPermissions.ADD_STATUS)
                .hasPermission(SecurityPermissions.EDIT_STATUS)
                .hasPermission(SecurityPermissions.DELETE_STATUS)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.READ_TDB_RESUME)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateHistoryStatus() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(History_Status_API_PREFIX + History_Status_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_STATUS)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule deleteHistoryStatus() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(History_Status_API_PREFIX + History_Status_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_STATUS)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readHistoryStatusBYCode() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(History_Status_API_PREFIX + HISTORY_STATUS_BY_CODE)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_STATUS)
                .hasPermission(SecurityPermissions.ADD_STATUS)
                .hasPermission(SecurityPermissions.EDIT_STATUS)
                .hasPermission(SecurityPermissions.DELETE_STATUS)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.READ_TDB_RESUME)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}
