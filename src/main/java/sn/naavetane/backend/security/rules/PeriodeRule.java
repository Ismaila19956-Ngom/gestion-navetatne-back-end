package sn.naavetane.backend.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import sn.naavetane.backend.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PeriodeRule {
    static final String PERIODES_API_PREFIX = "/periodes";
    static final String PERIODE_ID = "/{periodeId}";

    @Bean
    public SecurityRule createPeriode() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(PERIODES_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readPeriode() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(PERIODES_API_PREFIX + PERIODE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllPeriodes() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(PERIODES_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updatePeriode() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(PERIODES_API_PREFIX + PERIODE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule deletePeriode() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(PERIODES_API_PREFIX + PERIODE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

}
