package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MediathequeRule {
    static final String  MEDIATHEQUE_API_PREFIX = "/mediatheques";
     static final String MEDIATHEQUE_ID = "/{mediathequeId}";
    static final String MEDIATHEQUE_BY_DOWNLOAD_PREFIX = "/{id}/_download";
   

    @Bean
    public SecurityRule createMediatheque() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(MEDIATHEQUE_API_PREFIX )
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_MEDIATHEQUE)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }


    @Bean
    public SecurityRule readDownloadFile() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(MEDIATHEQUE_API_PREFIX  + MEDIATHEQUE_BY_DOWNLOAD_PREFIX )
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_MEDIATHEQUE)
                .hasPermission(SecurityPermissions.ADD_MEDIATHEQUE)
                .hasPermission(SecurityPermissions.EDIT_MEDIATHEQUE)
                .hasPermission(SecurityPermissions.DELETE_MEDIATHEQUE)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule  readMediatheque() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(MEDIATHEQUE_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_MEDIATHEQUE)
                .hasPermission(SecurityPermissions.ADD_MEDIATHEQUE)
                .hasPermission(SecurityPermissions.EDIT_MEDIATHEQUE)
                .hasPermission(SecurityPermissions.DELETE_MEDIATHEQUE)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateMediatheque() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(MEDIATHEQUE_API_PREFIX + MEDIATHEQUE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_MEDIATHEQUE)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule deleteMediatheque() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(MEDIATHEQUE_API_PREFIX + MEDIATHEQUE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_MEDIATHEQUE)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}
