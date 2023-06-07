package es.uv.andoni.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http)
    throws Exception {
    http
      .csrf()
      .disable()
      .cors()
      .disable()
      .httpBasic()
      .and()
      .authorizeHttpRequests()
      .requestMatchers("/actuator/health")
      .permitAll()
      .requestMatchers("/**")
      .authenticated();

    return http.build();
  }
}