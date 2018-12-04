package com.tl.booking.promo.code.entity.configuration;

import java.util.Set;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotEmptySetFieldsValidator implements
    ConstraintValidator<NotEmptySetFields, Set<String>> {

  @Override
  public void initialize(NotEmptySetFields notEmptyFields) {
    // Do nothing because is void.
  }

  @Override
  public boolean isValid(Set<String> objects, ConstraintValidatorContext context) {
    return objects.stream().allMatch(nef -> !nef.trim().isEmpty());
  }

}
