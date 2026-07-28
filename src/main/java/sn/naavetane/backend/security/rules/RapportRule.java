package sn.naavetane.backend.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import sn.naavetane.backend.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RapportRule {
    static final String RAPPORT_API_PREFIX = "/rapport-declarations";
    static final String RAPPORT_ID = "/{rapportId}";

    @Bean
    public SecurityRule createRapport() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(RAPPORT_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateRapport() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(RAPPORT_API_PREFIX + RAPPORT_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readRapport() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(RAPPORT_API_PREFIX + RAPPORT_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllRapport() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(RAPPORT_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteRapport() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(RAPPORT_API_PREFIX + RAPPORT_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}
