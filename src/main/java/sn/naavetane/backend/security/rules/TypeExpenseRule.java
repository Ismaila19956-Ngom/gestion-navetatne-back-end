package sn.naavetane.backend.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import sn.naavetane.backend.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TypeExpenseRule {
    static final String  TYPE_EXPENSE_API_PREFIX = "/typeExpense";
    static final String TYPE_EXPENSE_ID = "/{typeExpenseId}";

    @Bean
    public SecurityRule createTypeExpense() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(TYPE_EXPENSE_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_REF_TYPE_EXPENSE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readTypeExpense() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(TYPE_EXPENSE_API_PREFIX + TYPE_EXPENSE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_REF_TYPE_EXPENSE)
                .hasPermission(SecurityPermissions.ADD_REF_TYPE_EXPENSE)
                .hasPermission(SecurityPermissions.EDIT_REF_TYPE_EXPENSE)
                .hasPermission(SecurityPermissions.DELETE_REF_TYPE_EXPENSE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllTypeExpense() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(TYPE_EXPENSE_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_REF_TYPE_EXPENSE)
                .hasPermission(SecurityPermissions.ADD_REF_TYPE_EXPENSE)
                .hasPermission(SecurityPermissions.EDIT_REF_TYPE_EXPENSE)
                .hasPermission(SecurityPermissions.DELETE_REF_TYPE_EXPENSE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateTypeExpense() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(TYPE_EXPENSE_API_PREFIX + TYPE_EXPENSE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_REF_TYPE_EXPENSE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule deleteTypeExpense() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(TYPE_EXPENSE_API_PREFIX + TYPE_EXPENSE_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_REF_TYPE_EXPENSE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}
