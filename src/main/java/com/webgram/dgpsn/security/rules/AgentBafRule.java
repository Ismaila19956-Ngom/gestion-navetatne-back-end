package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AgentBafRule {
    static final String AGENT_BAF_API_PREFIX = "/agentBaf";
    static final String AGENT_BAF_ID = "/{agentBafId}";
    static final String AGENT_BAF_MULTIPLE = "/multiple";

    @Bean
    public SecurityRule createAgentBaf() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(AGENT_BAF_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_AGENT_BAF)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule createMultipleAgentBafs() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(AGENT_BAF_API_PREFIX + AGENT_BAF_MULTIPLE)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_AGENT_BAF)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAgentBaf() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(AGENT_BAF_API_PREFIX + AGENT_BAF_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_AGENT_BAF)
                .hasPermission(SecurityPermissions.ADD_AGENT_BAF)
                .hasPermission(SecurityPermissions.EDIT_AGENT_BAF)
                .hasPermission(SecurityPermissions.DELETE_AGENT_BAF)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllAgentBafs() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(AGENT_BAF_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_AGENT_BAF)
                .hasPermission(SecurityPermissions.ADD_AGENT_BAF)
                .hasPermission(SecurityPermissions.EDIT_AGENT_BAF)
                .hasPermission(SecurityPermissions.DELETE_AGENT_BAF)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateAgentBaf() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(AGENT_BAF_API_PREFIX + AGENT_BAF_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_AGENT_BAF)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteAgentBaf() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(AGENT_BAF_API_PREFIX + AGENT_BAF_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_AGENT_BAF)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}