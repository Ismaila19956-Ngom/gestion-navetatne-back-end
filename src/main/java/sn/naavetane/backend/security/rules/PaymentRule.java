package sn.naavetane.backend.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentRule {
    static final String PAYMENT_API_PREFIX = "/api/payments";

    @Bean
    public SecurityRule initPayment() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(PAYMENT_API_PREFIX + "/init")
                .authenticated(true)
                .build();
    }

    @Bean
    public SecurityRule checkStatus() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(PAYMENT_API_PREFIX + "/status/{reference}")
                .authenticated(true)
                .build();
    }

    @Bean
    public SecurityRule webhook() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(PAYMENT_API_PREFIX + "/webhook")
                .authenticated(false)
                .build();
    }

    @Bean
    public SecurityRule simulateSuccess() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(PAYMENT_API_PREFIX + "/simulate-success")
                .authenticated(true)
                .build();
    }
}
