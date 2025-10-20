package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FormeJuridiqueRule {
    static final String FORMEJURIDIQUES_API_PREFIX = "/formejuridiques";
    static final String FORMEJURIDIQUE_ID = "/{formejuridiqueId}";

    @Bean
    public SecurityRule createFormejuridique() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(FORMEJURIDIQUES_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readFormejuridique() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(FORMEJURIDIQUES_API_PREFIX + FORMEJURIDIQUE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllFormejuridiques() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(FORMEJURIDIQUES_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateFormejuridique() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(FORMEJURIDIQUES_API_PREFIX + FORMEJURIDIQUE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule deleteFormejuridique() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(FORMEJURIDIQUES_API_PREFIX + FORMEJURIDIQUE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

}
