package dev.scaraz.common.exceptions;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;
import java.sql.Timestamp;

public class ScException extends AbstractThrowableProblem {

    public static final URI DEFAULT_TYPE = URI.create("https://scaraz.dev/error-reference");

    private final Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    public ScException(String title, Status status, String message) {
        super(DEFAULT_TYPE, title, status, message);
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
}
