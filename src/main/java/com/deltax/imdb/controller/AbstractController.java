package com.deltax.imdb.controller;

import com.deltax.imdb.constants.GeneralConstants;
import com.deltax.imdb.dto.response.UIErrorMessage;
import com.deltax.imdb.dto.response.UIResponse;
import com.deltax.imdb.exception.DataException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

public class AbstractController {

    List<String> ddlErrors = Arrays.asList("(Unknown column).*","(Table ).*(doesn't exist)");

    protected <T> ResponseEntity<UIResponse<T>> buildResponse(final T t, String message)
    {
        final UIResponse<T> uiResponse = new UIResponse<>(t);
        uiResponse.setCode(GeneralConstants.VALID);
        uiResponse.setMessage(message);
        uiResponse.setStatus("OK");
        return new ResponseEntity<>(uiResponse,HttpStatus.OK);
    }

    public ResponseEntity<UIErrorMessage> buildError(final DataException e)
    {
        final UIErrorMessage message = new UIErrorMessage();
        message.setStatus(e.getErrorCode());
        message.setMessage(e.getErrorMessage());
        message.setCause(e.getErrorCause());

        if (!e.getErrorMessage().equalsIgnoreCase(GeneralConstants.SQL_EXCEPTIONS))
        {
            if (e.getHttpStatus().equals(HttpStatus.BAD_REQUEST)) {
                message.setCode(GeneralConstants.BAD_REQUEST);
                return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
            }
            if (e.getHttpStatus().equals(HttpStatus.FORBIDDEN)) {
                message.setCode(HttpStatus.FORBIDDEN.value());
                return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
            }
            if (e.getHttpStatus().equals(HttpStatus.NOT_FOUND)) {
                message.setCode(GeneralConstants.DATA_NOT_FOUND);
                return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
            }
            if (e.getHttpStatus().equals(HttpStatus.OK)) {
                message.setCode(GeneralConstants.NO_CONTENT);
                return new ResponseEntity<>(message, HttpStatus.OK);
            }
            if (e.getHttpStatus().equals(HttpStatus.UNPROCESSABLE_ENTITY)) {
                message.setCode(GeneralConstants.UN_PROCESSABLE_ENTITY);
                return new ResponseEntity<>(message, HttpStatus.UNPROCESSABLE_ENTITY);
            }
        }
        else
        {
            if (!ddlErrors.stream().filter(element->  e.getErrorCause().matches("^"+element+"$")).findAny().isPresent())
            {
                message.setCode(GeneralConstants.UN_PROCESSABLE_ENTITY);
                message.setMessage(GeneralConstants.DATA_INTEGRITY_EXCEPTION);
                return new ResponseEntity<>(message, HttpStatus.UNPROCESSABLE_ENTITY);
            }
        }
        message.setCode(GeneralConstants.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
