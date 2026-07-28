package sn.naavetane.backend.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import sn.naavetane.backend.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TypeCadreLogiqueRule {
    static final String CADRE_LOGIQUE_PREFIX = "/typeCadreLogique";
    static final String CADRE_LOGIQUE_ID = "/{typeCadreLogiqueId}";

    @Bean
    public SecurityRule createCadre_logique() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(CADRE_LOGIQUE_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_CADRE_LOGIQUE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readCadre_logique() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(CADRE_LOGIQUE_PREFIX + CADRE_LOGIQUE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_CADRE_LOGIQUE)
                .hasPermission(SecurityPermissions.ADD_CADRE_LOGIQUE)
                .hasPermission(SecurityPermissions.EDIT_CADRE_LOGIQUE)
                .hasPermission(SecurityPermissions.DELETE_CADRE_LOGIQUE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readCadre_logiques() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(CADRE_LOGIQUE_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_CADRE_LOGIQUE)
                .hasPermission(SecurityPermissions.ADD_CADRE_LOGIQUE)
                .hasPermission(SecurityPermissions.EDIT_CADRE_LOGIQUE)
                .hasPermission(SecurityPermissions.DELETE_CADRE_LOGIQUE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateCadre_logique() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(CADRE_LOGIQUE_PREFIX + CADRE_LOGIQUE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_CADRE_LOGIQUE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}
