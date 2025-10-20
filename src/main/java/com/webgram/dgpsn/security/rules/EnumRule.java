package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EnumRule {
    static final String API_PREFIX = "/enums";
    static final String CRITICITY_PREFIX = "/criticities";
    static final String REF_TYPE_PREFIX = "/referentielTypes";
    static final String PERIOD_BY_PERIODICITY = "/periodes/byPeriodicity";


    @Bean
    public SecurityRule readAllReferentielType() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(API_PREFIX + REF_TYPE_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_LABEL)
                .hasPermission(SecurityPermissions.ADD_LABEL)
                .hasPermission(SecurityPermissions.EDIT_LABEL)
                .hasPermission(SecurityPermissions.DELETE_LABEL)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllCriticities() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(API_PREFIX + CRITICITY_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_ISSUE_LOG)
                .hasPermission(SecurityPermissions.ADD_ISSUE_LOG)
                .hasPermission(SecurityPermissions.EDIT_ISSUE_LOG)
                .hasPermission(SecurityPermissions.DELETE_ISSUE_LOG)
                .hasPermission(SecurityPermissions.READ_ISSUE_LOG)
                .hasPermission(SecurityPermissions.ADD_ISSUE_LOG)
                .hasPermission(SecurityPermissions.EDIT_ISSUE_LOG)
                .hasPermission(SecurityPermissions.DELETE_ISSUE_LOG)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readPeriodByPeriodicity() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(API_PREFIX + PERIOD_BY_PERIODICITY)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }


}
