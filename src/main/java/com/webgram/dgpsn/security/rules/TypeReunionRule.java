package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TypeReunionRule {
    static final String TYPEREUNIONS_API_PREFIX = "/typereunions";
    static final String TYPEREUNION_ID = "/{typereunionId}";

    @Bean
    public SecurityRule createTypeReunion() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(TYPEREUNIONS_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readTypeReunion() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(TYPEREUNIONS_API_PREFIX + TYPEREUNION_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllTypeReunion() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(TYPEREUNIONS_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateTypeReunion() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(TYPEREUNIONS_API_PREFIX + TYPEREUNION_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule deleteTypeReunion() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(TYPEREUNIONS_API_PREFIX + TYPEREUNION_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

}
