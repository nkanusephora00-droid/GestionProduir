package com.example.demo.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;
import java.lang.annotation.ElementType;

/**
 * Validateur personnalisé pour les emails avec règles plus strictes
 */
public class EmailValidator implements ConstraintValidator<ValidEmail, String> {
    
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );
    
    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        
        // Longueur maximale pour les emails
        if (email.length() > 254) {
            return false;
        }
        
        // Validation avec regex
        return EMAIL_PATTERN.matcher(email.trim()).matches();
    }
}
