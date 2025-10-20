package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class IssueLogActionRealizedRule {
    static final String  Issue_Log_Action_Realized_API_PREFIX = "/issueLogActionRealized";
     static final String Issue_Log_Action_Realized_ID = "/{issueLogActionRealizedId}";
    static final String Issue_Log_Action_Realized = "/byIssuelog";

    @Bean
    public SecurityRule createIssueLogActionRealized() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(Issue_Log_Action_Realized_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_ISSUE_LOG)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readByIssuelog() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(Issue_Log_Action_Realized_API_PREFIX + Issue_Log_Action_Realized + Issue_Log_Action_Realized_ID)
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
    public SecurityRule  readAllIssueLogActionRealizeds() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(Issue_Log_Action_Realized_API_PREFIX)
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
    public SecurityRule updateIssueLogActionRealized() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(Issue_Log_Action_Realized_API_PREFIX + Issue_Log_Action_Realized_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_ISSUE_LOG)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule deleteIssueLogActionRealized() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(Issue_Log_Action_Realized_API_PREFIX + Issue_Log_Action_Realized_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_ISSUE_LOG)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}
