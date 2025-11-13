package com.webgram.dgpsn.security.rules;


import com.webgram.dgpsn.security.SecurityPermissions;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GestonContratRule {
    static final String AGENT_PREFIX  = "/agents";
    static final String AGENT_ID = "/{agentId}";
    static final String GESTION_CONTRAT = "/gestion-contrats";
    static final String CONTRAT_ID = "/{contratId}";
    static final String STATUS_CONTRAT = "/status";

    @Bean
    public SecurityRule getContratsByAgent() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(AGENT_PREFIX + AGENT_ID + GESTION_CONTRAT)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_CONTRAT_AGENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule createContratAgent() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(AGENT_PREFIX + AGENT_ID + GESTION_CONTRAT)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_CONTRAT_AGENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateStatusContrat() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PATCH)
                .apiPattern(AGENT_PREFIX + AGENT_ID + GESTION_CONTRAT + CONTRAT_ID + STATUS_CONTRAT)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.UPDATE_CONTRAT_AGENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }



    @Bean
    public SecurityRule deleteContrat() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(AGENT_PREFIX + AGENT_ID + GESTION_CONTRAT + CONTRAT_ID )
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_CONTRAT_AGENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }


    @Bean
    public SecurityRule upadteContrat() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(AGENT_PREFIX + AGENT_ID + GESTION_CONTRAT + CONTRAT_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.UPDATE_CONTRAT_AGENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}
