package sn.naavetane.backend.security.rules;

import sn.naavetane.backend.security.SecurityPermissions;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CaracteristiqueRecrutementRule {

    static final String CARACTERISTIQUE_RECRUTEMENT_API_PREFIX = "/caracteristiqueRecrutements";
    static final String CARACTERISTIQUE_RECRUTEMENT_ID = "/{caracteristiqueRecrutementId}";

    @Bean
    public SecurityRule createCaracteristiqueRecrutement() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(CARACTERISTIQUE_RECRUTEMENT_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_CARACTERISTIQUE_RECRUTEMENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readCaracteristiqueRecrutement() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(CARACTERISTIQUE_RECRUTEMENT_API_PREFIX + CARACTERISTIQUE_RECRUTEMENT_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_CARACTERISTIQUE_RECRUTEMENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readPageCcaracteristiqueRecrutement() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(CARACTERISTIQUE_RECRUTEMENT_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_CARACTERISTIQUE_RECRUTEMENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateCaracteristiqueRecrutement() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PATCH)
                .apiPattern(CARACTERISTIQUE_RECRUTEMENT_API_PREFIX + CARACTERISTIQUE_RECRUTEMENT_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_CARACTERISTIQUE_RECRUTEMENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteCaracteristiqueRecrutement() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(CARACTERISTIQUE_RECRUTEMENT_API_PREFIX + CARACTERISTIQUE_RECRUTEMENT_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_CARACTERISTIQUE_RECRUTEMENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}
