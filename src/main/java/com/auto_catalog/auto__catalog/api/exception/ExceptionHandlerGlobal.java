package com.auto_catalog.auto__catalog.api.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
@Slf4j
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

    @ExceptionHandler(value = {UsernameNotFoundException.class})
    public HttpStatus usernameNotFoundException(Exception exception){
        log.error("Username not found: " + exception);
        return HttpStatus.valueOf(401);
    }
}
