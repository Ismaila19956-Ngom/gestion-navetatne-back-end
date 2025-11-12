package com.webgram.dgpsn.services.Impl;

import com.webgram.dgpsn.annotations.Journal;
import com.webgram.dgpsn.entities.RealisationEntity;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.RealisationMapper;
import com.webgram.dgpsn.models.LigneBudgetaireDTO;
import com.webgram.dgpsn.models.RealisationDTO;
import com.webgram.dgpsn.models.RealisationExportDTO;
import com.webgram.dgpsn.repositories.RealisationRepository;
import com.webgram.dgpsn.services.ExcelExportService;
import com.webgram.dgpsn.services.LigneBudgetaireService;
import com.webgram.dgpsn.services.RealisationService;
import com.webgram.dgpsn.tools.ActionType;
import com.webgram.dgpsn.entities.enums.TypeLigneBugetaire;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class RealisationServiceImpl implements RealisationService {
    private final RealisationRepository realisationRepository;
    private final RealisationMapper realisationMapper;
    private final LigneBudgetaireService ligneBudgetaireService;
    private final ExcelExportService excelExportService;

    @Override
    @Journal(actionType = ActionType.ADD_DATES_IMPORTANTE)
    public RealisationDTO create(RealisationDTO realisationDTO) {
        var savedRealisation = realisationRepository.save(realisationMapper.asEntity(realisationDTO));
        log.info("Realisation successfully added {}", savedRealisation);
        return realisationMapper.asDto(savedRealisation);
    }

    @Override
    @Journal(actionType = ActionType.ADD_DATES_IMPORTANTE)
    public List<RealisationDTO> createMultiple(List<RealisationDTO> realisationDTOs) {
        var savedRealisations = realisationRepository.saveAll(
                realisationDTOs.stream()
                        .map(realisationMapper::asEntity)
                        .collect(Collectors.toList())
        );
        log.info("Multiple Realisation entries successfully added, count: {}", savedRealisations.size());
        return savedRealisations.stream()
                .map(realisationMapper::asDto)
                .collect(Collectors.toList());
    }

    @Override
    @Journal(actionType = ActionType.UPDATE_DATES_IMPORTANTE)
    public RealisationDTO update(RealisationDTO realisationDTO) {
        try {
            if (realisationRepository.existsById(realisationDTO.getId())) {
                var realisationEntity = realisationMapper.asEntity(realisationDTO);
                var updatedRealisation = realisationMapper.asDto(realisationRepository.save(realisationEntity));
                log.info("Realisation successfully updated {}", updatedRealisation.getId());
                return updatedRealisation;
            } else {
                throw new ResourceNotFoundException("Realisation", realisationDTO.getId());
            }
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("Realisation", realisationDTO.getId());
        }
    }

    @Override
    @Journal(actionType = ActionType.READ_DATES_IMPORTANTE)
    public RealisationDTO read(Long realisationId) {
        var realisation = realisationRepository
                .findById(realisationId)
                .orElseThrow(() -> new ResourceNotFoundException("Realisation", realisationId));
        log.info("Reading Realisation id {}", realisation);
        return realisationMapper.asDto(realisation);
    }

    @Override
    @Journal(actionType = ActionType.DELETE_DATES_IMPORTANTE)
    public void delete(Long realisationId) {
        try {
            realisationRepository.deleteById(realisationId);
            log.info("The Realisation id {} is deleted", realisationId);
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("Realisation", realisationId);
        }
    }

    @Override
    @Journal(actionType = ActionType.READ_DATES_IMPORTANTE)
    public Page<RealisationDTO> readAll(
            Pageable pageable,
            String code,
            Long realisationsId,
            Double montant,
            LocalDate date,
            String fournisseur,
            String numeroBon,
            String numeroBE,
            String numeroMandat,
            String description,
            Long ligneBudgetaireId
    ) {
        return realisationRepository
                .readAllByFiltering(pageable, code, realisationsId, montant, date, fournisseur, numeroBon, numeroBE, numeroMandat, description, ligneBudgetaireId)
                .map(realisationMapper::asDto);
    }

    @Override
    @Journal(actionType = ActionType.READ_DATES_IMPORTANTE)
    public List<RealisationDTO> findByBudgetId(Long budgetId) {
        // 1. Récupérer toutes les lignes du budget
        List<LigneBudgetaireDTO> lignes = ligneBudgetaireService.findByBudgetId(budgetId);
        // 2. Extraire les IDs des lignes
        List<Long> ligneIds = lignes.stream()
                .map(LigneBudgetaireDTO::getId)
                .collect(Collectors.toList());
        // 3. Chercher les réalisations pour ces ligneIds
        if (ligneIds.isEmpty()) {
            return Collections.emptyList();
        }

        return realisationRepository.findByLigneBudgetaireIdIn(ligneIds)
                .stream()
                .map(realisationMapper::asDto)
                .collect(Collectors.toList());
    }

    // ==================== MÉTHODES D'EXPORT EXCEL ====================

    @Override
    @Journal(actionType = ActionType.READ_DATES_IMPORTANTE)
    public byte[] exportRecettesToExcel(Integer annee, String trimestre, String typePeriode) throws IOException {
        try {
            log.info("Exporting recettes to Excel - Année: {}, Trimestre: {}, Type: {}", annee, trimestre, typePeriode);

            List<RealisationExportDTO> recettesData = getRecettesData(annee, trimestre, typePeriode);
            log.info("Recettes data retrieved: {} items", recettesData.size());

            String periode = getPeriodeLabel(trimestre, typePeriode);

            return excelExportService.exportRecettesToExcel(recettesData, annee, periode, typePeriode);
        } catch (Exception e) {
            log.error("Error exporting recettes to Excel - Année: {}, Trimestre: {}, Type: {}", annee, trimestre, typePeriode, e);
            throw new IOException("Failed to export recettes to Excel: " + e.getMessage(), e);
        }
    }

    @Override
    @Journal(actionType = ActionType.READ_DATES_IMPORTANTE)
    public byte[] exportDepensesToExcel(Integer annee, String trimestre, String typePeriode) throws IOException {
        try {
            log.info("Exporting dépenses to Excel - Année: {}, Trimestre: {}, Type: {}", annee, trimestre, typePeriode);

            List<RealisationExportDTO> depensesData = getDepensesData(annee, trimestre, typePeriode);
            log.info("Dépenses data retrieved: {} items", depensesData.size());

            String periode = getPeriodeLabel(trimestre, typePeriode);

            return excelExportService.exportDepensesToExcel(depensesData, annee, periode, typePeriode);
        } catch (Exception e) {
            log.error("Error exporting dépenses to Excel - Année: {}, Trimestre: {}, Type: {}", annee, trimestre, typePeriode, e);
            throw new IOException("Failed to export dépenses to Excel: " + e.getMessage(), e);
        }
    }

    @Override
    @Journal(actionType = ActionType.READ_DATES_IMPORTANTE)
    public List<RealisationExportDTO> getRecettesData(Integer annee, String trimestre, String typePeriode) {
        try {
            log.info("Getting recettes data - Année: {}, Trimestre: {}, Type: {}", annee, trimestre, typePeriode);

            // Récupérer toutes les réalisations de type CLASSE_7 (recettes) pour l'année donnée
            List<RealisationDTO> realisations = getAllRealisationsByYear(annee, TypeLigneBugetaire.CLASSE_7);
            log.info("Found {} realisations for year {} and type CLASSE_7", realisations.size(), annee);

            if (realisations.isEmpty()) {
                log.warn("No realisations found for year {} and type CLASSE_7", annee);
                // Retourner une liste vide avec juste la ligne TOTAL
                List<RealisationExportDTO> exportData = new ArrayList<>();
                RealisationExportDTO totalDTO = createEmptyTotal("TOTAL RECETTES");
                exportData.add(totalDTO);
                return exportData;
            }

            // DEBUG: Vérifier les ligneBudgetaireId
            log.info(">>> DEBUG: Checking ligneBudgetaireId for {} realisations", realisations.size());
            for (RealisationDTO r : realisations) {
                log.info(">>> Realisation {}: ligneBudgetaireId = {}, ligneBudgetaire = {}",
                    r.getId(),
                    r.getLigneBudgetaireId(),
                    r.getLigneBudgetaire() != null ? r.getLigneBudgetaire().getId() : "null");
            }

            // Grouper par ligne budgétaire
            Map<Long, List<RealisationDTO>> groupedByLigne = realisations.stream()
                    .filter(r -> r.getLigneBudgetaire() != null)
                    .collect(Collectors.groupingBy(r -> r.getLigneBudgetaire().getId()));

            log.info(">>> DEBUG: After grouping by ligne budgétaire: {} groups", groupedByLigne.size());

            List<RealisationExportDTO> exportData = new ArrayList<>();

            // Pour chaque ligne budgétaire, calculer les totaux
            for (Map.Entry<Long, List<RealisationDTO>> entry : groupedByLigne.entrySet()) {
                List<RealisationDTO> ligneRealisations = entry.getValue();

                if (!ligneRealisations.isEmpty()) {
                    RealisationDTO firstRealisation = ligneRealisations.get(0);

                RealisationExportDTO exportDTO = RealisationExportDTO.builder()
                        .libelle(firstRealisation.getLigneBudgetaire() != null ?
                                firstRealisation.getLigneBudgetaire().getRubrique().getLibelle() : "")
                        .codeRubrique(firstRealisation.getCode())
                        .typeDepense("RECETTE")
                        .servicesVotees(firstRealisation.getLigneBudgetaire() != null ?
                                firstRealisation.getLigneBudgetaire().getMontant() : 0.0)
                        .budget(firstRealisation.getLigneBudgetaire() != null ?
                                firstRealisation.getLigneBudgetaire().getMontant() : 0.0)
                        .isTotal(false)
                        .annee(annee)
                        .trimestre(trimestre)
                        .build();

                // Calculer les réalisations par trimestre
                Map<Integer, Double> realisationsParTrimestre = calculateRealisationsByTrimestre(ligneRealisations, annee);

                exportDTO.setRealisationT1(realisationsParTrimestre.getOrDefault(1, 0.0));
                exportDTO.setRealisationT2(realisationsParTrimestre.getOrDefault(2, 0.0));
                exportDTO.setRealisationT3(realisationsParTrimestre.getOrDefault(3, 0.0));
                exportDTO.setRealisationT4(realisationsParTrimestre.getOrDefault(4, 0.0));

                // Calculer les taux de réalisation
                Double budget = exportDTO.getServicesVotees();
                if (budget != null && budget > 0) {
                    exportDTO.setTauxRealisationT1(calculatePercentage(exportDTO.getRealisationT1(), budget));
                    exportDTO.setTauxRealisationT2(calculatePercentage(exportDTO.getRealisationT1() + exportDTO.getRealisationT2(), budget));
                    exportDTO.setTauxRealisationT3(calculatePercentage(
                            exportDTO.getRealisationT1() + exportDTO.getRealisationT2() + exportDTO.getRealisationT3(), budget));
                    exportDTO.setTauxRealisationT4(calculatePercentage(
                            exportDTO.getRealisationT1() + exportDTO.getRealisationT2() +
                                    exportDTO.getRealisationT3() + exportDTO.getRealisationT4(), budget));

                    // Taux global selon le trimestre sélectionné
                    Double realisationCumulee = getRealisationCumuleeByTrimestre(exportDTO, trimestre);
                    exportDTO.setTauxRealisationGlobal(calculatePercentage(realisationCumulee, budget));
                    exportDTO.setEcart(budget - realisationCumulee);
                }

                // Ajouter des observations
                exportDTO.setObservations(generateObservations(exportDTO.getTauxRealisationGlobal()));

                exportData.add(exportDTO);
            }
        }

            // Ajouter la ligne TOTAL
            RealisationExportDTO totalDTO = calculateTotal(exportData, "TOTAL RECETTES");
            exportData.add(totalDTO);

            return exportData;
        } catch (Exception e) {
            log.error("Error getting recettes data - Année: {}, Trimestre: {}, Type: {}", annee, trimestre, typePeriode, e);
            // Retourner une liste vide avec juste la ligne TOTAL en cas d'erreur
            List<RealisationExportDTO> exportData = new ArrayList<>();
            RealisationExportDTO totalDTO = createEmptyTotal("TOTAL RECETTES");
            exportData.add(totalDTO);
            return exportData;
        }
    }

    @Override
    @Journal(actionType = ActionType.READ_DATES_IMPORTANTE)
    public List<RealisationExportDTO> getDepensesData(Integer annee, String trimestre, String typePeriode) {
        try {
            log.info("Getting dépenses data - Année: {}, Trimestre: {}, Type: {}", annee, trimestre, typePeriode);

            // Récupérer toutes les réalisations de type CLASSE_6 (fonctionnement) pour l'année donnée
            List<RealisationDTO> realisations = getAllRealisationsByYear(annee, TypeLigneBugetaire.CLASSE_6);
            log.info("Found {} realisations for year {} and type CLASSE_6", realisations.size(), annee);

            if (realisations.isEmpty()) {
                log.warn("No realisations found for year {} and type CLASSE_6", annee);
                // Retourner une liste vide avec juste la ligne TOTAL
                List<RealisationExportDTO> exportData = new ArrayList<>();
                RealisationExportDTO totalDTO = createEmptyTotal("TOTAL DÉPENSES FONCTIONNEMENT");
                exportData.add(totalDTO);
                return exportData;
            }

            // Grouper par ligne budgétaire
            Map<Long, List<RealisationDTO>> groupedByLigne = realisations.stream()
                    .filter(r -> r.getLigneBudgetaire() != null)
                    .collect(Collectors.groupingBy(r -> r.getLigneBudgetaire().getId()));

            List<RealisationExportDTO> exportData = new ArrayList<>();

            // Pour chaque ligne budgétaire, calculer les totaux
            for (Map.Entry<Long, List<RealisationDTO>> entry : groupedByLigne.entrySet()) {
                List<RealisationDTO> ligneRealisations = entry.getValue();

                if (!ligneRealisations.isEmpty()) {
                    RealisationDTO firstRealisation = ligneRealisations.get(0);

                RealisationExportDTO exportDTO = RealisationExportDTO.builder()
                        .libelle(firstRealisation.getLigneBudgetaire() != null ?
                                firstRealisation.getLigneBudgetaire().getRubrique().getLibelle() : "")
                        .codeRubrique(firstRealisation.getCode())
                        .typeDepense("FONCTIONNEMENT")
                        .budget(firstRealisation.getLigneBudgetaire() != null ?
                                firstRealisation.getLigneBudgetaire().getMontant() : 0.0)
                        .isTotal(false)
                        .annee(annee)
                        .trimestre(trimestre)
                        .build();

                // Calculer les réalisations par trimestre
                Map<Integer, Double> realisationsParTrimestre = calculateRealisationsByTrimestre(ligneRealisations, annee);

                exportDTO.setRealisationT1(realisationsParTrimestre.getOrDefault(1, 0.0));
                exportDTO.setRealisationT2(realisationsParTrimestre.getOrDefault(2, 0.0));
                exportDTO.setRealisationT3(realisationsParTrimestre.getOrDefault(3, 0.0));
                exportDTO.setRealisationT4(realisationsParTrimestre.getOrDefault(4, 0.0));

                // Calculer les taux de réalisation
                Double budget = exportDTO.getBudget();
                if (budget != null && budget > 0) {
                    exportDTO.setTauxRealisationT1(calculatePercentage(exportDTO.getRealisationT1(), budget));
                    exportDTO.setTauxRealisationT2(calculatePercentage(exportDTO.getRealisationT2(), budget));
                    exportDTO.setTauxRealisationT3(calculatePercentage(exportDTO.getRealisationT3(), budget));
                    exportDTO.setTauxRealisationT4(calculatePercentage(exportDTO.getRealisationT4(), budget));

                    // Taux global (cumul jusqu'au trimestre sélectionné)
                    Double realisationCumulee = getRealisationCumuleeByTrimestre(exportDTO, trimestre);
                    exportDTO.setTauxRealisationGlobal(calculatePercentage(realisationCumulee, budget));
                }

                exportData.add(exportDTO);
            }
        }

            // Ajouter la ligne TOTAL
            RealisationExportDTO totalDTO = calculateTotal(exportData, "TOTAL DÉPENSES FONCTIONNEMENT");
            exportData.add(totalDTO);

            return exportData;
        } catch (Exception e) {
            log.error("Error getting dépenses data - Année: {}, Trimestre: {}, Type: {}", annee, trimestre, typePeriode, e);
            // Retourner une liste vide avec juste la ligne TOTAL en cas d'erreur
            List<RealisationExportDTO> exportData = new ArrayList<>();
            RealisationExportDTO totalDTO = createEmptyTotal("TOTAL DÉPENSES FONCTIONNEMENT");
            exportData.add(totalDTO);
            return exportData;
        }
    }

    // ==================== MÉTHODES UTILITAIRES ====================

    /**
     * Récupère toutes les réalisations pour une année et un type donnés
     */
    private List<RealisationDTO> getAllRealisationsByYear(Integer annee, TypeLigneBugetaire typeDepense) {
        try {
            log.info("Fetching realisations for year {} and type {}", annee, typeDepense);

            // Récupération optimisée : filtrage en base de données
            List<RealisationEntity> entities = realisationRepository.findByYearAndType(annee, typeDepense);

            log.info("Found {} realisations for year {} and type {}", entities.size(), annee, typeDepense);

            // Conversion en DTO
            return entities.stream()
                    .map(realisationMapper::asDto)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            log.error("Error fetching realisations for year {} and type {}", annee, typeDepense, e);
            return Collections.emptyList();
        }
    }

    /**
     * Calcule les réalisations par trimestre
     */
    private Map<Integer, Double> calculateRealisationsByTrimestre(List<RealisationDTO> realisations, Integer annee) {
        Map<Integer, Double> result = new HashMap<>();
        result.put(1, 0.0);
        result.put(2, 0.0);
        result.put(3, 0.0);
        result.put(4, 0.0);

        for (RealisationDTO realisation : realisations) {
            LocalDate date = realisation.getDate();
            if (date != null && date.getYear() == annee) {
                int trimestre = (date.getMonthValue() - 1) / 3 + 1;
                Double currentValue = result.get(trimestre);
                result.put(trimestre, currentValue + (realisation.getMontant() != null ? realisation.getMontant() : 0.0));
            }
        }

        return result;
    }

    /**
     * Calcule le pourcentage
     */
    private Double calculatePercentage(Double value, Double total) {
        if (total == null || total == 0) {
            return 0.0;
        }
        return (value != null ? value : 0.0) * 100 / total;
    }

    /**
     * Récupère la réalisation cumulée jusqu'à un trimestre donné
     */
    private Double getRealisationCumuleeByTrimestre(RealisationExportDTO exportDTO, String trimestre) {
        if (trimestre == null) {
            return exportDTO.getRealisationT1() + exportDTO.getRealisationT2() +
                    exportDTO.getRealisationT3() + exportDTO.getRealisationT4();
        }

        switch (trimestre.toUpperCase()) {
            case "T1":
                return exportDTO.getRealisationT1();
            case "T2":
                return exportDTO.getRealisationT1() + exportDTO.getRealisationT2();
            case "T3":
                return exportDTO.getRealisationT1() + exportDTO.getRealisationT2() + exportDTO.getRealisationT3();
            case "T4":
                return exportDTO.getRealisationT1() + exportDTO.getRealisationT2() +
                        exportDTO.getRealisationT3() + exportDTO.getRealisationT4();
            default:
                return 0.0;
        }
    }

    /**
     * Génère des observations basées sur le taux de réalisation
     */
    private String generateObservations(Double tauxRealisation) {
        if (tauxRealisation == null) {
            return "";
        }

        if (tauxRealisation < 50) {
            return "Attention : Taux faible";
        } else if (tauxRealisation < 80) {
            return "En cours de réalisation";
        } else if (tauxRealisation >= 100) {
            return "Objectif atteint";
        } else {
            return "Bon niveau de réalisation";
        }
    }

    /**
     * Calcule la ligne de total
     */
    private RealisationExportDTO calculateTotal(List<RealisationExportDTO> data, String libelle) {
        RealisationExportDTO total = RealisationExportDTO.builder()
                .libelle(libelle)
                .isTotal(true)
                .budget(0.0)
                .servicesVotees(0.0)
                .realisationT1(0.0)
                .realisationT2(0.0)
                .realisationT3(0.0)
                .realisationT4(0.0)
                .ecart(0.0)
                .build();

        for (RealisationExportDTO item : data) {
            if (!Boolean.TRUE.equals(item.getIsTotal())) {
                total.setBudget(total.getBudget() + (item.getBudget() != null ? item.getBudget() : 0.0));
                total.setServicesVotees(total.getServicesVotees() + (item.getServicesVotees() != null ? item.getServicesVotees() : 0.0));
                total.setRealisationT1(total.getRealisationT1() + (item.getRealisationT1() != null ? item.getRealisationT1() : 0.0));
                total.setRealisationT2(total.getRealisationT2() + (item.getRealisationT2() != null ? item.getRealisationT2() : 0.0));
                total.setRealisationT3(total.getRealisationT3() + (item.getRealisationT3() != null ? item.getRealisationT3() : 0.0));
                total.setRealisationT4(total.getRealisationT4() + (item.getRealisationT4() != null ? item.getRealisationT4() : 0.0));
                total.setEcart(total.getEcart() + (item.getEcart() != null ? item.getEcart() : 0.0));
            }
        }

        // Calculer les taux totaux
        Double budgetTotal = total.getBudget() != null && total.getBudget() > 0 ? total.getBudget() :
                (total.getServicesVotees() != null && total.getServicesVotees() > 0 ? total.getServicesVotees() : 1.0);

        total.setTauxRealisationT1(calculatePercentage(total.getRealisationT1(), budgetTotal));
        total.setTauxRealisationT2(calculatePercentage(total.getRealisationT2(), budgetTotal));
        total.setTauxRealisationT3(calculatePercentage(total.getRealisationT3(), budgetTotal));
        total.setTauxRealisationT4(calculatePercentage(total.getRealisationT4(), budgetTotal));

        Double realisationTotale = total.getRealisationT1() + total.getRealisationT2() +
                total.getRealisationT3() + total.getRealisationT4();
        total.setTauxRealisationGlobal(calculatePercentage(realisationTotale, budgetTotal));

        return total;
    }

    /**
     * Génère le libellé de la période
     */
    private String getPeriodeLabel(String trimestre, String typePeriode) {
        if ("trimestriel".equalsIgnoreCase(typePeriode) && trimestre != null) {
            switch (trimestre.toUpperCase()) {
                case "T1":
                    return "1er Trimestre";
                case "T2":
                    return "2ème Trimestre";
                case "T3":
                    return "3ème Trimestre";
                case "T4":
                    return "4ème Trimestre";
                default:
                    return trimestre;
            }
        } else if ("annuel".equalsIgnoreCase(typePeriode)) {
            return "Année complète";
        } else if ("mensuel".equalsIgnoreCase(typePeriode)) {
            return "Mensuel";
        }
        return trimestre != null ? trimestre : "";
    }

    /**
     * Crée une ligne de total vide (pour les cas où il n'y a pas de données)
     */
    private RealisationExportDTO createEmptyTotal(String libelle) {
        return RealisationExportDTO.builder()
                .libelle(libelle)
                .isTotal(true)
                .budget(0.0)
                .servicesVotees(0.0)
                .realisationT1(0.0)
                .realisationT2(0.0)
                .realisationT3(0.0)
                .realisationT4(0.0)
                .tauxRealisationT1(0.0)
                .tauxRealisationT2(0.0)
                .tauxRealisationT3(0.0)
                .tauxRealisationT4(0.0)
                .tauxRealisationGlobal(0.0)
                .ecart(0.0)
                .build();
    }
}
