package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MeetingRule {
    static final String  MEETING_API_PREFIX = "/meetings";
     static final String MEETING_ID = "/{meetingId}";
    static final String  MEETING_AGENT_API_PREFIX = "/meetingAgent";
   

    @Bean
    public SecurityRule createMeeting() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(MEETING_API_PREFIX )
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_MEETING)
                .hasPermission(SecurityPermissions.READ_PROJECT_ACTIVITY_MONITORING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }


    @Bean
    public SecurityRule readAllMeeting() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(MEETING_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_MEETING)
                .hasPermission(SecurityPermissions.ADD_MEETING)
                .hasPermission(SecurityPermissions.EDIT_MEETING)
                .hasPermission(SecurityPermissions.DELETE_MEETING)
                .hasPermission(SecurityPermissions.READ_PROJECT_ACTIVITY_MONITORING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule updateMeetingAgent() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(MEETING_API_PREFIX + MEETING_AGENT_API_PREFIX + MEETING_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_MEETING)
                .hasPermission(SecurityPermissions.READ_PROJECT_ACTIVITY_MONITORING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule  readMeeting() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(MEETING_API_PREFIX + MEETING_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_MEETING)
                .hasPermission(SecurityPermissions.ADD_MEETING)
                .hasPermission(SecurityPermissions.EDIT_MEETING)
                .hasPermission(SecurityPermissions.DELETE_MEETING)
                .hasPermission(SecurityPermissions.READ_PROJECT_ACTIVITY_MONITORING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateMeeting() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(MEETING_API_PREFIX + MEETING_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_MEETING)
                .hasPermission(SecurityPermissions.READ_PROJECT_ACTIVITY_MONITORING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule deleteMeeting() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(MEETING_API_PREFIX + MEETING_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_MEETING)
                .hasPermission(SecurityPermissions.READ_PROJECT_ACTIVITY_MONITORING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule viewMeeting() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(MEETING_API_PREFIX )
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_PROJECT_ACTIVITY_MONITORING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}
