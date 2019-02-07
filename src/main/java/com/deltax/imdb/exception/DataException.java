package com.deltax.imdb.exception;

import org.springframework.http.HttpStatus;

public class DataException extends Exception {

    private static final long serialVersionUID = 8393688636580014922L;

    private final String errorCode;

    private final String errorMessage;

    private final HttpStatus httpStatus;

    private final String errorCause;

    public DataException(final String errorCode, final String errorMessage, final HttpStatus httpStatus, final String errorCause )
    {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
        this.errorCause = errorCause;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getErrorCause() {
        return errorCause;
    }

}
