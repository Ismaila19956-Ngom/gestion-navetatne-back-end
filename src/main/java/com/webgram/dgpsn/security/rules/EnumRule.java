package com.webgram.dgpsn.security.rules;

import com.webgram.dgpsn.security.SecurityPermissions;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EnumRule {
    static final String API_PREFIX = "/enums";
    static final String REF_TYPE_PREFIX = "/referentielTypes";



    @Bean
    public SecurityRule readAllReferentielType() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(API_PREFIX + REF_TYPE_PREFIX)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.READ_LABEL)
//                .hasPermission(SecurityPermissions.ADD_LABEL)
//                .hasPermission(SecurityPermissions.EDIT_LABEL)
//                .hasPermission(SecurityPermissions.DELETE_LABEL)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }




}
