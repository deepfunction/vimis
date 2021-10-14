package ru.akrtkv.egisz.vimis.exception;

public class SoapMessageServiceException extends RuntimeException {

    public SoapMessageServiceException() {
    }

    public SoapMessageServiceException(String message) {
        super(message);
    }

    public SoapMessageServiceException(Throwable cause) {
        super(cause);
    }
}
