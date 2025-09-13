package co.com.crediya.api.security;

import co.com.crediya.api.dto.ErrorResponseDto;
import co.com.crediya.api.util.Constantes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.ViewResolver;

import java.util.List;

import static co.com.crediya.api.security.util.Roles.ASESOR;
import static co.com.crediya.api.security.util.Roles.CLIENT;


@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    private final SecurityContextRepository securityContextRepository;

    public SecurityConfig(SecurityContextRepository securityContextRepository) {
        this.securityContextRepository = securityContextRepository;
    }

    private static final ServerResponse.Context DEFAULT_CONTEXT = new ServerResponse.Context() {
        @Override
        public List<HttpMessageWriter<?>> messageWriters() {
            return HandlerStrategies.withDefaults().messageWriters();
        }

        @Override
        public List<ViewResolver> viewResolvers() {
            return HandlerStrategies.withDefaults().viewResolvers();
        }
    };

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http){
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .securityContextRepository(securityContextRepository)
                .exceptionHandling(exceptions -> {
                    exceptions.authenticationEntryPoint((exchange, ex) ->
                            ServerResponse.status(HttpStatus.UNAUTHORIZED)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .bodyValue(ErrorResponseDto.builder()
                                            .httpStatus(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                                            .message(ex.getMessage())
                                            .build())
                                    .flatMap(res -> res.writeTo(exchange, DEFAULT_CONTEXT))
                    );
                    exceptions.accessDeniedHandler((exchange, ex) ->
                            ServerResponse.status(HttpStatus.FORBIDDEN)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .bodyValue(ErrorResponseDto.builder()
                                            .httpStatus(HttpStatus.FORBIDDEN.getReasonPhrase())
                                            .message(ex.getMessage())
                                            .build())
                                    .flatMap(res -> res.writeTo(exchange, DEFAULT_CONTEXT))
                    );
                })
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/api/v1/login/**").permitAll()
                        .pathMatchers("/swagger-docs/**", "/api-docs/**", "/webjars/**", "/swagger-ui/**").permitAll()
                        .pathMatchers("/actuator/health").permitAll()
                        .pathMatchers(HttpMethod.POST, Constantes.URL_SOLICITAR_CREDITO).hasAnyRole(CLIENT.name())
                        .pathMatchers(HttpMethod.GET, Constantes.URL_SOLICITAR_CREDITO).hasAnyRole(ASESOR.name())
                        .pathMatchers(HttpMethod.PUT, Constantes.URL_SOLICITAR_CREDITO).hasAnyRole(ASESOR.name())
                        .anyExchange().authenticated()
                )
                .build();
    }
}
