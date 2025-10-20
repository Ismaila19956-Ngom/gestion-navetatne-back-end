package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RecommendationRule {
    static final String RECOMMENDATION_PREFIX = "/recommendations";
    static final String RECOMMENDATION_ID = "/{recommendationId}";

//    static  final String RECOMMENDATION_DOWNLOAD_FILE="/{id}/_download";
//
//   static final String RECOMMENDATION_IMPORT ="/import";
//   static final String RECOMMENDATION_EXPORT ="/export";



    @Bean
    public SecurityRule createRecommendation() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(RECOMMENDATION_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_RECOMMENDATION)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readcRecommendation() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(RECOMMENDATION_PREFIX + RECOMMENDATION_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_RECOMMENDATION)
                .hasPermission(SecurityPermissions.EDIT_RECOMMENDATION)
                .hasPermission(SecurityPermissions.ADD_RECOMMENDATION)
                .hasPermission(SecurityPermissions.DELETE_RECOMMENDATION)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readRecommendations() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(RECOMMENDATION_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_RECOMMENDATION)
                .hasPermission(SecurityPermissions.EDIT_RECOMMENDATION)
                .hasPermission(SecurityPermissions.ADD_RECOMMENDATION)
                .hasPermission(SecurityPermissions.DELETE_RECOMMENDATION)
                .hasPermission(SecurityPermissions.DELETE_RECOMMENDATION)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateRecommendation() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(RECOMMENDATION_PREFIX + RECOMMENDATION_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_RECOMMENDATION)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

   

  

 
}
