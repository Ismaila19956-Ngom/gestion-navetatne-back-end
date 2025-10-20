package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EtablissementClasseRule {

    static final String ETABLISSEMENT_CLASSE_API_PREFIX = "/etablissements-classes";
    static final String ETABLISSEMENT_CLASSE_ID = "/{etablissementClasseId}";

    @Bean
    public SecurityRule createEtablissementClasse() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(ETABLISSEMENT_CLASSE_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_ETABLISSEMENTS_CLASSES)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateEtablissementClasse() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(ETABLISSEMENT_CLASSE_API_PREFIX + ETABLISSEMENT_CLASSE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_ETABLISSEMENTS_CLASSES)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteEtablissementClasse() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(ETABLISSEMENT_CLASSE_API_PREFIX + ETABLISSEMENT_CLASSE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_ETABLISSEMENTS_CLASSES)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllEtablissementClasse() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(ETABLISSEMENT_CLASSE_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_ETABLISSEMENTS_CLASSES)
                .hasPermission(SecurityPermissions.ADD_ETABLISSEMENTS_CLASSES)
                .hasPermission(SecurityPermissions.EDIT_ETABLISSEMENTS_CLASSES)
                .hasPermission(SecurityPermissions.DELETE_ETABLISSEMENTS_CLASSES)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

}
