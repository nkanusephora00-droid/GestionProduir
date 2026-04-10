package com.example.demo; // Définit le package des tests.

import org.junit.jupiter.api.Test; // Import de l’annotation de test JUnit.
import org.springframework.boot.test.context.SpringBootTest; // Import de l’annotation pour charger le contexte Spring.

@SpringBootTest // Démarre le contexte Spring pour ce test.
class DemoApplicationTests { // Classe de test pour l’application.

    @Test // Méthode de test.
    void contextLoads() { // Vérifie que le contexte Spring se charge sans erreur.
    }

}
