package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResolveChannelRule {
    static final String RESOLVE_CHANNEL_PREFIX = "/resolveChannels";
    static final String RESOLVE_CHANNEL_ID = "/{resolveChannelId}";

//    static  final String RESOLVE_CHANNEL_DOWNLOAD_FILE="/{id}/_download";
//
//   static final String RESOLVE_CHANNEL_IMPORT ="/import";
//   static final String RESOLVE_CHANNEL_EXPORT ="/export";



    @Bean
    public SecurityRule createResolveChannel() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(RESOLVE_CHANNEL_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_RESOLVE_CHANNEL)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readResolveChannel() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(RESOLVE_CHANNEL_PREFIX + RESOLVE_CHANNEL_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_RESOLVE_CHANNEL)
                .hasPermission(SecurityPermissions.DELETE_RESOLVE_CHANNEL)
                .hasPermission(SecurityPermissions.EDIT_RESOLVE_CHANNEL)
                .hasPermission(SecurityPermissions.EDIT_RESOLVE_CHANNEL)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readResolveChannels() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(RESOLVE_CHANNEL_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_RESOLVE_CHANNEL)
                .hasPermission(SecurityPermissions.DELETE_RESOLVE_CHANNEL)
                .hasPermission(SecurityPermissions.EDIT_RESOLVE_CHANNEL)
                .hasPermission(SecurityPermissions.EDIT_RESOLVE_CHANNEL)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateResolveChannel() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(RESOLVE_CHANNEL_PREFIX + RESOLVE_CHANNEL_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_RESOLVE_CHANNEL)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

   

  

 
}
