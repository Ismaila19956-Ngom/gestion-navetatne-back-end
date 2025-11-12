package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PassationMarketRule {
    static final String PASSATION_MARKET_PREFIX = "/passationmarket";

    static final String PASSATION_MARKET_ID = "/{passationMarketId}";


    @Bean
    public SecurityRule createPassationMarket() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(PASSATION_MARKET_PREFIX)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.ADD_PLAN_DE_PASSATION_MARKET)
                .hasPermission(SecurityPermissions.READ_PROJECT_APPEL_OFFRE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readPassationMarket() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(PASSATION_MARKET_PREFIX +PASSATION_MARKET_ID )
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_PLAN_DE_PASSATION_MARKET)
                .hasPermission(SecurityPermissions.READ_PLAN_DE_PASSATION_MARKET)
                .hasPermission(SecurityPermissions. EDIT_PLAN_DE_PASSATION_MARKET)
                .hasPermission(SecurityPermissions.DELETE_PLAN_DE_PASSATION_MARKET)
                .hasPermission(SecurityPermissions.READ_PROJECT_APPEL_OFFRE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readPassationsMarket() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(PASSATION_MARKET_PREFIX)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.ADD_PLAN_DE_PASSATION_MARKET)
//                .hasPermission(SecurityPermissions.READ_PLAN_DE_PASSATION_MARKET)
//                .hasPermission(SecurityPermissions. EDIT_PLAN_DE_PASSATION_MARKET)
//                .hasPermission(SecurityPermissions.DELETE_PLAN_DE_PASSATION_MARKET)
                .hasPermission(SecurityPermissions.READ_PROJECT_APPEL_OFFRE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updatePassationMarket() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(PASSATION_MARKET_PREFIX+ PASSATION_MARKET_ID )
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.EDIT_PLAN_DE_PASSATION_MARKET)
                .hasPermission(SecurityPermissions.READ_PROJECT_APPEL_OFFRE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deletePassationMarket() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(PASSATION_MARKET_PREFIX + PASSATION_MARKET_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.DELETE_PLAN_DE_PASSATION_MARKET)
                .hasPermission(SecurityPermissions.READ_PROJECT_APPEL_OFFRE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule viewPassationMarket() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(PASSATION_MARKET_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_PROJECT_APPEL_OFFRE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

}
