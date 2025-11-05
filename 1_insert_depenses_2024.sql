-- Script d'insertion de données de test pour les dépenses 2024
-- À exécuter APRÈS avoir vérifié avec 0_verify_depenses_structure.sql

-- IMPORTANT: Vérifiez d'abord que la ligne_budgetaire_id=2 et realisations_id=56 existent
-- Si différent, modifiez les valeurs ci-dessous

-- T1 2024 - Janvier (15 janvier)
INSERT INTO realisation (
    montant,
    date,
    fournisseur,
    numero_bon,
    numero_be,
    numero_mandat,
    facture,
    description,
    ligne_budgetaire_id,
    realisations_id
) VALUES (
    150000.00,
    '2024-01-15',
    'Fournisseur Alpha',
    'BON-2024-T1-001',
    'BE-2024-001',
    'MAN-2024-001',
    'FACT-2024-001',
    'Fournitures de bureau et consommables - Janvier',
    2,
    56
);

-- T1 2024 - Février (20 février)
INSERT INTO realisation (
    montant,
    date,
    fournisseur,
    numero_bon,
    numero_be,
    numero_mandat,
    facture,
    description,
    ligne_budgetaire_id,
    realisations_id
) VALUES (
    180000.00,
    '2024-02-20',
    'Fournisseur Beta',
    'BON-2024-T1-002',
    'BE-2024-002',
    'MAN-2024-002',
    'FACT-2024-002',
    'Matériel informatique - Février',
    2,
    56
);

-- T1 2024 - Mars (10 mars)
INSERT INTO realisation (
    montant,
    date,
    fournisseur,
    numero_bon,
    numero_be,
    numero_mandat,
    facture,
    description,
    ligne_budgetaire_id,
    realisations_id
) VALUES (
    120000.00,
    '2024-03-10',
    'Fournisseur Gamma',
    'BON-2024-T1-003',
    'BE-2024-003',
    'MAN-2024-003',
    'FACT-2024-003',
    'Frais de maintenance - Mars',
    2,
    56
);

-- T2 2024 - Avril (15 avril)
INSERT INTO realisation (
    montant,
    date,
    fournisseur,
    numero_bon,
    numero_be,
    numero_mandat,
    facture,
    description,
    ligne_budgetaire_id,
    realisations_id
) VALUES (
    95000.00,
    '2024-04-15',
    'Fournisseur Delta',
    'BON-2024-T2-001',
    'BE-2024-004',
    'MAN-2024-004',
    'FACT-2024-004',
    'Fournitures diverses - Avril',
    2,
    56
);

-- T2 2024 - Mai (25 mai)
INSERT INTO realisation (
    montant,
    date,
    fournisseur,
    numero_bon,
    numero_be,
    numero_mandat,
    facture,
    description,
    ligne_budgetaire_id,
    realisations_id
) VALUES (
    110000.00,
    '2024-05-25',
    'Fournisseur Epsilon',
    'BON-2024-T2-002',
    'BE-2024-005',
    'MAN-2024-005',
    'FACT-2024-005',
    'Équipements de bureau - Mai',
    2,
    56
);

-- T3 2024 - Juillet (18 juillet)
INSERT INTO realisation (
    montant,
    date,
    fournisseur,
    numero_bon,
    numero_be,
    numero_mandat,
    facture,
    description,
    ligne_budgetaire_id,
    realisations_id
) VALUES (
    135000.00,
    '2024-07-18',
    'Fournisseur Zeta',
    'BON-2024-T3-001',
    'BE-2024-006',
    'MAN-2024-006',
    'FACT-2024-006',
    'Matériel technique - Juillet',
    2,
    56
);

-- T3 2024 - Septembre (5 septembre)
INSERT INTO realisation (
    montant,
    date,
    fournisseur,
    numero_bon,
    numero_be,
    numero_mandat,
    facture,
    description,
    ligne_budgetaire_id,
    realisations_id
) VALUES (
    88000.00,
    '2024-09-05',
    'Fournisseur Eta',
    'BON-2024-T3-002',
    'BE-2024-007',
    'MAN-2024-007',
    'FACT-2024-007',
    'Consommables divers - Septembre',
    2,
    56
);

-- T4 2024 - Octobre (22 octobre)
INSERT INTO realisation (
    montant,
    date,
    fournisseur,
    numero_bon,
    numero_be,
    numero_mandat,
    facture,
    description,
    ligne_budgetaire_id,
    realisations_id
) VALUES (
    75000.00,
    '2024-10-22',
    'Fournisseur Theta',
    'BON-2024-T4-001',
    'BE-2024-008',
    'MAN-2024-008',
    'FACT-2024-008',
    'Services généraux - Octobre',
    2,
    56
);

-- T4 2024 - Novembre (30 novembre)
INSERT INTO realisation (
    montant,
    date,
    fournisseur,
    numero_bon,
    numero_be,
    numero_mandat,
    facture,
    description,
    ligne_budgetaire_id,
    realisations_id
) VALUES (
    125765.00,
    '2024-11-30',
    'Fournisseur Iota',
    'BON-2024-T4-002',
    'BE-2024-009',
    'MAN-2024-009',
    'FACT-2024-009',
    'Clôture exercice - Novembre',
    2,
    56
);

-- Vérifier les insertions
SELECT
    COUNT(*) as nombre_insertions,
    SUM(montant) as total_montant,
    EXTRACT(YEAR FROM date) as annee
FROM realisation
WHERE EXTRACT(YEAR FROM date) = 2024
    AND ligne_budgetaire_id = 2
GROUP BY EXTRACT(YEAR FROM date);

-- Détail par trimestre
SELECT
    CASE
        WHEN EXTRACT(MONTH FROM date) BETWEEN 1 AND 3 THEN 'T1'
        WHEN EXTRACT(MONTH FROM date) BETWEEN 4 AND 6 THEN 'T2'
        WHEN EXTRACT(MONTH FROM date) BETWEEN 7 AND 9 THEN 'T3'
        ELSE 'T4'
    END as trimestre,
    COUNT(*) as nombre,
    SUM(montant) as total
FROM realisation
WHERE EXTRACT(YEAR FROM date) = 2024
    AND ligne_budgetaire_id = 2
GROUP BY trimestre
ORDER BY trimestre;

-- RÉSUMÉ:
-- 9 réalisations de dépenses pour 2024
-- Total: 1,078,765 FCFA
-- Budget ligne ID 2: 1,098,765 FCFA
-- Taux de réalisation: ~98.2%
--
-- Répartition par trimestre:
-- T1: 450,000 FCFA (3 réalisations)
-- T2: 205,000 FCFA (2 réalisations)
-- T3: 223,000 FCFA (2 réalisations)
-- T4: 200,765 FCFA (2 réalisations)
