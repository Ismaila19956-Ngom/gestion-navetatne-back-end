package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StateProgressRule {
    static final String STATE_PROGRESS_PREFIX = "/stateProgress";
    static final String STATE_PROGRESS_ID = "/{stateProgressId}";


    @Bean
    public SecurityRule createState_progress() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(STATE_PROGRESS_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_STATE_PROGRESS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readState_progress() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(STATE_PROGRESS_PREFIX + STATE_PROGRESS_ID )
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_STATE_PROGRESS)
                .hasPermission(SecurityPermissions.READ_STATE_PROGRESS)
                .hasPermission(SecurityPermissions. EDIT_STATE_PROGRESS)
                .hasPermission(SecurityPermissions.DELETE_STATE_PROGRESS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readState_progresss() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(STATE_PROGRESS_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_STATE_PROGRESS)
                .hasPermission(SecurityPermissions.READ_STATE_PROGRESS)
                .hasPermission(SecurityPermissions.EDIT_STATE_PROGRESS)
                .hasPermission(SecurityPermissions.DELETE_STATE_PROGRESS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateState_progress() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(STATE_PROGRESS_PREFIX + STATE_PROGRESS_ID )
                .build()
                .condition()
                .hasPermission(SecurityPermissions. EDIT_STATE_PROGRESS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule deleteStateProgress() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(STATE_PROGRESS_PREFIX + STATE_PROGRESS_ID )
                .build()
                .condition()
                .hasPermission(SecurityPermissions. DELETE_STATE_PROGRESS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

   

  

 
}
