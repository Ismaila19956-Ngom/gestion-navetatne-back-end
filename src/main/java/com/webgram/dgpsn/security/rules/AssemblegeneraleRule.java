package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AssemblegeneraleRule {
    static final String ASSEMBLEGENERALS_API_PREFIX = "/assemblegenerals";
    static final String ASSEMBLEGENERALS_ID = "/{assemblegeneralId}";
    static final String ASSEMBLEGENERALS_EXPORT = "/export";


    @Bean
    public SecurityRule AddAssemblegeneral() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(ASSEMBLEGENERALS_API_PREFIX)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.ADD_AG)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllAssemblegenerals() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(ASSEMBLEGENERALS_API_PREFIX)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.READ_AG)
//                .hasPermission(SecurityPermissions.ADD_AG)
//                .hasPermission(SecurityPermissions.EDIT_AG)
//                .hasPermission(SecurityPermissions.DELETE_AG)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAssemblegeneral() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(ASSEMBLEGENERALS_API_PREFIX + ASSEMBLEGENERALS_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.READ_AG)
//                .hasPermission(SecurityPermissions.ADD_AG)
//                .hasPermission(SecurityPermissions.EDIT_AG)
//                .hasPermission(SecurityPermissions.DELETE_AG)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateAssemblegeneral() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(ASSEMBLEGENERALS_API_PREFIX + ASSEMBLEGENERALS_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.EDIT_AG)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteAssemblegeneral() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(ASSEMBLEGENERALS_API_PREFIX + ASSEMBLEGENERALS_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.DELETE_AG)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }


    @Bean
    public SecurityRule exportAssemblegeneral() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(ASSEMBLEGENERALS_API_PREFIX + ASSEMBLEGENERALS_EXPORT)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.EXPORT_AG)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}
