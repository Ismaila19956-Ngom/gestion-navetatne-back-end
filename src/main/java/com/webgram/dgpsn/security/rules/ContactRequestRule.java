package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ContactRequestRule {
    static final String CONTACT_REQUEST_API_PREFIX = "/contact-requests";
    static final String CONTACT_REQUEST_ID = "/{requestId}";
    static final String STATUS = "/statut";
    static final String DOWNLOAD_PREFIX = "/_download";


    @Bean
    public SecurityRule createContactRequest() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(CONTACT_REQUEST_API_PREFIX + "/create")
                .build()
                .condition()
               .hasPermission(SecurityPermissions.ADD_CONTACT_REQUEST)
//                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readContactRequest() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(CONTACT_REQUEST_API_PREFIX + CONTACT_REQUEST_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_CONTACT_REQUEST)
                .hasPermission(SecurityPermissions.ADD_CONTACT_REQUEST)
                .hasPermission(SecurityPermissions.EDIT_CONTACT_REQUEST)
                .hasPermission(SecurityPermissions.DELETE_CONTACT_REQUEST)
//                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllContactRequest() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(CONTACT_REQUEST_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_CONTACT_REQUEST)
                .hasPermission(SecurityPermissions.ADD_CONTACT_REQUEST)
                .hasPermission(SecurityPermissions.EDIT_CONTACT_REQUEST)
                .hasPermission(SecurityPermissions.DELETE_CONTACT_REQUEST)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateContactRequest() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(CONTACT_REQUEST_API_PREFIX + CONTACT_REQUEST_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_CONTACT_REQUEST)
//                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule deleteContactRequest() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(CONTACT_REQUEST_API_PREFIX + CONTACT_REQUEST_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_CONTACT_REQUEST)
//                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule changeStatutContactRequest() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(CONTACT_REQUEST_API_PREFIX + CONTACT_REQUEST_ID + STATUS)
                .build()
                .condition()
                 .hasPermission(SecurityPermissions.EDIT_CONTACT_REQUEST)
//                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    //Download file
    @Bean
    public SecurityRule downloadFileContactRequest() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(CONTACT_REQUEST_API_PREFIX + CONTACT_REQUEST_ID + DOWNLOAD_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_FILE_CONTACT_REQUEST)
//                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }



}
