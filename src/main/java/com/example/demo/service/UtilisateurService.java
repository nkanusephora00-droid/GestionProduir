package com.example.demo.service;

import com.example.demo.model.Utilisateur;
import com.example.demo.repository.UtilisateurRepository;
import com.example.demo.security.PasswordService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurService {
    private final UtilisateurRepository utilisateurRepository;
    private final PasswordService passwordService;
    
    // Injection par constructeur (bonne pratique)
    public UtilisateurService(UtilisateurRepository utilisateurRepository, PasswordService passwordService) {
        this.utilisateurRepository = utilisateurRepository;
        this.passwordService = passwordService;
    }
    
    // CRUD - CREATE
    public Utilisateur creer(Utilisateur utilisateur) {
        // Validation de l'email
        if (utilisateur.getEmail() == null || utilisateur.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("L'email est obligatoire");
        }
        
        // Validation du mot de passe
        if (!passwordService.validerMotDePasse(utilisateur.getMotDePasse())) {
            throw new IllegalArgumentException("Le mot de passe doit contenir au moins 8 caractères, une majuscule, une minuscule, un chiffre et un caractère spécial");
        }
        
        // Logique métier : vérifier si l'email existe déjà
        if (utilisateurRepository.findByEmail(utilisateur.getEmail()).isPresent()) {
            throw new RuntimeException("Cet email est déjà utilisé !");
        }
        
        // Hashage du mot de passe avant sauvegarde
        String motDePasseHashé = passwordService.hasherMotDePasse(utilisateur.getMotDePasse());
        utilisateur.setMotDePasse(motDePasseHashé);
        
        return utilisateurRepository.save(utilisateur);
    }
    
    // CRUD - READ
    public List<Utilisateur> listerTous() {
        return utilisateurRepository.findAll();
    }
    
    public Optional<Utilisateur> trouverParId(Integer id) {
        return utilisateurRepository.findById(id);
    }
    
    public Optional<Utilisateur> trouverParEmail(String email) {
        return utilisateurRepository.findByEmail(email);
    }
    
    // CRUD - UPDATE
    public Utilisateur modifier(Integer id, Utilisateur utilisateurModifie) {
        return utilisateurRepository.findById(id)
            .map(utilisateur -> {
                utilisateur.setNom(utilisateurModifie.getNom());
                utilisateur.setPrenom(utilisateurModifie.getPrenom());
                utilisateur.setEmail(utilisateurModifie.getEmail());
                // Optionnel : mettre à jour le mot de passe seulement s'il est fourni
                if (utilisateurModifie.getMotDePasse() != null && !utilisateurModifie.getMotDePasse().isEmpty()) {
                    utilisateur.setMotDePasse(utilisateurModifie.getMotDePasse());
                }
                return utilisateurRepository.save(utilisateur);
            })
            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID : " + id));
    }
    
    // CRUD - DELETE
    public void supprimer(Integer id) {
        if (!utilisateurRepository.existsById(id)) {
            throw new RuntimeException("Utilisateur non trouvé avec l'ID : " + id);
        }
        utilisateurRepository.deleteById(id);
    }
    
    // Méthodes d'authentification
    public Optional<Utilisateur> authentifier(String email, String motDePasse) {
        if (email == null || motDePasse == null) {
            return Optional.empty();
        }
        
        Optional<Utilisateur> utilisateurOpt = utilisateurRepository.findByEmail(email);
        if (utilisateurOpt.isPresent()) {
            Utilisateur utilisateur = utilisateurOpt.get();
            if (passwordService.verifierMotDePasse(motDePasse, utilisateur.getMotDePasse())) {
                return Optional.of(utilisateur);
            }
        }
        return Optional.empty();
    }
    
    public void changerMotDePasse(Integer id, String ancienMotDePasse, String nouveauMotDePasse) {
        Optional<Utilisateur> utilisateurOpt = utilisateurRepository.findById(id);
        if (utilisateurOpt.isEmpty()) {
            throw new RuntimeException("Utilisateur non trouvé avec l'ID : " + id);
        }
        
        Utilisateur utilisateur = utilisateurOpt.get();
        
        // Vérifier l'ancien mot de passe
        if (!passwordService.verifierMotDePasse(ancienMotDePasse, utilisateur.getMotDePasse())) {
            throw new IllegalArgumentException("L'ancien mot de passe est incorrect");
        }
        
        // Valider le nouveau mot de passe
        if (!passwordService.validerMotDePasse(nouveauMotDePasse)) {
            throw new IllegalArgumentException("Le nouveau mot de passe doit contenir au moins 8 caractères, une majuscule, une minuscule, un chiffre et un caractère spécial");
        }
        
        // Hasher et sauvegarder le nouveau mot de passe
        String nouveauMotDePasseHashé = passwordService.hasherMotDePasse(nouveauMotDePasse);
        utilisateur.setMotDePasse(nouveauMotDePasseHashé);
        utilisateurRepository.save(utilisateur);
    }
    
    public String reinitialiserMotDePasse(Integer id) {
        Optional<Utilisateur> utilisateurOpt = utilisateurRepository.findById(id);
        if (utilisateurOpt.isEmpty()) {
            throw new RuntimeException("Utilisateur non trouvé avec l'ID : " + id);
        }
        
        Utilisateur utilisateur = utilisateurOpt.get();
        String nouveauMotDePasse = passwordService.genererMotDePasseAleatoire();
        String motDePasseHashé = passwordService.hasherMotDePasse(nouveauMotDePasse);
        
        utilisateur.setMotDePasse(motDePasseHashé);
        utilisateurRepository.save(utilisateur);
        
        return nouveauMotDePasse;
    }
    
    // Méthodes utilitaires
    public boolean existe(Integer id) {
        return utilisateurRepository.existsById(id);
    }
    
    public long compter() {
        return utilisateurRepository.count();
    }
}
