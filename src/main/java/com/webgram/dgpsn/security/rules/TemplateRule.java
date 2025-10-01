package com.webgram.dgpsn.security.rules;

import com.webgram.dgpsn.security.SecurityPermissions;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TemplateRule {
    static final String ALERTE_PREFIX = "/templates";
    //static final String TAG_ID = "/{tagId}";

    static final String ALERTE_ID = "/{templateId}";

    static final String ALERTE_ALERTE = "/readAllTypeAlerte";
    static final String ALERTE_ALERTE_NOT = "/alertTypeNotAdded";

    static final String ALERTE_PRIORITY = "/readAllPriorities";
    static final String ALERTE_CATEGORY_ALERTE = "/readAllCategorieAlerte";

    @Bean
    public SecurityRule createTemplate() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(ALERTE_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_ALERTE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateTemplate() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(ALERTE_PREFIX + ALERTE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_ALERTE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readTemplates() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(ALERTE_PREFIX + ALERTE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_ALERTE)
                .hasPermission(SecurityPermissions.ADD_ALERTE)
                .hasPermission(SecurityPermissions.EDIT_ALERTE)
                .hasPermission(SecurityPermissions.DELETE_ALERTE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllTemplates() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(ALERTE_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_ALERTE)
                .hasPermission(SecurityPermissions.ADD_ALERTE)
                .hasPermission(SecurityPermissions.EDIT_ALERTE)
                .hasPermission(SecurityPermissions.DELETE_ALERTE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule readAllTypeAlerte() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(ALERTE_PREFIX + ALERTE_ALERTE )
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_ALERTE)
                .hasPermission(SecurityPermissions.ADD_ALERTE)
                .hasPermission(SecurityPermissions.EDIT_ALERTE)
                .hasPermission(SecurityPermissions.DELETE_ALERTE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllTypeAlerteNot() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(ALERTE_PREFIX + ALERTE_ALERTE_NOT )
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_ALERTE)
                .hasPermission(SecurityPermissions.ADD_ALERTE)
                .hasPermission(SecurityPermissions.EDIT_ALERTE)
                .hasPermission(SecurityPermissions.DELETE_ALERTE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule deleteTemplate() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(ALERTE_PREFIX + ALERTE_ID )
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_ALERTE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllPriorities() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(ALERTE_PREFIX + ALERTE_PRIORITY)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_ALERTE)
                .hasPermission(SecurityPermissions.ADD_ALERTE)
                .hasPermission(SecurityPermissions.EDIT_ALERTE)
                .hasPermission(SecurityPermissions.DELETE_ALERTE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllCategoryAlert() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(ALERTE_PREFIX + ALERTE_CATEGORY_ALERTE)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_ALERTE)
                .hasPermission(SecurityPermissions.ADD_ALERTE)
                .hasPermission(SecurityPermissions.EDIT_ALERTE)
                .hasPermission(SecurityPermissions.DELETE_ALERTE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}
