package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EntrepriseRule {
    static final String ENTREPRISE_API_PREFIX = "/entreprises";
    static final String ENTREPRISE_ID = "/{entrepriseId}";

    @Bean
    public SecurityRule createEntreprise() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(ENTREPRISE_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readEntreprise() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(ENTREPRISE_API_PREFIX + ENTREPRISE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllEntreprise() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(ENTREPRISE_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateEntreprise() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(ENTREPRISE_API_PREFIX + ENTREPRISE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule deleteEntreprise() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(ENTREPRISE_API_PREFIX + ENTREPRISE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

}
