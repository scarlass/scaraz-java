package dev.scaraz.common.validation.constraint;

import dev.scaraz.common.validation.validator.URLValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Repeatable(IsURL.List.class)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = URLValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
public @interface IsURL {

    String message() default "{scaraz.validation.constraints.URI.message}";

    boolean nullable() default true;

    Class<? extends Payload>[] payload() default {};

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
    @interface List {
        IsURL[] value();
    }

}
