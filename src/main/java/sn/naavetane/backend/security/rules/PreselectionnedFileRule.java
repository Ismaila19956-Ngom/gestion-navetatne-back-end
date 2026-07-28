package sn.naavetane.backend.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import sn.naavetane.backend.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PreselectionnedFileRule {
    static final String PRESELECTIONNED_FILE_PREFIX = "/preselectiondfile";

    static final String PRESELECTIONNED_FILE_ID = "/{preselectiondId}";


    @Bean
    public SecurityRule createPreselectionnedFile() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(PRESELECTIONNED_FILE_PREFIX)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.ADD_PRESELECTIONNED_FILE)
                .hasPermission(SecurityPermissions.READ_PROJECT_APPEL_OFFRE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readPreselectionnedFile() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(PRESELECTIONNED_FILE_PREFIX +PRESELECTIONNED_FILE_ID )
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.ADD_PRESELECTIONNED_FILE)
//                .hasPermission(SecurityPermissions.READ_PRESELECTIONNED_FILE)
//                .hasPermission(SecurityPermissions. EDIT_PRESELECTIONNED_FILE)
//                .hasPermission(SecurityPermissions.DELETE_PRESELECTIONNED_FILE)
                .hasPermission(SecurityPermissions.READ_PROJECT_APPEL_OFFRE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readPreselectionnedFiles() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(PRESELECTIONNED_FILE_PREFIX)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.ADD_PRESELECTIONNED_FILE)
//                .hasPermission(SecurityPermissions.READ_PRESELECTIONNED_FILE)
//                .hasPermission(SecurityPermissions. EDIT_PRESELECTIONNED_FILE)
//                .hasPermission(SecurityPermissions.DELETE_PRESELECTIONNED_FILE)
                .hasPermission(SecurityPermissions.READ_PROJECT_APPEL_OFFRE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updatePreselectionnedFile() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(PRESELECTIONNED_FILE_PREFIX+ PRESELECTIONNED_FILE_ID )
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.EDIT_PRESELECTIONNED_FILE)
                .hasPermission(SecurityPermissions.READ_PROJECT_APPEL_OFFRE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deletePreselectionnedFile() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(PRESELECTIONNED_FILE_PREFIX + PRESELECTIONNED_FILE_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.DELETE_PRESELECTIONNED_FILE)
                .hasPermission(SecurityPermissions.READ_PROJECT_APPEL_OFFRE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule viewPreselectionnedFile() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(PRESELECTIONNED_FILE_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_PROJECT_APPEL_OFFRE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}
