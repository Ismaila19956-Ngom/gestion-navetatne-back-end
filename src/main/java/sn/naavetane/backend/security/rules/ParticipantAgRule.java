package sn.naavetane.backend.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import sn.naavetane.backend.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ParticipantAgRule {
    static final String PARTICIPATION_AG_API_PREFIX = "/participantags";
    static final String PARTICIPATION_AG_ID = "/{participantagId}";
    static final String PARTICIPATION_AG_EXPORT = "/export";


    @Bean
    public SecurityRule createParticipantags() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(PARTICIPATION_AG_API_PREFIX)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.ADD_PARTICIPATION_AG)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllParticipantags() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(PARTICIPATION_AG_API_PREFIX)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.READ_PARTICIPATION_AG)
//                .hasPermission(SecurityPermissions.ADD_PARTICIPATION_AG)
//                .hasPermission(SecurityPermissions.EDIT_PARTICIPATION_AG)
//                .hasPermission(SecurityPermissions.DELETE_PARTICIPATION_AG)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readParticipantags() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(PARTICIPATION_AG_API_PREFIX + PARTICIPATION_AG_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.READ_PARTICIPATION_AG)
//                .hasPermission(SecurityPermissions.ADD_PARTICIPATION_AG)
//                .hasPermission(SecurityPermissions.EDIT_PARTICIPATION_AG)
//                .hasPermission(SecurityPermissions.DELETE_PARTICIPATION_AG)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateParticipantags() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(PARTICIPATION_AG_API_PREFIX + PARTICIPATION_AG_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.EDIT_PARTICIPATION_AG)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteParticipantags() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(PARTICIPATION_AG_API_PREFIX + PARTICIPATION_AG_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.DELETE_PARTICIPATION_AG)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }


    @Bean
    public SecurityRule exporteParticipantags() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(PARTICIPATION_AG_API_PREFIX + PARTICIPATION_AG_EXPORT)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.EXPORT_PARTICIPATION_AG)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}
