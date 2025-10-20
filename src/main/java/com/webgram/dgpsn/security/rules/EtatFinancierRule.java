package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EtatFinancierRule {
    static final String ETAT_FINANCIER_API_PREFIX = "/etatFinanciers";
    static final String ETAT_FINANCIER_ID = "/{etatFinancierId}";


    @Bean
    public SecurityRule AddEtatFinancier() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(ETAT_FINANCIER_API_PREFIX)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.ADD_DEPENSE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllEtatFinanciers() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(ETAT_FINANCIER_API_PREFIX)
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
    public SecurityRule readEtatFinancier() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(ETAT_FINANCIER_API_PREFIX + ETAT_FINANCIER_ID)
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
    public SecurityRule updateEtatFinancier() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(ETAT_FINANCIER_API_PREFIX + ETAT_FINANCIER_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.EDIT_DEPENSE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteEtatFinancier() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(ETAT_FINANCIER_API_PREFIX + ETAT_FINANCIER_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.DELETE_DEPENSE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

}
