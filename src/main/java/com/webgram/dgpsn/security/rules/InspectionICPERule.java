package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InspectionICPERule {

    static final String INSPECTION_ICPE_API_PREFIX = "/inspections-icpe";
    static final String INSPECTION_ICPE_ID = "/{inspectionICPEId}";
    static final String INSPECTION_ICPE_FILE = "/{inspectionICPEId}/{docType}";

    @Bean
    public SecurityRule createInspectionICPE() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(INSPECTION_ICPE_API_PREFIX)
                .build()
                .condition()
               .hasPermission(SecurityPermissions.ADD_INSPECTION_ICPE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readInspectionICPE() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(INSPECTION_ICPE_API_PREFIX + INSPECTION_ICPE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_INSPECTION_ICPE)
                .hasPermission(SecurityPermissions.ADD_INSPECTION_ICPE)
                .hasPermission(SecurityPermissions.EDIT_INSPECTION_ICPE)
                .hasPermission(SecurityPermissions.DELETE_INSPECTION_ICPE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllInspectionICPE() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(INSPECTION_ICPE_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_INSPECTION_ICPE)
                .hasPermission(SecurityPermissions.ADD_INSPECTION_ICPE)
                .hasPermission(SecurityPermissions.EDIT_INSPECTION_ICPE)
                .hasPermission(SecurityPermissions.DELETE_INSPECTION_ICPE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateInspectionICPE() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(INSPECTION_ICPE_API_PREFIX + INSPECTION_ICPE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_INSPECTION_ICPE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteInspectionICPE() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(INSPECTION_ICPE_API_PREFIX + INSPECTION_ICPE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_INSPECTION_ICPE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule downloadInspectionICPEFile() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(INSPECTION_ICPE_API_PREFIX + INSPECTION_ICPE_FILE)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}