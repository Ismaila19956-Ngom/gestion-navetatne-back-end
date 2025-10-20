package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubSectorRule {
    static final String SUB_CATEGORY_PREFIX = "/subSectors";
    static final String SUB_CATEGORY_ID = "/{subSectorId}";

    @Bean
    public SecurityRule createSubSector() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(SUB_CATEGORY_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_SUB_CATEGORY)
                .hasPermission(SecurityPermissions.ADD_PROJECT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readSubSector() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(SUB_CATEGORY_PREFIX + SUB_CATEGORY_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_SUB_CATEGORY)
                .hasPermission(SecurityPermissions.ADD_SUB_CATEGORY)
                .hasPermission(SecurityPermissions.EDIT_SUB_CATEGORY)
                .hasPermission(SecurityPermissions.DELETE_SUB_CATEGORY)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readSubSectors() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(SUB_CATEGORY_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_SUB_CATEGORY)
                .hasPermission(SecurityPermissions.ADD_SUB_CATEGORY)
                .hasPermission(SecurityPermissions.EDIT_SUB_CATEGORY)
                .hasPermission(SecurityPermissions.DELETE_SUB_CATEGORY)
                // ajout issa
                .hasPermission(SecurityPermissions.ADD_PROJECT)
                .hasPermission(SecurityPermissions.EDIT_PROJECT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateSubSector() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(SUB_CATEGORY_PREFIX + SUB_CATEGORY_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_SUB_CATEGORY)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteSubSector() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(SUB_CATEGORY_PREFIX + SUB_CATEGORY_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_SUB_CATEGORY)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}
