package sn.naavetane.backend.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import sn.naavetane.backend.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SpecficNatureRule {
    static final String SPECIFIC_NATURE_PREFIX = "/specificNatures";
    static final String SPECIFIC_NATURE_ID = "/{specificNatureId}";

//    static  final String REVIEW_DOWNLOAD_FILE="/{id}/_download";
//
//   static final String REVIEW_IMPORT ="/import";
//   static final String REVIEW_EXPORT ="/export";



    @Bean
    public SecurityRule createSpecific_natu() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(SPECIFIC_NATURE_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_SPECIFIC_NATURE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readSpecific_natu() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(SPECIFIC_NATURE_PREFIX + SPECIFIC_NATURE_ID )
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_SPECIFIC_NATURE)
                .hasPermission(SecurityPermissions.READ_SPECIFIC_NATURE)
                .hasPermission(SecurityPermissions. EDIT_SPECIFIC_NATURE)
                .hasPermission(SecurityPermissions.DELETE_SPECIFIC_NATURE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readSpecific_natus() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(SPECIFIC_NATURE_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_SPECIFIC_NATURE)
                .hasPermission(SecurityPermissions.READ_SPECIFIC_NATURE)
                .hasPermission(SecurityPermissions.EDIT_SPECIFIC_NATURE)
                .hasPermission(SecurityPermissions.DELETE_SPECIFIC_NATURE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateSpecific_natu() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(SPECIFIC_NATURE_PREFIX + SPECIFIC_NATURE_ID )
                .build()
                .condition()
                .hasPermission(SecurityPermissions. EDIT_SPECIFIC_NATURE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

   

  

 
}
