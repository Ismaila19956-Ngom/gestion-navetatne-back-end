package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InstructionRule {
    static final String API_PREFIX = "/instructions";
    static final String API_ID = "/{id}";
    static final String API_ETAPE = "/{id}/etape";

    @Bean
    public SecurityRule createInstructionRule() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(API_PREFIX)
                .build()
                .condition()
                 .hasPermission(SecurityPermissions.ADD_INSTRUCTION)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    
    @Bean
    public SecurityRule readInstructionRule() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(API_PREFIX + "/**") // Covers both list and by id
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_INSTRUCTION)
                .hasPermission(SecurityPermissions.ADD_INSTRUCTION)
                .hasPermission(SecurityPermissions.EDIT_INSTRUCTION)
                .hasPermission(SecurityPermissions.DELETE_INSTRUCTION)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    
    @Bean
    public SecurityRule updateInstructionRule() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(API_PREFIX + API_ID)
                .build()
                .condition()
                 .hasPermission(SecurityPermissions.EDIT_INSTRUCTION)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    
    @Bean
    public SecurityRule deleteInstructionRule() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(API_PREFIX + API_ID)
                .build()
                .condition()
                 .hasPermission(SecurityPermissions.DELETE_INSTRUCTION)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateInstructionEtapeRule() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PATCH)
                .apiPattern(API_PREFIX + API_ETAPE)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_INSTRUCTION)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}