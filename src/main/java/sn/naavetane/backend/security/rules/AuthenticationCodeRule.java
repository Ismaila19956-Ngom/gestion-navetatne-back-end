package sn.naavetane.backend.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import sn.naavetane.backend.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationCodeRule {
    static final String AUTH_CODE_API_PREFIX = "/auth-code/generate-temporary-code";
    static final String TEMPORARY_CODE_API_PREFIX = "/auth-code/temporary-code";
    static final String UPDATE_TEMPORARY_CODE_STATUS_API_PREFIX = "/auth-code/temporary-code/{code}/status";


    @Bean
    public SecurityRule generateTemporaryCode() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(AUTH_CODE_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.GENERATE_AUTH_CODE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule getTemporaryCode() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(TEMPORARY_CODE_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_TEMPORARY_CODE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateTemporaryCodeStatus() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(UPDATE_TEMPORARY_CODE_STATUS_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.UPDATE_TEMPORARY_CODE_STATUS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}
