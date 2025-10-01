package com.webgram.dgpsn.security.rules;

import com.webgram.dgpsn.security.SecurityPermissions;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrganigrammeRule {
    static final String ORGANIGRAMME_API_PREFIX = "/organigramme";
    static final String ORGANIGRAMME_ID = "/{organigrammeId}";
    static final String Archive_ID = "/{id}";
    static final String archiveUnite = "/archives";
    static final String DESARCHIVE_UNITE = "/unarchives";

    @Bean
    public SecurityRule createOrganigramme() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(ORGANIGRAMME_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .hasPermission(SecurityPermissions.ADD_ORGANIGRAMME)
                .end();
    }

    @Bean
    public SecurityRule createChildrens() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(ORGANIGRAMME_API_PREFIX + ORGANIGRAMME_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .hasPermission(SecurityPermissions.ADD_ORGANIGRAMME)
                .end();
    }

    @Bean
    public SecurityRule readAOrganigramme() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(ORGANIGRAMME_API_PREFIX + ORGANIGRAMME_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_ORGANIGRAMME)
                .hasPermission(SecurityPermissions.READ_ORGANIGRAMME)
                .hasPermission(SecurityPermissions.EDIT_ORGANIGRAMME)
                .hasPermission(SecurityPermissions.DELETE_ORGANIGRAMME)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readParentsOrganigramme() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(ORGANIGRAMME_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_ORGANIGRAMME)
                .hasPermission(SecurityPermissions.READ_ORGANIGRAMME)
                .hasPermission(SecurityPermissions.EDIT_ORGANIGRAMME)
                .hasPermission(SecurityPermissions.DELETE_ORGANIGRAMME)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule archiveTounite() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(ORGANIGRAMME_API_PREFIX + Archive_ID + archiveUnite)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_ORGANIGRAMME)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule desarchiveUnite() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(ORGANIGRAMME_API_PREFIX + Archive_ID + DESARCHIVE_UNITE)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_ORGANIGRAMME)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllOrganigrammes() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(ORGANIGRAMME_API_PREFIX+"/allListOfOrganigramme")
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_ORGANIGRAMME)
                .hasPermission(SecurityPermissions.READ_ORGANIGRAMME)
                .hasPermission(SecurityPermissions.EDIT_ORGANIGRAMME)
                .hasPermission(SecurityPermissions.DELETE_ORGANIGRAMME)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateOrganigramme() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(ORGANIGRAMME_API_PREFIX + ORGANIGRAMME_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .hasPermission(SecurityPermissions.EDIT_ORGANIGRAMME)
                .end();
    }

    @Bean
    public SecurityRule deleteOrganigramme() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(ORGANIGRAMME_API_PREFIX + ORGANIGRAMME_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .hasPermission(SecurityPermissions.DELETE_ORGANIGRAMME)
                .end();
    }
}
