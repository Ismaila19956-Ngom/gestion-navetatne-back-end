package sn.naavetane.backend.security.rules;

import sn.naavetane.backend.security.SecurityPermissions;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ParticipantRule {

    static final String PARTICIPANT_API_PREFIX = "/participants";
    static final String PARTICIPANT_ID = "/{id}";

    @Bean
    public SecurityRule createParticipant() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(PARTICIPANT_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_PARTICIPANT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readParticipant() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(PARTICIPANT_API_PREFIX + PARTICIPANT_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_PARTICIPANT)
                .hasPermission(SecurityPermissions.ADD_PARTICIPANT)
                .hasPermission(SecurityPermissions.EDIT_PARTICIPANT)
                .hasPermission(SecurityPermissions.DELETE_PARTICIPANT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllParticipants() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(PARTICIPANT_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_PARTICIPANT)
                .hasPermission(SecurityPermissions.ADD_PARTICIPANT)
                .hasPermission(SecurityPermissions.EDIT_PARTICIPANT)
                .hasPermission(SecurityPermissions.DELETE_PARTICIPANT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateParticipant() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(PARTICIPANT_API_PREFIX + PARTICIPANT_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_PARTICIPANT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteParticipant() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(PARTICIPANT_API_PREFIX + PARTICIPANT_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_PARTICIPANT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}
