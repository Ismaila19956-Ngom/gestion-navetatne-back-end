package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class IssueLogResolveChannelRule {
    static final String  ISSUE_LOG_ResolveChannel_API_PREFIX = "/issuelogResolveChannel";
     static final String ISSUE_LOG_ResolveChannel_ID = "/{issuelogResolveChannelId}";
    static final String BY_ResolveChannel_PREFIX = "/resolveChannelByIssuelog/{issuelogId}";


    @Bean
    public SecurityRule createIssuelogResolveChannel() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(ISSUE_LOG_ResolveChannel_API_PREFIX )
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_ISSUE_LOG)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }


    @Bean
    public SecurityRule readStructureByIssuelog() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(ISSUE_LOG_ResolveChannel_API_PREFIX +BY_ResolveChannel_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_ISSUE_LOG)
                .hasPermission(SecurityPermissions.ADD_ISSUE_LOG)
                .hasPermission(SecurityPermissions.EDIT_ISSUE_LOG)
                .hasPermission(SecurityPermissions.DELETE_ISSUE_LOG)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule  readIssuelogResolveChannel() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(ISSUE_LOG_ResolveChannel_API_PREFIX +ISSUE_LOG_ResolveChannel_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_ISSUE_LOG)
                .hasPermission(SecurityPermissions.ADD_ISSUE_LOG)
                .hasPermission(SecurityPermissions.EDIT_ISSUE_LOG)
                .hasPermission(SecurityPermissions.DELETE_ISSUE_LOG)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateIssuelogResolveChannel() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(ISSUE_LOG_ResolveChannel_API_PREFIX + ISSUE_LOG_ResolveChannel_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_ISSUE_LOG)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule deleteIssuelogResolveChannel() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(ISSUE_LOG_ResolveChannel_API_PREFIX + ISSUE_LOG_ResolveChannel_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_ISSUE_LOG)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}
