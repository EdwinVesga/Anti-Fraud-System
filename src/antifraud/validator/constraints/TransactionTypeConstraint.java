package antifraud.validator.constraints;

import antifraud.validator.TransactionTypeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TransactionTypeValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface TransactionTypeConstraint {
    String message() default "Invalid transaction type";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
