package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

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
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule createSubFolder() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(FOLDER_API_PREFIX + FOLDER_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAFolder() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(FOLDER_API_PREFIX + FOLDER_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readParentsFolder() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(FOLDER_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllFolders() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(FOLDER_API_PREFIX+"/all")
                .build()
                .condition()
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
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}
