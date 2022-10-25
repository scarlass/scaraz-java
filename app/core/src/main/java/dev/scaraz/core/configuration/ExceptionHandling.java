package dev.scaraz.core.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.zalando.problem.Problem;
import org.zalando.problem.spring.web.advice.ProblemHandling;
import org.zalando.problem.spring.web.advice.security.SecurityAdviceTrait;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

@Slf4j
@ControllerAdvice
public class ExceptionHandling implements ProblemHandling, SecurityAdviceTrait {

    private static final URI DEFAULT_URI = URI.create("https://scaraz.dev/error-reference");

    @Override
    public URI defaultConstraintViolationType() {
        return DEFAULT_URI;
    }

    @Override
    public void log(Throwable throwable, Problem problem, NativeWebRequest nativeReq, HttpStatus status) {
        HttpServletRequest req = ((ServletWebRequest) nativeReq).getRequest();
        if (status.is4xxClientError()) {
            log.warn("{} {} {} - {}",
                    req.getMethod(),
                    req.getRequestURI(),
                    status.value(),
                    problem.getDetail());
        }
        else if (status.is5xxServerError()) {
            throwable.printStackTrace();
            log.warn("{} {} {} - {}",
                    req.getMethod(),
                    req.getRequestURI(),
                    status.value(),
                    problem.getDetail());
        }
    }
}
