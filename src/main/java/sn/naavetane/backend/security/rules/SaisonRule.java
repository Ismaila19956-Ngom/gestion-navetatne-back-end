package sn.naavetane.backend.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SaisonRule {

    @Bean
    public SecurityRule readSaisons() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern("/api/saisons/**")
                .authenticated(true)
                .build();
    }

    @Bean
    public SecurityRule writeSaisons() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern("/api/saisons/**")
                .authenticated(true)
                .build();
    }
    
    @Bean
    public SecurityRule updateSaisons() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern("/api/saisons/**")
                .authenticated(true)
                .build();
    }
    
    @Bean
    public SecurityRule deleteSaisons() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern("/api/saisons/**")
                .authenticated(true)
                .build();
    }
}
