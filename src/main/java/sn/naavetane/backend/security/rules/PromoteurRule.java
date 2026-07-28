package sn.naavetane.backend.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import sn.naavetane.backend.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PromoteurRule {

    static final String PROMOTEUR_API_PREFIX = "/promoteurs";
    static final String PROMOTEUR_ID = "/{promoteurId}";

    @Bean
    public SecurityRule createPromoteur() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(PROMOTEUR_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_PROMOTEUR)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readPromoteur() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(PROMOTEUR_API_PREFIX + PROMOTEUR_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_PROMOTEUR)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllPromoteur() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(PROMOTEUR_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .hasPermission(SecurityPermissions.READ_PROMOTEUR)
                .end();
    }

    @Bean
    public SecurityRule updatePromoteur() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(PROMOTEUR_API_PREFIX + PROMOTEUR_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .hasPermission(SecurityPermissions.EDIT_PROMOTEUR)
                .end();
    }

    @Bean
    public SecurityRule deletePromoteur() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(PROMOTEUR_API_PREFIX + PROMOTEUR_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .hasPermission(SecurityPermissions.DELETE_PROMOTEUR)
                .end();
    }
}
