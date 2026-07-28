package sn.naavetane.backend.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EquipeRule {

    @Bean
    public SecurityRule readEquipes() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern("/api/equipes")
                .authenticated(false) // Permettre la lecture des équipes sans token
                .build();
    }

    @Bean
    public SecurityRule createEquipe() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern("/api/equipes")
                .authenticated(false) // Permettre la création d'équipes sans token pour l'instant
                .build();
    }
}
