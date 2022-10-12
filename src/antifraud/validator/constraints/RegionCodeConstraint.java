package antifraud.validator.constraints;

import antifraud.validator.RegionCodeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = RegionCodeValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface RegionCodeConstraint {
    String message() default "Invalid region code";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
