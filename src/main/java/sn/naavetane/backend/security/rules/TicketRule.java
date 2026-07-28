package sn.naavetane.backend.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TicketRule {
    static final String TICKET_API_PREFIX = "/api/tickets";

    // Récupérer ses billets (utilisateur connecté)
    @Bean
    public SecurityRule getMesBillets() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(TICKET_API_PREFIX + "/mes-billets")
                .authenticated(true)
                .build();
    }

    // Scanner un billet QR Code (contrôleur au stade)
    @Bean
    public SecurityRule scanTicket() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(TICKET_API_PREFIX + "/scan")
                .authenticated(true)
                .build();
    }

    @Bean
    public SecurityRule ventePhysique() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(TICKET_API_PREFIX + "/vente-physique")
                .authenticated(true)
                .build();
    }
}
