package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ActorRule {
    static final String ACTOR_API_PREFIX = "/actor-project";
    static final String ACTOR_ID = "/{actorProjectId}";
    static final String IMPORT_PREFIX = "/import";
    static final String EXPORT_PREFIX = "/export";

    @Bean
    public SecurityRule createActorProject() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(ACTOR_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_ACTOR)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readActorProject() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(ACTOR_API_PREFIX + ACTOR_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_ACTOR)
                .hasPermission(SecurityPermissions.ADD_ACTOR)
                .hasPermission(SecurityPermissions.EDIT_ACTOR)
                .hasPermission(SecurityPermissions.DELETE_ACTOR)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllActorByProject() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(ACTOR_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_ACTOR)
                .hasPermission(SecurityPermissions.ADD_ACTOR)
                .hasPermission(SecurityPermissions.EDIT_ACTOR)
                .hasPermission(SecurityPermissions.DELETE_ACTOR)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateActorProject() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(ACTOR_API_PREFIX + ACTOR_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_ACTOR)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule deleteActorProject() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(ACTOR_API_PREFIX + ACTOR_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_ACTOR)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule importActor() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(ACTOR_API_PREFIX + IMPORT_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.IMPORT_ACTOR)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule exportActor() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(ACTOR_API_PREFIX + EXPORT_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EXPORT_ACTOR)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

}
