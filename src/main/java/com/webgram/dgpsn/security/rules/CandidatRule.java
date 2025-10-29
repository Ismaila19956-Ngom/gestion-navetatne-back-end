package com.webgram.dgpsn.security.rules;

import com.webgram.dgpsn.security.SecurityPermissions;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CandidatRule {

    static final String CANDIDAT_API_PREFIX = "/candidats";
    static final String CANDIDAT_ID = "/{id}";

    @Bean
    public SecurityRule saveOrUpdateCandidat() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(CANDIDAT_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_CANDIDAT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule getCandidatById() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(CANDIDAT_API_PREFIX + CANDIDAT_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_CANDIDAT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule getCandidatsFiltered() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(CANDIDAT_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_CANDIDAT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateCandidat() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(CANDIDAT_API_PREFIX + CANDIDAT_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_CANDIDAT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteCandidat() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(CANDIDAT_API_PREFIX + CANDIDAT_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_CANDIDAT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}
