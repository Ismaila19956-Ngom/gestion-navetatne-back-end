package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubCategoryRule {
    static final String SUB_CATEGORY_PREFIX = "/subCategories";
    static final String SUB_CATEGORY_ID = "/{subCategoryId}";

    @Bean
    public SecurityRule createSub_Category() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(SUB_CATEGORY_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_SUB_CATEGORY)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readSub_Category() {
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
    public SecurityRule readSub_Categorys() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(SUB_CATEGORY_PREFIX)
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
    public SecurityRule updateSub_Category() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(SUB_CATEGORY_PREFIX + SUB_CATEGORY_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_SUB_CATEGORY)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteSubCategory() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern( SUB_CATEGORY_PREFIX + SUB_CATEGORY_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_SUB_CATEGORY)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}
