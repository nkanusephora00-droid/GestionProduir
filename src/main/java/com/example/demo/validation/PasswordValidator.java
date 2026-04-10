package com.example.demo.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * Validateur personnalisé pour les mots de passe
 * Utilise les mêmes règles que PasswordService mais dans la validation JSR-303
 */
public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {
    
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
        "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).*$"
    );
    
    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) {
            return false;
        }
        
        // Longueur minimale
        if (password.length() < 8 || password.length() > 100) {
            return false;
        }
        
        // Complexité avec regex
        return PASSWORD_PATTERN.matcher(password).matches();
    }
}
