package sn.naavetane.backend.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import sn.naavetane.backend.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GouvernanceImpactRule {
    static final String  GOUV_IMPACT_API_PREFIX = "/gouvernance-impact";
    static final String GOUV_IMPACT_ID = "/{gouvernanceImpactId}";

    @Bean
    public SecurityRule addImpactGouvernance() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(GOUV_IMPACT_API_PREFIX)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.ADD_GOUV_IMPACTS)
                .hasPermission(SecurityPermissions.READ_PROJECT_ESG)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllImpactGouvernanceProject() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(GOUV_IMPACT_API_PREFIX + GOUV_IMPACT_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.READ_GOUV_IMPACTS)
                .hasPermission(SecurityPermissions.READ_PROJECT_ESG)
//                .hasPermission(SecurityPermissions.ADD_GOUV_IMPACTS)
//                .hasPermission(SecurityPermissions.EDIT_GOUV_IMPACTS)
//                .hasPermission(SecurityPermissions.DELETE_GOUV_IMPACTS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readGouvernanceImpact() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(GOUV_IMPACT_API_PREFIX)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.READ_GOUV_IMPACTS)
//                .hasPermission(SecurityPermissions.ADD_GOUV_IMPACTS)
                .hasPermission(SecurityPermissions.READ_PROJECT_ESG)
//                .hasPermission(SecurityPermissions.EDIT_GOUV_IMPACTS)
//                .hasPermission(SecurityPermissions.DELETE_GOUV_IMPACTS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateImpactGouvernance() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(GOUV_IMPACT_API_PREFIX + GOUV_IMPACT_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.EDIT_GOUV_IMPACTS)
                .hasPermission(SecurityPermissions.READ_PROJECT_ESG)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteImpactGouvernance() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(GOUV_IMPACT_API_PREFIX + GOUV_IMPACT_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.DELETE_GOUV_IMPACTS)
                .hasPermission(SecurityPermissions.READ_PROJECT_ESG)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

}
