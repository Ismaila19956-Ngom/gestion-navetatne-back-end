package sn.naavetane.backend.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import sn.naavetane.backend.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FundingNeedRule {
    static final String  FUNDING_NEED_API_PREFIX = "/fundingConfig";
    static final String  FUNDING_NEED_ID = "/{fundingConfigId}";


    @Bean
    public SecurityRule AddFindingNeed() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(FUNDING_NEED_API_PREFIX )
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_FUNDING_NEED)
                .hasPermission(SecurityPermissions.READ_PROJECT_FINANCEMENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule viewFindingNeed() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(FUNDING_NEED_API_PREFIX )
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_PROJECT_FINANCEMENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }


    @Bean
    public SecurityRule readAllFindingNeedByActivity() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(FUNDING_NEED_API_PREFIX )
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_FUNDING_NEED)
                .hasPermission(SecurityPermissions.ADD_FUNDING_NEED)
                .hasPermission(SecurityPermissions.EDIT_FUNDING_NEED)
                .hasPermission(SecurityPermissions.DELETE_FUNDING_NEED)
                .hasPermission(SecurityPermissions.READ_PROJECT_FINANCEMENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateFundingNeed() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(FUNDING_NEED_API_PREFIX +  FUNDING_NEED_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_FUNDING_NEED)
                .hasPermission(SecurityPermissions.READ_PROJECT_FINANCEMENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteFundingNeed() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(FUNDING_NEED_API_PREFIX +  FUNDING_NEED_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_FUNDING_NEED)
                .hasPermission(SecurityPermissions.READ_PROJECT_FINANCEMENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

}
