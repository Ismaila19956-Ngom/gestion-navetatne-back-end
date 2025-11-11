package com.webgram.dgpsn.services;

import com.webgram.dgpsn.entities.*;
import com.webgram.dgpsn.entities.enums.TypeLigneBugetaire;
import com.webgram.dgpsn.entities.enums.TypeProjet;
import com.webgram.dgpsn.models.responses.ptba.PtbaActivityDTO;
import com.webgram.dgpsn.models.responses.ptba.PtbaFundingSourcesDTO;
import com.webgram.dgpsn.models.responses.ptba.PtbaResponseDTO;
import com.webgram.dgpsn.models.responses.ptba.PtbaTrimesterDTO;
import com.webgram.dgpsn.repositories.*;
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
    private final ValueIndicatorRepository valueIndicatorRepository;
    private final BudgetDgpsnRepository budgetDgpsnRepository;

    /**
     * Génère le PTBA complet pour un projet donné de manière optimisée.
     * Cette méthode utilise une stratégie de récupération par lots pour éviter le problème N+1.
     */
    public PtbaResponseDTO generatePtba(Long projetId, Integer annee) {
        log.info("Début de la génération du PTBA pour le projet ID: {}", projetId);

        // --- VALIDATION INITIALE ---
        ManagementUnitEntity projet = managementUnitRepository.findById(projetId)
                .orElseThrow(() -> new RuntimeException("Projet non trouvé avec l'id : " + projetId));

        if (!TypeProjet.PROJECT.equals(projet.getType()) && !TypeProjet.PROGRAMME.equals(projet.getType())) {
            throw new RuntimeException("L'entité doit être de type PROGRAMME ou PROJECT");
        }

        // --- ÉTAPE 1 : RÉCUPÉRATION EFFICACE DE TOUTE LA HIÉRARCHIE ---
        log.info("Étape 1: Récupération de la hiérarchie (Objectifs, Actions, Activités)");
        List<ManagementUnitEntity> objectifs = managementUnitRepository.findByParentIdAndType(projet.getId(), TypeProjet.OBJECTIF);
        if (objectifs.isEmpty()) {
            return buildEmptyResponse(projet, annee); // Retourner une réponse vide si pas d'objectifs
        }

        List<Long> objectifIds = objectifs.stream().map(ManagementUnitEntity::getId).collect(Collectors.toList());
        List<ManagementUnitEntity> actions = managementUnitRepository.findByParentIdInAndType(objectifIds, TypeProjet.ACTION);
        Map<Long, List<ManagementUnitEntity>> actionsByObjectifId = actions.stream()
                .collect(Collectors.groupingBy(action -> action.getParent().getId()));

        List<Long> actionIds = actions.stream().map(ManagementUnitEntity::getId).collect(Collectors.toList());
        List<ManagementUnitEntity> activites = !actionIds.isEmpty()
                ? managementUnitRepository.findByParentIdInAndType(actionIds, TypeProjet.ACTIVITY)
                : Collections.emptyList();
        Map<Long, List<ManagementUnitEntity>> activitesByActionId = activites.stream()
                .collect(Collectors.groupingBy(activite -> activite.getParent().getId()));


        // --- ÉTAPE 2 : RÉCUPÉRATION PAR LOTS DES DONNÉES LIÉES AUX ACTIVITÉS ---
        log.info("Étape 2: Récupération des données liées (Tâches, Indicateurs)");
        List<Long> activiteIds = activites.stream().map(ManagementUnitEntity::getId).collect(Collectors.toList());
        Map<Long, List<TacheEntity>> tachesByActiviteId = new HashMap<>();
        Map<Long, List<ValueIndicatorEntity>> indicateursByActiviteId = new HashMap<>();

        if (!activiteIds.isEmpty()) {
            tachesByActiviteId = tacheRepository.findByActiviteIdIn(activiteIds)
                    .stream().collect(Collectors.groupingBy(tache -> tache.getActivite().getId()));

            indicateursByActiviteId = valueIndicatorRepository.findByActivityIdIn(activiteIds)
                    .stream().collect(Collectors.groupingBy(ind -> ind.getActivity().getId()));
        }

        // --- ÉTAPE 3 : RÉCUPÉRATION PAR LOTS DE TOUTES LES SOURCES DE FINANCEMENT ---
        log.info("Étape 3: Récupération de toutes les sources de financement");
        List<Long> allTacheIds = tachesByActiviteId.values().stream().flatMap(List::stream).map(TacheEntity::getId).collect(Collectors.toList());
        List<Long> allIndicateurIds = indicateursByActiviteId.values().stream().flatMap(List::stream).map(ValueIndicatorEntity::getId).collect(Collectors.toList());

        Map<Long, List<FundingSourceEntity>> sourcesByActiviteId = getFundingSources(fundingSourceRepository::findByManagementUnitIdIn, activiteIds, fs -> fs.getManagementUnit().getId());
        Map<Long, List<FundingSourceEntity>> sourcesByTacheId = getFundingSources(fundingSourceRepository::findByTacheIdIn, allTacheIds, fs -> fs.getTache().getId());
        Map<Long, List<FundingSourceEntity>> sourcesByIndicateurId = getFundingSources(fundingSourceRepository::findByValueIndicatorIdIn, allIndicateurIds, fs -> fs.getValueIndicator().getId());

        // --- ÉTAPE 4 : ASSEMBLAGE DES DTOS EN MÉMOIRE (AUCUN APPEL BD) ---
        log.info("Étape 4: Assemblage des DTOs en mémoire");
        Set<String> allBailleurs = new HashSet<>();
        List<PtbaActivityDTO> allActivitiesDTO = new ArrayList<>();

        for (ManagementUnitEntity objectif : objectifs) {
            for (ManagementUnitEntity action : actionsByObjectifId.getOrDefault(objectif.getId(), Collections.emptyList())) {
                for (ManagementUnitEntity activite : activitesByActionId.getOrDefault(action.getId(), Collections.emptyList())) {

                    List<TacheEntity> relatedTaches = tachesByActiviteId.getOrDefault(activite.getId(), Collections.emptyList());
                    List<ValueIndicatorEntity> relatedIndicateurs = indicateursByActiviteId.getOrDefault(activite.getId(), Collections.emptyList());

                    PtbaActivityDTO activityDTO = buildPtbaActivityDTO(
                            objectif, action, activite,
                            relatedTaches, relatedIndicateurs,
                            sourcesByActiviteId, sourcesByTacheId, sourcesByIndicateurId,
                            allBailleurs
                    );
                    allActivitiesDTO.add(activityDTO);
                }
            }
        }

        // --- ÉTAPE 5 : CONSTRUCTION DE LA RÉPONSE FINALE ET CALCUL DES TOTAUX ---
        log.info("Étape 5: Calcul des totaux et finalisation de la réponse");
        PtbaResponseDTO response = buildFinalResponse(projet, annee, allActivitiesDTO, allBailleurs);
        calculateTotals(response); // Calcule les totaux à la fin

        return response;
    }

    /**
     * Construit le DTO d'une activité PTBA à partir des données pré-chargées.
     */
    private PtbaActivityDTO buildPtbaActivityDTO(
            ManagementUnitEntity objectif, ManagementUnitEntity action, ManagementUnitEntity activite,
            List<TacheEntity> taches, List<ValueIndicatorEntity> indicateurs,
            Map<Long, List<FundingSourceEntity>> sourcesByActiviteId,
            Map<Long, List<FundingSourceEntity>> sourcesByTacheId,
            Map<Long, List<FundingSourceEntity>> sourcesByIndicateurId,
            Set<String> allBailleurs) {

        PtbaActivityDTO dto = PtbaActivityDTO.builder()
                .id(activite.getId())
                .objectif(objectif.getName())
                .objectifCode(objectif.getNomenclature())
                .action(action.getName())
                .actionCode(action.getCode())
                .activite(activite.getName())
                .activiteCode(activite.getCode())
                .build();

        // Indicateurs et Tâches (textes concaténés)
        dto.setIndicateurs(indicateurs.stream().map(ind -> ind.getIndicatorProjet().getIndicator().getLibelle()).collect(Collectors.joining("; ")));
        dto.setTaches(taches.stream().map(TacheEntity::getCommentaire).filter(Objects::nonNull).collect(Collectors.joining("; "))); // Utilise le commentaire comme nom de la tâche

        // Planification mensuelle
        buildMonthlyPlanning(dto, taches);

        // Acteurs
        if (activite.getResponsible() != null) {
            dto.setActeurResponsable(activite.getResponsible().getPrenom() + " " + activite.getResponsible().getNom());
        }
        if (activite.getActorsInvolved() != null && !activite.getActorsInvolved().isEmpty()) {
            dto.setActeurImplique(activite.getActorsInvolved().stream().map(StructureEntity::getNom).collect(Collectors.joining(", ")));
        }

        // Sources de financement
        PtbaFundingSourcesDTO fundingSources = buildFundingSources(activite, taches, indicateurs, sourcesByActiviteId, sourcesByTacheId, sourcesByIndicateurId, allBailleurs);
        dto.setSourcesFinancement(fundingSources);
        dto.setCoutCFA(fundingSources.getTotal());

        // Sources de vérification et observations
        if (activite.getVerificationSources() != null && !activite.getVerificationSources().isEmpty()) {
            dto.setSourcesVerification(activite.getVerificationSources().stream().map(LabelEntity::getLibelle).collect(Collectors.joining(", ")));
        }
        dto.setObservations(activite.getDescription());

        return dto;
    }

    /**
     * Construit les sources de financement pour une activité à partir des données pré-chargées.
     */
    private PtbaFundingSourcesDTO buildFundingSources(
            ManagementUnitEntity activite, List<TacheEntity> taches, List<ValueIndicatorEntity> indicateurs,
            Map<Long, List<FundingSourceEntity>> sourcesByActiviteId,
            Map<Long, List<FundingSourceEntity>> sourcesByTacheId,
            Map<Long, List<FundingSourceEntity>> sourcesByIndicateurId,
            Set<String> allBailleurs) {

        PtbaFundingSourcesDTO dto = new PtbaFundingSourcesDTO();

        // Sources de l'activité elle-même
        addFundingSourcesToDTO(sourcesByActiviteId.getOrDefault(activite.getId(), Collections.emptyList()), dto, allBailleurs);

        // Sources des tâches liées
        for (TacheEntity tache : taches) {
            addFundingSourcesToDTO(sourcesByTacheId.getOrDefault(tache.getId(), Collections.emptyList()), dto, allBailleurs);
        }

        // Sources des indicateurs liés
        for (ValueIndicatorEntity indicateur : indicateurs) {
            addFundingSourcesToDTO(sourcesByIndicateurId.getOrDefault(indicateur.getId(), Collections.emptyList()), dto, allBailleurs);
        }

        return dto;
    }

    /**
     * Construit la planification mensuelle à partir de la liste des tâches.
     */
    private void buildMonthlyPlanning(PtbaActivityDTO dto, List<TacheEntity> taches) {
        dto.setTrimestre1(new PtbaTrimesterDTO());
        dto.setTrimestre2(new PtbaTrimesterDTO());
        dto.setTrimestre3(new PtbaTrimesterDTO());
        dto.setTrimestre4(new PtbaTrimesterDTO());

        for (TacheEntity tache : taches) {
            if (tache.getTrimestre() != null && tache.getMois() != null) {
                markMonthAsActive(tache.getTrimestre(), tache.getMois(), dto);
            }
        }
    }

    private void markMonthAsActive(Integer trimestre, Integer mois, PtbaActivityDTO dto) {
        PtbaTrimesterDTO targetTrim;
        switch (trimestre) {
            case 1: targetTrim = dto.getTrimestre1(); break;
            case 2: targetTrim = dto.getTrimestre2(); break;
            case 3: targetTrim = dto.getTrimestre3(); break;
            case 4: targetTrim = dto.getTrimestre4(); break;
            default: return;
        }

        switch (mois) {
            case 1: targetTrim.setJanvier(true); break;
            case 2: targetTrim.setFevrier(true); break;
            case 3: targetTrim.setMars(true); break;
            case 4: targetTrim.setAvril(true); break;
            case 5: targetTrim.setMai(true); break;
            case 6: targetTrim.setJuin(true); break;
            case 7: targetTrim.setJuillet(true); break;
            case 8: targetTrim.setAout(true); break;
            case 9: targetTrim.setSeptembre(true); break;
            case 10: targetTrim.setOctobre(true); break;
            case 11: targetTrim.setNovembre(true); break;
            case 12: targetTrim.setDecembre(true); break;
        }
    }

    /**
     * Ajoute des sources de financement au DTO et met à jour l'ensemble global des bailleurs.
     */
    private void addFundingSourcesToDTO(List<FundingSourceEntity> sources, PtbaFundingSourcesDTO dto, Set<String> allBailleurs) {
        for (FundingSourceEntity source : sources) {
            if (source.getStructure() != null && source.getMontant() != null && source.getMontant() > 0) {
                String bailleurName = source.getStructure().getNom();
                dto.addSource(bailleurName, source.getMontant());
                allBailleurs.add(bailleurName); // Ajoute le bailleur à la liste globale
            }
        }
    }

    /**
     * Calcule tous les totaux
     */
