package sn.naavetane.backend.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import sn.naavetane.backend.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EnvironnementalIndicateurRule {
    static final String ENV_INDICATEUR_API_PREFIX = "/environmental-indicateur";
    static final String ENV_INDICATEUR_ID = "/{environmentalIndicateurId}";

    @Bean
    public SecurityRule addIndicateurEnvironmental() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(ENV_INDICATEUR_API_PREFIX)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.ADD_ENV_INDICATEURS)
                .hasPermission(SecurityPermissions.READ_PROJECT_ESG)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllIndicateurEnvironmentalProject() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(ENV_INDICATEUR_API_PREFIX + ENV_INDICATEUR_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.READ_ENV_INDICATEURS)
                .hasPermission(SecurityPermissions.READ_PROJECT_ESG)
//                .hasPermission(SecurityPermissions.ADD_ENV_INDICATEURS)
//                .hasPermission(SecurityPermissions.EDIT_ENV_INDICATEURS)
//                .hasPermission(SecurityPermissions.DELETE_ENV_INDICATEURS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readEnvironnementIndicateur() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(ENV_INDICATEUR_API_PREFIX)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.READ_ENV_INDICATEURS)
                .hasPermission(SecurityPermissions.READ_PROJECT_ESG)
//                .hasPermission(SecurityPermissions.ADD_ENV_INDICATEURS)
//                .hasPermission(SecurityPermissions.EDIT_ENV_INDICATEURS)
//                .hasPermission(SecurityPermissions.DELETE_ENV_INDICATEURS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateIndicateurEnvironnemental() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(ENV_INDICATEUR_API_PREFIX + ENV_INDICATEUR_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_PROJECT_ESG)
//                .hasPermission(SecurityPermissions.EDIT_ENV_INDICATEURS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteIndicateurEnvironnemental() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(ENV_INDICATEUR_API_PREFIX + ENV_INDICATEUR_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_PROJECT_ESG)
//                .hasPermission(SecurityPermissions.DELETE_ENV_INDICATEURS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

}
