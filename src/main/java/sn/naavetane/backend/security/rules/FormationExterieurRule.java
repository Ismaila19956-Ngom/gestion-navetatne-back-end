package sn.naavetane.backend.security.rules;

import sn.naavetane.backend.security.SecurityPermissions;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FormationExterieurRule {

    static final String FORMATION_EXTERIEUR_API_PREFIX = "/formation-exterieurs";
    static final String FORMATION_EXTERIEUR_ID = "/{formationExterieurId}";

    @Bean
    public SecurityRule formationExterieurAdd() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(FORMATION_EXTERIEUR_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_FORMATION_EXTERIEUR)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readFormationExterieur() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(FORMATION_EXTERIEUR_API_PREFIX + FORMATION_EXTERIEUR_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_FORMATION_EXTERIEUR)
                .hasPermission(SecurityPermissions.ADD_FORMATION_EXTERIEUR)
                .hasPermission(SecurityPermissions.EDIT_FORMATION_EXTERIEUR)
                .hasPermission(SecurityPermissions.DELETE_FORMATION_EXTERIEUR)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllFormationExterieurByProject() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(FORMATION_EXTERIEUR_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_FORMATION_EXTERIEUR)
                .hasPermission(SecurityPermissions.ADD_FORMATION_EXTERIEUR)
                .hasPermission(SecurityPermissions.EDIT_FORMATION_EXTERIEUR)
                .hasPermission(SecurityPermissions.DELETE_FORMATION_EXTERIEUR)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateFormationExterieur() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(FORMATION_EXTERIEUR_API_PREFIX + FORMATION_EXTERIEUR_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_FORMATION_EXTERIEUR)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteFormationExterieur() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(FORMATION_EXTERIEUR_API_PREFIX + FORMATION_EXTERIEUR_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_FORMATION_EXTERIEUR)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}
