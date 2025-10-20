package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StatusQueryRule {
    static final String STATUS_QUERY_API_PREFIX = "/statusQueries";
    static final String STATUS_QUERY_ID = "/{statusQueryId}";




    @Bean
    public SecurityRule createStatusQuery() {

        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(STATUS_QUERY_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_QUERY_PROGESS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean

    public SecurityRule readStatusQuery() {


        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(STATUS_QUERY_API_PREFIX + STATUS_QUERY_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_QUERY_PROGESS)
                .hasPermission(SecurityPermissions.ADD_QUERY_PROGESS)
                .hasPermission(SecurityPermissions.EDIT_QUERY_PROGESS)
                .hasPermission(SecurityPermissions.DELETE_QUERY_PROGESS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean

    public SecurityRule readStatusQuerys() {


        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(STATUS_QUERY_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_QUERY_PROGESS)
                .hasPermission(SecurityPermissions.ADD_QUERY_PROGESS)
                .hasPermission(SecurityPermissions.EDIT_QUERY_PROGESS)
                .hasPermission(SecurityPermissions.DELETE_QUERY_PROGESS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateStatusQuery() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(STATUS_QUERY_API_PREFIX + STATUS_QUERY_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_QUERY_PROGESS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteStatusQuery() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(STATUS_QUERY_API_PREFIX + STATUS_QUERY_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_QUERY_PROGESS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

}
