package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewRiskRule {
    static final String REVIEW_RISK_PREFIX = "/reviewRisks";
    static final String REVIEW_RISK_ID = "/{reviewRiskId}";

    static final String REVIEW_RISK_POST = "/new";

//    static  final String REVIEW_RISK_DOWNLOAD_FILE="/{id}/_download";
//
//   static final String REVIEW_RISK_IMPORT ="/import";
//   static final String REVIEW_RISK_EXPORT ="/export";



    @Bean
    public SecurityRule createReviewRisk() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(REVIEW_RISK_PREFIX + REVIEW_RISK_POST )
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_REVIEW_RISK)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readReviewRisk() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(REVIEW_RISK_PREFIX + REVIEW_RISK_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_REVIEW_RISK)
                .hasPermission(SecurityPermissions.READ_REVIEW_RISK)
                .hasPermission(SecurityPermissions. EDIT_REVIEW_RISK)
                .hasPermission(SecurityPermissions.DELETE_REVIEW_RISK)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readReviewsRisks() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(REVIEW_RISK_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_REVIEW_RISK)
                .hasPermission(SecurityPermissions.READ_REVIEW_RISK)
                .hasPermission(SecurityPermissions. EDIT_REVIEW_RISK)
                .hasPermission(SecurityPermissions.DELETE_REVIEW_RISK)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateReviewRisk() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(REVIEW_RISK_PREFIX + REVIEW_RISK_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions. EDIT_REVIEW_RISK)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteReviewRisk() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(REVIEW_RISK_PREFIX + REVIEW_RISK_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions. EDIT_REVIEW_RISK)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }


  

 
}