//    private void calculateTotals(PtbaResponseDTO response) {
//        Map<String, Double> totauxObjectif = new HashMap<>();
//        Map<String, Double> totauxAction = new HashMap<>();
//        Map<String, Double> totauxSourcesGlobal = new HashMap<>();
//        double totalGeneral = 0.0;
//
//        for (PtbaActivityDTO activity : response.getActivities()) {
//            // Total par objectif
//            String objectifKey = activity.getObjectif();
//            totauxObjectif.put(objectifKey,
//                    totauxObjectif.getOrDefault(objectifKey, 0.0) + activity.getCoutCFA());
//
//            // Total par action
//            String actionKey = activity.getObjectif() + " > " + activity.getAction();
//            totauxAction.put(actionKey,
//                    totauxAction.getOrDefault(actionKey, 0.0) + activity.getCoutCFA());
//
//            // Total général
//            totalGeneral += activity.getCoutCFA();
//
//            // Totaux des sources de financement
//            if (activity.getSourcesFinancement() != null && activity.getSourcesFinancement().getSources() != null) {
//                activity.getSourcesFinancement().getSources().forEach((bailleur, montant) -> {
//                    totauxSourcesGlobal.put(bailleur,
//                            totauxSourcesGlobal.getOrDefault(bailleur, 0.0) + montant);
//                });
//            }
//        }
//
//        response.setTotauxParObjectif(totauxObjectif);
//        response.setTotauxParAction(totauxAction);
//        response.setTotalGeneral(totalGeneral);
//        response.setTotauxSourcesFinancement(totauxSourcesGlobal);
//    }


    // --- MÉTHODES UTILITAIRES ---

    private <T, K> Map<K, List<FundingSourceEntity>> getFundingSources(
            Function<List<T>, List<FundingSourceEntity>> fetchFunction,
            List<T> ids,
            Function<FundingSourceEntity, K> keyExtractor) {
        if (ids == null || ids.isEmpty()) {
            return Collections.emptyMap();
        }
        return fetchFunction.apply(ids).stream().collect(Collectors.groupingBy(keyExtractor));
    }

    private PtbaResponseDTO buildEmptyResponse(ManagementUnitEntity projet, Integer annee) {
        return PtbaResponseDTO.builder()
                .projetId(projet.getId())
                .projetCode(projet.getCode())
                .projetName(projet.getName())
                .annee(annee != null ? annee : projet.getAnneeDebut())
                .activities(Collections.emptyList())
                .bailleurs(Collections.emptySet())
                .totalGeneral(0.0)
                .build();
    }

    private PtbaResponseDTO buildFinalResponse(ManagementUnitEntity projet, Integer annee, List<PtbaActivityDTO> activities, Set<String> bailleurs) {
        PtbaResponseDTO response = PtbaResponseDTO.builder()
                .projetId(projet.getId())
                .projetCode(projet.getCode())
                .projetName(projet.getName())
                .annee(annee != null ? annee : projet.getAnneeDebut())
                .activities(activities)
                .bailleurs(bailleurs)
                .build();

        if (projet.getStructure() != null) {
            response.setStructure(projet.getStructure().getNom());
        }
        if (projet.getResponsible() != null) {
            response.setResponsable(projet.getResponsible().getPrenom() + " " + projet.getResponsible().getNom());
        }
        return response;
    }

    ////////////////////////////////////
    public PtbaResponseDTO generatePtbaByBudgetId(Long budgetId, Integer annee) {
        log.info("Génération PTBA DGPSN pour Budget ID: {}", budgetId);

        BudgetDgpsnEntity budget = budgetDgpsnRepository.findById(budgetId)
                .orElseThrow(() -> new RuntimeException("Budget DGPSN non trouvé : " + budgetId));

        if (budget.getLignesBudgetaires() == null || budget.getLignesBudgetaires().isEmpty()) {
            return buildEmptyResponse(budget);
        }

        List<PtbaActivityDTO> lignesDto = new ArrayList<>();
        Set<String> bailleurs = new HashSet<>();

        for (LigneBudgetaireEntity ligne : budget.getLignesBudgetaires()) {
            if (ligne.getRubrique() == null) continue;

            PlanComptableElementEntity rubrique = ligne.getRubrique();

            PtbaActivityDTO dto = PtbaActivityDTO.builder()
                    .id(ligne.getId())
                    .objectif(rubrique.getLibelle())
                    .objectifCode(rubrique.getCode())
                    .action(getTypeLabel(ligne.getTypeLigneBugetaire()))
                    .actionCode(ligne.getTypeLigneBugetaire().name())
                    .activite("-")
                    .activiteCode("-")
                    .indicateurs("-")
                    .taches(extractCommentaire(ligne.getCommentaire()))
                    .coutCFA(ligne.getBudgetModifie() != null ? ligne.getBudgetModifie() :
                            ligne.getPrimitif() != null ? ligne.getPrimitif() : ligne.getMontant())
                    .build();

            // Sources de financement (dans commentaire ou futur champ)
            PtbaFundingSourcesDTO sources = extractSourcesFromCommentaire(ligne.getCommentaire());
            sources.getBailleurNames().forEach(bailleurs::add);
            dto.setSourcesFinancement(sources);

            // Planification (si dans commentaire : "T1", "Trimestre 2", etc.)
            extractTrimestreFromCommentaire(ligne.getCommentaire(), dto);

            lignesDto.add(dto);
        }

        PtbaResponseDTO response = PtbaResponseDTO.builder()
                .projetId(budget.getId())
                .projetCode(budget.getCode())
                .projetName(budget.getLibelle())
                .annee(annee != null ? annee : budget.getAnnee())
                .activities(lignesDto)
                .bailleurs(bailleurs)
                .build();

        calculateTotals(response);
        return response;
    }

    private String getTypeLabel(TypeLigneBugetaire type) {
        return type == TypeLigneBugetaire.CLASSE_7 ? "RECETTE" :
                type == TypeLigneBugetaire.CLASSE_6 ? "DÉPENSE" : "AUTRE";
    }

    private String extractCommentaire(String commentaire) {
        return commentaire != null && !commentaire.trim().isEmpty() ? commentaire : "-";
    }

    private PtbaFundingSourcesDTO extractSourcesFromCommentaire(String commentaire) {
        PtbaFundingSourcesDTO dto = new PtbaFundingSourcesDTO();
        if (commentaire == null || commentaire.isBlank()) return dto;

        // Format attendu : "BAILLEUR:500000; AUTRE:200000"
        String[] parts = commentaire.split(";");
        for (String part : parts) {
            if (!part.contains(":")) continue;
            String[] kv = part.split(":", 2);
            if (kv.length != 2) continue;
            try {
                String bailleur = kv[0].trim();
                Double montant = Double.parseDouble(kv[1].trim().replaceAll("[^0-9.]", ""));
                if (montant > 0) dto.addSource(bailleur, montant);
            } catch (Exception e) {
                log.debug("Source non parsable: {}", part);
            }
        }
        return dto;
    }

    private void extractTrimestreFromCommentaire(String commentaire, PtbaActivityDTO dto) {
        if (commentaire == null) return;

        String lower = commentaire.toLowerCase();
        if (lower.contains("t1") || lower.contains("trimestre 1")) markTrimestre(dto, 1);
        if (lower.contains("t2") || lower.contains("trimestre 2")) markTrimestre(dto, 2);
        if (lower.contains("t3") || lower.contains("trimestre 3")) markTrimestre(dto, 3);
        if (lower.contains("t4") || lower.contains("trimestre 4")) markTrimestre(dto, 4);
    }

    private void markTrimestre(PtbaActivityDTO dto, int trimestre) {
        PtbaTrimesterDTO t = switch (trimestre) {
            case 1 -> dto.getTrimestre1();
            case 2 -> dto.getTrimestre2();
            case 3 -> dto.getTrimestre3();
            case 4 -> dto.getTrimestre4();
            default -> null;
        };
        if (t != null) {
            t.setJanvier(true); t.setFevrier(true); t.setMars(true);
            if (trimestre == 2) { t.setAvril(true); t.setMai(true); t.setJuin(true); }
            if (trimestre == 3) { t.setJuillet(true); t.setAout(true); t.setSeptembre(true); }
            if (trimestre == 4) { t.setOctobre(true); t.setNovembre(true); t.setDecembre(true); }
        }
    }

    private void calculateTotals(PtbaResponseDTO response) {
        Map<String, Double> totauxRubrique = new HashMap<>();
        Map<String, Double> totauxType = new HashMap<>();
        Map<String, Double> totauxSources = new HashMap<>();
        double total = 0.0;

        for (PtbaActivityDTO a : response.getActivities()) {
            String rubrique = a.getObjectif();
            totauxRubrique.put(rubrique, totauxRubrique.getOrDefault(rubrique, 0.0) + a.getCoutCFA());

            String type = a.getAction(); // RECETTE ou DÉPENSE
            totauxType.put(type, totauxType.getOrDefault(type, 0.0) + a.getCoutCFA());

            total += a.getCoutCFA();

            a.getSourcesFinancement().getSources().forEach((b, m) ->
                    totauxSources.put(b, totauxSources.getOrDefault(b, 0.0) + m));
        }

        response.setTotauxParObjectif(totauxRubrique);
        response.setTotauxParAction(totauxType);
        response.setTotalGeneral(total);
        response.setTotauxSourcesFinancement(totauxSources);
    }

    private PtbaResponseDTO buildEmptyResponse(BudgetDgpsnEntity budget) {
        return PtbaResponseDTO.builder()
                .projetId(budget.getId())
                .projetCode(budget.getCode())
                .projetName(budget.getLibelle())
                .annee(budget.getAnnee())
                .activities(Collections.emptyList())
                .bailleurs(Collections.emptySet())
                .totalGeneral(0.0)
                .build();
    }
}
