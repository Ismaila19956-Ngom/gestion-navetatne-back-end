package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class IssueLogComponentRule {
    static final String  ISSUE_LOG_Component_API_PREFIX = "/issuelogComponent";
     static final String ISSUE_LOG_Component_ID = "/{issuelogComponentId}";
    static final String BY_COMPONENT_PREFIX = "/byComponent/{componentId}";
    static final String LINK_PREFIX = "/link";

    @Bean
    public SecurityRule createIssuelogComponent() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(ISSUE_LOG_Component_API_PREFIX )
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_ISSUE_LOG_COMPONENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule createIssueLogComponent() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(ISSUE_LOG_Component_API_PREFIX + LINK_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_ISSUE_LOG_COMPONENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readIssuelogComponentByComponent() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(ISSUE_LOG_Component_API_PREFIX + BY_COMPONENT_PREFIX )
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_ISSUE_LOG_COMPONENT)
                .hasPermission(SecurityPermissions.ADD_ISSUE_LOG_COMPONENT)
                .hasPermission(SecurityPermissions.EDIT_ISSUE_LOG_COMPONENT)
                .hasPermission(SecurityPermissions.DELETE_ISSUE_LOG_COMPONENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule  readAllIssueLogs() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(ISSUE_LOG_Component_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_ISSUE_LOG_COMPONENT)
                .hasPermission(SecurityPermissions.ADD_ISSUE_LOG_COMPONENT)
                .hasPermission(SecurityPermissions.EDIT_ISSUE_LOG_COMPONENT)
                .hasPermission(SecurityPermissions.DELETE_ISSUE_LOG_COMPONENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateIssueLogComponent() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(ISSUE_LOG_Component_API_PREFIX + ISSUE_LOG_Component_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_ISSUE_LOG_COMPONENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule deleteIssueLogComponent() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(ISSUE_LOG_Component_API_PREFIX + ISSUE_LOG_Component_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_ISSUE_LOG_COMPONENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}
