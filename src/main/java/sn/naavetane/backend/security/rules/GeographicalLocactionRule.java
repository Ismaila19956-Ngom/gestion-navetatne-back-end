package sn.naavetane.backend.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import sn.naavetane.backend.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GeographicalLocactionRule {
    static final String  Geographical_Locations_API_PREFIX = "/geographicalLocations";
    static final String Geographical_Locations_ID = "/{geographicalLocationsId}";

    static final String REPORTING_BY_REGION ="/reportingByRegion";
    static final String REGION_ID ="/{regionId}";

    @Bean
    public SecurityRule createGeographicalLocations() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(Geographical_Locations_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_FUNDING_TYPE)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readGeographicalLocations() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(Geographical_Locations_API_PREFIX + Geographical_Locations_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_FUNDING_TYPE)
                .hasPermission(SecurityPermissions.ADD_FUNDING_TYPE)
                .hasPermission(SecurityPermissions.EDIT_FUNDING_TYPE)
                .hasPermission(SecurityPermissions.DELETE_FUNDING_TYPE)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readGeographicalAllLocations() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(Geographical_Locations_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_FUNDING_TYPE)
                .hasPermission(SecurityPermissions.ADD_FUNDING_TYPE)
                .hasPermission(SecurityPermissions.EDIT_FUNDING_TYPE)
                .hasPermission(SecurityPermissions.DELETE_FUNDING_TYPE)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateGeographicalLocations() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(Geographical_Locations_API_PREFIX + Geographical_Locations_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_FUNDING_TYPE)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule deleteGeographicalLocations() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(Geographical_Locations_API_PREFIX + Geographical_Locations_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_FUNDING)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule getReportingByRegion() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(Geographical_Locations_API_PREFIX+ REPORTING_BY_REGION +REGION_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_TDB_RESUME)
                .hasPermission(SecurityPermissions.READ_PROJECT_SETTINGS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}
