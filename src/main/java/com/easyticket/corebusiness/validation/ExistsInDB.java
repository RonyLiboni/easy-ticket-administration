package com.easyticket.corebusiness.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = ExistsInDBValidator.class)
@Target(FIELD)
@Retention(RUNTIME)
public @interface ExistsInDB {

    String message() default "The {field} in {entity} doesn't exist";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<?> entity();

    String field();

}
