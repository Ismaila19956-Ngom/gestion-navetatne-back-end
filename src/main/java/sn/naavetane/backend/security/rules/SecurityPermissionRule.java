package sn.naavetane.backend.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import sn.naavetane.backend.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SecurityPermissionRule {
    static final String SECURITY_PERMISSION_API_PREFIX = "/permissions";
    static final String SECURITY_PERMISSION_MODULE = "/{readByModule}";
    static final String SECURITY_PERMISSION_MODULES = "/{readModules}";

    @Bean
    public SecurityRule readAllPermissions() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(SECURITY_PERMISSION_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllPermissionsByModule() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(SECURITY_PERMISSION_API_PREFIX + SECURITY_PERMISSION_MODULE)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllModules() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(SECURITY_PERMISSION_API_PREFIX + SECURITY_PERMISSION_MODULES)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

}
