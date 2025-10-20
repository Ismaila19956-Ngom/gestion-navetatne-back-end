package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SocialIndicateurRule {
    static final String SOCIAL_INDICATEUR_API_PREFIX = "/social-indicateur";
    static final String SOCIAL_INDICATEUR_ID = "/{socialIndicateurId}";

    @Bean
    public SecurityRule addIndicateurSocial() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(SOCIAL_INDICATEUR_API_PREFIX)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.ADD_SOCIAL_INDICATEURS)
                .hasPermission(SecurityPermissions.READ_PROJECT_ESG)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllIndicateurSocialProject() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(SOCIAL_INDICATEUR_API_PREFIX + SOCIAL_INDICATEUR_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.READ_SOCIAL_INDICATEURS)
                .hasPermission(SecurityPermissions.READ_PROJECT_ESG)
//                .hasPermission(SecurityPermissions.ADD_SOCIAL_INDICATEURS)
//                .hasPermission(SecurityPermissions.EDIT_SOCIAL_INDICATEURS)
//                .hasPermission(SecurityPermissions.DELETE_SOCIAL_INDICATEURS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readSocialIndicateur() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(SOCIAL_INDICATEUR_API_PREFIX)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.READ_SOCIAL_INDICATEURS)
                .hasPermission(SecurityPermissions.READ_PROJECT_ESG)
//                .hasPermission(SecurityPermissions.ADD_SOCIAL_INDICATEURS)
//                .hasPermission(SecurityPermissions.EDIT_SOCIAL_INDICATEURS)
//                .hasPermission(SecurityPermissions.DELETE_SOCIAL_INDICATEURS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateIndicateurSocial() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(SOCIAL_INDICATEUR_API_PREFIX + SOCIAL_INDICATEUR_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.EDIT_SOCIAL_INDICATEURS)
                .hasPermission(SecurityPermissions.READ_PROJECT_ESG)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteIndicateurSocial() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(SOCIAL_INDICATEUR_API_PREFIX + SOCIAL_INDICATEUR_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.DELETE_SOCIAL_INDICATEURS)
                .hasPermission(SecurityPermissions.READ_PROJECT_ESG)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

}
