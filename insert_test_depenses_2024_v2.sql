-- Script modifié pour ajouter des données de test de dépenses pour 2024
-- Version 2 : Sans spécifier realisations_id si ce n'est pas obligatoire

-- D'abord, vérifions quelle ligne budgétaire CLASSE_6 existe
SELECT lb.id, lb.montant, tlb.name as type, r.libelle as rubrique
FROM ligne_budgetaire lb
JOIN type_ligne_bugetaire tlb ON lb.type_ligne_bugetaire_id = tlb.id
JOIN rubrique r ON lb.rubrique_id = r.id
WHERE tlb.name = 'CLASSE_6';

-- Commentez les lignes ci-dessus après vérification et décommentez les INSERT ci-dessous

-- Réalisation T1 2024 (Février)
-- INSERT INTO realisation (montant, date, fournisseur, numero_bon, numero_be, numero_mandat, facture, description, ligne_budgetaire_id)
-- VALUES (250000.00, '2024-02-15', 'Fournisseur A', 'BON2024-001', 'BE2024-001', 'MAN2024-001', 'FACT2024-001', 'Achat matériel bureau T1', 2);

-- Réalisation T2 2024 (Mai)
-- INSERT INTO realisation (montant, date, fournisseur, numero_bon, numero_be, numero_mandat, facture, description, ligne_budgetaire_id)
-- VALUES (180000.00, '2024-05-20', 'Fournisseur B', 'BON2024-002', 'BE2024-002', 'MAN2024-002', 'FACT2024-002', 'Fournitures de bureau T2', 2);

-- Réalisation T3 2024 (Août)
-- INSERT INTO realisation (montant, date, fournisseur, numero_bon, numero_be, numero_mandat, facture, description, ligne_budgetaire_id)
-- VALUES (320000.00, '2024-08-10', 'Fournisseur C', 'BON2024-003', 'BE2024-003', 'MAN2024-003', 'FACT2024-003', 'Équipements divers T3', 2);

-- Réalisation T4 2024 (Novembre)
-- INSERT INTO realisation (montant, date, fournisseur, numero_bon, numero_be, numero_mandat, facture, description, ligne_budgetaire_id)
-- VALUES (150000.00, '2024-11-05', 'Fournisseur D', 'BON2024-004', 'BE2024-004', 'MAN2024-004', 'FACT2024-004', 'Maintenance équipements T4', 2);
