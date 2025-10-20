package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TagRule {
    static final String TAG_PREFIX = "/tags";
    static final String TAG_ID = "/{tagId}";

    @Bean
    public SecurityRule createTag() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(TAG_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_TAG)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readTag() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(TAG_PREFIX + TAG_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_TAG)
                .hasPermission(SecurityPermissions.ADD_TAG)
                .hasPermission(SecurityPermissions.EDIT_TAG)
                .hasPermission(SecurityPermissions.DELETE_TAG)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readTags() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(TAG_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_TAG)
                .hasPermission(SecurityPermissions.ADD_TAG)
                .hasPermission(SecurityPermissions.EDIT_TAG)
                .hasPermission(SecurityPermissions.DELETE_TAG)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateTags() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(TAG_PREFIX + TAG_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_TAG)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule deleteTag() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(TAG_PREFIX + TAG_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_TAG)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}
