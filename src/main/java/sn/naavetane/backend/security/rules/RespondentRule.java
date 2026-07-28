package sn.naavetane.backend.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import sn.naavetane.backend.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RespondentRule {
    static final String RESPONDENT_PREFIX = "/respondents";
    static final String RESPONDENT_ID = "/{respondentId}";

//    static  final String RESPONDENT_DOWNLOAD_FILE="/{id}/_download";
//
//   static final String RESPONDENT_IMPORT ="/import";
//   static final String RESPONDENT_EXPORT ="/export";



    @Bean
    public SecurityRule createRespondent() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(RESPONDENT_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_RESPONDENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readRespondent() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(RESPONDENT_PREFIX + RESPONDENT_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_RESPONDENT)
                .hasPermission(SecurityPermissions.ADD_RESPONDENT)
                .hasPermission(SecurityPermissions.EDIT_RESPONDENT)
                .hasPermission(SecurityPermissions.DELETE_RESPONDENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readRespondents() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(RESPONDENT_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_RESPONDENT)
                .hasPermission(SecurityPermissions.ADD_RESPONDENT)
                .hasPermission(SecurityPermissions.EDIT_RESPONDENT)
                .hasPermission(SecurityPermissions.DELETE_RESPONDENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateRespondent() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(RESPONDENT_PREFIX + RESPONDENT_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_RESPONDENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteRespondent() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(RESPONDENT_PREFIX + RESPONDENT_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_RESPONDENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

   

  

 
}
