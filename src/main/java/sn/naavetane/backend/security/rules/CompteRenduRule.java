package sn.naavetane.backend.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import sn.naavetane.backend.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompteRenduRule {
    static final String COMPTE_RENDU_API_PREFIX = "/compteRendus";
    static final String COMPTE_RENDU_ID = "/{compteRenduId}";

    @Bean
    public SecurityRule createCompteRendu() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(COMPTE_RENDU_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateCompteRendu() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(COMPTE_RENDU_API_PREFIX + COMPTE_RENDU_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readCompteRendu() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(COMPTE_RENDU_API_PREFIX + COMPTE_RENDU_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllCompteRendu() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(COMPTE_RENDU_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteCompteRendu() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(COMPTE_RENDU_API_PREFIX + COMPTE_RENDU_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}
