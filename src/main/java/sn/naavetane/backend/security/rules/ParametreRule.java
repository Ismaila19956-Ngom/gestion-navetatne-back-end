package sn.naavetane.backend.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ParametreRule {

    @Bean
    public SecurityRule readParametres() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern("/api/parametres/**")
                .authenticated(true)
                .build();
    }

    @Bean
    public SecurityRule writeParametres() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern("/api/parametres/**")
                .authenticated(true)
                .build();
    }
}
