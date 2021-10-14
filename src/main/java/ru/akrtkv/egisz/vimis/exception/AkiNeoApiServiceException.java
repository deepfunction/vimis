package ru.akrtkv.egisz.vimis.exception;

public class AkiNeoApiServiceException extends RuntimeException {

    public AkiNeoApiServiceException() {
    }

    public AkiNeoApiServiceException(String message) {
        super(message);
    }

    public AkiNeoApiServiceException(Throwable cause) {
        super(cause);
    }
}
