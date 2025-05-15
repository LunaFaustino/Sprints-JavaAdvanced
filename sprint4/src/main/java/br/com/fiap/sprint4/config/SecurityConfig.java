package br.com.fiap.sprint4.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
                        .requestMatchers("/login").permitAll() // Garantir acesso à página de login

                        .requestMatchers("/clinicas/novo", "/clinicas/*/editar", "/clinicas/*/excluir").hasRole("ADMIN")
                        .requestMatchers("/dentistas/novo", "/dentistas/*/editar", "/dentistas/*/excluir").hasRole("ADMIN")
                        .requestMatchers("/pacientes/novo", "/pacientes/*/editar", "/pacientes/*/excluir").hasRole("ADMIN")
                        .requestMatchers("/enderecos/novo", "/enderecos/*/editar", "/enderecos/*/excluir").hasRole("ADMIN")
                        .requestMatchers("/usuarios/**").hasRole("ADMIN")

                        .requestMatchers("/", "/clinicas", "/clinicas/*",
                                "/dentistas", "/dentistas/*",
                                "/pacientes", "/pacientes/*",
                                "/enderecos", "/enderecos/*").hasAnyRole("USER", "ADMIN")

                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );

        return http.build();
    }
}