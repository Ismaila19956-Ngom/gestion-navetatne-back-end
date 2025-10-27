package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QualiteAirRule {

    static final String QUALITE_AIR_API_PREFIX = "/qualite-air";
    static final String QUALITE_AIR_ID = "/{qualiteAirId}";
    static final String QUALITE_AIR_FILE = "/{qualiteAirId}/{docType}";

    @Bean
    public SecurityRule createQualiteAir() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(QUALITE_AIR_API_PREFIX)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.ADD_QUALITE_AIR)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readQualiteAir() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(QUALITE_AIR_API_PREFIX + QUALITE_AIR_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.READ_QUALITE_AIR)
//                .hasPermission(SecurityPermissions.ADD_QUALITE_AIR)
//                .hasPermission(SecurityPermissions.EDIT_QUALITE_AIR)
//                .hasPermission(SecurityPermissions.DELETE_QUALITE_AIR)
//                .hasPermission(SecurityPermissions.CONSULTE_QUALITE_AIR)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllQualiteAir() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(QUALITE_AIR_API_PREFIX)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.READ_QUALITE_AIR)
//                .hasPermission(SecurityPermissions.ADD_QUALITE_AIR)
//                .hasPermission(SecurityPermissions.EDIT_QUALITE_AIR)
//                .hasPermission(SecurityPermissions.DELETE_QUALITE_AIR)
//                .hasPermission(SecurityPermissions.CONSULTE_QUALITE_AIR)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateQualiteAir() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(QUALITE_AIR_API_PREFIX + QUALITE_AIR_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.EDIT_QUALITE_AIR)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteQualiteAir() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(QUALITE_AIR_API_PREFIX + QUALITE_AIR_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.DELETE_QUALITE_AIR)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule downloadQualiteAirFile() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(QUALITE_AIR_API_PREFIX + QUALITE_AIR_FILE)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.TELECHARGER_QUALITE_AIR)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}