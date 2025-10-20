package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class IndicatorRule {
    static final String  Indicator_API_PREFIX = "/indicators";
    static final String Indicator_ID = "/{indicatorsId}";

    @Bean
    public SecurityRule createIndicator() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(Indicator_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_REF_INDICATOR)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readIndicators() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(Indicator_API_PREFIX + Indicator_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_REF_INDICATOR)
                .hasPermission(SecurityPermissions.ADD_REF_INDICATOR)
                .hasPermission(SecurityPermissions.EDIT_REF_INDICATOR)
                .hasPermission(SecurityPermissions.DELETE_REF_INDICATOR)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllIndicators() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(Indicator_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_REF_INDICATOR)
                .hasPermission(SecurityPermissions.ADD_REF_INDICATOR)
                .hasPermission(SecurityPermissions.EDIT_REF_INDICATOR)
                .hasPermission(SecurityPermissions.DELETE_REF_INDICATOR)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateIndicator() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(Indicator_API_PREFIX + Indicator_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_REF_INDICATOR)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule deleteIndicator() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(Indicator_API_PREFIX + Indicator_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_REF_INDICATOR)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}
