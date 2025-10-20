package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SocialImpactRule {
    static final String  SOCIAL_IMPACT_API_PREFIX = "/social-impact";
    static final String SOCIAL_IMPACT_ID = "/{socialImpactId}";

    @Bean
    public SecurityRule addImpactSocial() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(SOCIAL_IMPACT_API_PREFIX)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.ADD_SOCIAL_IMPACTS)
                .hasPermission(SecurityPermissions.READ_PROJECT_ESG)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllImpactSocialProject() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(SOCIAL_IMPACT_API_PREFIX + SOCIAL_IMPACT_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.READ_SOCIAL_IMPACTS)
                .hasPermission(SecurityPermissions.READ_PROJECT_ESG)
//                .hasPermission(SecurityPermissions.ADD_SOCIAL_IMPACTS)
//                .hasPermission(SecurityPermissions.EDIT_SOCIAL_IMPACTS)
//                .hasPermission(SecurityPermissions.DELETE_SOCIAL_IMPACTS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readSocialImpact() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(SOCIAL_IMPACT_API_PREFIX)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.READ_SOCIAL_IMPACTS)
                .hasPermission(SecurityPermissions.READ_PROJECT_ESG)
//                .hasPermission(SecurityPermissions.ADD_SOCIAL_IMPACTS)
//                .hasPermission(SecurityPermissions.EDIT_SOCIAL_IMPACTS)
//                .hasPermission(SecurityPermissions.DELETE_SOCIAL_IMPACTS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateImpactSocialal() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(SOCIAL_IMPACT_API_PREFIX + SOCIAL_IMPACT_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.EDIT_SOCIAL_IMPACTS)
                .hasPermission(SecurityPermissions.READ_PROJECT_ESG)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteImpactSocial() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(SOCIAL_IMPACT_API_PREFIX + SOCIAL_IMPACT_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_PROJECT_ESG)
//                .hasPermission(SecurityPermissions.DELETE_SOCIAL_IMPACTS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

}
