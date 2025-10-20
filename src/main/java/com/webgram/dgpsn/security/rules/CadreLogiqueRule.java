package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CadreLogiqueRule {
    static final String CADRE_LOGIQUE_API_PREFIX = "/cadreLogique";
    static final String CADRE_LOGIQUE_ID = "/{cadreLogiqueId}";
    static final String TREE_PREFIX = "/tree";
    static final String REGION = "/regions";
    static final String BY_PARENT = "/byParent";

    @Bean
    public SecurityRule createCadreLogique() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(CADRE_LOGIQUE_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_CADRE_LOGIQUE)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readCadreLogique() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(CADRE_LOGIQUE_API_PREFIX + CADRE_LOGIQUE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_CADRE_LOGIQUE)
                .hasPermission(SecurityPermissions.ADD_CADRE_LOGIQUE)
                .hasPermission(SecurityPermissions.EDIT_CADRE_LOGIQUE)
                .hasPermission(SecurityPermissions.DELETE_CADRE_LOGIQUE)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.READ_TDB_RESUME)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllCadreLogique() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(CADRE_LOGIQUE_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_CADRE_LOGIQUE)
                .hasPermission(SecurityPermissions.ADD_CADRE_LOGIQUE)
                .hasPermission(SecurityPermissions.EDIT_CADRE_LOGIQUE)
                .hasPermission(SecurityPermissions.DELETE_CADRE_LOGIQUE)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.READ_TDB_RESUME)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule readTreeCadreLogique() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(CADRE_LOGIQUE_API_PREFIX + TREE_PREFIX + CADRE_LOGIQUE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_CADRE_LOGIQUE)
                .hasPermission(SecurityPermissions.ADD_CADRE_LOGIQUE)
                .hasPermission(SecurityPermissions.EDIT_CADRE_LOGIQUE)
                .hasPermission(SecurityPermissions.DELETE_CADRE_LOGIQUE)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.READ_TDB_RESUME)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateCadreLogique() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(CADRE_LOGIQUE_API_PREFIX + CADRE_LOGIQUE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_CADRE_LOGIQUE)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule deleteCadreLogique() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(CADRE_LOGIQUE_API_PREFIX + CADRE_LOGIQUE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_CADRE_LOGIQUE)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readRegions() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(CADRE_LOGIQUE_API_PREFIX + REGION)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_CADRE_LOGIQUE)
                .hasPermission(SecurityPermissions.ADD_CADRE_LOGIQUE)
                .hasPermission(SecurityPermissions.EDIT_CADRE_LOGIQUE)
                .hasPermission(SecurityPermissions.DELETE_CADRE_LOGIQUE)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.READ_TDB_RESUME)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readByParent() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(CADRE_LOGIQUE_API_PREFIX + BY_PARENT + CADRE_LOGIQUE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_CADRE_LOGIQUE)
                .hasPermission(SecurityPermissions.ADD_CADRE_LOGIQUE)
                .hasPermission(SecurityPermissions.EDIT_CADRE_LOGIQUE)
                .hasPermission(SecurityPermissions.DELETE_CADRE_LOGIQUE)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.READ_TDB_RESUME)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }



}
