package com.wayapay.payment.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Arrays;
import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiError {
    private boolean status;
    private String message;
    private int code;
    private List<String> errors;


    public ApiError(boolean status, int code, String message, List<String> errors) {
        super();
        this.status = status;
        this.code = code;
        this.message = message;
        this.errors = errors;
    }

    public ApiError(boolean status, int code, String message,  String error) {
        super();
        this.status = status;
        this.code = code;
        this.message = message;
        this.errors = Arrays.asList(error);
    }

    public ApiError(boolean status, int code, String message) {
        super();
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
