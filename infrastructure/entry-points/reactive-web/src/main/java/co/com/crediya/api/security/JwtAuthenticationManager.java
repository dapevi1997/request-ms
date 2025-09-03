package co.com.crediya.api.security;

import co.com.crediya.api.security.util.JwtService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {
    private final JwtService jwtService;

    public JwtAuthenticationManager(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.just(authentication)
                .flatMap(auth -> {
                    try {
                        var claims = jwtService.extractAllClaims(auth.getCredentials().toString());
                        List<String> roles = claims.get("roles", List.class);
                        var authorities = roles.stream()
                                .map(SimpleGrantedAuthority::new)
                                .toList();

                        return Mono.just(new UsernamePasswordAuthenticationToken(
                                claims.getSubject(),
                                null,
                                authorities
                        ));
                    } catch (Exception e) {
                        return Mono.error(new BadCredentialsException("Invalid Token"));
                    }
                });
    }
}
