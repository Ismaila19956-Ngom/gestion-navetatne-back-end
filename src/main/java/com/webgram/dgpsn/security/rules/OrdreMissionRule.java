package com.webgram.dgpsn.security.rules;

import com.webgram.dgpsn.security.SecurityPermissions;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;


@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrdreMissionRule {
    static final String ORDRE_MISSION_API_PREFIX = "/ordreMission";
    static final String ORDRE_MISSION_ID = "/{ordreMissionId}";

    static final String ORDRE_MISSION_AGENT = "/{agentId}";

    static final String STATUS_ORDRE_MISSION = "/status";


    static final String ORDRE_MISSION_DOCUMENT_PREFIX = "/doc";

    static final String ORDRE_MISSION_ID_DOCUMENT = "/{documentId}";
    static final String ORDRE_MISSION_DOCUMENT = "/docOrdre";



    @Bean
    public SecurityRule readAllOrdreMission() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(ORDRE_MISSION_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_ORDRE_MISSION_AGENT)
                .hasPermission(SecurityPermissions.READ_ORDRE_MISSION_AGENT)
                .hasPermission(SecurityPermissions.DELETE_ORDRE_MISSION_AGENT)
                .hasPermission(SecurityPermissions.EDIT_ORDRE_MISSION_AGENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }


    @Bean
    public SecurityRule readOrdreWithId() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(ORDRE_MISSION_API_PREFIX+ORDRE_MISSION_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_ORDRE_MISSION_AGENT)
                .hasPermission(SecurityPermissions.READ_ORDRE_MISSION_AGENT)
                .hasPermission(SecurityPermissions.DELETE_ORDRE_MISSION_AGENT)
                .hasPermission(SecurityPermissions.EDIT_ORDRE_MISSION_AGENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule readOrdreWithDocAgentSpecifiqueId() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(ORDRE_MISSION_API_PREFIX+ORDRE_MISSION_ID+ORDRE_MISSION_AGENT)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_ORDRE_MISSION_AGENT)
                .hasPermission(SecurityPermissions.READ_ORDRE_MISSION_AGENT)
                .hasPermission(SecurityPermissions.DELETE_ORDRE_MISSION_AGENT)
                .hasPermission(SecurityPermissions.EDIT_ORDRE_MISSION_AGENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule createOrdreMission() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(ORDRE_MISSION_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_ORDRE_MISSION_AGENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule createOrdreMissionDocument() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(ORDRE_MISSION_API_PREFIX+ORDRE_MISSION_DOCUMENT+ORDRE_MISSION_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_ORDRE_MISSION_AGENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateOrdreMission() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(ORDRE_MISSION_API_PREFIX+ORDRE_MISSION_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_ORDRE_MISSION_AGENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule updateStatutOrdreMission() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(ORDRE_MISSION_API_PREFIX+STATUS_ORDRE_MISSION+ORDRE_MISSION_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_ORDRE_MISSION_AGENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule deleteOrdreMission() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(ORDRE_MISSION_API_PREFIX+ORDRE_MISSION_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_ORDRE_MISSION_AGENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule deleteOrdreMissionDocument() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(ORDRE_MISSION_API_PREFIX+ORDRE_MISSION_DOCUMENT_PREFIX+ORDRE_MISSION_ID_DOCUMENT)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_ORDRE_MISSION_AGENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }



}
