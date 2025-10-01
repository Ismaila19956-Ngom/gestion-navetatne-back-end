package com.webgram.dgpsn.security.rules;

import com.webgram.dgpsn.security.SecurityPermissions;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StructureRule {
    static final String STRUCTURE_API_PREFIX = "/structures";
    static final String STRUCTURE_ID = "/{structureId}";

    @Bean
    public SecurityRule createStructure() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(STRUCTURE_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
//                .hasPermission(SecurityPermissions.ADD_STRUCTURE)
                .end();
    }

    @Bean
    public SecurityRule readStructure() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(STRUCTURE_API_PREFIX + STRUCTURE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
//                .hasPermission(SecurityPermissions.ADD_STRUCTURE)
//                .hasPermission(SecurityPermissions.READ_STRUCTURE)
//                .hasPermission(SecurityPermissions.EDIT_STRUCTURE)
//                .hasPermission(SecurityPermissions.DELETE_STRUCTURE)
                .end();
    }

    @Bean
    public SecurityRule readStructures() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(STRUCTURE_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
//                .hasPermission(SecurityPermissions.ADD_STRUCTURE)
//                .hasPermission(SecurityPermissions.READ_STRUCTURE)
//                .hasPermission(SecurityPermissions.EDIT_STRUCTURE)
//                .hasPermission(SecurityPermissions.DELETE_STRUCTURE)
                .end();
    }

    @Bean
    public SecurityRule updateStructure() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(STRUCTURE_API_PREFIX + STRUCTURE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
//                .hasPermission(SecurityPermissions.EDIT_STRUCTURE)
                .end();
    }

    @Bean
    public SecurityRule deleteStructure() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(STRUCTURE_API_PREFIX + STRUCTURE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
//                .hasPermission(SecurityPermissions.DELETE_STRUCTURE)
                .end();
    }

}
