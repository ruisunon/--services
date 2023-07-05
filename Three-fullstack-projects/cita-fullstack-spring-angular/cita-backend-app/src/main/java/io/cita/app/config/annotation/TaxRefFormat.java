package io.cita.app.config.annotation;

import io.cita.app.common.TaxRefConstraintValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(value = {
		ElementType.ANNOTATION_TYPE, 
		ElementType.TYPE, 
		ElementType.FIELD, 
		ElementType.METHOD, 
		ElementType.PARAMETER
})
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TaxRefConstraintValidator.class)
public @interface TaxRefFormat {
	
	// TODO: replace default msg with '{TaxRefFormat.invalid}' where it can be read from property files
	String message() default "Tax reference does not match critereas, Re-check";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
	
}



