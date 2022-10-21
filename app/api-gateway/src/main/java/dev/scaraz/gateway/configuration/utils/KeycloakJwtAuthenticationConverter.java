package dev.scaraz.gateway.configuration.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtGrantedAuthoritiesConverterAdapter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class KeycloakJwtAuthenticationConverter implements Converter<Jwt, Mono<AbstractAuthenticationToken>> {

    private static final String USERNAME_CLAIM = "preferred_username";

    private final Converter<Jwt, Flux<GrantedAuthority>> authoritiesConverter;

    public KeycloakJwtAuthenticationConverter(String clientId) {
        this.authoritiesConverter = new ReactiveJwtGrantedAuthoritiesConverterAdapter(
                new KeycloakGrantedAuthoritiesConverter(clientId)
        );
    }

    @Override
    public Mono<AbstractAuthenticationToken> convert(Jwt jwt) {
        return authoritiesConverter.convert(jwt)
                .collectList()
                .map(a -> new JwtAuthenticationToken(jwt, a, extractUsername(jwt)));
    }

    protected String extractUsername(Jwt jwt) {
        return jwt.hasClaim(USERNAME_CLAIM) ?
               jwt.getClaimAsString(USERNAME_CLAIM) :
               jwt.getSubject();
    }

}
