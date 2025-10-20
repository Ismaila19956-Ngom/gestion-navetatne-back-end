package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class IssueLogStructureChannelRule {
    static final String  ISSUE_LOG_STRUCTURE_API_PREFIX = "/issuelogStucture";
     static final String ISSUE_LOG_STRUCTURE_ID = "/{issuelogId}";
    static final String BY_STRUCTURE_PREFIX = "/structureByIssuelog";
    static final String BY_STRUCTURE_ISSUE = "/{issuelogStructureId}";


    @Bean
    public SecurityRule createIssuelogStructure() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(ISSUE_LOG_STRUCTURE_API_PREFIX )
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_ISSUE_LOG)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }


    @Bean
    public SecurityRule readStructureByIssuelogs() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(ISSUE_LOG_STRUCTURE_API_PREFIX + BY_STRUCTURE_PREFIX + ISSUE_LOG_STRUCTURE_ID)
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
    public SecurityRule  readIssuelogStructure() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(ISSUE_LOG_STRUCTURE_API_PREFIX + BY_STRUCTURE_ISSUE)
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
    public SecurityRule updateIssuelogStructure() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(ISSUE_LOG_STRUCTURE_API_PREFIX + ISSUE_LOG_STRUCTURE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_ISSUE_LOG)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule deleteIssuelogStructure() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(ISSUE_LOG_STRUCTURE_API_PREFIX + ISSUE_LOG_STRUCTURE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_ISSUE_LOG)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}
