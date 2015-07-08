package com.bizislife.util;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.bizislife.util.annotation.ValidEmail;


public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

    @Override
    public void initialize(ValidEmail constraintAnnotation) {
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
    	
        return (ValidationSet.isValidEmail(email));
    }

}