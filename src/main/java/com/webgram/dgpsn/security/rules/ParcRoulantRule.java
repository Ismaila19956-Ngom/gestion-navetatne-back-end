package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ParcRoulantRule {
    static final String PARC_ROULANT_API_PREFIX = "/parcRoulant";
    static final String PARC_ROULANT_ID = "/{parcRoulantId}";
    static final String PARC_ROULANT_MULTIPLE = "/multiple";

    @Bean
    public SecurityRule createParcRoulant() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(PARC_ROULANT_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_PARC_ROULANT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule createMultipleParcRoulants() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(PARC_ROULANT_API_PREFIX + PARC_ROULANT_MULTIPLE)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_PARC_ROULANT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readParcRoulant() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(PARC_ROULANT_API_PREFIX + PARC_ROULANT_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_PARC_ROULANT)
                .hasPermission(SecurityPermissions.ADD_PARC_ROULANT)
                .hasPermission(SecurityPermissions.EDIT_PARC_ROULANT)
                .hasPermission(SecurityPermissions.DELETE_PARC_ROULANT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllParcRoulants() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(PARC_ROULANT_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_PARC_ROULANT)
                .hasPermission(SecurityPermissions.ADD_PARC_ROULANT)
                .hasPermission(SecurityPermissions.EDIT_PARC_ROULANT)
                .hasPermission(SecurityPermissions.DELETE_PARC_ROULANT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateParcRoulant() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(PARC_ROULANT_API_PREFIX + PARC_ROULANT_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_PARC_ROULANT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteParcRoulant() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(PARC_ROULANT_API_PREFIX + PARC_ROULANT_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_PARC_ROULANT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}