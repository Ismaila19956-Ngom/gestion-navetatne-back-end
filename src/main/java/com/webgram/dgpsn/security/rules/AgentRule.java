package com.webgram.dgpsn.security.rules;

import com.webgram.dgpsn.security.SecurityPermissions;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AgentRule {
    static final String AGENT_API_PREFIX = "/agents";
    static final String AGENT_ID = "/{agentId}";
    static final String AGENT_PHOTOPROFIL = "/photo-profil/{agentId}";
    static final String AGENT_MATRICULE = "/matricule/{matricule}";
    static final String DOWN_LOAD = "/{id}/_download";
    static final String READ_IMAGE = "/{id}/readimage";
    static final String IMPORT = "/import";
    static final String EXPORT = "/export";
    static final String AGENTBYUNITE = "/agent-by-unites";
    static final String NOT_USER = "/notInUser";
    static final String ADD_FILE = "/addFile";
    static final String ADD_PHOTO_PROFIL = "/{id}/photo";
    static final String AGENT_WIHOUT_ACTIVE_USER = "/without-active-user";

    @Bean
    public SecurityRule readAllAgent() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(AGENT_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .hasPermission(SecurityPermissions.READ_AGENT)
                .end();
    }

    @Bean
    public SecurityRule getAgentsWithoutActiveUser() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(AGENT_API_PREFIX + AGENT_WIHOUT_ACTIVE_USER)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .hasPermission(SecurityPermissions.READ_AGENT)
                .end();
    }
    @Bean
    public SecurityRule saveAgent(SecurityPermissionRule securityPermissionRule) {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(AGENT_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_AGENT)
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
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule readAgentByMatricule() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(AGENT_API_PREFIX + AGENT_MATRICULE)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_AGENT)
                .hasPermission(SecurityPermissions.ADD_AGENT)
                .hasPermission(SecurityPermissions.EDIT_AGENT)
                .hasPermission(SecurityPermissions.DELETE_AGENT)
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
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deletePhotoProfil() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(AGENT_API_PREFIX + AGENT_PHOTOPROFIL)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule downloadAgent() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(AGENT_API_PREFIX + DOWN_LOAD)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readImage() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(AGENT_API_PREFIX + READ_IMAGE)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule importAgent() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(AGENT_API_PREFIX + IMPORT)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule exportAgent() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(AGENT_API_PREFIX + EXPORT)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAgentNotInUser() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(AGENT_API_PREFIX + NOT_USER)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule addFile() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(AGENT_API_PREFIX + AGENT_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule getAgentsByUnite() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(AGENT_API_PREFIX+AGENTBYUNITE)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .hasPermission(SecurityPermissions.READ_AGENT)
                .end();
    }

    @Bean
    public SecurityRule uploadProfilePhoto() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(AGENT_API_PREFIX+ADD_PHOTO_PROFIL)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}
