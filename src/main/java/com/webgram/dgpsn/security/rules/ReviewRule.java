package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewRule {
    static final String REVIEW_PREFIX = "/reviews";
    static final String REVIEW_ID = "/{reviewId}";
    static final String REVIEW_ISSUELOG = "/new";
    static final String REVIEW_ISSUELOG_API = "/reviewIssuelogs";
    static final String REVIEW_ISSUELOG_ID = "{reviewIssuelogId}";

//    static  final String REVIEW_DOWNLOAD_FILE="/{id}/_download";

   static final String REVIEW_IMPORT ="/import";
   static final String REVIEW_EXPORT ="/export";



    @Bean
    public SecurityRule createReviews() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(REVIEW_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_REVIEW)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule createReviewsNew() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(REVIEW_ISSUELOG_API +REVIEW_ISSUELOG)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_REVIEW_ISSUE_LOG)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readReviewProbleme() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(REVIEW_ISSUELOG_API )
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
    public SecurityRule deleteReviewProbleme() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(REVIEW_ISSUELOG_API +REVIEW_ISSUELOG_ID )
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_REVIEW_ISSUE_LOG)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }



    @Bean
    public SecurityRule readReview() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(REVIEW_PREFIX + REVIEW_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_REVIEW)
                .hasPermission(SecurityPermissions.READ_REVIEW)
                .hasPermission(SecurityPermissions. EDIT_REVIEW)
                .hasPermission(SecurityPermissions.DELETE_REVIEW)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllReviews() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(REVIEW_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_REVIEW)
                .hasPermission(SecurityPermissions.READ_REVIEW)
                .hasPermission(SecurityPermissions. EDIT_REVIEW)
                .hasPermission(SecurityPermissions.DELETE_REVIEW)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateReviews() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(REVIEW_PREFIX + REVIEW_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions. EDIT_REVIEW)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule deleteReviews() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(REVIEW_PREFIX + REVIEW_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_REVIEW)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }


    @Bean
    public SecurityRule importReview() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(REVIEW_IMPORT)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.IMPORT_REVIEW)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule exportReviews() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(REVIEW_EXPORT)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EXPORT_REVIEW)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

 
}
