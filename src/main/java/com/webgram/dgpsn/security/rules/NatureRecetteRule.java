package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NatureRecetteRule {
    static final String NATURE_RECETTE_API_PREFIX = "/naturedelarecettes";
    static final String NATURE_RECETTE_ID = "/{naturedelarecetteId}";
    @Bean
    public SecurityRule createRecettes() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(NATURE_RECETTE_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_NATURE_RECETTE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readRecettes() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(NATURE_RECETTE_API_PREFIX + NATURE_RECETTE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_NATURE_RECETTE)
                .hasPermission(SecurityPermissions.ADD_NATURE_RECETTE)
                .hasPermission(SecurityPermissions.EDIT_NATURE_RECETTE)
                .hasPermission(SecurityPermissions.DELETE_NATURE_RECETTE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllRecette() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(NATURE_RECETTE_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_NATURE_RECETTE)
                .hasPermission(SecurityPermissions.ADD_NATURE_RECETTE)
                .hasPermission(SecurityPermissions.EDIT_NATURE_RECETTE)
                .hasPermission(SecurityPermissions.DELETE_NATURE_RECETTE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateRecettes() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(NATURE_RECETTE_API_PREFIX + NATURE_RECETTE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_NATURE_RECETTE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule deleteRecettes() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(NATURE_RECETTE_API_PREFIX + NATURE_RECETTE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_NATURE_RECETTE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}
