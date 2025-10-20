package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RepertoireDeclarationRule {
    static final String REPERTOIRE_DECLARATION_API_PREFIX = "/repertoire-declarations";
    static final String REPERTOIRE_DECLARATION_ID = "/{repertoireDeclarationId}";

    @Bean
    public SecurityRule createRepertoireDeclaration() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(REPERTOIRE_DECLARATION_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateRepertoireDeclaration() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(REPERTOIRE_DECLARATION_API_PREFIX + REPERTOIRE_DECLARATION_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readRepertoireDeclaration() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(REPERTOIRE_DECLARATION_API_PREFIX + REPERTOIRE_DECLARATION_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllRepertoireDeclaration() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(REPERTOIRE_DECLARATION_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteRepertoireDeclaration() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(REPERTOIRE_DECLARATION_API_PREFIX + REPERTOIRE_DECLARATION_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}