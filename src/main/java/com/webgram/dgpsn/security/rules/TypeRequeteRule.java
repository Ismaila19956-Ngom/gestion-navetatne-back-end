package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TypeRequeteRule {
    static final String TYPE_REQUETE_API_PREFIX = "/typeRequete";
    static final String TYPE_REQUETE_ID = "/{typeRequeteId}";

    @Bean
    public SecurityRule createTypeRequete() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(TYPE_REQUETE_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_REF_TYPE_REQUETE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readTypeRequete() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(TYPE_REQUETE_API_PREFIX +TYPE_REQUETE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_REF_TYPE_REQUETE)
                .hasPermission(SecurityPermissions.ADD_REF_TYPE_REQUETE)
                .hasPermission(SecurityPermissions.EDIT_REF_TYPE_REQUETE)
                .hasPermission(SecurityPermissions.DELETE_REF_TYPE_REQUETE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllTypeRequete() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(TYPE_REQUETE_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_REF_TYPE_REQUETE)
                .hasPermission(SecurityPermissions.ADD_REF_TYPE_REQUETE)
                .hasPermission(SecurityPermissions.EDIT_REF_TYPE_REQUETE)
                .hasPermission(SecurityPermissions.DELETE_REF_TYPE_REQUETE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateTypeRequete() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(TYPE_REQUETE_API_PREFIX +TYPE_REQUETE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_REF_TYPE_REQUETE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule deleteTypeRequete() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(TYPE_REQUETE_API_PREFIX +TYPE_REQUETE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_REF_TYPE_REQUETE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}
