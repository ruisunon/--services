package io.cita.app.common;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import io.cita.app.config.annotation.TaxRefFormat;

@Component
public class TaxRefConstraintValidator implements ConstraintValidator<TaxRefFormat, String> {
	
	@Override
	public boolean isValid(@NonNull String taxRef, ConstraintValidatorContext context) {
		return !taxRef.isBlank() 
				&& taxRef.length() == 8
				&& taxRef.matches("[0-9][0-9][0-9][0-9][0-9][0-9][0-9][A-Z]");
	}
	
}



