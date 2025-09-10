package com.sazzad.event_ticket.config;

import com.sazzad.event_ticket.filters.UserProvisioningFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain (
            HttpSecurity httpSecurity,
            UserProvisioningFilter userProvisioningFilter,
            JwtAuthenticationConverter jwtAuthenticationConverter) throws Exception {

        httpSecurity
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers(HttpMethod.GET, "/api/v1/published-events/**").permitAll()
                                .requestMatchers("/api/v1/events").hasAnyRole("ORGANIZER")
                                .requestMatchers("/api/v1/ticket-validation").hasAnyRole("STAFF")

                                //catch all rule
                                .anyRequest().authenticated()
                )

                .csrf(csrf -> csrf.disable())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt( jwt ->
                                jwt.jwtAuthenticationConverter(jwtAuthenticationConverter)
                        ))

                .addFilterAfter(userProvisioningFilter, BearerTokenAuthenticationFilter.class);

        return httpSecurity.build();
    }
}
