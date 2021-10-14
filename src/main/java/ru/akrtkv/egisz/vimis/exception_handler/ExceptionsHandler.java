package ru.akrtkv.egisz.vimis.exception_handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestClientException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.akrtkv.egisz.vimis.dto.rir.Error;
import ru.akrtkv.egisz.vimis.exception.*;
import ru.akrtkv.egisz.vimis.utils.ErrorCodes;

@ControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Error> handleValidationException(MethodArgumentNotValidException e) {
        var errorMessage = new StringBuilder();
        for (ObjectError error : e.getBindingResult().getAllErrors()) {
            String field = ((FieldError) error).getField();
            errorMessage.append(field).append(" ").append(error.getDefaultMessage()).append(", ");
        }
        return new ResponseEntity<>(
                new Error(
                        ErrorCodes.ERR_VALIDATION,
                        errorMessage.substring(0, errorMessage.toString().length() - 2)
                ),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Error> handleRuntimeException(RuntimeException e) {
        return new ResponseEntity<>(new Error(ErrorCodes.ERR_RUNTIME, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Error> handleMismatchedException(MethodArgumentTypeMismatchException e) {
        return new ResponseEntity<>(new Error(ErrorCodes.ERR_MISMATCHED, e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WebServiceException.class)
    public ResponseEntity<Error> handleWebServiceException(WebServiceException e) {
        return new ResponseEntity<>(new Error(ErrorCodes.ERR_EGISZ, e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConverterException.class)
    public ResponseEntity<Error> handleConverterException(ConverterException e) {
        return new ResponseEntity<>(new Error(ErrorCodes.ERR_CONVERTER, e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RestClientException.class)
    public ResponseEntity<Error> handleRestClientException(RestClientException e) {
        return new ResponseEntity<>(new Error(ErrorCodes.ERR_REST_CLIENT, e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SoapMessageServiceException.class)
    public ResponseEntity<Error> handleSoapMessageServiceException(SoapMessageServiceException e) {
        return new ResponseEntity<>(new Error(ErrorCodes.ERR_SOAP_MES_SERVICE, e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InterceptorException.class)
    public ResponseEntity<Error> handleInterceptorException(InterceptorException e) {
        return new ResponseEntity<>(new Error(ErrorCodes.ERR_INTERCEPTOR, e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DocumentServiceException.class)
    public ResponseEntity<Error> handleDocServiceException(DocumentServiceException e) {
        return new ResponseEntity<>(new Error(ErrorCodes.ERR_DOC_SERVICE, e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MisMoServiceException.class)
    public ResponseEntity<Error> handleMisMoServiceException(MisMoServiceException e) {
        return new ResponseEntity<>(new Error(ErrorCodes.ERR_MIS_MO_SERVICE, e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AkiNeoApiServiceException.class)
    public ResponseEntity<Error> handleAkiNeoApiServiceException(AkiNeoApiServiceException e) {
        return new ResponseEntity<>(new Error(ErrorCodes.ERR_AKINEO_API_SERVICE, e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MisApiServiceException.class)
    public ResponseEntity<Error> handleMisApiServiceException(MisApiServiceException e) {
        return new ResponseEntity<>(new Error(ErrorCodes.ERR_MIS_API_SERVICE, e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
