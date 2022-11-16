package com.easyticket.corebusiness.validation.exist_in_db.should_exist_in_db;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = ShouldExistInDataBaseValidator.class)
@Target(ElementType.FIELD)
@Retention(RUNTIME)
public @interface ShouldExistInDataBase {

    String message() default "The {field} in {entity} should exist in database!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<?> entity();

    String field();

}
