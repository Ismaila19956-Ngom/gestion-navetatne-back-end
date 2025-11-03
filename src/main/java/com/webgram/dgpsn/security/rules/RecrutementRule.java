package com.webgram.dgpsn.security.rules;

import com.webgram.dgpsn.security.SecurityPermissions;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RecrutementRule {
    static final String RECRUTEMENT_API_PREFIX = "/recrutements";
    static final String RECRUTEMENT_ID = "/{recrutementId}";
    static final String DOWNLOAD_PREFIX = "/_download";
    static final String IMPORT_PREFIX = "/import";
    static final String EXPORT_PREFIX = "/export";
    static final String FILTER_NOT_IN_USER = "/notInUser";
    static final String CANDIDATS_PREFIX = "/candidats";
    static final String CANDIDAT_ID = "/{idCandidat}";



    @Bean
    public SecurityRule createRecrutement() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(RECRUTEMENT_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_RECRUTEMENT)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readRecrutement() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(RECRUTEMENT_API_PREFIX + RECRUTEMENT_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_RECRUTEMENT)
                .hasPermission(SecurityPermissions.ADD_RECRUTEMENT)
                .hasPermission(SecurityPermissions.EDIT_RECRUTEMENT)
                .hasPermission(SecurityPermissions.DELETE_RECRUTEMENT)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllRecrutement() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(RECRUTEMENT_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_RECRUTEMENT)
                .hasPermission(SecurityPermissions.ADD_RECRUTEMENT)
                .hasPermission(SecurityPermissions.EDIT_RECRUTEMENT)
                .hasPermission(SecurityPermissions.DELETE_RECRUTEMENT)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateRecrutement() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(RECRUTEMENT_API_PREFIX + RECRUTEMENT_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_RECRUTEMENT)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteRecrutement() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(RECRUTEMENT_API_PREFIX + RECRUTEMENT_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_RECRUTEMENT)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateStatutRecrutement() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                // Le pattern doit correspondre exactement à l'URL de votre API
                .apiPattern(RECRUTEMENT_API_PREFIX + RECRUTEMENT_ID + "/statut")
                .build()
                .condition()
                // Autorise les utilisateurs ayant l'une de ces permissions
                .hasPermission(SecurityPermissions.UPDATE_STATUT_RECRUTEMENT)
                .hasPermission(SecurityPermissions.EDIT_RECRUTEMENT) // Vous pouvez aussi réutiliser une permission existante
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule importRecrutement() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(RECRUTEMENT_API_PREFIX + IMPORT_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.IMPORT_RECRUTEMENT)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule exportRecrutement() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(RECRUTEMENT_API_PREFIX + EXPORT_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EXPORT_RECRUTEMENT)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule readFileRecrutement() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(RECRUTEMENT_API_PREFIX + RECRUTEMENT_ID + DOWNLOAD_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_FILE_RECRUTEMENT)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readRecrutementNotInUser() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(RECRUTEMENT_API_PREFIX + FILTER_NOT_IN_USER)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_RECRUTEMENT)
                .hasPermission(SecurityPermissions.ADD_RECRUTEMENT)
                .hasPermission(SecurityPermissions.EDIT_RECRUTEMENT)
                .hasPermission(SecurityPermissions.DELETE_RECRUTEMENT)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }


    @Bean
    public SecurityRule addCandidatToRecrutement() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(RECRUTEMENT_API_PREFIX + RECRUTEMENT_ID + CANDIDATS_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_CANDIDAT)
                .hasPermission(SecurityPermissions.ADD_RECRUTEMENT)
                .hasPermission(SecurityPermissions.EDIT_RECRUTEMENT)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readCandidatsByRecrutement() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(RECRUTEMENT_API_PREFIX + RECRUTEMENT_ID + CANDIDATS_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_CANDIDAT)
                .hasPermission(SecurityPermissions.READ_RECRUTEMENT)
                .hasPermission(SecurityPermissions.ADD_RECRUTEMENT)
                .hasPermission(SecurityPermissions.EDIT_RECRUTEMENT)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateCandidatOfRecrutement() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(RECRUTEMENT_API_PREFIX + RECRUTEMENT_ID + CANDIDATS_PREFIX + CANDIDAT_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_CANDIDAT)
                .hasPermission(SecurityPermissions.READ_RECRUTEMENT)
                .hasPermission(SecurityPermissions.ADD_RECRUTEMENT)
                .hasPermission(SecurityPermissions.EDIT_RECRUTEMENT)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}
