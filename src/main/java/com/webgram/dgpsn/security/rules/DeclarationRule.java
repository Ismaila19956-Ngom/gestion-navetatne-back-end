package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeclarationRule {
    static final String DECLARATION_API_PREFIX = "/declarations";
    static final String DECLARATION_ID = "/{declarationId}";

    @Bean
    public SecurityRule createDeclaration() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(DECLARATION_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_URGENCE_ENVIRO)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateDeclaration() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(DECLARATION_API_PREFIX + DECLARATION_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_URGENCE_ENVIRO)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readDeclaration() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(DECLARATION_API_PREFIX + DECLARATION_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_URGENCE_ENVIRO)
                .hasPermission(SecurityPermissions.EDIT_URGENCE_ENVIRO)
                .hasPermission(SecurityPermissions.READ_URGENCE_ENVIRO)
                .hasPermission(SecurityPermissions.DELETE_URGENCE_ENVIRO)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllDeclaration() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(DECLARATION_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_URGENCE_ENVIRO)
                .hasPermission(SecurityPermissions.EDIT_URGENCE_ENVIRO)
                .hasPermission(SecurityPermissions.READ_URGENCE_ENVIRO)
                .hasPermission(SecurityPermissions.DELETE_URGENCE_ENVIRO)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteDeclaration() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(DECLARATION_API_PREFIX + DECLARATION_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_URGENCE_ENVIRO)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}
