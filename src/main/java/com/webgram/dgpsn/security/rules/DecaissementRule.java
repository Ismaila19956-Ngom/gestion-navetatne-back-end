package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DecaissementRule {
    static final String DECAISSEMENT_API_PREFIX = "/decaissement";
    static final String DECAISSEMENT_ID = "/{decaissementId}";
    static final String DECAISSEMENT_MULTIPLE = "/multiple";

    @Bean
    public SecurityRule createDecaissement() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(DECAISSEMENT_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_DECAISSEMENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule createMultipleDecaissements() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(DECAISSEMENT_API_PREFIX + DECAISSEMENT_MULTIPLE)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_DECAISSEMENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readDecaissement() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(DECAISSEMENT_API_PREFIX + DECAISSEMENT_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_DECAISSEMENT)
                .hasPermission(SecurityPermissions.ADD_DECAISSEMENT)
                .hasPermission(SecurityPermissions.EDIT_DECAISSEMENT)
                .hasPermission(SecurityPermissions.DELETE_DECAISSEMENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllDecaissements() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(DECAISSEMENT_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_DECAISSEMENT)
                .hasPermission(SecurityPermissions.ADD_DECAISSEMENT)
                .hasPermission(SecurityPermissions.EDIT_DECAISSEMENT)
                .hasPermission(SecurityPermissions.DELETE_DECAISSEMENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateDecaissement() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(DECAISSEMENT_API_PREFIX + DECAISSEMENT_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_DECAISSEMENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteDecaissement() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(DECAISSEMENT_API_PREFIX + DECAISSEMENT_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_DECAISSEMENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}