package sn.naavetane.backend.security.rules;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import sn.naavetane.backend.security.SecurityPermissions;

@Component
public class PortailRule {

    @Bean
    public SecurityRule readPortailDashboard() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern("/api/portail/dashboard")
                .authenticated(false) // Public API
                .build();
    }

    @Bean
    public SecurityRule readPortailSliders() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern("/api/portail/sliders")
                .authenticated(false) // Public API
                .build();
    }

    @Bean
    public SecurityRule readPortailSocials() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern("/api/portail/socials")
                .authenticated(false) // Public API
                .build();
    }

    @Bean
    public SecurityRule readPortailActualites() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern("/api/portail/actualites")
                .authenticated(false) // Public API
                .build();
    }

    @Bean
    public SecurityRule readPortailNewEntities() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern("/api/portail/textes")
                .apiPattern("/api/portail/organisation")
                .apiPattern("/api/portail/faqs")
                .apiPattern("/api/portail/contacts")
                .authenticated(false) // Public API
                .build();
    }
    
    // --- ADMIN (CMS) RULES ---
    
    @Bean
    public SecurityRule readAllPortail() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern("/api/portail/sliders/all")
                .apiPattern("/api/portail/actualites/all")
                .apiPattern("/api/portail/textes/all")
                .apiPattern("/api/portail/organisation/all")
                .apiPattern("/api/portail/faqs/all")
                .apiPattern("/api/portail/contacts/all")
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .hasPermission(SecurityPermissions.READ_STRUCTURE)
                .end();
    }

    @Bean
    public SecurityRule writePortail() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern("/api/portail/sliders")
                .apiPattern("/api/portail/actualites")
                .apiPattern("/api/portail/socials")
                .apiPattern("/api/portail/textes")
                .apiPattern("/api/portail/organisation")
                .apiPattern("/api/portail/faqs")
                .apiPattern("/api/portail/contacts")
                .apiPattern("/api/portail/upload")
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .hasPermission(SecurityPermissions.READ_STRUCTURE)
                .end();
    }
    
    @Bean
    public SecurityRule deletePortail() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern("/api/portail/sliders/{id}")
                .apiPattern("/api/portail/actualites/{id}")
                .apiPattern("/api/portail/socials/{id}")
                .apiPattern("/api/portail/textes/{id}")
                .apiPattern("/api/portail/organisation/{id}")
                .apiPattern("/api/portail/faqs/{id}")
                .apiPattern("/api/portail/contacts/{id}")
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .hasPermission(SecurityPermissions.READ_STRUCTURE)
                .end();
    }
}
