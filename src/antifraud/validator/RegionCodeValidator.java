package antifraud.validator;


import antifraud.constant.RegionCode;
import antifraud.validator.constraints.RegionCodeConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RegionCodeValidator implements
        ConstraintValidator<RegionCodeConstraint, String> {

    @Override
    public void initialize(RegionCodeConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        return RegionCode.exists(value);
    }
}
