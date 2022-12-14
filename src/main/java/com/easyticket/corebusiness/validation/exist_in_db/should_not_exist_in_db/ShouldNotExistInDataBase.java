package com.easyticket.corebusiness.validation.exist_in_db.should_not_exist_in_db;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = ShouldNotExistInDataBaseValidator.class)
@Target(FIELD)
@Retention(RUNTIME)
public @interface ShouldNotExistInDataBase {

    String message() default "The {field} in {entity} should not exist in database!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<?> entity();

    String field();

}
