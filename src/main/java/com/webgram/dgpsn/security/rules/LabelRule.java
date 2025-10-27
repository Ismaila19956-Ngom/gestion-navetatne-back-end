package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LabelRule {
    static final String LABEL_API_PREFIX = "/labels";
    static final String LABEL_ID = "/{labelId}";

    @Bean
    public SecurityRule createLabel() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(LABEL_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_LABEL)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readLabel() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(LABEL_API_PREFIX + LABEL_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_LABEL)
                .hasPermission(SecurityPermissions.ADD_LABEL)
//                .hasPermission(SecurityPermissions.ADD_PARC_ROULANT)
//                .hasPermission(SecurityPermissions.ADD_PASSATION)
                .hasPermission(SecurityPermissions.EDIT_LABEL)
//                .hasPermission(SecurityPermissions.ADD_AGENT_BAF)
                .hasPermission(SecurityPermissions.DELETE_LABEL)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllLabel() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(LABEL_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_LABEL)
                .hasPermission(SecurityPermissions.ADD_LABEL)
//                .hasPermission(SecurityPermissions.ADD_PARC_ROULANT)
//                .hasPermission(SecurityPermissions.ADD_AGENT_BAF)
//                .hasPermission(SecurityPermissions.ADD_PASSATION)
                .hasPermission(SecurityPermissions.EDIT_LABEL)
                .hasPermission(SecurityPermissions.DELETE_LABEL)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
//                .hasPermission(SecurityPermissions.ADD_PASSATION)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateLabel() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(LABEL_API_PREFIX + LABEL_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_LABEL)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule deleteLabel() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(LABEL_API_PREFIX + LABEL_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_LABEL)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

}
