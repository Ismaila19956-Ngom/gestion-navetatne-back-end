package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProcedureNominationRule {
    static final String PROCEDURENOMINATIONS_API_PREFIX = "/procedurenominations";
    static final String PROCEDURENOMINATION_ID = "/{procedurenominationId}";

    @Bean
    public SecurityRule createProcedureNomination() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(PROCEDURENOMINATIONS_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readProcedureNomination() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(PROCEDURENOMINATIONS_API_PREFIX + PROCEDURENOMINATION_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllProcedureNomination() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(PROCEDURENOMINATIONS_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateProcedureNomination() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(PROCEDURENOMINATIONS_API_PREFIX + PROCEDURENOMINATION_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule deleteProcedureNomination() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(PROCEDURENOMINATIONS_API_PREFIX + PROCEDURENOMINATION_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

}
