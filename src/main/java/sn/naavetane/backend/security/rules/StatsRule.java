package sn.naavetane.backend.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StatsRule {
    static final String STATS_API_PREFIX = "/api/stats";

    @Bean
    public SecurityRule getDashboardStats() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(STATS_API_PREFIX + "/dashboard")
                .authenticated(true)
                .build();
    }
}
