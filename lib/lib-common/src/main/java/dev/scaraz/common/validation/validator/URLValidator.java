package dev.scaraz.common.validation.validator;

import dev.scaraz.common.validation.constraint.IsURL;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.net.URL;

public class URLValidator implements ConstraintValidator<IsURL, String> {

    boolean nullable = true;

    @Override
    public void initialize(IsURL a) {
        this.nullable = a.nullable();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext ctx) {
        if (nullable && value == null) return true;

        try {
            URL url = new URL(value);
            url.toURI();
            return true;
        }
        catch (Exception ex) {
        }
        return false;
    }

}
