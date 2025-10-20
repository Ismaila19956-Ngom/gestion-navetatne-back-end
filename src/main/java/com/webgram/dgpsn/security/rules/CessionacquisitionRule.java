package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CessionacquisitionRule {
    static final String CESSIONACQUISITIONS_API_PREFIX = "/cessionacquisitions";
    static final String CESSIONACQUISITIONS_ID = "/{cessionacquisitionsId}";
    static final String CESSIONACQUISITIONS_EXPORT = "/export";


    @Bean
    public SecurityRule AddCessionacquisition() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(CESSIONACQUISITIONS_API_PREFIX)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.ADD_CESSIONACQUISITIONS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllCessionacquisitions() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(CESSIONACQUISITIONS_API_PREFIX)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.READ_CESSIONACQUISITIONS)
//                .hasPermission(SecurityPermissions.ADD_CESSIONACQUISITIONS)
//                .hasPermission(SecurityPermissions.EDIT_CESSIONACQUISITIONS)
//                .hasPermission(SecurityPermissions.DELETE_CESSIONACQUISITIONS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readCessionacquisition() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(CESSIONACQUISITIONS_API_PREFIX + CESSIONACQUISITIONS_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.READ_CESSIONACQUISITIONS)
//                .hasPermission(SecurityPermissions.ADD_CESSIONACQUISITIONS)
//                .hasPermission(SecurityPermissions.EDIT_CESSIONACQUISITIONS)
//                .hasPermission(SecurityPermissions.DELETE_CESSIONACQUISITIONS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateCessionacquisition() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(CESSIONACQUISITIONS_API_PREFIX + CESSIONACQUISITIONS_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.EDIT_CESSIONACQUISITIONS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteCessionacquisition() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(CESSIONACQUISITIONS_API_PREFIX + CESSIONACQUISITIONS_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.DELETE_CESSIONACQUISITIONS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }


    @Bean
    public SecurityRule exportCessionacquisition() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(CESSIONACQUISITIONS_API_PREFIX + CESSIONACQUISITIONS_EXPORT)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.EXPORT_CESSIONACQUISITIONS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}
