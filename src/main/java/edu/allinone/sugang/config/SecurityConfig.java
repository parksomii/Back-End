package edu.allinone.sugang.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/login/**").permitAll()
                        .requestMatchers("/notice/**").permitAll()
                        .requestMatchers("/faq/**").permitAll()
                        .anyRequest().authenticated()

                )
                .formLogin(login -> login
                        .defaultSuccessUrl("/index", true)
                        .permitAll()
                )
                .logout(Customizer.withDefaults());

        return http.build();
    }

}
