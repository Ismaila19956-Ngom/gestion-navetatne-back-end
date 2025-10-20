package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PhaseRule {
    static final String PHASE_API_PREFIX = "/phases";
    static final String PHASE_ID = "/{phaseId}";
    static final String EXPORT_PDF_PREFIX = "/export/pdf";

    @Bean
    public SecurityRule createPhase() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(PHASE_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_PHASE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule readPhase() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(PHASE_API_PREFIX + PHASE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_PHASE)
                .hasPermission(SecurityPermissions.ADD_PHASE)
                .hasPermission(SecurityPermissions.EDIT_PHASE)
                .hasPermission(SecurityPermissions.DELETE_PHASE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllPhases() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(PHASE_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_PHASE)
                .hasPermission(SecurityPermissions.ADD_PHASE)
                .hasPermission(SecurityPermissions.EDIT_PHASE)
                .hasPermission(SecurityPermissions.DELETE_PHASE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule updatePhase() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(PHASE_API_PREFIX + PHASE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_PHASE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule deletePhase() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(PHASE_API_PREFIX + PHASE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_PHASE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule exportPdf() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(PHASE_API_PREFIX + EXPORT_PDF_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EXPORT_PHASE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}
