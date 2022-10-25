package dev.scaraz.common.exceptions;


import org.zalando.problem.Status;

public final class NotFoundException extends ScException {

    public NotFoundException(String title, String message) {
        super(title, Status.NOT_FOUND, message);
    }

    public NotFoundException(String message) {
        this("Not Found", message);
    }

}
