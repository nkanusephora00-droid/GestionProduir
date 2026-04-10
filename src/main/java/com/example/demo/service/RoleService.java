package com.example.demo.service;

import com.example.demo.model.Role;
import com.example.demo.model.Utilisateur;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class RoleService {
    
    private final RoleRepository roleRepository;
    private final UtilisateurRepository utilisateurRepository;
    
    public RoleService(RoleRepository roleRepository, UtilisateurRepository utilisateurRepository) {
        this.roleRepository = roleRepository;
        this.utilisateurRepository = utilisateurRepository;
    }
    
    // CRUD - CREATE
    public Role creer(Role role) {
        // Vérifier si le rôle existe déjà
        if (roleRepository.existsByNom(role.getNom())) {
            throw new RuntimeException("Un rôle avec le nom '" + role.getNom() + "' existe déjà");
        }
        return roleRepository.save(role);
    }
    
    // CRUD - READ
    public List<Role> listerTous() {
        return roleRepository.findAll();
    }
    
    public Optional<Role> trouverParId(Integer id) {
        return roleRepository.findById(id);
    }
    
    public Optional<Role> trouverParNom(String nom) {
        return roleRepository.findByNom(nom);
    }
    
    public List<Role> rechercherParNom(String nom) {
        return roleRepository.findByNomContainingIgnoreCase(nom);
    }
    
    // CRUD - UPDATE
    public Role modifier(Integer id, Role roleModifie) {
        return roleRepository.findById(id)
            .map(role -> {
                role.setNom(roleModifie.getNom());
                role.setDescription(roleModifie.getDescription());
                return roleRepository.save(role);
            })
            .orElseThrow(() -> new RuntimeException("Rôle non trouvé avec l'ID : " + id));
    }
    
    // CRUD - DELETE
    public void supprimer(Integer id) {
        if (!roleRepository.existsById(id)) {
            throw new RuntimeException("Rôle non trouvé avec l'ID : " + id);
        }
        
        Role role = roleRepository.findById(id).get();
        
        // Vérifier si des utilisateurs ont ce rôle
        if (!role.getUtilisateurs().isEmpty()) {
            throw new RuntimeException("Impossible de supprimer le rôle : il est utilisé par " + 
                    role.getUtilisateurs().size() + " utilisateur(s)");
        }
        
        roleRepository.deleteById(id);
    }
    
    // Gestion des rôles utilisateurs
    public void ajouterRoleAUtilisateur(Integer utilisateurId, Integer roleId, String attribuePar) {
        Utilisateur utilisateur = utilisateurRepository.findById(utilisateurId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID : " + utilisateurId));
        
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Rôle non trouvé avec l'ID : " + roleId));
        
        if (!utilisateur.hasRole(role)) {
            utilisateur.addRole(role);
            utilisateurRepository.save(utilisateur);
        }
    }
    
    public void retirerRoleDeUtilisateur(Integer utilisateurId, Integer roleId) {
        Utilisateur utilisateur = utilisateurRepository.findById(utilisateurId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID : " + utilisateurId));
        
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Rôle non trouvé avec l'ID : " + roleId));
        
        if (utilisateur.hasRole(role)) {
            utilisateur.removeRole(role);
            utilisateurRepository.save(utilisateur);
        }
    }
    
    // Méthodes utilitaires
    public boolean existe(Integer id) {
        return roleRepository.existsById(id);
    }
    
    public boolean existeParNom(String nom) {
        return roleRepository.existsByNom(nom);
    }
    
    public long compter() {
        return roleRepository.count();
    }
    
    public long compterUtilisateursParRole(String nomRole) {
        return roleRepository.countUtilisateursByRole(nomRole);
    }
    
    public List<Role> trouverRolesDeUtilisateur(Integer utilisateurId) {
        return roleRepository.findByUtilisateurId(utilisateurId);
    }
    
    // Initialisation des rôles par défaut
    public void initialiserRolesParDefaut() {
        if (!roleRepository.existsByNom("ADMIN")) {
            creer(new Role("ADMIN", "Administrateur système avec tous les droits"));
        }
        
        if (!roleRepository.existsByNom("MANAGER")) {
            creer(new Role("MANAGER", "Manager avec droits de gestion"));
        }
        
        if (!roleRepository.existsByNom("USER")) {
            creer(new Role("USER", "Utilisateur standard avec droits limités"));
        }
    }
}
