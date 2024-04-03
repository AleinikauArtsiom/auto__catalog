package com.auto_catalog.auto__catalog.api.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionHandlerGlobal {


    @ExceptionHandler(value = {CustomValidException.class, jakarta.validation.ValidationException.class})
    public ModelAndView customValidExceptionHandler(Exception exception, HttpServletRequest httpServletRequest){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("error", exception);
        modelAndView.addObject("url", httpServletRequest.getRequestURI());
        modelAndView.setViewName("failure");
        modelAndView.setStatus(HttpStatusCode.valueOf(400));
        System.out.println(exception);
        return modelAndView;
    }
}
