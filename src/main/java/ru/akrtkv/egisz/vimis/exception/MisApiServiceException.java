package ru.akrtkv.egisz.vimis.exception;

public class MisApiServiceException extends RuntimeException {

    public MisApiServiceException() {
    }

    public MisApiServiceException(String message) {
        super(message);
    }

    public MisApiServiceException(Throwable cause) {
        super(cause);
    }
}
