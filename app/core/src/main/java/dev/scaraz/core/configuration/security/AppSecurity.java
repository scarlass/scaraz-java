package dev.scaraz.core.configuration.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AppSecurity {

    private KeycloakAuthenticationToken auth() {
        return (KeycloakAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
    }

    @SuppressWarnings("unchecked")
    public KeycloakPrincipal<KeycloakSecurityContext> getPrincipal() {
        return (KeycloakPrincipal<KeycloakSecurityContext>) auth().getPrincipal();
    }

    public AccessToken getToken() {
        return getPrincipal().getKeycloakSecurityContext().getToken();
    }

    public String getCurrentUsername() {
        return getToken().getPreferredUsername();
    }

}
