package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SectorRule {
    static final String SECTOR_PREFIX = "/sector";
    static final String SECTOR_ID = "/{sectorId}";

//    static  final String REVIEW_DOWNLOAD_FILE="/{id}/_download";
//
//   static final String REVIEW_IMPORT ="/import";
//   static final String REVIEW_EXPORT ="/export";



    @Bean
    public SecurityRule createSector() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(SECTOR_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_REVIEW)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readSector() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(SECTOR_PREFIX + SECTOR_ID )
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_SECTOR)
                .hasPermission(SecurityPermissions.READ_SECTOR)
                .hasPermission(SecurityPermissions. EDIT_SECTOR)
                .hasPermission(SecurityPermissions.DELETE_SECTOR)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readSectors() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(SECTOR_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_SECTOR)
                .hasPermission(SecurityPermissions.READ_SECTOR)
                .hasPermission(SecurityPermissions. EDIT_SECTOR)
                .hasPermission(SecurityPermissions.DELETE_SECTOR)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateSector() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(SECTOR_PREFIX + SECTOR_ID )
                .build()
                .condition()
                .hasPermission(SecurityPermissions. EDIT_SECTOR)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

   

  

 
}
