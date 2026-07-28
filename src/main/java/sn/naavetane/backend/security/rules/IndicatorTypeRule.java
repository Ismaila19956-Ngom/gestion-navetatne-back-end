package sn.naavetane.backend.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import sn.naavetane.backend.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class IndicatorTypeRule {
    static final String  Indicator_Type_API_PREFIX = "/indicatorTypes";
    static final String Indicator_Type_ID = "/{indicatorTypeId}";

    @Bean
    public SecurityRule createIndicatorType() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(Indicator_Type_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_INDICATOR_TYPE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readIndicatorType() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(Indicator_Type_API_PREFIX + Indicator_Type_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_INDICATOR_TYPE)
                .hasPermission(SecurityPermissions.ADD_INDICATOR_TYPE)
                .hasPermission(SecurityPermissions.EDIT_INDICATOR_TYPE)
                .hasPermission(SecurityPermissions.DELETE_INDICATOR_TYPE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule  readAllTags() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(Indicator_Type_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_INDICATOR_TYPE)
                .hasPermission(SecurityPermissions.ADD_INDICATOR_TYPE)
                .hasPermission(SecurityPermissions.EDIT_INDICATOR_TYPE)
                .hasPermission(SecurityPermissions.DELETE_INDICATOR_TYPE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateIndicatorType() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(Indicator_Type_API_PREFIX + Indicator_Type_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_INDICATOR_TYPE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule deleteIndicatorType() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(Indicator_Type_API_PREFIX + Indicator_Type_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_INDICATOR_TYPE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}
