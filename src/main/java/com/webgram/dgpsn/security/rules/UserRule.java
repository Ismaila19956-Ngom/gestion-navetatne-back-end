package com.webgram.dgpsn.security.rules;

import com.webgram.dgpsn.security.SecurityPermissions;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRule {
    static final String USER_API_PREFIX = "/users";
    static final String USER_ID = "/{userId}";
    static final String ACTIVE_OR_DESACTIVE = "/activeOrDesactive";

    @Bean
    public SecurityRule createUsers() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(USER_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_USER)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }


    @Bean
    public SecurityRule readUser() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(USER_API_PREFIX + USER_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_USER)
                .hasPermission(SecurityPermissions.ADD_USER)
                .hasPermission(SecurityPermissions.EDIT_USER)
                .hasPermission(SecurityPermissions.DELETE_USER)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readUsers() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(USER_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_USER)
                .hasPermission(SecurityPermissions.ADD_USER)
                .hasPermission(SecurityPermissions.EDIT_USER)
                .hasPermission(SecurityPermissions.DELETE_USER)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateUser() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(USER_API_PREFIX + USER_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_USER)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule activeOrDesactive() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(USER_API_PREFIX + ACTIVE_OR_DESACTIVE + USER_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteUser() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(USER_API_PREFIX + USER_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_USER)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updatePassword() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(USER_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.UPDATE_PASSWORD)
                .hasPermission(SecurityPermissions.UPDATE_MY_PASSWORD)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}
