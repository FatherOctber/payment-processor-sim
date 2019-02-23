package com.fatheroctober.ppsim.customerservice.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomerControllerAdvice {

    private static final Logger logger = LoggerFactory.getLogger(CustomerControllerAdvice.class);

    @ExceptionHandler(value = {Exception.class})
    public DefaultResponse handleGeneralError(Exception e) {
        logger.error("General error", e);
        return DefaultResponse.generalError();
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public DefaultResponse handleBadFormatError(IllegalArgumentException e) {
        logger.error("Bad request error", e);
        return DefaultResponse.badRequestError();
    }
}
