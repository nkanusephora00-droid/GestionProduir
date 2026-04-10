package com.example.demo.mapper;

import com.example.demo.dto.*;
import com.example.demo.model.Utilisateur;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper pour convertir entre les entités Utilisateur et les DTOs
 * Masque automatiquement les données sensibles
 */
@Component
public class UtilisateurMapper {
    
    /**
     * Convertit une entité Utilisateur en UtilisateurResponse (sans mot de passe)
     */
    public UtilisateurResponse toResponse(Utilisateur utilisateur) {
        if (utilisateur == null) {
            return null;
        }
        
        return new UtilisateurResponse(
            utilisateur.getId(),
            utilisateur.getNom(),
            utilisateur.getPrenom(),
            utilisateur.getEmail()
        );
    }
    
    /**
     * Convertit une entité Utilisateur en UtilisateurResponse avec message
     */
    public UtilisateurResponse toResponse(Utilisateur utilisateur, String message) {
        if (utilisateur == null) {
            return new UtilisateurResponse(null, null, null, null, message);
        }
        
        return new UtilisateurResponse(
            utilisateur.getId(),
            utilisateur.getNom(),
            utilisateur.getPrenom(),
            utilisateur.getEmail(),
            message
        );
    }
    
    /**
     * Convertit une entité Utilisateur en AuthResponse (sans mot de passe)
     */
    public AuthResponse toAuthResponse(Utilisateur utilisateur, String message) {
        if (utilisateur == null) {
            return new AuthResponse(null, null, null, null, message);
        }
        
        return new AuthResponse(
            utilisateur.getId(),
            utilisateur.getNom(),
            utilisateur.getPrenom(),
            utilisateur.getEmail(),
            message
        );
    }
    
    /**
     * Convertit une entité Utilisateur en AuthResponse avec token
     */
    public AuthResponse toAuthResponse(Utilisateur utilisateur, String token, String message) {
        if (utilisateur == null) {
            return new AuthResponse(null, null, null, null, token, message);
        }
        
        return new AuthResponse(
            utilisateur.getId(),
            utilisateur.getNom(),
            utilisateur.getPrenom(),
            utilisateur.getEmail(),
            token,
            message
        );
    }
    
    /**
     * Convertit un UtilisateurCreateRequest en entité Utilisateur
     */
    public Utilisateur toEntity(UtilisateurCreateRequest request) {
        if (request == null) {
            return null;
        }
        
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNom(request.getNom());
        utilisateur.setPrenom(request.getPrenom());
        utilisateur.setEmail(request.getEmail());
        utilisateur.setMotDePasse(request.getMotDePasse()); // Sera hashé dans le service
        
        return utilisateur;
    }
    
    /**
     * Met à jour une entité Utilisateur à partir d'un UtilisateurUpdateRequest
     */
    public void updateEntity(Utilisateur utilisateur, UtilisateurUpdateRequest request) {
        if (utilisateur == null || request == null) {
            return;
        }
        
        if (request.getNom() != null) {
            utilisateur.setNom(request.getNom());
        }
        if (request.getPrenom() != null) {
            utilisateur.setPrenom(request.getPrenom());
        }
        if (request.getEmail() != null) {
            utilisateur.setEmail(request.getEmail());
        }
        if (request.getMotDePasse() != null) {
            utilisateur.setMotDePasse(request.getMotDePasse()); // Sera hashé dans le service
        }
    }
    
    /**
     * Convertit une liste d'entités en liste de DTOs
     */
    public List<UtilisateurResponse> toResponseList(List<Utilisateur> utilisateurs) {
        if (utilisateurs == null) {
            return List.of();
        }
        
        return utilisateurs.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
