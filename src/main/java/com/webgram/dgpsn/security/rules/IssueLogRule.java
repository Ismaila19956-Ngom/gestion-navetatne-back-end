package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class IssueLogRule {
    static final String  ISSUE_LOG_API_PREFIX = "/issuelogs";
    static final String ISSUE_LOG_ID = "/{issueLogId}";
    static final String  ISSUE_LOG_RISk_API_PREFIX = "/issueLogRisk";

    static final String ISSUE_LOG_IMPORT = "/import";



    static final String ISSUE_LOG_EXPORT = "/export/excel";
    static final String ISSUE_LOG_RISK_ID = "/issueLogRisk/{issueLogRiskId}";
    static final String ISSUE_LOG_LIST = "/list/{projectId}";

    static final String VALID_ISSUE_LOG = "/valid/{issueLogId}";



    @Bean
    public SecurityRule createIssueLog() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(ISSUE_LOG_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_ISSUE_LOG)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readIssuelogs() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(ISSUE_LOG_API_PREFIX + ISSUE_LOG_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_ISSUE_LOG)
                .hasPermission(SecurityPermissions.ADD_ISSUE_LOG)
                .hasPermission(SecurityPermissions.EDIT_ISSUE_LOG)
                .hasPermission(SecurityPermissions.DELETE_ISSUE_LOG)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule readListIssuelogs() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(ISSUE_LOG_API_PREFIX + ISSUE_LOG_LIST)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_ISSUE_LOG)
                .hasPermission(SecurityPermissions.ADD_ISSUE_LOG)
                .hasPermission(SecurityPermissions.EDIT_ISSUE_LOG)
                .hasPermission(SecurityPermissions.DELETE_ISSUE_LOG)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readISSUE_LOGs() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(ISSUE_LOG_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_ISSUE_LOG)
                .hasPermission(SecurityPermissions.ADD_ISSUE_LOG)
                .hasPermission(SecurityPermissions.EDIT_ISSUE_LOG)
                .hasPermission(SecurityPermissions.DELETE_ISSUE_LOG)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateIssueLog() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(ISSUE_LOG_API_PREFIX + ISSUE_LOG_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_ISSUE_LOG)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateIssueLogs() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(ISSUE_LOG_API_PREFIX + ISSUE_LOG_RISk_API_PREFIX + ISSUE_LOG_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_ISSUE_LOG)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule updateIssueLogRisks() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(ISSUE_LOG_API_PREFIX + ISSUE_LOG_RISk_API_PREFIX + ISSUE_LOG_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_ISSUE_LOG)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule deleteIssuelogs() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(ISSUE_LOG_API_PREFIX + ISSUE_LOG_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_ISSUE_LOG)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule deleteIssuelogsRisk() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(ISSUE_LOG_API_PREFIX + ISSUE_LOG_RISK_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_ISSUE_LOG)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule importIssuelogs() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(ISSUE_LOG_API_PREFIX + ISSUE_LOG_IMPORT)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.IMPORT_ISSUE_LOG)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule exportIssuelogs() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(ISSUE_LOG_API_PREFIX + ISSUE_LOG_EXPORT)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EXPORT_ISSUE_LOG)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule validIssueLog() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(ISSUE_LOG_API_PREFIX + VALID_ISSUE_LOG)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.VALID_ISSUE_LOG)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}
