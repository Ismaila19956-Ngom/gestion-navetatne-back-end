package sn.naavetane.backend.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import sn.naavetane.backend.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UgpProjetRule {
    static final String UGP_PREFIX = "/ugp-project";
    static final String UGP_ID = "/{ugpProjectId}";

    @Bean
    public SecurityRule createUgp() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(UGP_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_UGP)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readUgp() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(UGP_PREFIX + UGP_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_UGP)
                .hasPermission(SecurityPermissions.ADD_UGP)
                .hasPermission(SecurityPermissions.EDIT_UGP)
                .hasPermission(SecurityPermissions.DELETE_UGP)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readUgps() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(UGP_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_UGP)
                .hasPermission(SecurityPermissions.ADD_UGP)
                .hasPermission(SecurityPermissions.EDIT_UGP)
                .hasPermission(SecurityPermissions.DELETE_UGP)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateUgp() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(UGP_PREFIX + UGP_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_UGP)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteUgp() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(UGP_PREFIX + UGP_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_UGP)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}
