package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategorieBudgetaireRule {
    static final String CATEGORIEBUDGETAIRES_API_PREFIX = "/categoriebudgetaires";
    static final String CATEGORIEBUDGETAIRE_ID = "/{categoriebudgetaireId}";

    @Bean
    public SecurityRule createCategorieBudgetaire() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(CATEGORIEBUDGETAIRES_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readCategorieBudgetaire() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(CATEGORIEBUDGETAIRES_API_PREFIX + CATEGORIEBUDGETAIRE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllCategorieBudgetaire() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(CATEGORIEBUDGETAIRES_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateCategorieBudgetaire() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(CATEGORIEBUDGETAIRES_API_PREFIX + CATEGORIEBUDGETAIRE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule deleteCategorieBudgetaire() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(CATEGORIEBUDGETAIRES_API_PREFIX + CATEGORIEBUDGETAIRE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

}
