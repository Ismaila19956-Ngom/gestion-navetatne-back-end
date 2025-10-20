package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ParticipantMissionRule {
    static final String PARTICIPANT_MISSION_API_PREFIX = "/participant";
    static final String PARTICIPANT_MISSION_ID = "/{participantId}";
    @Bean
    public SecurityRule createParticipantMission() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(PARTICIPANT_MISSION_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_PARTICIPANT_MISSION)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readParticipantMission() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(PARTICIPANT_MISSION_API_PREFIX + PARTICIPANT_MISSION_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_PARTICIPANT_MISSION)
                .hasPermission(SecurityPermissions.ADD_PARTICIPANT_MISSION)
                .hasPermission(SecurityPermissions.EDIT_PARTICIPANT_MISSION)
                .hasPermission(SecurityPermissions.DELETE_PARTICIPANT_MISSION)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllParticipantMissionActivity() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(PARTICIPANT_MISSION_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_PARTICIPANT_MISSION)
                .hasPermission(SecurityPermissions.ADD_PARTICIPANT_MISSION)
                .hasPermission(SecurityPermissions.EDIT_PARTICIPANT_MISSION)
                .hasPermission(SecurityPermissions.DELETE_PARTICIPANT_MISSION)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateParticipantMission() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(PARTICIPANT_MISSION_API_PREFIX + PARTICIPANT_MISSION_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_PARTICIPANT_MISSION)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule deleteParticipantMissionActivity() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(PARTICIPANT_MISSION_API_PREFIX + PARTICIPANT_MISSION_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_PARTICIPANT_MISSION)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}
