package com.example.demo.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service de sécurité pour la gestion des mots de passe
 * Utilise BCrypt pour le hashage (algorithme recommandé par Spring Security)
 */
@Service
public class PasswordService {
    
    private final PasswordEncoder passwordEncoder;
    
    public PasswordService() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }
    
    /**
     * Hash un mot de passe en clair
     * @param passwordEnClair Le mot de passe à hasher
     * @return Le mot de passe hashé
     */
    public String hasherMotDePasse(String passwordEnClair) {
        if (passwordEnClair == null || passwordEnClair.trim().isEmpty()) {
            throw new IllegalArgumentException("Le mot de passe ne peut pas être vide");
        }
        return passwordEncoder.encode(passwordEnClair);
    }
    
    /**
     * Vérifie si un mot de passe en clair correspond au hash stocké
     * @param passwordEnClair Le mot de passe à vérifier
     * @param passwordHashé Le hash stocké en base de données
     * @return true si les mots de passe correspondent, false sinon
     */
    public boolean verifierMotDePasse(String passwordEnClair, String passwordHashé) {
        if (passwordEnClair == null || passwordHashé == null) {
            return false;
        }
        return passwordEncoder.matches(passwordEnClair, passwordHashé);
    }
    
    /**
     * Vérifie si un mot de passe respecte les critères de sécurité
     * @param password Le mot de passe à valider
     * @return true si le mot de passe est valide
     */
    public boolean validerMotDePasse(String password) {
        if (password == null) {
            return false;
        }
        
        // Critères de sécurité :
        // - Au moins 8 caractères
        // - Au moins une lettre majuscule
        // - Au moins une lettre minuscule  
        // - Au moins un chiffre
        // - Au moins un caractère spécial
        
        return password.length() >= 8
                && password.matches(".*[A-Z].*")  // Au moins une majuscule
                && password.matches(".*[a-z].*")  // Au moins une minuscule
                && password.matches(".*\\d.*")    // Au moins un chiffre
                && password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*"); // Au moins un caractère spécial
    }
    
    /**
     * Génère un mot de passe aléatoire sécurisé
     * @return Un mot de passe de 12 caractères
     */
    public String genererMotDePasseAleatoire() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";
        StringBuilder password = new StringBuilder();
        
        for (int i = 0; i < 12; i++) {
            int index = (int) (Math.random() * chars.length());
            password.append(chars.charAt(index));
        }
        
        return password.toString();
    }
    
    /**
     * Getteur pour le PasswordEncoder (utile pour les tests)
     * @return Le PasswordEncoder utilisé
     */
    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }
}
