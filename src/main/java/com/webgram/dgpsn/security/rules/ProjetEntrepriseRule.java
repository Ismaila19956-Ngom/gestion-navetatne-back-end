package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProjetEntrepriseRule {
    static final String PROJET_ENTREPRISE_API_PREFIX = "/project-entreprise";
    static final String PROJET_ENTREPRISE_ID = "/{projetEntrepriseId}";


    @Bean
    public SecurityRule createProjetByEntreprise() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(PROJET_ENTREPRISE_API_PREFIX)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.ADD_PROJET_ENTREPRISE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readProjectByEntreprise() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(PROJET_ENTREPRISE_API_PREFIX + PROJET_ENTREPRISE_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.READ_PROJET_ENTREPRISE)
//                .hasPermission(SecurityPermissions.ADD_PROJET_ENTREPRISE)
//                .hasPermission(SecurityPermissions.EDIT_PROJET_ENTREPRISE)
//                .hasPermission(SecurityPermissions.DELETE_PROJET_ENTREPRISE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllProjectByEntrepriseId() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(PROJET_ENTREPRISE_API_PREFIX)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.READ_PROJET_ENTREPRISE)
//                .hasPermission(SecurityPermissions.ADD_PROJET_ENTREPRISE)
//                .hasPermission(SecurityPermissions.EDIT_PROJET_ENTREPRISE)
//                .hasPermission(SecurityPermissions.DELETE_PROJET_ENTREPRISE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateProjetByEntreprise() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(PROJET_ENTREPRISE_API_PREFIX + PROJET_ENTREPRISE_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.EDIT_PROJET_ENTREPRISE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule deleteProjectByEntrepriseId() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(PROJET_ENTREPRISE_API_PREFIX + PROJET_ENTREPRISE_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.DELETE_PROJET_ENTREPRISE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

}
