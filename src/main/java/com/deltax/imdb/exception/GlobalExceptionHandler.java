package com.deltax.imdb.exception;

import com.deltax.imdb.dto.response.UIErrorMessage;
import com.deltax.imdb.utils.ValidationUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import javax.validation.ConstraintViolationException;

@Component
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<UIErrorMessage> handle(ConstraintViolationException exception)
    {
        final UIErrorMessage uiErrorMessage = new UIErrorMessage();
        uiErrorMessage.setCode(1400);
        uiErrorMessage.setStatus("FAIL");
        uiErrorMessage.setCause(exception.getMessage());
        uiErrorMessage.setMessage("Entered value should be greater than or equal to 1");

        return new ResponseEntity<>(uiErrorMessage,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { MethodArgumentNotValidException.class })
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<UIErrorMessage> handleConflict(MethodArgumentNotValidException ex) {

        BindingResult bindingResult = ex.getBindingResult();
        final UIErrorMessage uiErrorMessage = new UIErrorMessage();
        uiErrorMessage.setCode(1400);
        uiErrorMessage.setStatus("FAIL");
        uiErrorMessage.setCause(ex.getMessage());
        uiErrorMessage.setMessage(ValidationUtil.fromBindingErrors(bindingResult).toString().replaceAll("\\[","").replaceAll("\\]",""));

        return new ResponseEntity<>(uiErrorMessage,HttpStatus.BAD_REQUEST);
    }
}
