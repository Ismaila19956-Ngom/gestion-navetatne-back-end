package com.webgram.dgpsn.security.rules;

import com.webgram.dgpsn.security.SecurityPermissions;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FinancementReportRule {
    static final String FINANCEMENT_REPORT_API_PREFIX = "/reports/financement";


    @Bean
    public SecurityRule generateFinancementReportByProject() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(FINANCEMENT_REPORT_API_PREFIX)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.READ_FINANCEMENT_REPORT)
//                .hasPermission(SecurityPermissions.ADD_FINANCEMENT_REPORT)
//                .hasPermission(SecurityPermissions.EDIT_FINANCEMENT_REPORT)
//                .hasPermission(SecurityPermissions.DELETE_FINANCEMENT_REPORT)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

}
