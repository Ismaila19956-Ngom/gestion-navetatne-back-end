package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SuivDeclarationRule {
    static final String SUIV_DECLARATION_API_PREFIX = "/suiv-declarations";
    static final String SUIV_DECLARATION_ID = "/{suivDeclarationId}";

    @Bean
    public SecurityRule createSuivDeclaration() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(SUIV_DECLARATION_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateSuivDeclaration() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(SUIV_DECLARATION_API_PREFIX + SUIV_DECLARATION_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readSuivDeclaration() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(SUIV_DECLARATION_API_PREFIX + SUIV_DECLARATION_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllSuivDeclaration() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(SUIV_DECLARATION_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteSuivDeclaration() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(SUIV_DECLARATION_API_PREFIX + SUIV_DECLARATION_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}