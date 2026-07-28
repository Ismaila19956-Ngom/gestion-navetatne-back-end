package sn.naavetane.backend.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import sn.naavetane.backend.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PeriodRule {
    static final String PERIOD_API_PREFIX = "/periods";
    static final String PERIOD_ID = "/{periodId}";
    @Bean
    public SecurityRule createPeriod() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(PERIOD_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_PERIOD)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readPeriod() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(PERIOD_API_PREFIX + PERIOD_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_PERIOD)
                .hasPermission(SecurityPermissions.ADD_PERIOD)
                .hasPermission(SecurityPermissions.EDIT_PERIOD)
                .hasPermission(SecurityPermissions.DELETE_PERIOD)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllPeriods() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(PERIOD_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_PERIOD)
                .hasPermission(SecurityPermissions.ADD_PERIOD)
                .hasPermission(SecurityPermissions.EDIT_PERIOD)
                .hasPermission(SecurityPermissions.DELETE_PERIOD)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updatePeriod() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(PERIOD_API_PREFIX + PERIOD_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_PERIOD)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule deletePeriod() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(PERIOD_API_PREFIX + PERIOD_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_PERIOD)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}
