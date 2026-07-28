package sn.naavetane.backend.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategorieRule {

    @Bean
    public SecurityRule readCategories() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern("/api/categories")
                .authenticated(true)
                .build();
    }

    @Bean
    public SecurityRule createCategorie() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern("/api/categories")
                .authenticated(true)
                .build();
    }

    @Bean
    public SecurityRule updateCategorie() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern("/api/categories/**")
                .authenticated(true)
                .build();
    }

    @Bean
    public SecurityRule deleteCategorie() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern("/api/categories/**")
                .authenticated(true)
                .build();
    }

    @Bean
    public SecurityRule patchCategorieStock() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PATCH)
                .apiPattern("/api/categories/journee/**")
                .authenticated(true)
                .build();
    }
}
