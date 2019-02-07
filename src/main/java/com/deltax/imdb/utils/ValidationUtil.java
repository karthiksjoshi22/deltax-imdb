package com.deltax.imdb.utils;

import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

public class ValidationUtil {

    private ValidationUtil()
    {

    }

    /**
     * Returns error message from MethodArgumentNotValidException
     * @param errors
     * @return
     */
    public static List<String> fromBindingErrors(Errors errors)
    {
        List<String> validErrors = new ArrayList<>();
        for (ObjectError objectError : errors.getAllErrors())
        {
            validErrors.add(objectError.getDefaultMessage());
        }
        return validErrors;
    }

    public static String replaceMetaCharForLike(String searchParam)
    {
        searchParam=searchParam.replace("_","\\_").replace("%","\\%");
        return searchParam;
    }
}
