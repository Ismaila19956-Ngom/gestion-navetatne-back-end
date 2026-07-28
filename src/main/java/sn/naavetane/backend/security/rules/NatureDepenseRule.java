package sn.naavetane.backend.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import sn.naavetane.backend.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NatureDepenseRule {
    static final String NATUREDEPENSES_API_PREFIX = "/naturedepenses";
    static final String NATUREDEPENSE_ID = "/{naturedepenseId}";

    @Bean
    public SecurityRule createNatureDepense() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(NATUREDEPENSES_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readNatureDepense() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(NATUREDEPENSES_API_PREFIX + NATUREDEPENSE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllNatureDepenses() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(NATUREDEPENSES_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateNatureDepense() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(NATUREDEPENSES_API_PREFIX + NATUREDEPENSE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule deleteNatureDepense() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(NATUREDEPENSES_API_PREFIX + NATUREDEPENSE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

}
