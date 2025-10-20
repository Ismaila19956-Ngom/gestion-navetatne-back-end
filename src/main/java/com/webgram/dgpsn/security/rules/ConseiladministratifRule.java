package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConseiladministratifRule {
    static final String CONSEILADMINISTRATIFS_API_PREFIX = "/conseiladministratifs";
    static final String CONSEILADMINISTRATIFS_ID = "/{conseiladministratifId}";
    static final String CONSEILADMINISTRATIFS_EXPORT = "/export";


    @Bean
    public SecurityRule createConseilAdministration() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(CONSEILADMINISTRATIFS_API_PREFIX)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.ADD_CA)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readConseilAdministration() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(CONSEILADMINISTRATIFS_API_PREFIX + CONSEILADMINISTRATIFS_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.READ_CA)
//                .hasPermission(SecurityPermissions.ADD_CA)
//                .hasPermission(SecurityPermissions.EDIT_CA)
//                .hasPermission(SecurityPermissions.DELETE_CA)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllConseilAdministration() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(CONSEILADMINISTRATIFS_API_PREFIX)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.READ_CA)
//                .hasPermission(SecurityPermissions.ADD_CA)
//                .hasPermission(SecurityPermissions.EDIT_CA)
//                .hasPermission(SecurityPermissions.DELETE_CA)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateConseilAdministration() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(CONSEILADMINISTRATIFS_API_PREFIX + CONSEILADMINISTRATIFS_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.EDIT_CA)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteConseilAdministration() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(CONSEILADMINISTRATIFS_API_PREFIX + CONSEILADMINISTRATIFS_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.DELETE_CA)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }


    @Bean
    public SecurityRule exportConseiladministratif() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(CONSEILADMINISTRATIFS_API_PREFIX + CONSEILADMINISTRATIFS_EXPORT)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.EXPORT_CA)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}
