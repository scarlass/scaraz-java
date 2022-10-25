package dev.scaraz.common.exceptions;


import org.zalando.problem.Status;

public final class BadRequestException extends ScException {

    public BadRequestException(String title, String message) {
        super(title, Status.NOT_FOUND, message);
    }

    public BadRequestException(String message) {
        this("Bad Request", message);
    }

}
