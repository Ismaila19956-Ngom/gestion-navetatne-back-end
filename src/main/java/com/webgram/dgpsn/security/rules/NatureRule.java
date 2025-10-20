package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NatureRule {
    static final String NATURE_API_PREFIX = "/natures";
    static final String NATURE_ID = "/{natureId}";
    @Bean
    public SecurityRule createNature() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(NATURE_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_NATURE)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readNature() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(NATURE_API_PREFIX + NATURE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_NATURE)
                .hasPermission(SecurityPermissions.ADD_NATURE)
                .hasPermission(SecurityPermissions.EDIT_NATURE)
                .hasPermission(SecurityPermissions.DELETE_NATURE)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAlNatures() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(NATURE_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_NATURE)
                .hasPermission(SecurityPermissions.ADD_NATURE)
                .hasPermission(SecurityPermissions.EDIT_NATURE)
                .hasPermission(SecurityPermissions.DELETE_NATURE)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateNature() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(NATURE_API_PREFIX + NATURE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_NATURE)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule deleteNature() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(NATURE_API_PREFIX + NATURE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_NATURE)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}
