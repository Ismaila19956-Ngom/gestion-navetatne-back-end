package sn.naavetane.backend.security.rules;

import sn.naavetane.backend.security.SecurityPermissions;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TypeUniteRule {
    static final String TYPE_UNITE_API_PREFIX = "/typeUnite";
    static final String TYPE_UNITE_ID = "/{typeUniteId}";

    @Bean
    public SecurityRule createTypeUnite() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(TYPE_UNITE_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule createChildrenTypeUnite() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(TYPE_UNITE_API_PREFIX + TYPE_UNITE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readTypeUnite() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(TYPE_UNITE_API_PREFIX + TYPE_UNITE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readParentsTypeUniteOnly() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(TYPE_UNITE_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllTypeUnites() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(TYPE_UNITE_API_PREFIX+"/allListOfTypeUnite")
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateTypeUnite() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(TYPE_UNITE_API_PREFIX + TYPE_UNITE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteTypeUnite() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(TYPE_UNITE_API_PREFIX + TYPE_UNITE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}
