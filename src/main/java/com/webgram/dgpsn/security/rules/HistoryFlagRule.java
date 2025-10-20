package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HistoryFlagRule {
    static final String  History_Flag_API_PREFIX = "/histories-flag";
    static final String History_Flag_ID = "/{historyFlagId}";
    static final String History_Flag_BY_CODE = "/byFlagCode";

    @Bean
    public SecurityRule createHistoryFlag() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(History_Flag_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_FLAG)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.READ_TDB_RESUME)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readHistoryFlag() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(History_Flag_API_PREFIX + History_Flag_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_FLAG)
                .hasPermission(SecurityPermissions.ADD_FLAG)
                .hasPermission(SecurityPermissions.EDIT_FLAG)
                .hasPermission(SecurityPermissions.DELETE_FLAG)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.READ_TDB_RESUME)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readHistoryFlags() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(History_Flag_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_FLAG)
                .hasPermission(SecurityPermissions.ADD_FLAG)
                .hasPermission(SecurityPermissions.EDIT_FLAG)
                .hasPermission(SecurityPermissions.DELETE_FLAG)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.READ_TDB_RESUME)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateHistoryFlags() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(History_Flag_API_PREFIX + History_Flag_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_FLAG)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.READ_TDB_RESUME)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule deleteHistoryFlag() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(History_Flag_API_PREFIX + History_Flag_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_FLAG)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.READ_TDB_RESUME)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readHistoryFlagBYCode() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(History_Flag_API_PREFIX + History_Flag_BY_CODE)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_FLAG)
                .hasPermission(SecurityPermissions.ADD_FLAG)
                .hasPermission(SecurityPermissions.EDIT_FLAG)
                .hasPermission(SecurityPermissions.DELETE_FLAG)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.READ_TDB_RESUME)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}
