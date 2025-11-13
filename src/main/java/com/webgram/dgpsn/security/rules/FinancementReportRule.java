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
                .hasPermission(SecurityPermissions.READ_RAPPORT)
                .hasPermission(SecurityPermissions.EXPORT_RAPPORT)
                .hasPermission(SecurityPermissions.GENERATE_RAPPORT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

}
