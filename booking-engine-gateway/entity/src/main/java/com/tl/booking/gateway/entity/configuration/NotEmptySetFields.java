package com.tl.booking.gateway.entity.configuration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotEmptySetFieldsValidator.class)
public @interface NotEmptySetFields {

  String message() default "Set cannot contain empty fields";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

}
