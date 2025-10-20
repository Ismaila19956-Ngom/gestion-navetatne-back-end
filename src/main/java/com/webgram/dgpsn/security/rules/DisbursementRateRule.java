package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DisbursementRateRule {
    static final String DISBURSEMENT_RATE_API_PREFIX = "/disbursementRates";
    static final String DISBURSEMENT_RATE_ID = "/{disbursementRateId}";
    static final String RATE = "/fiche/{projectId}";

    @Bean
    public SecurityRule createDisbursementRate() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(DISBURSEMENT_RATE_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_DISBURSEMENT_RATE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readDisbursementRate() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(DISBURSEMENT_RATE_API_PREFIX + DISBURSEMENT_RATE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_DISBURSEMENT_RATE)
                .hasPermission(SecurityPermissions.ADD_DISBURSEMENT_RATE)
                .hasPermission(SecurityPermissions.EDIT_DISBURSEMENT_RATE)
                .hasPermission(SecurityPermissions.DELETE_DISBURSEMENT_RATE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllDisbursement() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(DISBURSEMENT_RATE_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_DISBURSEMENT_RATE)
                .hasPermission(SecurityPermissions.ADD_DISBURSEMENT_RATE)
                .hasPermission(SecurityPermissions.EDIT_DISBURSEMENT_RATE)
                .hasPermission(SecurityPermissions.DELETE_DISBURSEMENT_RATE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateDisbursementRate() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(DISBURSEMENT_RATE_API_PREFIX + DISBURSEMENT_RATE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_DISBURSEMENT_RATE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule deleteDisbursementRate() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(DISBURSEMENT_RATE_API_PREFIX + DISBURSEMENT_RATE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_DISBURSEMENT_RATE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readRate() {
      //  System.out.println(DISBURSEMENT_RATE_API_PREFIX+RATE);
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(DISBURSEMENT_RATE_API_PREFIX+RATE)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_DISBURSEMENT_RATE)
                .hasPermission(SecurityPermissions.ADD_DISBURSEMENT_RATE)
                .hasPermission(SecurityPermissions.EDIT_DISBURSEMENT_RATE)
                .hasPermission(SecurityPermissions.DELETE_DISBURSEMENT_RATE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }




}
