package sn.naavetane.backend.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import sn.naavetane.backend.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SocialConformitesRule {
    static final String SOCIAL_CONFORMITE_API_PREFIX = "/social-conformites-reglementaire";
    static final String SOCIAL_CONFORMITE_ID = "/{socialConformiteId}";

    @Bean
    public SecurityRule addConformiteSocial() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(SOCIAL_CONFORMITE_API_PREFIX)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.ADD_SOCIAL_CONFORMITES)
                .hasPermission(SecurityPermissions.READ_PROJECT_ESG)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllConformiteSocialProject() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(SOCIAL_CONFORMITE_API_PREFIX + SOCIAL_CONFORMITE_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.READ_SOCIAL_CONFORMITES)
//                .hasPermission(SecurityPermissions.READ_PROJECT_ESG)
//                .hasPermission(SecurityPermissions.ADD_SOCIAL_CONFORMITES)
//                .hasPermission(SecurityPermissions.EDIT_SOCIAL_CONFORMITES)
//                .hasPermission(SecurityPermissions.DELETE_SOCIAL_CONFORMITES)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readSocialConformite() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(SOCIAL_CONFORMITE_API_PREFIX)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.READ_SOCIAL_CONFORMITES)
                .hasPermission(SecurityPermissions.READ_PROJECT_ESG)
//                .hasPermission(SecurityPermissions.ADD_SOCIAL_CONFORMITES)
//                .hasPermission(SecurityPermissions.EDIT_SOCIAL_CONFORMITES)
//                .hasPermission(SecurityPermissions.DELETE_SOCIAL_CONFORMITES)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateConformiteSocial() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(SOCIAL_CONFORMITE_API_PREFIX + SOCIAL_CONFORMITE_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.EDIT_SOCIAL_CONFORMITES)
                .hasPermission(SecurityPermissions.READ_PROJECT_ESG)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteConformiteSocial() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(SOCIAL_CONFORMITE_API_PREFIX + SOCIAL_CONFORMITE_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.DELETE_SOCIAL_CONFORMITES)
                .hasPermission(SecurityPermissions.READ_PROJECT_ESG)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

}
