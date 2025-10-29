package com.webgram.dgpsn.configurations;

import com.webgram.dgpsn.repositories.UserRepository;
import com.webgram.dgpsn.security.WebSecurityManager;
import com.webgram.dgpsn.security.jwt.JWTConfigurer;
import com.webgram.dgpsn.security.jwt.JWTFilter;
import com.webgram.dgpsn.security.jwt.TokenProvider;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@Profile(value = "!test")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration {
    final TokenProvider tokenProvider;
    final UserRepository userRepository;
    final CorsFilter corsFilter;
    final WebSecurityManager webSecurityManager;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(authz -> authz
                                .requestMatchers(
                                        "/v2/api-docs",
                                        "/configuration/ui",
                                        "/swagger-resources/**",
                                        "/swagger-ui/**",
                                        "/v3/api-docs",
                                        "/v3/api-docs/**",
                                        "/configuration/security",
                                        "/swagger-ui.html",
                                        "/manage/**",
                                        "/api/*/v3/api-docs",
                                        "/api/v3/api-docs/**",
                                        "/api/*/v3/api-docs/**",
                                        "/webjars/springfox-swagger-ui/**",
                                        "/api/*/v2/api-docs",
                                        "/auth/**"
                                ).permitAll()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);

        webSecurityManager.manageSecurityRules(http);

        http.authorizeHttpRequests(authz -> authz.anyRequest().denyAll());

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private JWTFilter jwtFilter() {
        return new JWTFilter(tokenProvider, userRepository);
    }

    private JWTConfigurer securityConfigurerAdapter() {
        return new JWTConfigurer(tokenProvider, userRepository);
    }
}
