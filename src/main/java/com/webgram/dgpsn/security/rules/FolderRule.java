package com.webgram.dgpsn.security.rules;

import com.webgram.dgpsn.security.SecurityPermissions;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FolderRule {
    static final String FOLDER_API_PREFIX = "/folders";
    static final String FOLDER_ID = "/{folderId}";

    @Bean
    public SecurityRule createFolder() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(FOLDER_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_FOLDER)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readFolder() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(FOLDER_API_PREFIX + FOLDER_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_FOLDER)
                .hasPermission(SecurityPermissions.ADD_FOLDER)
                .hasPermission(SecurityPermissions.EDIT_FOLDER)
                .hasPermission(SecurityPermissions.DELETE_FOLDER)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllFolder() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(FOLDER_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_FOLDER)
                .hasPermission(SecurityPermissions.EDIT_FOLDER)
                .hasPermission(SecurityPermissions.DELETE_FOLDER)
                .hasPermission(SecurityPermissions.ADD_FOLDER)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateFolder() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(FOLDER_API_PREFIX + FOLDER_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_FOLDER)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule deleteFolder() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(FOLDER_API_PREFIX + FOLDER_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_FOLDER)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}
