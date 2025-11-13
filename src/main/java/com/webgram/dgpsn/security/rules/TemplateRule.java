package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TemplateRule {
    static final String TEMPLATE_PREFIX = "/templates";
    //static final String TAG_ID = "/{tagId}";

    static final String TEMPLATE_ID = "/{templateId}";

    static final String TEMPLATE_ALERTE = "/readAllTypeAlerte";
    static final String TEMPLATE_ALERTE_NOT = "/alertTypeNotAdded";

    static final String TEMPLATE_PRIORITY = "/readAllPriorities";

    @Bean
    public SecurityRule createTemplate() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(TEMPLATE_PREFIX)
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
                .apiPattern(TEMPLATE_PREFIX + TEMPLATE_ID)
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
                .apiPattern(TEMPLATE_PREFIX + TEMPLATE_ID)
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
                .apiPattern(TEMPLATE_PREFIX)
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
                .apiPattern(TEMPLATE_PREFIX + TEMPLATE_ALERTE )
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
                .apiPattern(TEMPLATE_PREFIX + TEMPLATE_ALERTE_NOT )
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
                .apiPattern(TEMPLATE_PREFIX + TEMPLATE_ID )
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
                .apiPattern(TEMPLATE_PREFIX + TEMPLATE_PRIORITY)
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
