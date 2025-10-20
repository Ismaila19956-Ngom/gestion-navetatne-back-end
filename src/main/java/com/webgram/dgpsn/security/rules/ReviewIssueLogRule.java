package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewIssueLogRule {
    static final String REVIEW_ISSUE_LOG_PREFIX = "/reviewIssuelogs";
    static final String REVIEW_ISSUE_LOG_ID = "/{reviewIssuelogId}";

//    static  final String REVIEW_ISSUE_LOG_DOWNLOAD_FILE="/{id}/_download";
//
//   static final String REVIEW_ISSUE_LOG_IMPORT ="/import";
//   static final String REVIEW_ISSUE_LOG_EXPORT ="/export";



    @Bean
    public SecurityRule createReview() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(REVIEW_ISSUE_LOG_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_REVIEW_ISSUE_LOG)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readReviews() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(REVIEW_ISSUE_LOG_PREFIX + REVIEW_ISSUE_LOG_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_REVIEW_ISSUE_LOG)
                .hasPermission(SecurityPermissions.READ_REVIEW_ISSUE_LOG)
                .hasPermission(SecurityPermissions. EDIT_REVIEW_ISSUE_LOG)
                .hasPermission(SecurityPermissions.DELETE_REVIEW_ISSUE_LOG)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readRevie() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(REVIEW_ISSUE_LOG_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_REVIEW_ISSUE_LOG)
                .hasPermission(SecurityPermissions.READ_REVIEW_ISSUE_LOG)
                .hasPermission(SecurityPermissions. EDIT_REVIEW_ISSUE_LOG)
                .hasPermission(SecurityPermissions.DELETE_REVIEW_ISSUE_LOG)
                .end();
    }

    @Bean
    public SecurityRule updateReview() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(REVIEW_ISSUE_LOG_PREFIX + REVIEW_ISSUE_LOG_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions. EDIT_REVIEW_ISSUE_LOG)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteReview() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(REVIEW_ISSUE_LOG_PREFIX + REVIEW_ISSUE_LOG_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions. DELETE_REVIEW_ISSUE_LOG)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

   

  

 
}
