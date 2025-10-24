package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourrierRule {
    static final String COURRIER_API_PREFIX = "/courriers";
    static final String COURRIER_ID = "/{courrierId}";
    static final String ARCHIVER_PREFIX = "/archiver";
    static final String STATUT_PREFIX = "/statut";
    static final String STATISTIQUES_PREFIX = "/statistiques";
    static final String COMPTAGE_PREFIX = "/comptage";
    static final String ACTIFS_PREFIX = "/actifs";
    static final String NATURE_PREFIX = "/nature";
    static final String TYPE_PREFIX = "/type";

    @Bean
    public SecurityRule createCourrier() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(COURRIER_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_COURRIER)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readCourrier() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(COURRIER_API_PREFIX + COURRIER_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_COURRIER)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllCourrier() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(COURRIER_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_COURRIER)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateCourrier() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(COURRIER_API_PREFIX + COURRIER_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_COURRIER)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteCourrier() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(COURRIER_API_PREFIX + COURRIER_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_COURRIER)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule archiverCourrier() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(COURRIER_API_PREFIX + COURRIER_ID + ARCHIVER_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ARCHIVER_COURRIER)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule changerStatutCourrier() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(COURRIER_API_PREFIX + COURRIER_ID + STATUT_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.CHANGER_STATUT_COURRIER)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule countCourriers() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(COURRIER_API_PREFIX + STATISTIQUES_PREFIX + COMPTAGE_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_STATISTIQUES)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule countCourriersActifs() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(COURRIER_API_PREFIX + STATISTIQUES_PREFIX + ACTIFS_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_STATISTIQUES)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule getCourriersByNature() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(COURRIER_API_PREFIX + NATURE_PREFIX + "/{nature}")
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_COURRIER)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule getCourriersByType() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(COURRIER_API_PREFIX + TYPE_PREFIX + "/{type}")
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_COURRIER)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}