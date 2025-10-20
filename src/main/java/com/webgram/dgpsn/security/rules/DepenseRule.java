package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DepenseRule {
    static final String DEPENSE_API_PREFIX = "/depenses";
    static final String DEPENSE_ID = "/{depenseId}";
    static final String DEPENSE_EXPORT = "/export";


    @Bean
    public SecurityRule AddDepense() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(DEPENSE_API_PREFIX)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.ADD_DEPENSE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllDepenses() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(DEPENSE_API_PREFIX)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.READ_DEPENSE)
//                .hasPermission(SecurityPermissions.ADD_DEPENSE)
//                .hasPermission(SecurityPermissions.EDIT_DEPENSE)
//                .hasPermission(SecurityPermissions.DELETE_DEPENSE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readDepense() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(DEPENSE_API_PREFIX + DEPENSE_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.READ_DEPENSE)
//                .hasPermission(SecurityPermissions.ADD_DEPENSE)
//                .hasPermission(SecurityPermissions.EDIT_DEPENSE)
//                .hasPermission(SecurityPermissions.DELETE_DEPENSE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateDepense() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(DEPENSE_API_PREFIX + DEPENSE_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.EDIT_DEPENSE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteDepense() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(DEPENSE_API_PREFIX + DEPENSE_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.DELETE_DEPENSE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }


    @Bean
    public SecurityRule exportDepense() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(DEPENSE_API_PREFIX + DEPENSE_EXPORT)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.EXPORT_DEPENSE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}
