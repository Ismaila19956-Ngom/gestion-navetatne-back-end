package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AgrementRule {
    static final String API_PREFIX = "/agrements";
    static final String API_ID = "/{id}";

    @Bean
    public SecurityRule createAgrementRule() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_AGREMENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAgrementRule() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(API_PREFIX + "/**")
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_AGREMENT)
                .hasPermission(SecurityPermissions.ADD_AGREMENT)
                .hasPermission(SecurityPermissions.EDIT_AGREMENT)
                .hasPermission(SecurityPermissions.DELETE_AGREMENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateAgrementRule() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(API_PREFIX + API_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_AGREMENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteAgrementRule() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(API_PREFIX + API_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_AGREMENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}