package com.webgram.dgpsn.services.Impl;

import com.webgram.dgpsn.entities.BudgetDgpsnEntity;
import com.webgram.dgpsn.entities.PlanComptableElementEntity;
import com.webgram.dgpsn.entities.RealisationEntity;
import com.webgram.dgpsn.entities.enums.TypeLigneBugetaire;
import com.webgram.dgpsn.entities.enums.TypePlanComptable;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.models.*;
import com.webgram.dgpsn.models.responses.report.BudgetReportRowDTO;
import com.webgram.dgpsn.models.responses.report.EngagementDTO;
import com.webgram.dgpsn.models.responses.report.FinancialReportDTO;
import com.webgram.dgpsn.repositories.BudgetDgpsnRepository;
import com.webgram.dgpsn.repositories.LigneBudgetaireRepository;
import com.webgram.dgpsn.repositories.PlanComptableElementRepository;
import com.webgram.dgpsn.repositories.RealisationRepository;
import com.webgram.dgpsn.services.FinancementReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class FinancementReportServiceImpl implements FinancementReportService {

    private final BudgetDgpsnRepository budgetDgpsnRepository;
    private final LigneBudgetaireRepository ligneBudgetaireRepository;
    private final PlanComptableElementRepository planComptableRepository;
    private final RealisationRepository realisationRepository;

    @Override
    public FinancialReportDTO generateFinancialReport(Integer annee, String periodType) {
        log.info("Génération du rapport financier - année: {}, période: {}", annee, periodType);

        // MODIFIÉ: Récupérer tous les budgets pour l'année donnée
        List<BudgetDgpsnEntity> budgets = budgetDgpsnRepository.findByAnnee(annee);
        if (budgets.isEmpty()) {
            throw new ResourceNotFoundException("Aucun budget trouvé pour l'année ", annee);
        }

        List<Long> budgetIds = budgets.stream().map(BudgetDgpsnEntity::getId).collect(Collectors.toList());

        return FinancialReportDTO.builder()
                .annee(annee)
                .periodType(periodType)
                .recettesFonctionnement(generateRecettesFonctionnement(budgetIds, annee, periodType))
                .depensesFonctionnement(generateDepensesFonctionnement(budgetIds, annee, periodType))
                .investissements(generateInvestissements(budgetIds, annee, periodType))
                .recapInvestissement(generateRecapitulatifInvestissement(budgetIds, annee))
                .engagements(getEngagements(budgetIds, annee))
                .build();
    }

    public List<BudgetReportRowDTO> generateRecettesFonctionnement(List<Long> budgetIds, Integer annee, String periodType) {
        log.info("Génération des recettes de fonctionnement - budgetIds: {}, année: {}", budgetIds, annee);
        return generateReportForClasse(budgetIds, "7", TypeLigneBugetaire.CLASSE_7, annee, periodType);
    }

    public List<BudgetReportRowDTO> generateDepensesFonctionnement(List<Long> budgetIds, Integer annee, String periodType) {
        log.info("Génération des dépenses de fonctionnement - budgetIds: {}, année: {}", budgetIds, annee);
        return generateReportForClasse(budgetIds, "6", TypeLigneBugetaire.CLASSE_6, annee, periodType);
    }

    public List<BudgetReportRowDTO> generateInvestissements(List<Long> budgetIds, Integer annee, String periodType) {
        log.info("Génération des investissements - budgetIds: {}, année: {}", budgetIds, annee);
        return generateReportForClasse(budgetIds, "2", TypeLigneBugetaire.CLASSE_2, annee, periodType);
    }

    @Override
    public List<BudgetReportRowDTO> generateRecapitulatifInvestissement(List<Long> budgetIds, Integer annee) {
        log.info("Génération du récapitulatif investissement - budgetIds: {}, année: {}", budgetIds, annee);

        List<BudgetReportRowDTO> rows = generateReportForClasse(budgetIds, "2", TypeLigneBugetaire.CLASSE_2, annee, "trimestriel");

        for (BudgetReportRowDTO row : rows) {
            if (row.getRealisationsPeriodiques() != null) {
                row.setT1(row.getRealisationsPeriodiques().getOrDefault("t1", 0.0));
                row.setT2(row.getRealisationsPeriodiques().getOrDefault("t2", 0.0));
                row.setT3(row.getRealisationsPeriodiques().getOrDefault("t3", 0.0));
                row.setT4(row.getRealisationsPeriodiques().getOrDefault("t4", 0.0));
            }
        }

        return rows;
    }

    @Override
    public List<EngagementDTO> getEngagements(List<Long> budgetIds, Integer annee) {
        log.info("Récupération des engagements (réalisations) - budgetIds: {}, année: {}", budgetIds, annee);

        LocalDate startDate = LocalDate.of(annee, 1, 1);
        LocalDate endDate = LocalDate.of(annee, 12, 31);

        // MODIFIÉ: Récupérer toutes les lignes budgétaires des budgets concernés
        List<Long> ligneBudgetaireIds = ligneBudgetaireRepository.findByBudgetIdIn(budgetIds)
                .stream()
                .map(ligne -> ligne.getId())
                .toList();

        if (ligneBudgetaireIds.isEmpty()) {
            return new ArrayList<>();
        }

        // Le reste de la méthode est inchangé
        return realisationRepository.findByLigneBudgetaireIdIn(ligneBudgetaireIds)
                .stream()
                .filter(r -> r.getDate() != null &&
                        !r.getDate().isBefore(startDate) &&
                        !r.getDate().isAfter(endDate))
                .sorted(Comparator.comparing(RealisationEntity::getDate))
                .map(this::mapRealisationToEngagementDTO)
                .toList();
    }

    /**
     * Mapper RealisationEntity vers EngagementDTO (pour le rapport)
     */
    private EngagementDTO mapRealisationToEngagementDTO(RealisationEntity entity) {
        String compte = "";
        String servicesDGPSN = "";

        PlanComptableElementEntity element = entity.getRealisations();
        PlanComptableElementEntity compteElement = null;

        while (element != null) {
            if (element.getType() == TypePlanComptable.RUBRIQUE) {
                compteElement = element;
                break;
            }
            element = element.getParent();
        }

        compte = (compteElement != null) ? compteElement.getCode() : null;

        // Récupérer le service depuis la ligne budgétaire si nécessaire
        if (entity.getLigneBudgetaire() != null && entity.getLigneBudgetaire().getBudget() != null) {
            servicesDGPSN = "CABINET"; // Ou récupérer depuis votre modèle
        }

        return EngagementDTO.builder()
                .numBon(entity.getNumeroBon())
                .dateEngagement(entity.getDate())
                .numBE(entity.getNumeroBE())
                .numMandat(entity.getNumeroMandat())
                .factureEtat(entity.getFacture() != null ? entity.getFacture() : entity.getDescription())
                .montants(entity.getMontant())
                .fournisseurBeneficiaire(entity.getFournisseur() != null && entity.getFournisseur().getRaisonSociale() != null ? entity.getFournisseur().getRaisonSociale() : "")
                .servicesDGPSN(servicesDGPSN)
                .compte(compte)
                .date(entity.getDate())
                .build();
    }

    /**
     * Méthode générique pour générer un rapport pour une classe donnée
     */
    private List<BudgetReportRowDTO> generateReportForClasse(
            List<Long> budgetIds,
            String classeCode,
            TypeLigneBugetaire typeLigne,
            Integer annee,
            String periodType) {

        List<BudgetReportRowDTO> result = new ArrayList<>();
        List<PlanComptableElementDTO> rubriques = getRubriquesByClasse(classeCode);
        if (rubriques.isEmpty()) {
            log.warn("Aucune rubrique trouvée pour la classe {}", classeCode);
            return result;
        }
        Map<String, List<PlanComptableElementDTO>> rubriquesByCompte = groupRubriquesByCompte(rubriques);
        double totalBudget2025 = 0.0;
        double totalRealisationsCumulees = 0.0;
        double totalResteARealiser = 0.0;
        Map<String, Double> totalRealisationsPeriodiques = initializePeriodMap(periodType);

        for (Map.Entry<String, List<PlanComptableElementDTO>> entry : rubriquesByCompte.entrySet()) {
            List<PlanComptableElementDTO> rubriquesDuCompte = entry.getValue();

            PlanComptableElementDTO premiereRubrique = rubriquesDuCompte.get(0);
            String compteCode = getCompteCode(premiereRubrique);
            String compteLibelle = getCompteLibelle(premiereRubrique);

            double budgetCompte = 0.0;
            double realisationsCompte = 0.0;
            Map<String, Double> realisationsPeriodiqueCompte = initializePeriodMap(periodType);

            List<BudgetReportRowDTO> lignesRubriques = new ArrayList<>();

            for (PlanComptableElementDTO rubrique : rubriquesDuCompte) {
                List<LigneBudgetaireDTO> lignes = getLignesBudgetaires(budgetIds, rubrique.getId(), typeLigne);

                if (!lignes.isEmpty()) {
                    double budgetRubrique = lignes.stream()
                            .mapToDouble(l -> l.getMontant() != null ? l.getMontant() : 0.0)
                            .sum();

                    // Récupérer les IDs des lignes budgétaires pour cette rubrique
                    List<Long> ligneIds = lignes.stream()
                            .map(LigneBudgetaireDTO::getId)
                            .collect(Collectors.toList());

                    Map<String, Double> realisationsPeriodiques = getRealisationsPeriodiques(ligneIds, annee, periodType);
                    double realisationsCumulees = realisationsPeriodiques.values().stream()
                            .mapToDouble(Double::doubleValue)
                            .sum();

                    double tauxExecution = budgetRubrique > 0 ? (realisationsCumulees / budgetRubrique * 100) : 0.0;
                    double resteARealiser = budgetRubrique - realisationsCumulees;

                    BudgetReportRowDTO row = BudgetReportRowDTO.builder()
                            .classe(classeCode)
                            .compte(compteCode)
                            .sousCompte(getSousCompteCode(rubrique))
                            .rubrique(rubrique.getLibelle())
                            .budget2024(budgetRubrique)
                            .budget2025(budgetRubrique)
                            .realisationsPeriodiques(realisationsPeriodiques)
                            .realisationsCumulees(realisationsCumulees)
                            .tauxExecution(Math.round(tauxExecution * 100.0) / 100.0)
                            .niveauExecution(Math.round(tauxExecution * 100.0) / 100.0)
                            .resteARealiser(resteARealiser)
                            .isCategory(false)
                            .isSubCategory(false)
                            .isTotal(false)
                            .build();

                    lignesRubriques.add(row);

                    budgetCompte += budgetRubrique;
                    realisationsCompte += realisationsCumulees;
                    sumPeriodMaps(realisationsPeriodiqueCompte, realisationsPeriodiques);
                }
            }

            if (budgetCompte > 0 || realisationsCompte > 0) {
                double tauxExecutionCompte = budgetCompte > 0 ? (realisationsCompte / budgetCompte * 100) : 0.0;
                BudgetReportRowDTO compteRow = BudgetReportRowDTO.builder()
                        .classe(classeCode)
                        .compte(compteCode)
                        .rubrique(compteLibelle)
                        .budget2024(budgetCompte)
                        .budget2025(budgetCompte)
                        .realisationsPeriodiques(realisationsPeriodiqueCompte)
                        .realisationsCumulees(realisationsCompte)
                        .tauxExecution(Math.round(tauxExecutionCompte * 100.0) / 100.0)
                        .niveauExecution(Math.round(tauxExecutionCompte * 100.0) / 100.0)
                        .resteARealiser(budgetCompte - realisationsCompte)
                        .isCategory(true)
                        .isSubCategory(false)
                        .isTotal(false)
                        .build();

                result.add(compteRow);
                result.addAll(lignesRubriques);

                totalBudget2025 += budgetCompte;
                totalRealisationsCumulees += realisationsCompte;
                totalResteARealiser += (budgetCompte - realisationsCompte);
                sumPeriodMaps(totalRealisationsPeriodiques, realisationsPeriodiqueCompte);
            }
        }

        if (totalBudget2025 > 0 || totalRealisationsCumulees > 0) {
            double tauxExecutionTotal = totalBudget2025 > 0 ? (totalRealisationsCumulees / totalBudget2025 * 100) : 0.0;
            BudgetReportRowDTO totalRow = BudgetReportRowDTO.builder()
                    .classe(classeCode)
                    .rubrique(getTotalLabel(classeCode))
                    .budget2024(totalBudget2025)
                    .budget2025(totalBudget2025)
                    .realisationsPeriodiques(totalRealisationsPeriodiques)
                    .realisationsCumulees(totalRealisationsCumulees)
                    .tauxExecution(Math.round(tauxExecutionTotal * 100.0) / 100.0)
                    .niveauExecution(Math.round(tauxExecutionTotal * 100.0) / 100.0)
                    .resteARealiser(totalResteARealiser)
                    .isCategory(false)
                    .isSubCategory(false)
                    .isTotal(true)
                    .build();

            result.add(totalRow);
        }

        return result;
    }

    /**
     * Récupère les rubriques d'une classe donnée
     */
    private List<PlanComptableElementDTO> getRubriquesByClasse(String classeCode) {
        var classeOpt = planComptableRepository.findByCodeAndType(classeCode, TypePlanComptable.CLASSE);

        if (classeOpt.isEmpty()) {
            log.warn("Classe {} non trouvée", classeCode);
            return new ArrayList<>();
        }

        return planComptableRepository.findRubriquesByClasseIdPerso(classeOpt.get().getId())
                .stream()
                .map(this::mapPlanToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Groupe les rubriques par compte
     */
    private Map<String, List<PlanComptableElementDTO>> groupRubriquesByCompte(List<PlanComptableElementDTO> rubriques) {
        Map<String, List<PlanComptableElementDTO>> grouped = new LinkedHashMap<>();

        for (PlanComptableElementDTO rubrique : rubriques) {
            String compteKey = getCompteKey(rubrique);
            grouped.computeIfAbsent(compteKey, k -> new ArrayList<>()).add(rubrique);
        }

        return grouped;
    }

    /**
     * Récupère les lignes budgétaires pour une rubrique
     */
    private List<LigneBudgetaireDTO> getLignesBudgetaires(List<Long> budgetIds, Long rubriqueId, TypeLigneBugetaire typeLigne) {
        return ligneBudgetaireRepository.findByBudgetIdsAndRubriqueIdAndType(budgetIds, rubriqueId, typeLigne)
                .stream()
                .map(this::mapLigneToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Récupère les réalisations périodiques pour une liste de lignes budgétaires
     */
    private Map<String, Double> getRealisationsPeriodiques(List<Long> ligneBudgetaireIds, Integer annee, String periodType) {
        Map<String, Double> realisations = initializePeriodMap(periodType);

        if (ligneBudgetaireIds.isEmpty()) {
            return realisations;
        }

        LocalDate startDate = LocalDate.of(annee, 1, 1);
        LocalDate endDate = LocalDate.of(annee, 12, 31);

        // Récupérer toutes les réalisations pour ces lignes budgétaires
        List<RealisationEntity> realisationsEntities = realisationRepository.findByLigneBudgetaireIdIn(ligneBudgetaireIds);

        // Filtrer par année et grouper par période
        for (RealisationEntity realisation : realisationsEntities) {
            if (realisation.getDate() != null &&
                    !realisation.getDate().isBefore(startDate) &&
                    !realisation.getDate().isAfter(endDate)) {

                String periodKey = getPeriodKey(realisation.getDate(), periodType);
                double montant = realisation.getMontant() != null ? realisation.getMontant() : 0.0;
                realisations.merge(periodKey, montant, Double::sum);
            }
        }

        return realisations;
    }

    /**
     * Détermine la clé de période (mois ou trimestre) à partir d'une date
     */
    private String getPeriodKey(LocalDate date, String periodType) {
        if ("mensuel".equals(periodType)) {
            int month = date.getMonthValue();
            String[] mois = {"janvier", "fevrier", "mars", "avril", "mai", "juin",
                    "juillet", "aout", "septembre", "octobre", "novembre", "decembre"};
            return mois[month - 1];
        } else if ("trimestriel".equals(periodType)) {
            int month = date.getMonthValue();
            int trimestre = ((month - 1) / 3) + 1;
            return "t" + trimestre;
        }
        return "annuel";
    }

    /**
     * Initialise une map de périodes avec des valeurs à 0
     */
    private Map<String, Double> initializePeriodMap(String periodType) {
        Map<String, Double> map = new LinkedHashMap<>();

        if ("mensuel".equals(periodType)) {
            String[] mois = {"janvier", "fevrier", "mars", "avril", "mai", "juin",
                    "juillet", "aout", "septembre", "octobre", "novembre", "decembre"};
            for (String m : mois) {
                map.put(m, 0.0);
            }
        } else if ("trimestriel".equals(periodType)) {
            for (int i = 1; i <= 4; i++) {
                map.put("t" + i, 0.0);
            }
        }

        return map;
    }

    /**
     * Additionne deux maps de périodes
     */
    private void sumPeriodMaps(Map<String, Double> target, Map<String, Double> source) {
        for (Map.Entry<String, Double> entry : source.entrySet()) {
            target.merge(entry.getKey(), entry.getValue(), Double::sum);
        }
    }

    // Méthodes utilitaires
    private String getCompteKey(PlanComptableElementDTO rubrique) {
        if (rubrique.getParent() != null && rubrique.getParent().getParent() != null) {
            return rubrique.getParent().getParent().getCode();
        }
        return "UNKNOWN";
    }

    private String getCompteCode(PlanComptableElementDTO rubrique) {
        if (rubrique.getParent() != null && rubrique.getParent().getParent() != null) {
            return rubrique.getParent().getParent().getCode();
        }
        return "";
    }

    private String getCompteLibelle(PlanComptableElementDTO rubrique) {
        if (rubrique.getParent() != null && rubrique.getParent().getParent() != null) {
            return rubrique.getParent().getParent().getLibelle();
        }
        return "";
    }

    private String getSousCompteCode(PlanComptableElementDTO rubrique) {
        if (rubrique.getParent() != null) {
            return rubrique.getParent().getCode();
        }
        return "";
    }

    private String getTotalLabel(String classeCode) {
        switch (classeCode) {
            case "7":
                return "TOTAL RECETTES DE FONCTIONNEMENT";
            case "6":
                return "TOTAL DEPENSES DE FONCTIONNEMENT";
            case "2":
                return "TOTAL DEPENSES D'INVESTISSEMENT";
            default:
                return "TOTAL";
        }
    }

    // Mappers
    private PlanComptableElementDTO mapPlanToDTO(com.webgram.dgpsn.entities.PlanComptableElementEntity entity) {
        PlanComptableElementDTO dto = new PlanComptableElementDTO();
        dto.setId(entity.getId());
        dto.setCode(entity.getCode());
        dto.setLibelle(entity.getLibelle());
        dto.setCommentaire(entity.getCommentaire());
        dto.setType(entity.getType());

        if (entity.getParent() != null) {
            dto.setParentId(entity.getParent().getId());
            dto.setParent(mapPlanToDTO(entity.getParent()));
        }

        return dto;
    }

    private LigneBudgetaireDTO mapLigneToDTO(com.webgram.dgpsn.entities.LigneBudgetaireEntity entity) {
        LigneBudgetaireDTO dto = new LigneBudgetaireDTO();
        dto.setId(entity.getId());
        dto.setMontant(entity.getMontant());
        dto.setTypeLigneBugetaire(entity.getTypeLigneBugetaire());
        dto.setCommentaire(entity.getCommentaire());

        if (entity.getBudget() != null) {
            dto.setBudgetId(entity.getBudget().getId());
        }

        if (entity.getRubrique() != null) {
            dto.setRubriqueId(entity.getRubrique().getId());
        }

        return dto;
    }
}
