package sn.naavetane.backend.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JourneeRule {

    @Bean
    public SecurityRule readJourneesAVenir() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern("/api/journees/a-venir")
                .authenticated(false)
                .build();
    }

    @Bean
    public SecurityRule createJournee() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern("/api/journees")
                // TODO: restrict this to ORGANISATEUR role later. For now, we allow any authenticated user to create a Journee during prototype phase.
                .authenticated(true)
                .build();
    }

    @Bean
    public SecurityRule importJournees() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern("/api/journees/import")
                .authenticated(true)
                .build();
    }

    @Bean
    public SecurityRule readJournees() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern("/api/journees")
                .authenticated(true)
                .build();
    }

    @Bean
    public SecurityRule readJourneesDuJour() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern("/api/journees/du-jour")
                .authenticated(true)
                .build();
    }

    @Bean
    public SecurityRule updateJournee() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern("/api/journees/**")
                .authenticated(true)
                .build();
    }

    @Bean
    public SecurityRule deleteJournee() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern("/api/journees/**")
                .authenticated(true)
                .build();
    }
}
