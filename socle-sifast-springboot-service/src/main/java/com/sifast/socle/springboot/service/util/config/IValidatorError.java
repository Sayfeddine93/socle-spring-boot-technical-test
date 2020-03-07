package com.sifast.socle.springboot.service.util.config;

import java.util.stream.Collectors;

import org.springframework.validation.BindingResult;


public interface IValidatorError {

    static String getValidatorErrors(BindingResult bindingResult) {
        return bindingResult.getAllErrors().stream().map(x -> x.getDefaultMessage()).collect(Collectors.joining(", "));
    }
}
