-- Script d'initialisation pour PostgreSQL (optionnel)

-- Création de la base de données si elle n'existe pas
-- CREATE DATABASE IF NOT EXISTS demo;

-- Utilisateur de la base de données
-- CREATE USER IF NOT EXISTS demo_user WITH ENCRYPTED PASSWORD 'demo_password';
-- GRANT ALL PRIVILEGES ON DATABASE demo TO demo_user;

-- Tables (seront créées automatiquement par Hibernate)
-- CREATE TABLE IF NOT EXISTS produit (
--     id BIGSERIAL PRIMARY KEY,
--     nom VARCHAR(255) NOT NULL,
--     prix DOUBLE PRECISION
-- );

-- CREATE TABLE IF NOT EXISTS categorie (
--     id BIGSERIAL PRIMARY KEY,
--     nom VARCHAR(255) NOT NULL
-- );

-- Données initiales (optionnel)
-- INSERT INTO categorie (nom) VALUES ('Électronique'), ('Vêtements'), ('Alimentaire')
-- ON CONFLICT DO NOTHING;

-- INSERT INTO produit (nom, prix) VALUES 
--     ('Smartphone', 999.99),
--     ('Ordinateur portable', 1499.99),
--     ('T-shirt', 29.99)
-- ON CONFLICT DO NOTHING;
