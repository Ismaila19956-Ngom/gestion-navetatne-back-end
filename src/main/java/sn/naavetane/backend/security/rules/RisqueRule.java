package sn.naavetane.backend.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import sn.naavetane.backend.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RisqueRule {
    static final String RISQUES_API_PREFIX = "/risques";
    static final String RISQUE_ID = "/{risqueId}";

    @Bean
    public SecurityRule createRisque() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(RISQUES_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readRisque() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(RISQUES_API_PREFIX + RISQUE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllRisque() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(RISQUES_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateRisque() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(RISQUES_API_PREFIX + RISQUE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule deleteRisque() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(RISQUES_API_PREFIX + RISQUE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

}
