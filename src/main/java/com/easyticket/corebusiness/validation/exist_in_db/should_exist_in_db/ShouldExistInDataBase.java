package com.easyticket.corebusiness.validation.exist_in_db.should_exist_in_db;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = ShouldExistInDataBaseValidator.class)
@Target(FIELD)
@Retention(RUNTIME)
public @interface ShouldExistInDataBase {

    String message() default "The {field} in {entity} should exist in database!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<?> entity();

    String field();

}
