package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InformationDeclarationRule {
    static final String INFORMATION_DECLARATION_API_PREFIX = "/information-declarations";
    static final String INFORMATION_DECLARATION_ID = "/{informationDeclarationId}";

    @Bean
    public SecurityRule createInformationDeclaration() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(INFORMATION_DECLARATION_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateInformationDeclaration() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(INFORMATION_DECLARATION_API_PREFIX + INFORMATION_DECLARATION_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readInformationDeclaration() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(INFORMATION_DECLARATION_API_PREFIX + INFORMATION_DECLARATION_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllInformationDeclaration() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(INFORMATION_DECLARATION_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteInformationDeclaration() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(INFORMATION_DECLARATION_API_PREFIX + INFORMATION_DECLARATION_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}