package sn.naavetane.backend.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import sn.naavetane.backend.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PartnerGroupRule {
    static final String PARTNER_GROUP_API_PREFIX = "/partner-groups";
    static final String PARTNER_GROUP_ID = "/{partnerGroupId}";
    @Bean
    public SecurityRule createPartnerGroup() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(PARTNER_GROUP_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_PARTNER_GROUP)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readPartnerGroup() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(PARTNER_GROUP_API_PREFIX + PARTNER_GROUP_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_PARTNER_GROUP)
                .hasPermission(SecurityPermissions.ADD_PARTNER_GROUP)
                .hasPermission(SecurityPermissions.EDIT_PARTNER_GROUP)
                .hasPermission(SecurityPermissions.DELETE_PARTNER_GROUP)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAlPartnerGroups() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(PARTNER_GROUP_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_PARTNER_GROUP)
                .hasPermission(SecurityPermissions.ADD_PARTNER_GROUP)
                .hasPermission(SecurityPermissions.EDIT_PARTNER_GROUP)
                .hasPermission(SecurityPermissions.DELETE_PARTNER_GROUP)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updatePartnerGroup() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(PARTNER_GROUP_API_PREFIX + PARTNER_GROUP_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_PARTNER_GROUP)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule deletePartnerGroup() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(PARTNER_GROUP_API_PREFIX + PARTNER_GROUP_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_PARTNER_GROUP)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}
