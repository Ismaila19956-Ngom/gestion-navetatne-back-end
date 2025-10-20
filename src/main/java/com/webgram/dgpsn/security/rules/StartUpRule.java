package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StartUpRule {
    static final String AGENT_API_PREFIX = "/startUp";
    static final String AGENT_ID = "/{startUpId}";



    @Bean
    public SecurityRule createstartUp() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(AGENT_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_STARTUP)
//                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readstartUp() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(AGENT_API_PREFIX + AGENT_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_STARTUP)
                .hasPermission(SecurityPermissions.ADD_STARTUP)
                .hasPermission(SecurityPermissions.EDIT_STARTUP)
                .hasPermission(SecurityPermissions.DELETE_STARTUP)
//                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllStartUp() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(AGENT_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_STARTUP)
                .hasPermission(SecurityPermissions.ADD_STARTUP)
                .hasPermission(SecurityPermissions.EDIT_STARTUP)
                .hasPermission(SecurityPermissions.DELETE_STARTUP)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updatestartUp() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(AGENT_API_PREFIX + AGENT_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_STARTUP)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule deletestartUp() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(AGENT_API_PREFIX + AGENT_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_STARTUP)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

}
