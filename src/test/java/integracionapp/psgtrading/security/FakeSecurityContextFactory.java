package integracionapp.psgtrading.security;

import integracionapp.psgtrading.configuration.JwtConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.time.Instant;

public class FakeSecurityContextFactory implements WithSecurityContextFactory<FakeSecurityContext> {

    @Autowired
    private JwtEncoder jwtEncoder;

    @Override
    public SecurityContext createSecurityContext(FakeSecurityContext annotation) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Instant expiration = Instant.now().plusMillis(300_000);
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(String.valueOf(annotation.userId()))
                .expiresAt(expiration)
                .claim("scope", "USER")
                .claim(JwtConfig.TENANT_CLAIM, annotation.tenant())
                .build();
        JwsHeader jwsHeader = JwsHeader.with(JwtConfig.ALGORITHM).build();
        Jwt jwt = jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims));
        context.setAuthentication(new JwtAuthenticationToken(jwt));
        return context;
    }
}
