package com.webgram.dgpsn.security.rules;

import com.webgram.dgpsn.security.SecurityPermissions;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;


@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LabelRule {
    static final String LABEL_API_PREFIX = "/labels";
    static final String LABEL_ID = "/{labelId}";

    @Bean
    public SecurityRule createLabel() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(LABEL_API_PREFIX)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.ADD_LABEL)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readLabel() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(LABEL_API_PREFIX + LABEL_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.READ_LABEL)
//                .hasPermission(SecurityPermissions.ADD_LABEL)
//                .hasPermission(SecurityPermissions.EDIT_LABEL)
//                .hasPermission(SecurityPermissions.DELETE_LABEL)
                .hasPermission(SecurityPermissions.READ_FLUX_TRESORERIE_PARAMETRE)
                .hasPermission(SecurityPermissions.ADD_FLUX_TRESORERIE_PARAMETRE)
                .hasPermission(SecurityPermissions.EDIT_FLUX_TRESORERIE_PARAMETRE)
                .hasPermission(SecurityPermissions.DELETE_FLUX_TRESORERIE_PARAMETRE)
                .hasPermission(SecurityPermissions.READ_SOLDE_TRESORERIE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllLabel() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(LABEL_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_FLUX_TRESORERIE_PARAMETRE)
                .hasPermission(SecurityPermissions.ADD_FLUX_TRESORERIE_PARAMETRE)
                .hasPermission(SecurityPermissions.EDIT_FLUX_TRESORERIE_PARAMETRE)
                .hasPermission(SecurityPermissions.DELETE_FLUX_TRESORERIE_PARAMETRE)
                .hasPermission(SecurityPermissions.READ_SOLDE_TRESORERIE)
                .hasPermission(SecurityPermissions.ADD_BILAN_TRESORERIE_PARAMETRE)
                .hasPermission(SecurityPermissions.EDIT_BILAN_TRESORERIE_PARAMETRE)
                .hasPermission(SecurityPermissions.DELETE_BILAN_TRESORERIE_PARAMETRE)
                .hasPermission(SecurityPermissions.READ_BILAN_TRESORERIE_PARAMETRE)
                .hasPermission(SecurityPermissions.READ_BILAN)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateLabel() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(LABEL_API_PREFIX + LABEL_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.EDIT_LABEL)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule deleteLabel() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(LABEL_API_PREFIX + LABEL_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.DELETE_LABEL)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }


}
