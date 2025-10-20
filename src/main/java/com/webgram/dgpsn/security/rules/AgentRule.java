package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AgentRule {
    static final String AGENT_API_PREFIX = "/agents";
    static final String AGENT_ID = "/{agentId}";
    static final String DOWNLOAD_PREFIX = "/_download";
    static final String IMPORT_PREFIX = "/import";
    static final String EXPORT_PREFIX = "/export";
    static final String FILTER_NOT_IN_USER = "/notInUser";


    @Bean
    public SecurityRule createAgent() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(AGENT_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_AGENT)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAgent() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(AGENT_API_PREFIX + AGENT_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_AGENT)
                .hasPermission(SecurityPermissions.ADD_AGENT)
                .hasPermission(SecurityPermissions.EDIT_AGENT)
                .hasPermission(SecurityPermissions.DELETE_AGENT)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllAgent() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(AGENT_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_AGENT)
                .hasPermission(SecurityPermissions.ADD_AGENT)
                .hasPermission(SecurityPermissions.EDIT_AGENT)
                .hasPermission(SecurityPermissions.DELETE_AGENT)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateAgent() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(AGENT_API_PREFIX + AGENT_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_AGENT)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule deleteAgent() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(AGENT_API_PREFIX + AGENT_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_AGENT)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule importAgent() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(AGENT_API_PREFIX + IMPORT_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.IMPORT_AGENT)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule exportAgent() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(AGENT_API_PREFIX + EXPORT_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EXPORT_AGENT)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule readFileAgent() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(AGENT_API_PREFIX + AGENT_ID + DOWNLOAD_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_FILE_AGENT)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAgentNotInUser() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(AGENT_API_PREFIX + FILTER_NOT_IN_USER)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_AGENT)
                .hasPermission(SecurityPermissions.ADD_AGENT)
                .hasPermission(SecurityPermissions.EDIT_AGENT)
                .hasPermission(SecurityPermissions.DELETE_AGENT)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

}
