package com.webgram.dgpsn.services.Impl;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.webgram.dgpsn.entities.*;
import com.webgram.dgpsn.entities.enums.TypeConge;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.CessationFonctionMapper;
import com.webgram.dgpsn.mappers.CessationPerduMapper;
import com.webgram.dgpsn.models.*;
import com.webgram.dgpsn.repositories.*;
import com.webgram.dgpsn.services.AlerteService;
import com.webgram.dgpsn.services.CessationFonctionService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CessationFonctionServiceImpl implements CessationFonctionService {
    private final CessationFonctionRepository cessationFonctionRepository;
    private final CessationFonctionMapper cessationFonctionMapper;
    private final CessationPerduMapper cessationPerduMapper;
    private final CongeRepository congeRepository;
    private final AlerteService alerteService;
    private final CessationPerduRepository solderestantRepository;
    private static final String NUMERO_FICHE_PREFIX = "DOD";
    private static final String FORMATION_NOT_FOUND = "La cessation n'existe pas";
    private static final String AGENT_NOT_FOUND = "L'agent n'existe pas";


    @Override
    public CessationFonctionDTO create(CessationFonctionDTO cessationDTO) {
        var congeEntity = congeRepository.findById(cessationDTO.getCongeId()).orElseThrow(() -> new ResourceNotFoundException(AGENT_NOT_FOUND));
        verifierJoursRestantsDansAnnee(cessationDTO);
        manageSoldeAnnuel(cessationDTO, congeEntity);
        manageDureeRestante(cessationDTO, congeEntity);
        cessationDTO.setNumFicheCessation(generateNumeroFiche());
        AgentEntity agentEntity = congeEntity.getAgent();
        if (agentEntity == null) {
            throw new ResourceNotFoundException(AGENT_NOT_FOUND);
        }
        initializeAgentInfoForAlerte(cessationDTO, agentEntity);
//        boolean isCessationDateValid = cessationFonctionRepository.isDateCessationValidForConge(congeEntity, cessationDTO.getDateCessation());
//        if (!isCessationDateValid) {
//            throw new IllegalArgumentException("La date de cessation doit être comprise entre la date de début de l'autorisation et la date de fin de la demande d'autorisation");
//        }
        var savedConge = cessationFonctionRepository.save(cessationFonctionMapper.asEntity(cessationDTO));
        alerteService.generateAlertCreateCessationService(cessationDTO);
        return cessationFonctionMapper.asDto(savedConge);
    }


    @Override
    public CessationFonctionDTO update(Long cessationId, CessationFonctionDTO cessationDTO) {
        CessationFonctionEntity existingCessation = cessationFonctionRepository.findById(cessationId).orElseThrow(() -> new ResourceNotFoundException(FORMATION_NOT_FOUND));
        CongeEntity congeEntity = congeRepository.findById(cessationDTO.getCongeId()).orElseThrow(() -> new ResourceNotFoundException(AGENT_NOT_FOUND));
        manageSoldeAnnuelOnUpdate(cessationDTO, existingCessation, congeEntity);
        cessationDTO.setId(cessationId);
        var savedConge = cessationFonctionRepository.save(cessationFonctionMapper.asEntity(cessationDTO));
        return cessationFonctionMapper.asDto(savedConge);
    }

    @Override
    public CessationFonctionDTO read(Long cessationId) {
        var cessation = cessationFonctionRepository.findById(cessationId).orElseThrow(() -> new ResourceNotFoundException(FORMATION_NOT_FOUND));
        return cessationFonctionMapper.asDto(cessation);
    }

    @Override
    public List<CessationFonctionDTO> readAll() {
        return cessationFonctionRepository.findAll().stream().map(cessationFonctionMapper::asDto).collect(Collectors.toList());
    }

    @Override
    public Page<CessationFonctionDTO> readPage(Map<String, String> searchParams, int page, int size) throws ParseException {
        var booleanBuilder = new BooleanBuilder();
        if (Objects.nonNull(searchParams)) {
            var qConge = QCessationFonctionEntity.cessationFonctionEntity;

            if (searchParams.containsKey("libelle"))
                booleanBuilder.and(qConge.libelle.containsIgnoreCase(searchParams.get("libelle")));

            if (searchParams.containsKey("description"))
                booleanBuilder.and(qConge.description.containsIgnoreCase(searchParams.get("description")));

            if (searchParams.containsKey("dateCessation")) {
                var date = new SimpleDateFormat("yyyy-MM-dd").parse(searchParams.get("dateCessation"));
                booleanBuilder.and(qConge.dateCessation.eq((java.sql.Date) date));
            }

            String typeConge = searchParams.get("typeConge");
            if (typeConge != null && !typeConge.isEmpty()) {
                booleanBuilder.and(qConge.typeConge.stringValue().lower().containsIgnoreCase(typeConge.toLowerCase()));
            }
            String congeIdStr = searchParams.get("congeId");
            if (congeIdStr != null && !congeIdStr.isEmpty()) {
                Long congeId = Long.valueOf(congeIdStr);
                if (congeId != null && congeId != 0) {
                    booleanBuilder.and(qConge.conge.id.eq(congeId));
                }
            }

        }

        Sort sort = Sort.by(Sort.Order.desc("id"));
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Page<CessationFonctionEntity> cessationFonctionEntities = cessationFonctionRepository.findAll(booleanBuilder, pageRequest);
        return cessationFonctionMapper.asPage(cessationFonctionEntities);
    }

    @Override
    public void delete(Long cessationId) {
        try {
            cessationFonctionRepository.deleteById(cessationId);
            log.info("The agent id {} is deleted", cessationId);
        } catch (IllegalArgumentException ex) {
            log.info("The given id to delete must not be null");
        }
    }

    private String generateNumeroFiche() {
        List<String> numFiches = cessationFonctionRepository.findLastNumFicheCessation();
        int numero = 300;
        if (!numFiches.isEmpty()) {
            String lastNumeroFiche = numFiches.get(0);
            // Vérifiez si lastNumeroFiche est null avant de l'utiliser
            if (lastNumeroFiche != null) {
                numero = Integer.parseInt(lastNumeroFiche.substring(NUMERO_FICHE_PREFIX.length())) + 1;
            }
        }
        return NUMERO_FICHE_PREFIX + numero;
    }


    private void manageSoldeAnnuel(CessationFonctionDTO cessationDTO, CongeEntity congeEntity) {
//    int currentYear = LocalDate.now().getYear();
//    int annee = cessationDTO.getDateCessation().getYear()+1900;
        int annee = cessationDTO.getDateCessation().getYear() + 1900;
        List<CessationFonctionEntity> existingCessation = cessationFonctionRepository.findByCongeAndAnnee(congeEntity, annee);
        if (existingCessation == null || existingCessation.isEmpty()) {
            cessationDTO.setSoldeAnnuel(30);
            cessationDTO.setAnnee(annee);
        } else {
            CessationFonctionEntity latestCessation = existingCessation.get(existingCessation.size() - 1);
            cessationDTO.setSoldeAnnuel(latestCessation.getSoldeAnnuel());
            cessationDTO.setAnnee(latestCessation.getAnnee());
        }
        if (cessationDTO.getSoldeAnnuel() == 0) {
            throw new IllegalArgumentException("Solde de congé épuisé pour cette année " + cessationDTO.getAnnee());
        }
        if (cessationDTO.getNombreDeJoursdemande() > cessationDTO.getSoldeAnnuel()) {
            throw new IllegalArgumentException("Le nombre de jours demandés est supérieur au solde annuel restant qui est egal a: " + cessationDTO.getSoldeAnnuel() + "jours");
        }
        int nouveauSolde = cessationDTO.getSoldeAnnuel() - cessationDTO.getNombreDeJoursdemande();
        cessationDTO.setSoldeAnnuel(nouveauSolde);
        if (nouveauSolde == 0) {
            System.out.println("Le solde annuel est maintenant de zéro.");
        }
    }

    private void manageDureeRestante(CessationFonctionDTO cessationDTO, CongeEntity congeEntity) {
        if (congeEntity.getDureeCessation() == 0) {
            throw new IllegalArgumentException("Le solde global de congé autorisé est terminé. Une nouvelle demande  conge doit être envoyée.");
        }
        int nouvelleDureeRestante = congeEntity.getDureeCessation() - cessationDTO.getNombreDeJoursdemande();
        if (nouvelleDureeRestante < 0) {
            throw new IllegalArgumentException("Le nombre de jours demandé dépasse la durée totale autorisée pour ce congé.");
        }
//        congeEntity.setDureeCessation(nouvelleDureeRestante);
//        congeRepository.save(congeEntity);
    }

    private void manageSoldeAnnuelOnUpdate(CessationFonctionDTO cessationDTO, CessationFonctionEntity existingCessation, CongeEntity congeEntity) {
        int newNombreDeJoursDemande = cessationDTO.getNombreDeJoursdemande();
        int oldNombreDeJoursDemande = existingCessation.getNombreDeJoursdemande();
        int soldeAnnuel = existingCessation.getSoldeAnnuel();
        int differenceNombreJours = newNombreDeJoursDemande - oldNombreDeJoursDemande;
        int nouveauSolde = soldeAnnuel - differenceNombreJours;
        cessationDTO.setSoldeAnnuel(nouveauSolde);
        if (nouveauSolde < 0) {
            throw new RuntimeException(String.format("Solde de congé insuffisant pour cette année." + cessationDTO.getAnnee() + " il vous reste " + cessationDTO.setSoldeAnnuel(nouveauSolde) + " jours "));
        }
        if (soldeAnnuel - differenceNombreJours < 0) {
            throw new RuntimeException(String.format("Le nombre de jours demandé dépasse le solde annuel disponible."));
        }
    }

    public Integer getLatestSoldeAnnuelByCongeIdAndMatricule(Long congeId) {
        List<Integer> soldeAnnuelList = cessationFonctionRepository.getLatestSoldeAnnuelByCongeIdAndMatricule(congeId);
        if (!soldeAnnuelList.isEmpty()) {
            Integer latestSolde = soldeAnnuelList.get(0);
            if (latestSolde == null || latestSolde <= 0) {
                return 0;
            }
            return latestSolde;
        } else {
            return null;
        }
    }

    public Integer getLatestDureeSoldeByCongeId(Long congeId) {
        CongeEntity conge = congeRepository.findById(congeId).orElseThrow(() -> new IllegalArgumentException("Congé non trouvé"));
        List<CessationFonctionEntity> cessations = cessationFonctionRepository.findByCongeId(congeId);
        List<CessationPerduEntity> cessationPerdu = solderestantRepository.findByCongeId(congeId);
        int dureeInitiale = conge.getDureeCessation();
        int dureeRestantePerdu = 0;
        if (!cessationPerdu.isEmpty()) {
            dureeRestantePerdu = cessationPerdu.get(0).getNombreDeJoursdemande();
        }
        int joursUtilises = cessations.stream().mapToInt(CessationFonctionEntity::getNombreDeJoursdemande).sum();
        return dureeInitiale - (joursUtilises + dureeRestantePerdu);
    }

    private void initializeAgentInfoForAlerte(CessationFonctionDTO cessationDTO, AgentEntity agentEntity) {
        if (cessationDTO.getConge() == null) {
            cessationDTO.setConge(new CongeDTO());
        }
        if (cessationDTO.getConge().getAgent() == null) {
            cessationDTO.getConge().setAgent(new AgentDTO());
        }
        cessationDTO.getConge().getAgent().setPrenom(agentEntity.getPrenom());
        cessationDTO.getConge().getAgent().setNom(agentEntity.getNom());
        cessationDTO.getConge().getAgent().setMatricule(agentEntity.getMatricule());

    }

    @Transactional
    public boolean traiterSoldeRestantConge(Long congeId, int annee) {
        try {
            CongeEntity conge = congeRepository.findById(congeId).orElseThrow(() -> new IllegalStateException("Congé non trouvé avec ID: " + congeId));
            if (!periodeAutorisation(conge, annee)) {
                return false;
            }
            List<CessationFonctionEntity> cessationsAnnee = cessationFonctionRepository.findByCongeAndAnnee(conge, annee);
            if (cessationsAnnee.stream().anyMatch(c -> c.getAnnee() == annee)) {
                CessationFonctionEntity derniereCessation = cessationsAnnee.get(cessationsAnnee.size() - 1);
                int soldeAnnuelRestant = derniereCessation.getSoldeAnnuel();
                derniereCessation.setSoldeAnnuel(0);
                cessationFonctionRepository.save(derniereCessation);

                if (soldeAnnuelRestant > 0) {
                    enregistrerSoldeRestant(conge, soldeAnnuelRestant, annee);
                }
                return true;
            } else {
                int soldeAnnuelRestant = 30;
                enregistrerSoldeRestant(conge, soldeAnnuelRestant, annee);
                return true;
            }
        } catch (IllegalStateException e) {
            throw e;
        }
    }

    private boolean periodeAutorisation(CongeEntity conge, int annee) {
        int anneeDebut = getAnnee(conge.getDateDebut());
        int anneeFin = getAnnee(conge.getDateReprise());
        return annee >= anneeDebut && annee <= anneeFin;
    }

    private int getAnnee(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    private void enregistrerSoldeRestant(CongeEntity conge, int soldeAnnuelRestant, int annee) {
        String messageAlerte = createAlerteCessation(conge, soldeAnnuelRestant, annee);
        CessationPerduDTO soldeRestant = CessationPerduDTO.builder().congeId(conge.getId()).libelle("pas de correspondance").description("Le solde annuel restant de " + soldeAnnuelRestant + " jours pour l'année " + annee + " n'a pas été utilisé par l'agent. Il est enregistré comme des jours non pris.").dateCessation(new Date()).nombreDeJoursdemande(soldeAnnuelRestant).typeConge(TypeConge.CESSATION).soldeAnnuel(0).numFicheCessation(generateNumeroFiche()).annee(annee).message(messageAlerte).build();
        solderestantRepository.save(cessationPerduMapper.asEntity(soldeRestant));
    }

    @Override
    public String traitementSoldeRestant(int anneeSelectionnee) {
        int anneeActuelle = new Date().getYear() + 1900;
        if (anneeSelectionnee > anneeActuelle) {
            throw new IllegalArgumentException("Seules les années en cours et les années précédentes, qui n'ont pas encore été clôturées, sont autorisées à être clôturées. ");
        }
        List<CongeEntity> conges = congeRepository.findBySpecificYear(anneeSelectionnee);
        StringBuilder resultat = new StringBuilder();
        boolean tousLesSoldesDejaAZero = true;
        for (CongeEntity conge : conges) {
            List<CessationFonctionEntity> cessationsAnnee = cessationFonctionRepository.findByCongeAndAnnee(conge, anneeSelectionnee);
            Integer dureeSoldeDisponible = getLatestDureeSoldeByCongeId(conge.getId());
            if (dureeSoldeDisponible > 0 && (cessationsAnnee.isEmpty() || cessationsAnnee.get(cessationsAnnee.size() - 1).getSoldeAnnuel() > 0)) {
                tousLesSoldesDejaAZero = false;
                break;
            }
        }

        if (tousLesSoldesDejaAZero) {
            throw new IllegalStateException(String.format("L'année %d a déjà été clôturée pour tous les agents, y compris les cessations non prises pour cette année", anneeSelectionnee));
        }
        for (CongeEntity conge : conges) {
            Integer dureeSoldeDisponible = getLatestDureeSoldeByCongeId(conge.getId());
            if (dureeSoldeDisponible > 0) {
                try {
                    boolean success = traiterSoldeRestantConge(conge.getId(), anneeSelectionnee);
                    if (success) {
                        resultat.append(String.format("Traitement réussi pour l'agent avec Matricule: %d\n", conge.getAgent().getMatricule()));
                    }
                } catch (IllegalStateException e) {
                    resultat.append(e.getMessage()).append("\n");
                }
            }
        }
        return resultat.toString();
    }

    public String createAlerteCessation(CongeEntity conge, int nombreDeJoursdemande, int annee) {
        return "Vous avez perdu " + nombreDeJoursdemande + " jours de cessation. Vous ne pouvez plus prendre ces jours pour l'année " + annee;
    }


    private void verifierJoursRestantsDansAnnee(CessationFonctionDTO cessationDTO) {
        int annee = cessationDTO.getDateCessation().getYear() + 1900;
        LocalDate dateCessation = new Date(cessationDTO.getDateCessation().getTime()).toInstant()
                .atZone(java.time.ZoneId.systemDefault())
                .toLocalDate();
        LocalDate finAnnee = LocalDate.of(annee, 12, 31);
        int joursRestantsDansAnnee = (int) java.time.temporal.ChronoUnit.DAYS.between(dateCessation, finAnnee);

        if (cessationDTO.getNombreDeJoursdemande() > joursRestantsDansAnnee) {
            throw new IllegalArgumentException("Vous ne pouvez pas prendre " + cessationDTO.getNombreDeJoursdemande() +
                    " jours car il ne reste que " + joursRestantsDansAnnee + " jours dans l'année " + annee);
        }
    }
}