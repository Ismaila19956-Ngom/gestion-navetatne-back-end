package com.webgram.dgpsn.services;

import com.webgram.dgpsn.entities.*;
import com.webgram.dgpsn.entities.enums.TypeProjet;
import com.webgram.dgpsn.models.responses.ptba.PtbaActivityDTO;
import com.webgram.dgpsn.models.responses.ptba.PtbaFundingSourcesDTO;
import com.webgram.dgpsn.models.responses.ptba.PtbaResponseDTO;
import com.webgram.dgpsn.models.responses.ptba.PtbaTrimesterDTO;
import com.webgram.dgpsn.repositories.BudgetDgpsnRepository;
import com.webgram.dgpsn.repositories.FundingSourceRepository;
import com.webgram.dgpsn.repositories.ManagementUnitRepository;
import com.webgram.dgpsn.repositories.TacheRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class PtbaService {

    private final ManagementUnitRepository managementUnitRepository;
    private final TacheRepository tacheRepository;
    private final FundingSourceRepository fundingSourceRepository;
    private final BudgetDgpsnRepository budgetDgpsnRepository;

    /**
     * Point d'entrée flexible pour générer le PTBA.
     * Priorise le budgetId, sinon utilise l'année pour une vue consolidée.
     */
    public PtbaResponseDTO generatePtbaFlexible(Long budgetId, Integer annee) {
        if (budgetId != null) {
            return generatePtbaForSingleBudget(budgetId);
        } else if (annee != null) {
            return generatePtbaForYear(annee);
        } else {
            throw new IllegalArgumentException("Vous devez spécifier soit un budgetId, soit une année.");
        }
    }

    /**
     * Génère le PTBA pour un budget unique en suivant la hiérarchie des Management Units.
     */
    private PtbaResponseDTO generatePtbaForSingleBudget(Long budgetId) {
        log.info("Début de la génération du PTBA pour le budget ID: {}", budgetId);

        BudgetDgpsnEntity budget = budgetDgpsnRepository.findById(budgetId)
                .orElseThrow(() -> new EntityNotFoundException("Budget non trouvé avec l'id : " + budgetId));

        // --- ÉTAPE 1 : RÉCUPÉRATION DES OBJECTIFS RACINES LIÉS AU BUDGET ---
        List<ManagementUnitEntity> objectifs = managementUnitRepository.findByBudgetDgpsnIdAndType(budgetId, TypeProjet.OBJECTIF);
        if (objectifs.isEmpty()) {
            return buildEmptyResponse(budget.getLibelle(), budget.getCode(), budget.getAnnee(), Collections.emptyList(), Collections.emptySet());
        }
        log.debug("{} objectifs trouvés pour le budget {}", objectifs.size(), budget.getCode());

        // --- ÉTAPE 2 : RÉCUPÉRATION DE TOUTE LA HIÉRARCHIE DESCENDANTE ---
        List<PtbaActivityDTO> allActivitiesDTO = generateActivitiesFromHierarchy(objectifs);

        // --- ÉTAPE 3 : CONSTRUCTION DE LA RÉPONSE FINALE ---
        Set<String> allBailleurs = allActivitiesDTO.stream()
                .flatMap(activity -> activity.getSourcesFinancement().getSources().keySet().stream())
                .collect(Collectors.toSet());

        return buildFinalResponse(budget.getLibelle(), budget.getCode(), budget.getAnnee(), allActivitiesDTO, allBailleurs);
    }

    /**
     * Génère un PTBA consolidé en agrégeant les données de tous les budgets d'une année donnée.
     */
    private PtbaResponseDTO generatePtbaForYear(Integer annee) {
        log.info("Génération du PTBA consolidé pour l'année: {}", annee);

        List<BudgetDgpsnEntity> budgetsForYear = budgetDgpsnRepository.findByAnnee(annee);
        if (budgetsForYear.isEmpty()) {
            return buildEmptyResponse("PTBA Consolidé " + annee, "CONSO-" + annee, annee, Collections.emptyList(), Collections.emptySet());
        }

        List<PtbaActivityDTO> consolidatedActivities = new ArrayList<>();
        Set<String> consolidatedBailleurs = new HashSet<>();

        for (BudgetDgpsnEntity budget : budgetsForYear) {
            List<ManagementUnitEntity> objectifs = managementUnitRepository.findByBudgetDgpsnIdAndType(budget.getId(), TypeProjet.OBJECTIF);
            if (!objectifs.isEmpty()) {
                consolidatedActivities.addAll(generateActivitiesFromHierarchy(objectifs));
            }
        }

        consolidatedBailleurs = consolidatedActivities.stream()
                .flatMap(activity -> activity.getSourcesFinancement().getSources().keySet().stream())
                .collect(Collectors.toSet());

        return buildFinalResponse("PTBA Consolidé " + annee, "CONSO-" + annee, annee, consolidatedActivities, consolidatedBailleurs);
    }

    /**
     * Factorisation de la logique de parcours de la hiérarchie et de génération des DTO d'activité.
     */
    private List<PtbaActivityDTO> generateActivitiesFromHierarchy(List<ManagementUnitEntity> objectifs) {
        List<Long> objectifIds = objectifs.stream().map(ManagementUnitEntity::getId).collect(Collectors.toList());
        List<ManagementUnitEntity> actions = getChildrenInBatch(objectifIds, TypeProjet.ACTION);
        Map<Long, List<ManagementUnitEntity>> actionsByObjectifId = groupById(actions, mu -> mu.getParent().getId());

        List<Long> actionIds = actions.stream().map(ManagementUnitEntity::getId).collect(Collectors.toList());
        List<ManagementUnitEntity> activites = getChildrenInBatch(actionIds, TypeProjet.ACTIVITY);
        Map<Long, List<ManagementUnitEntity>> activitesByActionId = groupById(activites, mu -> mu.getParent().getId());

        List<Long> activiteIds = activites.stream().map(ManagementUnitEntity::getId).collect(Collectors.toList());
        List<ManagementUnitEntity> indicateurs = getChildrenInBatch(activiteIds, TypeProjet.INDICATOR);
        Map<Long, List<ManagementUnitEntity>> indicateursByActiviteId = groupById(indicateurs, mu -> mu.getParent().getId());

        List<Long> indicateurIds = indicateurs.stream().map(ManagementUnitEntity::getId).collect(Collectors.toList());
        Map<Long, List<TacheEntity>> tachesByIndicateurId = getTachesInBatch(indicateurIds);

        List<Long> allTacheIds = tachesByIndicateurId.values().stream().flatMap(List::stream).map(TacheEntity::getId).collect(Collectors.toList());
        Map<Long, List<FundingSourceEntity>> sourcesByActiviteId = getFundingSourcesInBatch(activiteIds);
        Map<Long, List<FundingSourceEntity>> sourcesByIndicateurId = getFundingSourcesInBatch(indicateurIds);
        Map<Long, List<FundingSourceEntity>> sourcesByTacheId = getFundingSourcesForTachesInBatch(allTacheIds);

        Set<String> allBailleurs = new HashSet<>();
        List<PtbaActivityDTO> allActivitiesDTO = new ArrayList<>();

        for (ManagementUnitEntity objectif : objectifs) {
            for (ManagementUnitEntity action : actionsByObjectifId.getOrDefault(objectif.getId(), Collections.emptyList())) {
                for (ManagementUnitEntity activite : activitesByActionId.getOrDefault(action.getId(), Collections.emptyList())) {
                    List<ManagementUnitEntity> relatedIndicateurs = indicateursByActiviteId.getOrDefault(activite.getId(), Collections.emptyList());
                    allActivitiesDTO.add(buildPtbaActivityDTO(
                            objectif, action, activite, relatedIndicateurs,
                            tachesByIndicateurId, sourcesByActiviteId, sourcesByIndicateurId, sourcesByTacheId, allBailleurs));
                }
            }
        }
        return allActivitiesDTO;
    }

    private PtbaActivityDTO buildPtbaActivityDTO(
            ManagementUnitEntity objectif, ManagementUnitEntity action, ManagementUnitEntity activite,
            List<ManagementUnitEntity> indicateurs,
            Map<Long, List<TacheEntity>> tachesByIndicateurId,
            Map<Long, List<FundingSourceEntity>> sourcesByActiviteId,
            Map<Long, List<FundingSourceEntity>> sourcesByIndicateurId,
            Map<Long, List<FundingSourceEntity>> sourcesByTacheId,
            Set<String> allBailleurs) {

        List<TacheEntity> allTachesForActivity = indicateurs.stream()
                .flatMap(ind -> tachesByIndicateurId.getOrDefault(ind.getId(), Collections.emptyList()).stream())
                .collect(Collectors.toList());

        PtbaActivityDTO dto = PtbaActivityDTO.builder()
                .id(activite.getId())
                .objectif(objectif.getName())
                .objectifCode(objectif.getNomenclature())
                .action(action.getName())
                .actionCode(action.getCode())
                .activite(activite.getName())
                .activiteCode(activite.getCode())
                .build();

        dto.setIndicateurs(indicateurs.stream().map(ManagementUnitEntity::getName).filter(Objects::nonNull).collect(Collectors.joining("\n")));
        dto.setTaches(allTachesForActivity.stream().map(TacheEntity::getCommentaire).filter(Objects::nonNull).collect(Collectors.joining("\n")));

        buildMonthlyPlanning(dto, allTachesForActivity);

        dto.setActeurResponsable(activite.getStructuresResponsables().stream().map(StructureEntity::getNom).collect(Collectors.joining(", ")));
        dto.setActeurImplique(activite.getActorsInvolved().stream().map(StructureEntity::getNom).collect(Collectors.joining(", ")));

        PtbaFundingSourcesDTO fundingSources = aggregateFundingSources(activite, indicateurs, allTachesForActivity,
                sourcesByActiviteId, sourcesByIndicateurId, sourcesByTacheId, allBailleurs);
        dto.setSourcesFinancement(fundingSources);
        dto.setCoutCFA(fundingSources.getTotal());

        dto.setSourcesVerification(activite.getVerificationSources().stream().map(LabelEntity::getLibelle).collect(Collectors.joining(", ")));
        dto.setObservations(activite.getDescription());

        return dto;
    }

    private PtbaFundingSourcesDTO aggregateFundingSources(
            ManagementUnitEntity activite, List<ManagementUnitEntity> indicateurs, List<TacheEntity> taches,
            Map<Long, List<FundingSourceEntity>> sourcesByActiviteId,
            Map<Long, List<FundingSourceEntity>> sourcesByIndicateurId,
            Map<Long, List<FundingSourceEntity>> sourcesByTacheId,
            Set<String> allBailleurs) {

        PtbaFundingSourcesDTO dto = new PtbaFundingSourcesDTO();
        addFundingSourcesToDTO(sourcesByActiviteId.getOrDefault(activite.getId(), Collections.emptyList()), dto, allBailleurs);
        for (ManagementUnitEntity indicateur : indicateurs) {
            addFundingSourcesToDTO(sourcesByIndicateurId.getOrDefault(indicateur.getId(), Collections.emptyList()), dto, allBailleurs);
        }
        for (TacheEntity tache : taches) {
            addFundingSourcesToDTO(sourcesByTacheId.getOrDefault(tache.getId(), Collections.emptyList()), dto, allBailleurs);
        }
        return dto;
    }

    private void buildMonthlyPlanning(PtbaActivityDTO dto, List<TacheEntity> taches) {
        dto.setTrimestre1(new PtbaTrimesterDTO());
        dto.setTrimestre2(new PtbaTrimesterDTO());
        dto.setTrimestre3(new PtbaTrimesterDTO());
        dto.setTrimestre4(new PtbaTrimesterDTO());

        for (TacheEntity tache : taches) {
            if (tache.getMois() != null && tache.getMois() >= 1 && tache.getMois() <= 12) {
                markCorrectMonthAsActive(dto, tache.getMois());
            }
        }
    }

    /**
     * CORRIGÉ : Coche le bon mois dans le bon trimestre DTO.
     * @param dto L'activité DTO contenant les 4 trimestres.
     * @param mois Le numéro du mois (1 pour Janvier, 12 pour Décembre).
     */
    private void markCorrectMonthAsActive(PtbaActivityDTO dto, int mois) {
        switch (mois) {
            case 1: dto.getTrimestre1().setJanvier(true); break;
            case 2: dto.getTrimestre1().setFevrier(true); break;
            case 3: dto.getTrimestre1().setMars(true); break;
            case 4: dto.getTrimestre2().setAvril(true); break;
            case 5: dto.getTrimestre2().setMai(true); break;
            case 6: dto.getTrimestre2().setJuin(true); break;
            case 7: dto.getTrimestre3().setJuillet(true); break;
            case 8: dto.getTrimestre3().setAout(true); break;
            case 9: dto.getTrimestre3().setSeptembre(true); break;
            case 10: dto.getTrimestre4().setOctobre(true); break;
            case 11: dto.getTrimestre4().setNovembre(true); break;
            case 12: dto.getTrimestre4().setDecembre(true); break;
            default: // Ne fait rien si le mois est invalide
        }
    }

    private void addFundingSourcesToDTO(List<FundingSourceEntity> sources, PtbaFundingSourcesDTO dto, Set<String> allBailleurs) {
        for (FundingSourceEntity source : sources) {
            if (source.getStructure() != null && source.getMontant() != null && source.getMontant() > 0) {
                String bailleurName = source.getStructure().getNom();
                dto.addSource(bailleurName, source.getMontant());
                allBailleurs.add(bailleurName);
            }
        }
    }

    // --- MÉTHODES UTILITAIRES DE PERFORMANCE ET DE LOGIQUE (INCHANGÉES) ---
    private List<ManagementUnitEntity> getChildrenInBatch(List<Long> parentIds, TypeProjet type) {
        if (parentIds == null || parentIds.isEmpty()) return Collections.emptyList();
        return managementUnitRepository.findByParentIdInAndType(parentIds, type);
    }
    private Map<Long, List<TacheEntity>> getTachesInBatch(List<Long> indicateurIds) {
        if (indicateurIds == null || indicateurIds.isEmpty()) return Collections.emptyMap();
        return tacheRepository.findByIndicatorIdIn(indicateurIds).stream()
                .collect(Collectors.groupingBy(t -> t.getIndicator().getId()));
    }
    private Map<Long, List<FundingSourceEntity>> getFundingSourcesInBatch(List<Long> managementUnitIds) {
        if (managementUnitIds == null || managementUnitIds.isEmpty()) return Collections.emptyMap();
        return fundingSourceRepository.findByManagementUnitIdIn(managementUnitIds).stream()
                .collect(Collectors.groupingBy(fs -> fs.getManagementUnit().getId()));
    }
    private Map<Long, List<FundingSourceEntity>> getFundingSourcesForTachesInBatch(List<Long> tacheIds) {
        if (tacheIds == null || tacheIds.isEmpty()) return Collections.emptyMap();
        return fundingSourceRepository.findByTacheIdIn(tacheIds).stream()
                .collect(Collectors.groupingBy(fs -> fs.getTache().getId()));
    }
    private <T> Map<Long, List<T>> groupById(List<T> list, Function<T, Long> idExtractor) {
        if (list == null || list.isEmpty()) return Collections.emptyMap();
        return list.stream().collect(Collectors.groupingBy(idExtractor));
    }
    private PtbaResponseDTO buildFinalResponse(String name, String code, Integer annee, List<PtbaActivityDTO> activities, Set<String> bailleurs) {
        PtbaResponseDTO response = PtbaResponseDTO.builder()
                .projetId(null) // ID non pertinent ici, la référence est le budget/année
                .projetCode(code)
                .projetName(name)
                .annee(annee)
                .activities(activities)
                .bailleurs(bailleurs)
                .build();

        response.setTotalGeneral(activities.stream().mapToDouble(PtbaActivityDTO::getCoutCFA).sum());
        return response;
    }

    private PtbaResponseDTO buildEmptyResponse(String name, String code, Integer annee, List<PtbaActivityDTO> activities, Set<String> bailleurs) {
        return PtbaResponseDTO.builder()
                .projetId(null)
                .projetCode(code)
                .projetName(name)
                .annee(annee)
                .activities(activities)
                .bailleurs(bailleurs)
                .totalGeneral(0.0)
                .build();
    }}
