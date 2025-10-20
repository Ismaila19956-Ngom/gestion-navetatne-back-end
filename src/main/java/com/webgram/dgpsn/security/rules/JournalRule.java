package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JournalRule {
    static final String JOURNAL_API_PREFIX = "/journals";

    @Bean
    public SecurityRule readAllJournal() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(JOURNAL_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_LOG)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }


}
