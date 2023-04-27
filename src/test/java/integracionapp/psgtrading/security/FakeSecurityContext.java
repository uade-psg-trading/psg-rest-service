package integracionapp.psgtrading.security;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = FakeSecurityContextFactory.class)
public @interface FakeSecurityContext {
    String tenant();
    long userId() default 1;
}
