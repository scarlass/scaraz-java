package dev.scaraz.core.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.zalando.problem.spring.web.advice.ProblemHandling;
import org.zalando.problem.spring.web.advice.security.SecurityAdviceTrait;

import java.net.URI;

@Slf4j
@ControllerAdvice
public class ExceptionHandling implements ProblemHandling, SecurityAdviceTrait {

    private static final URI DEFAULT_URI = URI.create("https://scaraz.dev/error-reference");

    @Override
    public URI defaultConstraintViolationType() {
        return DEFAULT_URI;
    }

}
