package dev.scaraz.http;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.mock.env.MockEnvironment;

@Slf4j
public class SpringTest {

    @Test
    public void test() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        MockEnvironment env = new MockEnvironment();
        env.setActiveProfiles("test");
        env.setProperty("scaraz.http.enabled", "true");

        ctx.setEnvironment(env);
        ctx.scan("dev.scaraz.http", "dev.scaraz.http.interfaces");
        ctx.refresh();

    }

}
