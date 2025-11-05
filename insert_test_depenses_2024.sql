-- Script pour ajouter des données de test de dépenses pour 2024
-- Utilise la ligne budgétaire existante ID 2 (CLASSE_6)

-- Réalisation T1 2024 (Janvier - Mars)
INSERT INTO realisation (code, montant, date, fournisseur, numero_bon, numero_be, numero_mandat, facture, description, ligne_budgetaire_id, realisations_id)
VALUES ('', 250000.00, '2024-02-15', 'Fournisseur A', 'BON2024-001', 'BE2024-001', 'MAN2024-001', 'FACT2024-001', 'Achat matériel bureau T1', 2, 56);

-- Réalisation T2 2024 (Avril - Juin)
INSERT INTO realisation (code, montant, date, fournisseur, numero_bon, numero_be, numero_mandat, facture, description, ligne_budgetaire_id, realisations_id)
VALUES ('', 180000.00, '2024-05-20', 'Fournisseur B', 'BON2024-002', 'BE2024-002', 'MAN2024-002', 'FACT2024-002', 'Fournitures de bureau T2', 2, 56);

-- Réalisation T3 2024 (Juillet - Septembre)
INSERT INTO realisation (code, montant, date, fournisseur, numero_bon, numero_be, numero_mandat, facture, description, ligne_budgetaire_id, realisations_id)
VALUES ('', 320000.00, '2024-08-10', 'Fournisseur C', 'BON2024-003', 'BE2024-003', 'MAN2024-003', 'FACT2024-003', 'Équipements divers T3', 2, 56);

-- Réalisation T4 2024 (Octobre - Décembre)
INSERT INTO realisation (code, montant, date, fournisseur, numero_bon, numero_be, numero_mandat, facture, description, ligne_budgetaire_id, realisations_id)
VALUES ('', 150000.00, '2024-11-05', 'Fournisseur D', 'BON2024-004', 'BE2024-004', 'MAN2024-004', 'FACT2024-004', 'Maintenance équipements T4', 2, 56);

-- Total ajouté: 900,000 FCFA pour l'année 2024
-- Budget ligne ID 2: 1,098,765 FCFA
-- Taux de réalisation: ~82%
