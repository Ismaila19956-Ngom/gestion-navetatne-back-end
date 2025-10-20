package com.webgram.dgpsn.services.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.webgram.dgpsn.entities.AlerteEntity;
import com.webgram.dgpsn.entities.UserEntity;
import com.webgram.dgpsn.entities.enums.Priority;
import com.webgram.dgpsn.entities.enums.TypeAlerte;
import com.webgram.dgpsn.entities.enums.TypePollution;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.AlerteMapper;
import com.webgram.dgpsn.models.*;
import com.webgram.dgpsn.repositories.*;
import com.webgram.dgpsn.services.AlerteService;


import java.text.SimpleDateFormat;
import java.util.*;


@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AlerteServiceImpl implements AlerteService {

    private final AlerteRepository alerteRepository;

    private final AlerteMapper alerteMapper;

    private final TemplateRepository templateRepository;

    private final UserRepository userRepository;

    private final ManagementUnitRepository managementUnitRepository;

    private final IssueLogRepository issueLogRepository;

  /*  @Override
    public List<AlerteDTO> getAlertNotRead(long userId) {
        return null;
    } */

    @Override
    public Page<AlerteDTO> readAll(Pageable pageable, String message, Date date, String critere, Date endDate, Boolean read, Priority priority, String username) {
        var pageableshort = PageRequest.of(pageable.getPageNumber(),pageable.getPageSize(), Sort.by("date").descending());
        log.info("date {} ", date);
        var alertes = alerteRepository.readAllByFilters(pageableshort, message, date, critere, endDate, read, priority, username)
                .map(alerteMapper::asDto);
        log.trace("list alert get ok {}", alertes);
        return alertes;

    }

    @Override
    public void setAlertToRead(Long alertId) {
        var alert = alerteRepository.findById(alertId)
                .orElseThrow(()-> new ResourceNotFoundException("alerte inexistant"));

        alert.setRead(true);
        alerteRepository.save(alert);
    }

    @Override
    public void generateAlertCreateIssueLog(IssueLogDTO issueLog) {
        var project = managementUnitRepository.findById(issueLog.getProjet()
                .getId()).orElseThrow(()-> new ResourceNotFoundException("project inexistant"));
        var template = templateRepository.findByTypeAlerte(TypeAlerte.CREATION_EVALUATION_ENVIRONNEMENTAL);
        if(template.isPresent()) {
            var model = template.get();
            var profiles = model.getProfiles();
            profiles.forEach(profile -> {
                var users = userRepository.findByProfile(profile);
                users.forEach(userEntity -> {
                    var message = model.getMessage();
                    message = StringUtils.isNotEmpty(issueLog.getLibelle())? message.replace("[LIBELLE]", issueLog.getLibelle()): message.replace("[LIBELLE]", "");
                    message = StringUtils.isNotEmpty(issueLog.getDescription())? message.replace("[DESCRIPTION]", issueLog.getDescription()): message.replace("[DESCRIPTION]", "");
                    message = Objects.nonNull(issueLog.getIdentificationDate())? message.replace("[DATE_IDENTIFICATION]", new SimpleDateFormat("dd/MM/yyyy").format(issueLog.getIdentificationDate())): message.replace("[DATE_IDENTIFICATION]", "");
                    message = Objects.nonNull(issueLog.getDeadline())? message.replace("[DATE_ECHEANCE]", new SimpleDateFormat("dd/MM/yyyy").format(issueLog.getDeadline())): message.replace("[DATE_ECHEANCE]", "");
                    if(Objects.nonNull(project)) {
                        log.info("project {}", project.getName());
                        message = message.replace("[NOM_PROJET]", project.getName());
                    }
                    if(Objects.nonNull(issueLog.getCriticity())) {
                        message = StringUtils.isNotEmpty(issueLog.getCriticity().getDescription())? message.replace("[CRITICITE]", issueLog.getCriticity().getDescription()): message.replace("[CRITICITE]", "");
                    }
                    message = message.replace("[USER]", userEntity.getAgent().getPrenom() + " "+ userEntity.getAgent().getNom());
                    var alert = AlerteEntity.builder()
                            .message(message)
                            .priority(model.getPriority())
                            .date(new Date())
                            .read(false)
                            .user(userEntity)
                            .build();
                    alerteRepository.save(alert);
                });

            });

        }
    }

    @Scheduled(cron = "0 0 0 */1 * ?")
    //@Scheduled(fixedDelay = 5000)
    public void generateAlertIssueLogNotResolved() {
        log.info("generation alerte avec hrone {} ");
        var issuelogs = issueLogRepository.findStatIssueLogsNotResolved();
        var template = templateRepository.findByTypeAlerte(TypeAlerte.GESTION_QUALITE);
        if(template.isPresent()) {
            issuelogs.forEach(issueLog -> {
                //log.info("genere alerte {} ", issueLog);
                Date dateToDay = new Date();
                if(Objects.nonNull(issueLog.getDeadline())) {
                    var diff  =  issueLog.getDeadline().getTime() - dateToDay.getTime();
                    float days = (diff / (1000*60*60*24));
                    if(days >=0 && days <= template.get().getDeadlines()) {
                        var project = managementUnitRepository.findById(issueLog.getProjet()
                                .getId()).orElseThrow(()-> new ResourceNotFoundException("project inexistant"));
                        var model = template.get();
                        var profiles = model.getProfiles();
                        profiles.forEach(profile -> {
                            var users = userRepository.findByProfile(profile);
                            users.forEach(userEntity -> {
                                var message = model.getMessage();
                                message = StringUtils.isNotEmpty(issueLog.getLibelle())? message.replace("[LIBELLE]", issueLog.getLibelle()): message.replace("[LIBELLE]", "");
                                message = StringUtils.isNotEmpty(issueLog.getDescription())? message.replace("[DESCRIPTION]", issueLog.getDescription()): message.replace("[DESCRIPTION]", "");
                                message = Objects.nonNull(issueLog.getIdentificationDate())? message.replace("[DATE_IDENTIFICATION]", new SimpleDateFormat("dd/MM/yyyy").format(issueLog.getIdentificationDate())): message.replace("[DATE_IDENTIFICATION]", "");
                                message = Objects.nonNull(issueLog.getDeadline())? message.replace("[DATE_ECHEANCE]", new SimpleDateFormat("dd/MM/yyyy").format(issueLog.getDeadline())): message.replace("[DATE_ECHEANCE]", "");
                                if(Objects.nonNull(project)) {
                                    log.info("project {}", project.getName());
                                    message = message.replace("[NOM_PROJET]", project.getName());
                                }
                                if(Objects.nonNull(issueLog.getCriticity())) {
                                    message = StringUtils.isNotEmpty(issueLog.getCriticity().getDescription())? message.replace("[CRITICITE]", issueLog.getCriticity().getDescription()): message.replace("[CRITICITE]", "");
                                }
                                message = message.replace("[USER]", userEntity.getAgent().getPrenom() + " "+ userEntity.getAgent().getNom());
                                var alert = AlerteEntity.builder()
                                        .message(message)
                                        .priority(model.getPriority())
                                        .date(new Date())
                                        .read(false)
                                        .user(userEntity)
                                        .build();
                                alerteRepository.save(alert);
                            });

                        });
                    }
                }

            });


        }

    }
    public void generateAlertForEvaluationEnvironnementale(EvaluationEnvironnementaleDTO evaluationEnvironnementaleDTO) {
        TypeAlerte typeAlerte = TypeAlerte.CREATION_EVALUATION_ENVIRONNEMENTAL;
        var template = templateRepository.findByTypeAlerte(typeAlerte);
        if (template.isPresent()) {
            var model = template.get();
            var profiles = model.getProfiles();
            profiles.forEach(profile -> {
                var users = userRepository.findByProfile(profile);
                users.forEach(userEntity -> {
                    var message = model.getMessage();
                    message = replacePlaceholdersForEvaluationEnvironnementale(message, evaluationEnvironnementaleDTO, userEntity);
                    var alert = AlerteEntity.builder()
                            .message(message)
                            .priority(model.getPriority())
                            .date(new Date())
                            .read(false)
                            .user(userEntity)
                            .build();
                    alerteRepository.save(alert);
                    log.info("Alert created for user: {} with message: {}", userEntity.getId(), message);
                });
            });
        } else {
            log.warn("No template found for TypeAlerte: {}", typeAlerte);
        }
    }    public void generateAlertForPollution(PollutionManagerDTO pollutionManagerDTO) {
        TypeAlerte typeAlerte = determineTypeAlerte(pollutionManagerDTO.getTypePollution());
        if (typeAlerte == null) {
            log.warn("No TypeAlerte found for pollution type: {}", pollutionManagerDTO.getTypePollution());
            return;
        }
        var template = templateRepository.findByTypeAlerte(typeAlerte);
        if (template.isPresent()) {
            var model = template.get();
            var profiles = model.getProfiles();
            profiles.forEach(profile -> {
                var users = userRepository.findByProfile(profile);
                users.forEach(userEntity -> {
                    var message = model.getMessage();
                    message = replacePlaceholders(message, pollutionManagerDTO, userEntity);
                    var alert = AlerteEntity.builder()
                            .message(message)
                            .priority(model.getPriority())
                            .date(new Date())
                            .read(false)
                            .user(userEntity)
                            .build();
                    alerteRepository.save(alert);
                    log.info("Alert created for user: {} with message: {}", userEntity.getId(), message);
                });
            });
        } else {
            log.warn("No template found for TypeAlerte: {}", typeAlerte);
        }
    }

    public void generateAlertForRejet(RejetPollutionDTO rejetPollutionDTO) {
        TypeAlerte typeAlerte = TypeAlerte.POLLUTION_REJET;
        var template = templateRepository.findByTypeAlerte(typeAlerte);
        if (template.isPresent()) {
            var model = template.get();
            var profiles = model.getProfiles();
            profiles.forEach(profile -> {
                var users = userRepository.findByProfile(profile);
                users.forEach(userEntity -> {
                    var message = model.getMessage();
                    message = replacePlaceholdersForRejet(message, rejetPollutionDTO, userEntity);
                    var alert = AlerteEntity.builder()
                            .message(message)
                            .priority(model.getPriority())
                            .date(new Date())
                            .read(false)
                            .user(userEntity)
                            .build();
                    alerteRepository.save(alert);
                    log.info("Alert created for user: {} with message: {}", userEntity.getId(), message);
                });
            });
        } else {
            log.warn("No template found for TypeAlerte: {}", typeAlerte);
        }
    }

    public void generateAlertForMilieux(MilieuxPollutionDTO milieuxPollutionDTO) {
        TypeAlerte typeAlerte = TypeAlerte.GESTION_QUALITE;
        var template = templateRepository.findByTypeAlerte(typeAlerte);
        if (template.isPresent()) {
            var model = template.get();
            var profiles = model.getProfiles();
            profiles.forEach(profile -> {
                var users = userRepository.findByProfile(profile);
                users.forEach(userEntity -> {
                    var message = model.getMessage();
                    message = replacePlaceholdersForMilieux(message, milieuxPollutionDTO, userEntity);
                    var alert = AlerteEntity.builder()
                            .message(message)
                            .priority(model.getPriority())
                            .date(new Date())
                            .read(false)
                            .user(userEntity)
                            .build();
                    alerteRepository.save(alert);
                    log.info("Alert created for user: {} with message: {}", userEntity.getId(), message);
                });
            });
        } else {
            log.warn("No template found for TypeAlerte: {}", typeAlerte);
        }
    }

    private TypeAlerte determineTypeAlerte(TypePollution typePollution) {
        if (typePollution == null) {
            return null;
        }
        if (typePollution == TypePollution.CHIMIQUE) {
            return TypeAlerte.GESTION_PRODUIT;
        }
        if (typePollution == TypePollution.DECHETS) {
            return TypeAlerte.GESTION_DECHET;
        }
        if (typePollution == TypePollution.PLASTIQUE) {
            return TypeAlerte.GESTION_PLASTIQUE;
        }
        log.warn("Unknown or unsupported pollution type: {}", typePollution);
        return null;
    }

    private String replacePlaceholders(String message, PollutionManagerDTO pollutionManagerDTO, UserEntity userEntity) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        message = message.replace("DATE_TRANSPORT", pollutionManagerDTO.getDateTransport() != null ? dateFormat.format(pollutionManagerDTO.getDateTransport()) : "N/A");
        message = message.replace("DATE_MOUVEMENT", pollutionManagerDTO.getDateMouvement() != null ? dateFormat.format(pollutionManagerDTO.getDateMouvement()) : "N/A");
        String libelle = null;
        if (pollutionManagerDTO.getPlastique() != null && pollutionManagerDTO.getPlastique().getLibelle() != null) {
            libelle = pollutionManagerDTO.getPlastique().getLibelle();
        } else if (pollutionManagerDTO.getProduit() != null && pollutionManagerDTO.getProduit().getLibelle() != null) {
            libelle = pollutionManagerDTO.getProduit().getLibelle();
        } else if (pollutionManagerDTO.getDechet() != null && pollutionManagerDTO.getDechet().getLibelle() != null) {
            libelle = pollutionManagerDTO.getDechet().getLibelle();
        }
        message = message.replace("LIBELLE", libelle != null ? libelle : "N/A");
        message = message.replace("ORIGINE", pollutionManagerDTO.getOrigine() != null && pollutionManagerDTO.getOrigine().getLibelle() != null ? pollutionManagerDTO.getOrigine().getLibelle() : "N/A");
        message = message.replace("DESTINATION", pollutionManagerDTO.getDestination() != null && pollutionManagerDTO.getDestination().getLibelle() != null ? pollutionManagerDTO.getDestination().getLibelle() : "N/A");
        message = message.replace("USER", userEntity.getAgent() != null && userEntity.getAgent().getPrenom() != null && userEntity.getAgent().getNom() != null ? userEntity.getAgent().getPrenom() + " " + userEntity.getAgent().getNom() : "N/A");
        return message;
    }

    private String replacePlaceholdersForRejet(String message, RejetPollutionDTO rejetPollutionDTO, UserEntity userEntity) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        message = message.replace("DATE_PRELEVEMENT", rejetPollutionDTO.getDatePrelevement() != null ? dateFormat.format(rejetPollutionDTO.getDatePrelevement()) : "N/A");
        String libelle = rejetPollutionDTO.getEntreprise() != null && rejetPollutionDTO.getEntreprise().getLibelle() != null ? rejetPollutionDTO.getEntreprise().getLibelle() : "N/A";
        message = message.replace("LIBELLE", libelle);
        message = message.replace("USER", userEntity.getAgent() != null && userEntity.getAgent().getPrenom() != null && userEntity.getAgent().getNom() != null ? userEntity.getAgent().getPrenom() + " " + userEntity.getAgent().getNom() : "N/A");
        return message;
    }

    private String replacePlaceholdersForMilieux(String message, MilieuxPollutionDTO milieuxPollutionDTO, UserEntity userEntity) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        message = message.replace("DATE_PRELEVEMENT", milieuxPollutionDTO.getDatePrelevement() != null ? dateFormat.format(milieuxPollutionDTO.getDatePrelevement()) : "N/A");
        String libelle = null;
        if (milieuxPollutionDTO.getPointPrelevement() != null && milieuxPollutionDTO.getPointPrelevement().getLibelle() != null) {
            libelle = milieuxPollutionDTO.getPointPrelevement().getLibelle();
        } else if (milieuxPollutionDTO.getComment() != null) {
            libelle = milieuxPollutionDTO.getComment();
        }
        message = message.replace("LIBELLE", libelle != null ? libelle : "N/A");
        message = message.replace("USER", userEntity.getAgent() != null && userEntity.getAgent().getPrenom() != null && userEntity.getAgent().getNom() != null ? userEntity.getAgent().getPrenom() + " " + userEntity.getAgent().getNom() : "N/A");
        return message;
    }
    private String replacePlaceholdersForEvaluationEnvironnementale(String message, EvaluationEnvironnementaleDTO evaluationEnvironnementaleDTO, UserEntity userEntity) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        message = message.replace("TITRE_PROJET", evaluationEnvironnementaleDTO.getTitreProjet() != null ? evaluationEnvironnementaleDTO.getTitreProjet() : "N/A");
        message = message.replace("TYPE_PROJET", evaluationEnvironnementaleDTO.getTypeProjet() != null ? evaluationEnvironnementaleDTO.getTypeProjet() : "N/A");
//        message = message.replace("LIBELLE", evaluationEnvironnementaleDTO.getTitreProjet() != null ? evaluationEnvironnementaleDTO.getTitreProjet() : "N/A");
        message = message.replace("DATE", dateFormat.format(new Date())); // No dateCreation, use current date
        message = message.replace("REFERENCE", evaluationEnvironnementaleDTO.getId() != null ? evaluationEnvironnementaleDTO.getId().toString() : "N/A");
        message = message.replace("USER", userEntity.getAgent() != null && userEntity.getAgent().getPrenom() != null && userEntity.getAgent().getNom() != null ? userEntity.getAgent().getPrenom() + " " + userEntity.getAgent().getNom() : "N/A");
        return message;
    }
}
