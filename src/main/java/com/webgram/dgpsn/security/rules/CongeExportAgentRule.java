package com.webgram.dgpsn.security.rules;

import com.webgram.dgpsn.security.SecurityPermissions;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CongeExportAgentRule {
    static final String CONGE_EXPORT_AGENT_API_PREFIX = "/conges/export";
    static final String EXPORT_CSV_PREFIX = "/csv";
    static final String EXPORT_PREFIX = "/excel";


    @Bean
    public SecurityRule exportCongesExcel() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(CONGE_EXPORT_AGENT_API_PREFIX + EXPORT_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EXPORT_AGENT)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }


    @Bean
    public SecurityRule exportCongesCSV() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(CONGE_EXPORT_AGENT_API_PREFIX + EXPORT_CSV_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EXPORT_AGENT)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }


}
