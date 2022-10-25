package dev.scaraz.gateway.configuration;

import dev.scaraz.common.exceptions.ScException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.zalando.problem.spring.webflux.advice.ProblemHandling;
import org.zalando.problem.spring.webflux.advice.security.SecurityAdviceTrait;

import java.net.URI;

@Slf4j
@ComponentScan
@ControllerAdvice
public class ExceptionHandling implements ProblemHandling, SecurityAdviceTrait {

    @Override
    public URI defaultConstraintViolationType() {
        return ScException.DEFAULT_TYPE;
    }

    @Override
    public boolean isCausalChainsEnabled() {
        return false;
    }

}
