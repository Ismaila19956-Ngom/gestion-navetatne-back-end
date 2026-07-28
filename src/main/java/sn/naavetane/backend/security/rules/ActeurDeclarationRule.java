package sn.naavetane.backend.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import sn.naavetane.backend.security.SecurityPermissions;


@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ActeurDeclarationRule {
    static final String ACTEUR_DECLARATION_API_PREFIX = "/acteur-declarations";
    static final String ACTEUR_DECLARATION_ID = "/{acteurDeclarationId}";

    @Bean
    public SecurityRule createActeurDeclaration() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(ACTEUR_DECLARATION_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateActeurDeclaration() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(ACTEUR_DECLARATION_API_PREFIX + ACTEUR_DECLARATION_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readActeurDeclaration() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(ACTEUR_DECLARATION_API_PREFIX + ACTEUR_DECLARATION_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllActeurDeclaration() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(ACTEUR_DECLARATION_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteActeurDeclaration() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(ACTEUR_DECLARATION_API_PREFIX + ACTEUR_DECLARATION_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}
