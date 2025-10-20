package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MeetingTypeRule {
    static final String  MEETING_TYPE_API_PREFIX = "/meetingTypes";
     static final String MEETING_TYPE_ID = "/{meetingTypeId}";
   

    @Bean
    public SecurityRule createTypeMeeting() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(MEETING_TYPE_API_PREFIX )
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_MEETINGTYPE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }


    @Bean
    public SecurityRule readAllTypeMeeting() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(MEETING_TYPE_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_MEETINGTYPE)
                .hasPermission(SecurityPermissions.ADD_MEETINGTYPE)
                .hasPermission(SecurityPermissions.EDIT_MEETINGTYPE)
                .hasPermission(SecurityPermissions.DELETE_MEETINGTYPE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule  readMeetingType() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(MEETING_TYPE_API_PREFIX + MEETING_TYPE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_MEETINGTYPE)
                .hasPermission(SecurityPermissions.ADD_MEETINGTYPE)
                .hasPermission(SecurityPermissions.EDIT_MEETINGTYPE)
                .hasPermission(SecurityPermissions.DELETE_MEETINGTYPE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateMeetingType() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(MEETING_TYPE_API_PREFIX + MEETING_TYPE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_MEETINGTYPE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule deleteMeetingType() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(MEETING_TYPE_API_PREFIX + MEETING_TYPE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_MEETING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}
