package sn.naavetane.backend.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import sn.naavetane.backend.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StatusRule {
    static final String STATE_STATUS_PREFIX = "/status";
    static final String STATE_STATUS_ID = "/{statusId}";

//    static  final String REVIEW_DOWNLOAD_FILE="/{id}/_download";
//
//   static final String REVIEW_IMPORT ="/import";
//   static final String REVIEW_EXPORT ="/export";



    @Bean
    public SecurityRule createStatus() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(STATE_STATUS_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_STATUS)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readStatus() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(STATE_STATUS_PREFIX + STATE_STATUS_ID )
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_STATUS)
                .hasPermission(SecurityPermissions.READ_STATUS)
                .hasPermission(SecurityPermissions. EDIT_STATUS)
                .hasPermission(SecurityPermissions.DELETE_STATUS)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readStatuss() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(STATE_STATUS_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_STATUS)
                .hasPermission(SecurityPermissions.READ_STATUS)
                .hasPermission(SecurityPermissions. EDIT_STATUS)
                .hasPermission(SecurityPermissions.DELETE_STATUS)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateStatus() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(STATE_STATUS_PREFIX + STATE_STATUS_ID )
                .build()
                .condition()
                .hasPermission(SecurityPermissions. EDIT_STATUS)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteStatus() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(STATE_STATUS_PREFIX + STATE_STATUS_ID )
                .build()
                .condition()
                .hasPermission(SecurityPermissions. DELETE_STATUS)
                .hasPermission(SecurityPermissions.READ_PROJECT_PROGRESS_TRACKING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

   

  

 
}
