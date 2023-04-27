package integracionapp.psgtrading.service;

import integracionapp.psgtrading.configuration.JwtConfig;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

/**
 * Clase utilitaria para obtener el tenant del usuario autenticado.
 */
public final class AuthorizationService {

    private AuthorizationService() {}

    public static String getTenant() {
        JwtAuthenticationToken token = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        String tenant = ((Jwt) token.getPrincipal()).getClaim(JwtConfig.TENANT_CLAIM);
        if (null == tenant) {
            throw new IllegalStateException("Tenant no informado en token de sesión");
        }
        return tenant;
    }

    public static String getUserIdFromToken() {
        JwtAuthenticationToken token = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        String userId = ((Jwt) token.getPrincipal()).getSubject();
        if (null == userId) {
            throw new IllegalStateException("User no informado en token de sesión");
        }
        return userId;
    }
}
