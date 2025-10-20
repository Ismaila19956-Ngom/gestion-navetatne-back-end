package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EvaluationEnvironnementaleRule {

    static final String EVALUATION_ENV_API_PREFIX = "/evaluations-environnementales";
    static final String EVALUATION_ENV_ID = "/{evaluationEnvironnementaleId}";
    static final String EVALUATION_ENV_FILE = "/{evaluationEnvironnementaleId}/{docType}";

    @Bean
    public SecurityRule createEvaluationEnvironnementale() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(EVALUATION_ENV_API_PREFIX)
                .build()
                .condition()
               .hasPermission(SecurityPermissions.ADD_EVALUATION_ENVIRO)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readEvaluationEnvironnementale() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(EVALUATION_ENV_API_PREFIX + EVALUATION_ENV_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_EVALUATION_ENVIRO)
                .hasPermission(SecurityPermissions.ADD_EVALUATION_ENVIRO)
                .hasPermission(SecurityPermissions.EDIT_EVALUATION_ENVIRO)
                .hasPermission(SecurityPermissions.DELETE_EVALUATION_ENVIRO)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllEvaluationEnvironnementale() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(EVALUATION_ENV_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_EVALUATION_ENVIRO)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateEvaluationEnvironnementale() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(EVALUATION_ENV_API_PREFIX + EVALUATION_ENV_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_EVALUATION_ENVIRO)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteEvaluationEnvironnementale() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(EVALUATION_ENV_API_PREFIX + EVALUATION_ENV_ID)
                .build()
                .condition()
               .hasPermission(SecurityPermissions.DELETE_EVALUATION_ENVIRO)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule downloadEvaluationEnvironnementaleFile() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(EVALUATION_ENV_API_PREFIX + EVALUATION_ENV_FILE)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}