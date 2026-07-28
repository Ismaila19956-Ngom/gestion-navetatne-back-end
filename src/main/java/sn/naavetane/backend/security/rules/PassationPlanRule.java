package sn.naavetane.backend.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import sn.naavetane.backend.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PassationPlanRule {
    static final String PASSATION_PLAN_PREFIX = "/passationplan";

    static final String PASSATION_PLAN_ID = "/{passationId}";


    @Bean
    public SecurityRule createPassation() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(PASSATION_PLAN_PREFIX)
                .build()
                .condition()
               .hasPermission(SecurityPermissions.ADD_PLAN_DE_PASSATION)
                .hasPermission(SecurityPermissions.READ_PROJECT_APPEL_OFFRE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readPassation() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(PASSATION_PLAN_PREFIX + PASSATION_PLAN_ID )
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_PLAN_DE_PASSATION)
                .hasPermission(SecurityPermissions.READ_PLAN_DE_PASSATION)
                .hasPermission(SecurityPermissions. EDIT_PLAN_DE_PASSATION)
                .hasPermission(SecurityPermissions.DELETE_PLAN_DE_PASSATION)
                .hasPermission(SecurityPermissions.READ_PROJECT_APPEL_OFFRE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readPassations() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(PASSATION_PLAN_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_PLAN_DE_PASSATION)
                .hasPermission(SecurityPermissions.READ_PLAN_DE_PASSATION)
                .hasPermission(SecurityPermissions. EDIT_PLAN_DE_PASSATION)
                .hasPermission(SecurityPermissions.DELETE_PLAN_DE_PASSATION)
                .hasPermission(SecurityPermissions.READ_PROJECT_APPEL_OFFRE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updatePassation() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(PASSATION_PLAN_PREFIX+ PASSATION_PLAN_ID )
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_PLAN_DE_PASSATION)
                .hasPermission(SecurityPermissions.READ_PROJECT_APPEL_OFFRE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deletePassation() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(PASSATION_PLAN_PREFIX + PASSATION_PLAN_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_PLAN_DE_PASSATION)
                .hasPermission(SecurityPermissions.READ_PROJECT_APPEL_OFFRE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule viewPassation() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(PASSATION_PLAN_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_PROJECT_APPEL_OFFRE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}
