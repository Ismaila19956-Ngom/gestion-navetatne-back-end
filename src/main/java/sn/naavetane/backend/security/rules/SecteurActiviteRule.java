package sn.naavetane.backend.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import sn.naavetane.backend.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SecteurActiviteRule {
    static final String SECTEUR_API_PREFIX = "/secteuractivites";
    static final String SECTEUR_ID = "/{secteuractiviteId}";

    @Bean
    public SecurityRule createSecteurActivite() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(SECTEUR_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readSecteurActivite() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(SECTEUR_API_PREFIX + SECTEUR_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllSecteurActivite() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(SECTEUR_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateSecteurActivite() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(SECTEUR_API_PREFIX + SECTEUR_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule deleteSecteurActivite() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(SECTEUR_API_PREFIX + SECTEUR_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

}
