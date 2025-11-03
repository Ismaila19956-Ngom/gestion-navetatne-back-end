package com.webgram.dgpsn.security.rules;

import com.webgram.dgpsn.security.SecurityPermissions;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotationRule {

    static final String NOTATION_API_PREFIX = "/notations";
    static final String NOTATION_ID = "/{notationId}";
    static final String CANDIDAT_PREFIX = "/candidat/{candidatId}";

    @Bean
    public SecurityRule createNotation() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(NOTATION_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_NOTATION)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateNotation() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(NOTATION_API_PREFIX + NOTATION_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_NOTATION)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteNotation() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(NOTATION_API_PREFIX + NOTATION_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_NOTATION)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readNotation() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(NOTATION_API_PREFIX + NOTATION_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_NOTATION)
                .hasPermission(SecurityPermissions.ADD_NOTATION)
                .hasPermission(SecurityPermissions.EDIT_NOTATION)
                .hasPermission(SecurityPermissions.DELETE_NOTATION)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readNotationsByCandidat() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(NOTATION_API_PREFIX + CANDIDAT_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_NOTATION)
                .hasPermission(SecurityPermissions.ADD_NOTATION)
                .hasPermission(SecurityPermissions.EDIT_NOTATION)
                .hasPermission(SecurityPermissions.DELETE_NOTATION)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllNotations() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(NOTATION_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_NOTATION)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}
