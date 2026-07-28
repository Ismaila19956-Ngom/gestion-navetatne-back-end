package sn.naavetane.backend.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import sn.naavetane.backend.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleRule {
    static final String ROLE_PREFIX = "/roles";
    static final String ROLE_ID = "/{roleId}";
    static final String PROJECT_ID = "/roleBy";



    @Bean
    public SecurityRule createRole() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(ROLE_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_ROLE)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readRole() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(ROLE_PREFIX + ROLE_ID )
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_ROLE)
                .hasPermission(SecurityPermissions.READ_ROLE)
                .hasPermission(SecurityPermissions.EDIT_ROLE)
                .hasPermission(SecurityPermissions.DELETE_ROLE)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readRoles() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(ROLE_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_ROLE)
                .hasPermission(SecurityPermissions.READ_ROLE)
                .hasPermission(SecurityPermissions.EDIT_ROLE)
                .hasPermission(SecurityPermissions.DELETE_ROLE)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateRole() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(ROLE_PREFIX + ROLE_ID )
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_ROLE)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean

    public SecurityRule deleteRole() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(ROLE_PREFIX + ROLE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_ROLE)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
        public SecurityRule readRoleUgp() {
            return SecurityRule.builder()
                    .httpMethod(HttpMethod.GET)
                    .apiPattern(ROLE_PREFIX + PROJECT_ID)
                    .build()
                    .condition()
                    .hasPermission(SecurityPermissions.ADD_ROLE)
                    .hasPermission(SecurityPermissions.READ_ROLE)
                    .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                    .hasPermission(SecurityPermissions.EDIT_ROLE)
                    .end();


        }


    }
