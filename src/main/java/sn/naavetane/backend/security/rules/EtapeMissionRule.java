package sn.naavetane.backend.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import sn.naavetane.backend.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EtapeMissionRule {
    static final String  ETAPE_MISSION_ACTIVITY_API_PREFIX = "/etapeMission";
    static final String ETAPE_MISSION_ACTIVITY_ID = "/{etapeMissionId}";


    @Bean
    public SecurityRule expenseActivityAddToProject() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(ETAPE_MISSION_ACTIVITY_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_ETAPE_MISSION_ACTIVITY)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readExpenseActivity() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(ETAPE_MISSION_ACTIVITY_API_PREFIX + ETAPE_MISSION_ACTIVITY_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_ETAPE_MISSION_ACTIVITY)
                .hasPermission(SecurityPermissions.ADD_ETAPE_MISSION_ACTIVITY)
                .hasPermission(SecurityPermissions.EDIT_ETAPE_MISSION_ACTIVITY)
                .hasPermission(SecurityPermissions.DELETE_ETAPE_MISSION_ACTIVITY)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllExpenseActivityByProject() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(ETAPE_MISSION_ACTIVITY_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_ETAPE_MISSION_ACTIVITY)
                .hasPermission(SecurityPermissions.ADD_ETAPE_MISSION_ACTIVITY)
                .hasPermission(SecurityPermissions.EDIT_ETAPE_MISSION_ACTIVITY)
                .hasPermission(SecurityPermissions.DELETE_ETAPE_MISSION_ACTIVITY)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateExpenseActivity() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(ETAPE_MISSION_ACTIVITY_API_PREFIX + ETAPE_MISSION_ACTIVITY_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_ETAPE_MISSION_ACTIVITY)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteExpenseActivity() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(ETAPE_MISSION_ACTIVITY_API_PREFIX + ETAPE_MISSION_ACTIVITY_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_ETAPE_MISSION_ACTIVITY)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }


}
