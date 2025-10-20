package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SuiviSurveillanceRule {
    static final String API_PREFIX = "/suivi-surveillance";
    static final String API_ID = "/{id}";

    @Bean
    public SecurityRule createSuiviSurveillanceRule() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(API_PREFIX)
                .build()
                .condition()
                 .hasPermission(SecurityPermissions.ADD_SUIVI_SURVEILLANCE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    
    @Bean
    public SecurityRule readSuiviSurveillanceRule() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(API_PREFIX + "/**")
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_SUIVI_SURVEILLANCE)
                .hasPermission(SecurityPermissions.ADD_SUIVI_SURVEILLANCE)
                .hasPermission(SecurityPermissions.EDIT_SUIVI_SURVEILLANCE)
                .hasPermission(SecurityPermissions.DELETE_SUIVI_SURVEILLANCE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    
    @Bean
    public SecurityRule updateSuiviSurveillanceRule() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(API_PREFIX + API_ID)
                .build()
                .condition()
                 .hasPermission(SecurityPermissions.EDIT_SUIVI_SURVEILLANCE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    
    @Bean
    public SecurityRule deleteSuiviSurveillanceRule() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(API_PREFIX + API_ID)
                .build()
                .condition()
                 .hasPermission(SecurityPermissions.DELETE_SUIVI_SURVEILLANCE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}