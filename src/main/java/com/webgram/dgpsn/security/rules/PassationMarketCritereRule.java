package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PassationMarketCritereRule {
    static final String CRITERE_MARKET_PREFIX = "/criteremarket";

    static final String MARKET_ID = "/marketAll";
    static final String CRITERE_MARKET_ID = "/{id}";



    @Bean
    public SecurityRule createCritereMarket() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(CRITERE_MARKET_PREFIX)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.ADD_CRITERE_MARKET)
                .hasPermission(SecurityPermissions.READ_PROJECT_APPEL_OFFRE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readCritereMarket() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(CRITERE_MARKET_PREFIX +CRITERE_MARKET_ID )
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.ADD_CRITERE_MARKET)
//                .hasPermission(SecurityPermissions.READ_CRITERE_MARKET)
//                .hasPermission(SecurityPermissions. EDIT_CRITERE_MARKET)
//                .hasPermission(SecurityPermissions.DELETE_CRITERE_MARKET)
                .hasPermission(SecurityPermissions.READ_PROJECT_APPEL_OFFRE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readCritereMarketMarket() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(CRITERE_MARKET_PREFIX +MARKET_ID+CRITERE_MARKET_ID )
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.ADD_CRITERE_MARKET)
//                .hasPermission(SecurityPermissions.READ_CRITERE_MARKET)
//                .hasPermission(SecurityPermissions. EDIT_CRITERE_MARKET)
//                .hasPermission(SecurityPermissions.DELETE_CRITERE_MARKET)
                .hasPermission(SecurityPermissions.READ_PROJECT_APPEL_OFFRE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readCriteresMarket() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(CRITERE_MARKET_PREFIX)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.ADD_CRITERE_MARKET)
//                .hasPermission(SecurityPermissions.READ_CRITERE_MARKET)
//                .hasPermission(SecurityPermissions. EDIT_CRITERE_MARKET)
//                .hasPermission(SecurityPermissions.DELETE_CRITERE_MARKET)
                .hasPermission(SecurityPermissions.READ_PROJECT_APPEL_OFFRE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateCritereMarket() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(CRITERE_MARKET_PREFIX+ CRITERE_MARKET_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.EDIT_CRITERE_MARKET)
                .hasPermission(SecurityPermissions.READ_PROJECT_APPEL_OFFRE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteCritereMarket() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(CRITERE_MARKET_PREFIX + CRITERE_MARKET_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.DELETE_CRITERE_MARKET)
                .hasPermission(SecurityPermissions.READ_PROJECT_APPEL_OFFRE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule viewCritereMarket() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(CRITERE_MARKET_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_PROJECT_APPEL_OFFRE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}
