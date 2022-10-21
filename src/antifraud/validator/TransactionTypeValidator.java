package antifraud.validator;

import antifraud.constant.RegionCode;
import antifraud.constant.TransactionType;
import antifraud.validator.constraints.TransactionTypeConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TransactionTypeValidator implements ConstraintValidator<TransactionTypeConstraint, String> {

    @Override
    public void initialize(TransactionTypeConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        return TransactionType.exists(value);
    }
}
