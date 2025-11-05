-- Script de vérification pour identifier la structure avant insertion

-- 1. Vérifier les lignes budgétaires CLASSE_6 disponibles
SELECT
    lb.id as ligne_budgetaire_id,
    lb.montant as budget,
    tlb.name as type,
    r.id as rubrique_id,
    r.libelle as rubrique_libelle
FROM ligne_budgetaire lb
JOIN type_ligne_bugetaire tlb ON lb.type_ligne_bugetaire_id = tlb.id
JOIN rubrique r ON lb.rubrique_id = r.id
WHERE tlb.name = 'CLASSE_6';

-- 2. Vérifier les rubriques de type REALISATION liées à cette ligne budgétaire
SELECT
    r.id as realisation_id,
    r.code,
    r.libelle,
    r.parent_id
FROM rubrique r
WHERE r.type = 'REALISATION'
  AND r.parent_id IN (
    SELECT lb.rubrique_id
    FROM ligne_budgetaire lb
    JOIN type_ligne_bugetaire tlb ON lb.type_ligne_bugetaire_id = tlb.id
    WHERE tlb.name = 'CLASSE_6'
  );

-- 3. Vérifier la structure de la table realisation
SELECT column_name, data_type, is_nullable, column_default
FROM information_schema.columns
WHERE table_name = 'realisation'
ORDER BY ordinal_position;
