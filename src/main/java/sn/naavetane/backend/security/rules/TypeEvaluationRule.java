package sn.naavetane.backend.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import sn.naavetane.backend.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TypeEvaluationRule {
    static final String TYPEDVALUATIONS_API_PREFIX = "/typedvaluations";
    static final String TYPEDVALUATION_ID = "/{typedvaluationId}";

    @Bean
    public SecurityRule createTypeEvaluation() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(TYPEDVALUATIONS_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readTypeEvaluation() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(TYPEDVALUATIONS_API_PREFIX + TYPEDVALUATION_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllTypeEvaluations() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(TYPEDVALUATIONS_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateTypeEvaluation() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(TYPEDVALUATIONS_API_PREFIX + TYPEDVALUATION_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule deleteTypeEvaluation() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(TYPEDVALUATIONS_API_PREFIX + TYPEDVALUATION_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

}
