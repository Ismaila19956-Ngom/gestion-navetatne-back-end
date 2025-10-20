package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EvaluationRule {
    static final String EVALUATION_API_PREFIX = "/evaluation";
    static final String EVALUATION_ID = "/{evaluationId}";

    @Bean
    public SecurityRule createEvaluation() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(EVALUATION_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_EVALUATION)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readEvaluation() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(EVALUATION_API_PREFIX + EVALUATION_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_EVALUATION)
                .hasPermission(SecurityPermissions.ADD_EVALUATION)
                .hasPermission(SecurityPermissions.EDIT_EVALUATION)
                .hasPermission(SecurityPermissions.DELETE_EVALUATION)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllEvaluations() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(EVALUATION_API_PREFIX + "/allEvaluations")
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_EVALUATION)
                .hasPermission(SecurityPermissions.ADD_EVALUATION)
                .hasPermission(SecurityPermissions.EDIT_EVALUATION)
                .hasPermission(SecurityPermissions.DELETE_EVALUATION)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateEvaluation() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(EVALUATION_API_PREFIX + EVALUATION_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_EVALUATION)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteEvaluation() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(EVALUATION_API_PREFIX + EVALUATION_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_EVALUATION)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}