package com.webgram.dgpsn.security.rules;

import com.webgram.dgpsn.security.SecurityPermissions;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;


@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CongeRule {
    static final String CONGE_API_PREFIX = "/conge";
    static final String CONGE_ID = "/{congeId}";
    static final String DOCUMENT_ID = "/pdf";
    static final String CONGE_LIST = "/list";
    static final String CONGE_STATUT_UPDATE = "/updateStatut";
    static final String CONGE_DOCUMENT = "/docOrdre";
    static final String CONGE_DELETE_DOCUMENT_ID = "/doc";
    static final String CONGE_PAGE = "/page";
    static final String CONGE_NUMERO_DECISION = "/numero-decision";
    static final String  CONGE_VALIDATE = "/validate";

    @Bean
    public SecurityRule createConge() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(CONGE_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_CONGE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule createCongeDocument() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(CONGE_API_PREFIX + CONGE_DOCUMENT + CONGE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_CONGE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateConge() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(CONGE_API_PREFIX + CONGE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_CONGE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateCongeStatut() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(CONGE_API_PREFIX + CONGE_ID + CONGE_STATUT_UPDATE)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_CONGE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateNumeroDecision() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(CONGE_API_PREFIX + CONGE_ID + CONGE_NUMERO_DECISION)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_CONGE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readConge() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(CONGE_API_PREFIX + CONGE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_CONGE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readDocumentConge() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(CONGE_API_PREFIX + CONGE_ID + DOCUMENT_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_CONGE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readListConge() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(CONGE_API_PREFIX + CONGE_LIST)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_CONGE)
                .hasPermission(SecurityPermissions.ADD_CONGE)
                .hasPermission(SecurityPermissions.EDIT_CONGE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readPageConge() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(CONGE_API_PREFIX + CONGE_PAGE)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.SUIVIE_AGENT_DOUANIER)
                .hasPermission(SecurityPermissions.READ_CONGE)
                .hasPermission(SecurityPermissions.ADD_CONGE)
                .hasPermission(SecurityPermissions.EDIT_CONGE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteConge() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(CONGE_API_PREFIX + CONGE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_CONGE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteDocumentConge() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(CONGE_API_PREFIX + CONGE_DELETE_DOCUMENT_ID + CONGE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_CONGE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule validateFluxtresorie() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(CONGE_API_PREFIX + CONGE_VALIDATE)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.VALIDATE_FLUX_TRESORERIE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}
