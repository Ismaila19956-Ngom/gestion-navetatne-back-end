package com.webgram.dgpsn.services;

import com.webgram.dgpsn.entities.*;
import com.webgram.dgpsn.entities.enums.TypeProjet;
import com.webgram.dgpsn.models.*;
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

    /**
     * Génère le PTBA complet pour un projet donné
     */
    public PtbaResponseDTO generatePtba(Long projetId, Integer annee) {
        // Récupérer le projet
        ManagementUnitEntity projet = managementUnitRepository.findById(projetId)
                .orElseThrow(() -> new RuntimeException("Projet non trouvé avec l'id : " + projetId));

        if (!TypeProjet.PROJECT.equals(projet.getType()) && !TypeProjet.PROGRAMME.equals(projet.getType())) {
            throw new RuntimeException("L'entité doit être de type PROGRAMME ou PROJECT");
        }

        // Set pour stocker tous les bailleurs uniques
        Set<String> allBailleurs = new HashSet<>();

        // Construire la réponse
        PtbaResponseDTO response = PtbaResponseDTO.builder()
                .projetId(projet.getId())
                .projetCode(projet.getCode())
                .projetName(projet.getName())
                .annee(annee != null ? annee : projet.getAnneeDebut())
                .activities(new ArrayList<>())
                .totauxParObjectif(new HashMap<>())
                .totauxParAction(new HashMap<>())
                .totauxSourcesFinancement(new HashMap<>())
                .build();

        // Récupérer tous les objectifs du projet
        List<ManagementUnitEntity> objectifs = findChildrenByType(projet.getId(), TypeProjet.OBJECTIF);

        List<PtbaActivityDTO> allActivities = new ArrayList<>();

        for (ManagementUnitEntity objectif : objectifs) {
            // Pour chaque objectif, récupérer ses actions
            List<ManagementUnitEntity> actions = findChildrenByType(objectif.getId(), TypeProjet.ACTION);

            for (ManagementUnitEntity action : actions) {
                // Pour chaque action, récupérer ses activités
                List<ManagementUnitEntity> activites = findChildrenByType(action.getId(), TypeProjet.ACTIVITY);

                for (ManagementUnitEntity activite : activites) {
                    // Construire l'activité PTBA
                    PtbaActivityDTO activityDTO = buildPtbaActivity(objectif, action, activite, annee, allBailleurs);
                    allActivities.add(activityDTO);
                }
            }
        }

        response.setActivities(allActivities);
        response.setBailleurs(allBailleurs);

        // Calculer les totaux
        calculateTotals(response);

        // Informations supplémentaires
        if (projet.getStructure() != null) {
            response.setStructure(projet.getStructure().getNom());
        }
        if (projet.getResponsible() != null) {
            response.setResponsable(projet.getResponsible().getPrenom() + " " + projet.getResponsible().getNom());
        }

        return response;
    }

    /**
     * Construit un PtbaActivityDTO à partir d'une activité
     */
    private PtbaActivityDTO buildPtbaActivity(
            ManagementUnitEntity objectif,
            ManagementUnitEntity action,
            ManagementUnitEntity activite,
            Integer annee,
            Set<String> allBailleurs) {

        PtbaActivityDTO dto = PtbaActivityDTO.builder()
                .id(activite.getId())
                .objectif(objectif.getName())
                .objectifCode(objectif.getCode())
                .action(action.getName())
                .actionCode(action.getCode())
                .activite(activite.getName())
                .activiteCode(activite.getCode())
                .build();

        // Récupérer les indicateurs
        List<ManagementUnitEntity> indicateurs = findChildrenByType(activite.getId(), TypeProjet.INDICATOR);
        if (!indicateurs.isEmpty()) {
            String indicateursText = indicateurs.stream()
                    .map(ManagementUnitEntity::getName)
                    .collect(Collectors.joining("; "));
            dto.setIndicateurs(indicateursText);
        }

        // Récupérer les tâches
        List<ManagementUnitEntity> taches = findChildrenByType(activite.getId(), TypeProjet.TACHE);
        if (!taches.isEmpty()) {
            String tachesText = taches.stream()
                    .map(ManagementUnitEntity::getName)
                    .collect(Collectors.joining("; "));
            dto.setTaches(tachesText);
        }

        // Construire la planification mensuelle à partir des tâches
        buildMonthlyPlanning(dto, activite.getId(), annee);

        // Récupérer les acteurs
        if (activite.getResponsible() != null) {
            dto.setActeurResponsable(activite.getResponsible().getPrenom() + " " +
                    activite.getResponsible().getNom());
        }

        if (activite.getActorsInvolved() != null && !activite.getActorsInvolved().isEmpty()) {
            String acteurs = activite.getActorsInvolved().stream()
                    .map(StructureEntity::getNom)
                    .collect(Collectors.joining(", "));
            dto.setActeurImplique(acteurs);
        }

        // Récupérer les sources de financement pour l'activité + indicateurs + tâches
        PtbaFundingSourcesDTO fundingSources = buildFundingSources(activite, indicateurs, taches, allBailleurs);
        dto.setSourcesFinancement(fundingSources);

        // Le coutCFA est la somme de toutes les sources de financement
        dto.setCoutCFA(fundingSources.getTotal());

        // Sources de vérification
        if (activite.getVerificationSources() != null && !activite.getVerificationSources().isEmpty()) {
            String sources = activite.getVerificationSources().stream()
                    .map(LabelEntity::getLibelle)
                    .collect(Collectors.joining(", "));
            dto.setSourcesVerification(sources);
        }

        // Observations
        dto.setObservations(activite.getDescription());

        return dto;
    }

    /**
     * Construit la planification mensuelle à partir des tâches
     */
    private void buildMonthlyPlanning(PtbaActivityDTO dto, Long activiteId, Integer annee) {
        // Récupérer toutes les tâches liées à l'activité
        List<TacheEntity> taches = tacheRepository.findByActiviteIdPerso(activiteId);

        // Initialiser les trimestres
        PtbaTrimesterDTO trim1 = initializeTrimester();
        PtbaTrimesterDTO trim2 = initializeTrimester();
        PtbaTrimesterDTO trim3 = initializeTrimester();
        PtbaTrimesterDTO trim4 = initializeTrimester();

        for (TacheEntity tache : taches) {
            if (tache.getTrimestre() != null && tache.getMois() != null) {
                markMonthAsActive(tache.getTrimestre(), tache.getMois(), trim1, trim2, trim3, trim4);
            }
        }

        dto.setTrimestre1(trim1);
        dto.setTrimestre2(trim2);
        dto.setTrimestre3(trim3);
        dto.setTrimestre4(trim4);
    }

    /**
     * Initialise un trimestre avec tous les mois à false
     */
    private PtbaTrimesterDTO initializeTrimester() {
        return PtbaTrimesterDTO.builder()
                .janvier(false).fevrier(false).mars(false)
                .avril(false).mai(false).juin(false)
                .juillet(false).aout(false).septembre(false)
                .octobre(false).novembre(false).decembre(false)
                .build();
    }

    /**
     * Marque un mois comme actif dans le bon trimestre
     */
    private void markMonthAsActive(Integer trimestre, Integer mois,
                                   PtbaTrimesterDTO trim1, PtbaTrimesterDTO trim2,
                                   PtbaTrimesterDTO trim3, PtbaTrimesterDTO trim4) {
        PtbaTrimesterDTO targetTrim;
        switch (trimestre) {
            case 1: targetTrim = trim1; break;
            case 2: targetTrim = trim2; break;
            case 3: targetTrim = trim3; break;
            case 4: targetTrim = trim4; break;
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
     * Construit les sources de financement pour une activité + indicateurs + tâches
     * Le coutCFA est la somme de toutes ces sources
     */
    private PtbaFundingSourcesDTO buildFundingSources(
            ManagementUnitEntity activite,
            List<ManagementUnitEntity> indicateurs,
            List<ManagementUnitEntity> taches,
            Set<String> allBailleurs) {

        PtbaFundingSourcesDTO dto = new PtbaFundingSourcesDTO();
        log.info("Début de construction des sources de financement pour l'activité ID: {}", activite.getId());

        // 1. Sources de financement de l'activité
        log.info("Récupération des sources de financement pour l'activité ID: {}", activite.getId());
        List<FundingSourceEntity> sourcesActivite = fundingSourceRepository.findByManagementUnitIdPerso(activite.getId());
        log.info("Nombre de sources trouvées pour l'activité : {}", sourcesActivite.size());
        addFundingSourcesToDTO(sourcesActivite, dto, allBailleurs);

        // 2. Sources de financement des indicateurs
        log.info("Traitement des {} indicateur(s) lié(s) à l'activité", indicateurs.size());
        for (ManagementUnitEntity indicateur : indicateurs) {
            log.info("Récupération des ValueIndicator pour l'indicateur ID: {}", indicateur.getId());
            List<ValueIndicatorEntity> valueIndicators = valueIndicatorRepository.findByActivityIdPerso(indicateur.getId());
            log.info("Nombre de ValueIndicator trouvés pour l'indicateur {} : {}", indicateur.getId(), valueIndicators.size());

            for (ValueIndicatorEntity valueIndicator : valueIndicators) {
                log.info("Récupération des sources de financement pour ValueIndicator ID: {}", valueIndicator.getId());
                List<FundingSourceEntity> sourcesIndicateur = fundingSourceRepository.findByValueIndicatorId(valueIndicator.getId());
                log.info("Nombre de sources trouvées pour ValueIndicator {} : {}", valueIndicator.getId(), sourcesIndicateur.size());
                addFundingSourcesToDTO(sourcesIndicateur, dto, allBailleurs);
            }
        }

        // 3. Sources de financement des tâches
        log.info("Traitement des {} tâche(s) liée(s) à l'activité", taches.size());
        log.info("Récupération de toutes les TacheEntity pour l'activité ID: {}", activite.getId());
        List<TacheEntity> tacheEntities = tacheRepository.findByActiviteIdPerso(activite.getId());
        log.info("Nombre total de TacheEntity récupérées : {}", tacheEntities.size());

        for (ManagementUnitEntity tache : taches) {
            log.info("Recherche de la TacheEntity correspondant à la tâche ID: {}", tache.getId());
            for (TacheEntity tacheEntity : tacheEntities) {
                if (tacheEntity.getId().equals(tache.getId())) {
                    log.info("TacheEntity trouvée pour ID: {}, récupération des sources de financement", tache.getId());
                    List<FundingSourceEntity> sourcesTache = fundingSourceRepository.findByTacheId(tacheEntity.getId());
                    log.info("Nombre de sources trouvées pour la tâche {} : {}", tache.getId(), sourcesTache.size());
                    addFundingSourcesToDTO(sourcesTache, dto, allBailleurs);
                    break; // Optionnel : sortir dès qu'on a trouvé
                }
            }
        }

        log.info("Construction des sources de financement terminée pour l'activité ID: {}", activite.getId());
        return dto;
    }

    /**
     * Ajoute des sources de financement au DTO et met à jour la liste des bailleurs
     */
    private void addFundingSourcesToDTO(
            List<FundingSourceEntity> sources,
            PtbaFundingSourcesDTO dto,
            Set<String> allBailleurs) {

        log.info("Début de l'ajout de {} source(s) de financement au DTO", sources.size());

        for (FundingSourceEntity source : sources) {
            if (source.getStructure() != null && source.getMontant() != null && source.getMontant() > 0) {
                String bailleurName = source.getStructure().getNom();
                log.info("Ajout source : BAILLEUR='{}', MONTANT={} (ID source: {})",
                        bailleurName, source.getMontant(), source.getId());

                dto.addSource(bailleurName, source.getMontant());
                allBailleurs.add(bailleurName);
            } else {
                log.info("Source ignorée (structure nulle, montant nul ou négatif) - ID: {}", source.getId());
            }
        }

        log.info("Fin de l'ajout des sources. Total bailleurs distincts mis à jour : {}", allBailleurs.size());
    }

    /**
     * Calcule tous les totaux
     */
    private void calculateTotals(PtbaResponseDTO response) {
        Map<String, Double> totauxObjectif = new HashMap<>();
        Map<String, Double> totauxAction = new HashMap<>();
        Map<String, Double> totauxSourcesGlobal = new HashMap<>();
        double totalGeneral = 0.0;

        for (PtbaActivityDTO activity : response.getActivities()) {
            // Total par objectif
            String objectifKey = activity.getObjectif();
            totauxObjectif.put(objectifKey,
                    totauxObjectif.getOrDefault(objectifKey, 0.0) + activity.getCoutCFA());

            // Total par action
            String actionKey = activity.getObjectif() + " > " + activity.getAction();
            totauxAction.put(actionKey,
                    totauxAction.getOrDefault(actionKey, 0.0) + activity.getCoutCFA());

            // Total général
            totalGeneral += activity.getCoutCFA();

            // Totaux des sources de financement
            if (activity.getSourcesFinancement() != null && activity.getSourcesFinancement().getSources() != null) {
                activity.getSourcesFinancement().getSources().forEach((bailleur, montant) -> {
                    totauxSourcesGlobal.put(bailleur,
                            totauxSourcesGlobal.getOrDefault(bailleur, 0.0) + montant);
                });
            }
        }

        response.setTotauxParObjectif(totauxObjectif);
        response.setTotauxParAction(totauxAction);
        response.setTotalGeneral(totalGeneral);
        response.setTotauxSourcesFinancement(totauxSourcesGlobal);
    }

    /**
     * Récupère les enfants d'une entité par type
     */
    private List<ManagementUnitEntity> findChildrenByType(Long parentId, TypeProjet type) {
        return managementUnitRepository.findByParentIdAndType(parentId, type);
    }
}
