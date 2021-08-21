package com.wayapay.payment.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;


@ControllerAdvice
public class CentralizedExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                                          HttpHeaders headers,
                                                                          HttpStatus status, WebRequest request) {

        logger.error("Missing servlet request parameter exception");
        String error = String.format("%s parameter is missing", ex.getParameterName());
        ApiError apiError = new ApiError(false, HttpStatus.BAD_REQUEST.value(), ex.getLocalizedMessage(), error);
        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
                                                                   HttpStatus status, WebRequest request) {
        logger.error("Url is not found");
        ApiError apiError = new ApiError(false, 404, "Sorry, invalid url");
        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.error("Method argument not valid");
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();


        List<String> errorString = new ArrayList<>();
        fieldErrors.forEach(fieldError -> {
            errorString.add(fieldError.getField() + ":" + fieldError.getDefaultMessage());
        });


        ApiError apiError = new ApiError(false, 400, "Error Processing your request", errorString);
        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler({NullPointerException.class})
    public ResponseEntity<Object> handleAccountNullPointerException(NullPointerException ex, WebRequest request) {
        String error = ex.getMessage();
        ApiError apiError = new ApiError(false, 400, "Resource not found", error);
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler({SocketTimeoutException.class})
    public ResponseEntity<Object> handleSocketTimeoutException(SocketTimeoutException ex, WebRequest request) {
        String error = ex.getMessage();
        ApiError apiError = new ApiError(false, 500,
                "Oops, sorry an error occurred, please try again later", error);
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler({ConnectException.class})
    public ResponseEntity<Object> handleConnectException(ConnectException ex, WebRequest request) {
        String error = ex.getMessage();
        ApiError apiError = new ApiError(false, 400, error);
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Object> illegalArgumentException(RuntimeException ex, WebRequest request) {
        String error = ex.getMessage();
        ApiError apiError = new ApiError(false, 500, error);
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }





    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiError error = new ApiError(false, status.value(), "Sorry, an error occurred, please try again later");
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }






    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleNoSuchElementException(Exception ex, WebRequest request) {
        String error = ex.getMessage();
        ApiError apiError = new ApiError(false, 500, "Error Processing your request", error);
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }
}
