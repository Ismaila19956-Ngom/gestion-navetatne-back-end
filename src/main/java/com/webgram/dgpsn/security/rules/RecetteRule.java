package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RecetteRule {
    static final String RECETTES_API_PREFIX = "/recettes";
    static final String RECETTE_ID = "/{recetteId}";
    static final String DEPENSE_EXPORT = "/export";


    @Bean
    public SecurityRule AddRecette() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(RECETTES_API_PREFIX)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.ADD_DEPENSE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllRecettes() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(RECETTES_API_PREFIX)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.READ_DEPENSE)
//                .hasPermission(SecurityPermissions.ADD_DEPENSE)
//                .hasPermission(SecurityPermissions.EDIT_DEPENSE)
//                .hasPermission(SecurityPermissions.DELETE_DEPENSE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readRecette() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(RECETTES_API_PREFIX + RECETTE_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.READ_DEPENSE)
//                .hasPermission(SecurityPermissions.ADD_DEPENSE)
//                .hasPermission(SecurityPermissions.EDIT_DEPENSE)
//                .hasPermission(SecurityPermissions.DELETE_DEPENSE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateRecette() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(RECETTES_API_PREFIX + RECETTE_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.EDIT_DEPENSE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteRecette() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(RECETTES_API_PREFIX + RECETTE_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.DELETE_DEPENSE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }


    @Bean
    public SecurityRule exportRecette() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(RECETTES_API_PREFIX + DEPENSE_EXPORT)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.EXPORT_DEPENSE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}
