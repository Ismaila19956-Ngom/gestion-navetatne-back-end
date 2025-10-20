package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StationRule {

    static final String STATION_API_PREFIX = "/stations";
    static final String STATION_ID = "/{stationId}";

    @Bean
    public SecurityRule createStation() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(STATION_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_STATIONS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readStation() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(STATION_API_PREFIX + STATION_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_STATIONS)
                .hasPermission(SecurityPermissions.EDIT_STATIONS)
                .hasPermission(SecurityPermissions.READ_STATIONS)
                .hasPermission(SecurityPermissions.DELETE_STATIONS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllStations() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(STATION_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_STATIONS)
                .hasPermission(SecurityPermissions.EDIT_STATIONS)
                .hasPermission(SecurityPermissions.READ_STATIONS)
                .hasPermission(SecurityPermissions.DELETE_STATIONS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateStation() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(STATION_API_PREFIX + STATION_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_STATIONS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteStation() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(STATION_API_PREFIX + STATION_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_STATIONS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}