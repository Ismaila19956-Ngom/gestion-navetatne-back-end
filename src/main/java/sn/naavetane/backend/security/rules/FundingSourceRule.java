package sn.naavetane.backend.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import sn.naavetane.backend.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FundingSourceRule {
    static final String  SOURCE_FINANCE_API_PREFIX = "/source";
    static final String SOURCE_FINANCE_ID = "/{sourceId}";
//    static final String SOURCE_FINANCE_IMPORT = "/import";
//    static final String SOURCE_FINANCE_EXPORT = "/export";

    @Bean
    public SecurityRule AddSource() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(SOURCE_FINANCE_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_FUNDING_SOURCE)
                .hasPermission(SecurityPermissions.READ_PROJECT_FINANCEMENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule viewSource() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(SOURCE_FINANCE_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_PROJECT_FINANCEMENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

//    @Bean
//    public SecurityRule readBudget() {
//        return SecurityRule.builder()
//                .httpMethod(HttpMethod.GET)
//                .apiPattern(SOURCE_FINANCE_API_PREFIX + SOURCE_FINANCE_ID)
//                .build()
//                .condition()
//                .hasPermission(SecurityPermissions.READ_SOURCE_FINANCE)
//                .hasPermission(SecurityPermissions.ADD_SOURCE_FINANCE)
//                .hasPermission(SecurityPermissions.EDIT_SOURCE_FINANCE)
//                .hasPermission(SecurityPermissions.DELETE_SOURCE_FINANCE)
//                .hasPermission(SecurityPermissions.ALL_ACCESS)
//                .end();
//    }

    @Bean
    public SecurityRule readAllSourceByActivity() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(SOURCE_FINANCE_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_FUNDING_SOURCE)
                .hasPermission(SecurityPermissions.ADD_FUNDING_SOURCE)
                .hasPermission(SecurityPermissions.EDIT_FUNDING_SOURCE)
                .hasPermission(SecurityPermissions.DELETE_FUNDING_SOURCE)
                .hasPermission(SecurityPermissions.READ_PROJECT_FINANCEMENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateSourcefinancement() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(SOURCE_FINANCE_API_PREFIX + SOURCE_FINANCE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_FUNDING_SOURCE)
                .hasPermission(SecurityPermissions.READ_PROJECT_FINANCEMENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteSource() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(SOURCE_FINANCE_API_PREFIX + SOURCE_FINANCE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_FUNDING_SOURCE)
                .hasPermission(SecurityPermissions.READ_PROJECT_FINANCEMENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
//    @Bean
//    public SecurityRule importMilestone() {
//        return SecurityRule.builder()
//                .httpMethod(HttpMethod.POST)
//                .apiPattern(SOURCE_FINANCE_API_PREFIX + SOURCE_FINANCE_IMPORT)
//                .build()
//                .condition()
//                .hasPermission(SecurityPermissions.IMPORT_SOURCE_FINANCE)
//                .hasPermission(SecurityPermissions.ALL_ACCESS)
//                .end();
//    }
//
//    @Bean
//    public SecurityRule exportMilestone() {
//        return SecurityRule.builder()
//                .httpMethod(HttpMethod.GET)
//                .apiPattern(SOURCE_FINANCE_API_PREFIX + SOURCE_FINANCE_EXPORT)
//                .build()
//                .condition()
//                .hasPermission(SecurityPermissions.EXPORT_SOURCE_FINANCE)
//                .hasPermission(SecurityPermissions.ALL_ACCESS)
//                .end();
//    }
}
