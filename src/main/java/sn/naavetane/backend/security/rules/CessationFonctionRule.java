package sn.naavetane.backend.security.rules;

import sn.naavetane.backend.security.SecurityPermissions;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;


@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CessationFonctionRule {
    static final String CESSATION_API_PREFIX = "/cessationFonction";
    static final String CESSATION_ID = "/{cessationId}";
    static final String CESSATION_LIST = "/list";
    static final String CESSATION_PAGE = "/page";

    static final String CESSATION_DUREE = "/duree";
    static final String CESSATION_SOLDE_CONGE = "/solde";
    static  final  String CESSATION_SOLDE_RESTANT ="/traiter-solde-restant";

    @Bean
    public SecurityRule createCessation() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(CESSATION_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_CONGE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule traiterSoldeRestantManuellement() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(CESSATION_API_PREFIX + CESSATION_SOLDE_RESTANT)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_CONGE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule updateCessation() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(CESSATION_API_PREFIX + CESSATION_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_CONGE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }


    @Bean
    public SecurityRule readCessation() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(CESSATION_API_PREFIX + CESSATION_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_CONGE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule getSoldeAnnuelByCongeId() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(CESSATION_API_PREFIX + CESSATION_ID + CESSATION_SOLDE_CONGE)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_CONGE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule getLatestDureeSoldeByCongeId() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(CESSATION_API_PREFIX + CESSATION_DUREE + CESSATION_ID )
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_CESSATION_CONGE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }


    @Bean
    public SecurityRule readListCessation() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(CESSATION_API_PREFIX + CESSATION_LIST)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_CESSATION_CONGE)
                .hasPermission(SecurityPermissions.ADD_CESSATION)
                .hasPermission(SecurityPermissions.EDIT_CESSATION)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readPageCessation() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(CESSATION_API_PREFIX + CESSATION_PAGE)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_CESSATION_CONGE)
                .hasPermission(SecurityPermissions.ADD_CESSATION)
                .hasPermission(SecurityPermissions.EDIT_CESSATION)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteCessation() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(CESSATION_API_PREFIX + CESSATION_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_CESSATION)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

}
