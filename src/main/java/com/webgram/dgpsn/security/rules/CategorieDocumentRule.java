package com.webgram.dgpsn.security.rules;

import com.webgram.dgpsn.security.SecurityPermissions;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategorieDocumentRule {
    static final String CATEGORIE_DOCUMENT_ID_PREFIX = "/categorieDocument";
    static final String CATEGORIE_DOCUMENT_ID = "/{docId}";

    static  final String CATEGORIE_DOCUMENT_LIST="/categoryDocument";

    @Bean
    public SecurityRule createCategorieDocument() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(CATEGORIE_DOCUMENT_ID_PREFIX)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.ADD_LABEL)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readCategorieDocument() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(CATEGORIE_DOCUMENT_ID_PREFIX + CATEGORIE_DOCUMENT_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.READ_LABEL)
//                .hasPermission(SecurityPermissions.ADD_LABEL)
//                .hasPermission(SecurityPermissions.EDIT_LABEL)
//                .hasPermission(SecurityPermissions.DELETE_LABEL)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllCategorieDocument() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(CATEGORIE_DOCUMENT_ID_PREFIX)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.READ_LABEL)
//                .hasPermission(SecurityPermissions.ADD_LABEL)
//                .hasPermission(SecurityPermissions.EDIT_LABEL)
//                .hasPermission(SecurityPermissions.DELETE_LABEL)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule readAllListCategory() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(CATEGORIE_DOCUMENT_ID_PREFIX+CATEGORIE_DOCUMENT_LIST)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.READ_LABEL)
//                .hasPermission(SecurityPermissions.ADD_LABEL)
//                .hasPermission(SecurityPermissions.EDIT_LABEL)
//                .hasPermission(SecurityPermissions.DELETE_LABEL)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateCategorieDocument() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(CATEGORIE_DOCUMENT_ID_PREFIX + CATEGORIE_DOCUMENT_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.EDIT_LABEL)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule deleteCategorieDocument() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(CATEGORIE_DOCUMENT_ID_PREFIX + CATEGORIE_DOCUMENT_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.DELETE_LABEL)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }


}
