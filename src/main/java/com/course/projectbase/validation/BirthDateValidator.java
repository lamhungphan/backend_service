package com.course.projectbase.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Date;

public class BirthDateValidator implements ConstraintValidator<ValidBirthDate, Date> {
    @Override
    public boolean isValid(Date birthDate, ConstraintValidatorContext context) {
        return birthDate != null && birthDate.before(new Date());
    }
}
