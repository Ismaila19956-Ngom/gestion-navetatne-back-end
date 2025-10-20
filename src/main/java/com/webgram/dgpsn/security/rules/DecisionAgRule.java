package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DecisionAgRule {
    static final String DECISION_AG_API_PREFIX = "/decisionags";
    static final String DECISION_AG_ID = "/{decisionagId}";
    static final String DECISION_AG_EXPORT = "/export";


    @Bean
    public SecurityRule crateDecisonAg() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(DECISION_AG_API_PREFIX)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.ADD_DECISION_AG)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllDecisonAg() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(DECISION_AG_API_PREFIX)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.READ_DECISION_AG)
//                .hasPermission(SecurityPermissions.ADD_DECISION_AG)
//                .hasPermission(SecurityPermissions.EDIT_DECISION_AG)
//                .hasPermission(SecurityPermissions.DELETE_DECISION_AG)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readDecisonAg() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(DECISION_AG_API_PREFIX + DECISION_AG_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.READ_DECISION_AG)
//                .hasPermission(SecurityPermissions.ADD_DECISION_AG)
//                .hasPermission(SecurityPermissions.EDIT_DECISION_AG)
//                .hasPermission(SecurityPermissions.DELETE_DECISION_AG)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateDecisonAg() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(DECISION_AG_API_PREFIX + DECISION_AG_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.EDIT_DECISION_AG)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteDecisonAg() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(DECISION_AG_API_PREFIX + DECISION_AG_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.DELETE_DECISION_AG)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }


    @Bean
    public SecurityRule exporteDecisonAg() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(DECISION_AG_API_PREFIX + DECISION_AG_EXPORT)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.EXPORT_DECISION_AG)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}
