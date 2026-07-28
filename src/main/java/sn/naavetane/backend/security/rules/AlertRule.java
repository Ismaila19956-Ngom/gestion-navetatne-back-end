package sn.naavetane.backend.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import sn.naavetane.backend.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AlertRule {
    static final String ALERTE_API_PREFIX = "/alertes";
    static final String ALERT_ID = "/{alertId}";

    @Bean
    public SecurityRule setAlertTotRead() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(ALERTE_API_PREFIX + ALERT_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_NOTIFICATION)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAll() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(ALERTE_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_NOTATION)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }


}
