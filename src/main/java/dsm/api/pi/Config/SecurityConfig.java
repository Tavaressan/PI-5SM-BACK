package dsm.api.pi.Config;


import com.fasterxml.jackson.databind.ObjectMapper;
import dsm.api.pi.Exception.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.time.LocalDateTime;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    FilterUserConfig filterUserConfig;

    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/barbeiro").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/barbeiro").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/servicos").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/servicos/hoje").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/barbeiro/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/servicos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/barbeiros").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/barbeiros").hasRole("UNIDADE")
                        .requestMatchers(HttpMethod.POST, "/servico").hasRole("UNIDADE")
                )
                .exceptionHandling(ex -> ex
                        // 401 - sem autenticação (token ausente/inválido)
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(HttpStatus.UNAUTHORIZED.value());
                            response.setContentType("application/json;charset=UTF-8");

                            ErrorResponse body = ErrorResponse.builder()
                                    .status(401)
                                    .error("Não Autorizado")
                                    .message("Token ausente ou inválido. Faça login novamente.")
                                    .path(request.getRequestURI())
                                    .timestamp(LocalDateTime.now())
                                    .build();

                            response.getWriter().write(objectMapper.writeValueAsString(body));
                        })

                        // 403 - autenticado, mas sem permissão
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setStatus(HttpStatus.FORBIDDEN.value());
                            response.setContentType("application/json;charset=UTF-8");

                            ErrorResponse body = ErrorResponse.builder()
                                    .status(403)
                                    .error("Acesso Negado")
                                    .message("Você não tem permissão para acessar este recurso.")
                                    .path(request.getRequestURI())
                                    .timestamp(LocalDateTime.now())
                                    .build();

                            response.getWriter().write(objectMapper.writeValueAsString(body));
                        })
                )
                .addFilterBefore(filterUserConfig, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
