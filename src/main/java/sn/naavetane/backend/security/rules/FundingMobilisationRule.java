package sn.naavetane.backend.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import sn.naavetane.backend.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FundingMobilisationRule {
    static final String  FUNDING_MOBILISATION_API_PREFIX = "/fundingConfig";
    static final String  FUNDING_MOBILISATION_ID = "/{fundingConfigId}";


    @Bean
    public SecurityRule AddFindingMobilisation() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(FUNDING_MOBILISATION_API_PREFIX )
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_FUNDING_MOBILISATION)
                .hasPermission(SecurityPermissions.READ_PROJECT_FINANCEMENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule ViewFindingMobilisation() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(FUNDING_MOBILISATION_API_PREFIX )
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_PROJECT_FINANCEMENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule readAllFindingMobilisationByActivity() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(FUNDING_MOBILISATION_API_PREFIX )
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_FUNDING_MOBILISATION)
                .hasPermission(SecurityPermissions.ADD_FUNDING_MOBILISATION)
                .hasPermission(SecurityPermissions.EDIT_FUNDING_MOBILISATION)
                .hasPermission(SecurityPermissions.DELETE_FUNDING_MOBILISATION)
                .hasPermission(SecurityPermissions.READ_PROJECT_FINANCEMENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateFundingMobilisation() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(FUNDING_MOBILISATION_API_PREFIX +  FUNDING_MOBILISATION_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_FUNDING_MOBILISATION)
                .hasPermission(SecurityPermissions.READ_PROJECT_FINANCEMENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteFundingMobilisation() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(FUNDING_MOBILISATION_API_PREFIX +  FUNDING_MOBILISATION_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_FUNDING_MOBILISATION)
                .hasPermission(SecurityPermissions.READ_PROJECT_FINANCEMENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

}
