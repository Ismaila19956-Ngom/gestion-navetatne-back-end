package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MarketFileRule {
    static final String MARKET_FILE_PREFIX = "/marketfile";

    static final String MARKET_FILE_ID = "/{fileMarketId}";

   static final  String MARKET_FILE_TEST="/listNotes";
    @Bean
    public SecurityRule createMarketFile() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(MARKET_FILE_PREFIX)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.ADD_MARKET_FILE)
                .hasPermission(SecurityPermissions.READ_PROJECT_APPEL_OFFRE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readMarketId() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(MARKET_FILE_PREFIX +MARKET_FILE_ID )
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.ADD_MARKET_FILE)
//                .hasPermission(SecurityPermissions.READ_MARKET_FILE)
//                .hasPermission(SecurityPermissions. EDIT_MARKET_FILE)
//                .hasPermission(SecurityPermissions.DELETE_MARKET_FILE)
                .hasPermission(SecurityPermissions.READ_PROJECT_APPEL_OFFRE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readFilesTest() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(MARKET_FILE_PREFIX + MARKET_FILE_TEST)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.ADD_MARKET_FILE)
//                .hasPermission(SecurityPermissions.READ_MARKET_FILE)
//                .hasPermission(SecurityPermissions. EDIT_MARKET_FILE)
//                .hasPermission(SecurityPermissions.DELETE_MARKET_FILE)
                .hasPermission(SecurityPermissions.READ_PROJECT_APPEL_OFFRE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readMarketFiles() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(MARKET_FILE_PREFIX)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.ADD_MARKET_FILE)
//                .hasPermission(SecurityPermissions.READ_MARKET_FILE)
//                .hasPermission(SecurityPermissions. EDIT_MARKET_FILE)
//                .hasPermission(SecurityPermissions.DELETE_MARKET_FILE)
                .hasPermission(SecurityPermissions.READ_PROJECT_APPEL_OFFRE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }




    @Bean
    public SecurityRule updateMarketFile() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(MARKET_FILE_PREFIX+ MARKET_FILE_ID )
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.EDIT_MARKET_FILE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .hasPermission(SecurityPermissions.READ_PROJECT_APPEL_OFFRE)
                .end();
    }

    @Bean
    public SecurityRule deleteMarketFile() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(MARKET_FILE_PREFIX+ MARKET_FILE_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.DELETE_PLAN_DE_PASSATION_MARKET)
                .hasPermission(SecurityPermissions.READ_PROJECT_APPEL_OFFRE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule viewMarketFile() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(MARKET_FILE_PREFIX)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.ADD_MARKET_FILE)
                .hasPermission(SecurityPermissions.READ_PROJECT_APPEL_OFFRE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}
