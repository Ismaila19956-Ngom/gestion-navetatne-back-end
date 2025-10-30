package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TacheRule {
    static final String TACHES_API_PREFIX = "/taches";
    static final String TACHE_ID = "/{tacheId}";
    static final String TACHE_STATUT = "/{tacheId}/statut";
    static final String TACHE_EXPORT = "/export";
    static final String TACHE_BY_ACTIVITE = "/activite/{activiteId}";

    @Bean
    public SecurityRule addTache() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(TACHES_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllTaches() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(TACHES_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readTache() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(TACHES_API_PREFIX + TACHE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readTachesByActivite() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(TACHES_API_PREFIX + TACHE_BY_ACTIVITE)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateTache() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(TACHES_API_PREFIX + TACHE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateTacheStatut() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PATCH)
                .apiPattern(TACHES_API_PREFIX + TACHE_STATUT)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteTache() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(TACHES_API_PREFIX + TACHE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule exportTache() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(TACHES_API_PREFIX + TACHE_EXPORT)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}
