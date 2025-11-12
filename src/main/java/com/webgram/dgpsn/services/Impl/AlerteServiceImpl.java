//package com.webgram.dgpsn.services.Impl;
//
//import com.webgram.dgpsn.entities.*;
//import com.webgram.dgpsn.entities.enums.*;
//import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
//import com.webgram.dgpsn.mappers.AlerteMapper;
//import com.webgram.dgpsn.models.*;
//import com.webgram.dgpsn.repositories.*;
//import com.webgram.dgpsn.services.AlerteService;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.text.SimpleDateFormat;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.*;
//
//
//@Service
//@Transactional
//@RequiredArgsConstructor
//@Slf4j
//public class AlerteServiceImpl implements AlerteService {
//
//    private final AlerteRepository alerteRepository;
//
//    private final AlerteMapper alerteMapper;
//
//    private final TemplateRepository templateRepository;
//
//    private final UserRepository userRepository;
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    @Override
//    public Page<AlerteDTO> readAll(Pageable pageable, String message, Date date, String critere, Date endDate, Boolean read, Priority priority, String username) {
//        var pageableshort = PageRequest.of(pageable.getPageNumber(),pageable.getPageSize(), Sort.by("date").descending());
//        log.info("date {} ", date);
//        var alertes = alerteRepository.readAllByFilters(pageableshort, message, date, critere, endDate, read, priority, username)
//                .map(alerteMapper::asDto);
//        log.trace("list alert get ok {}", alertes);
//        return alertes;
//
//    }
//
//    @Override
//    public void setAlertToRead(Long alertId) {
//        var alert = alerteRepository.findById(alertId)
//                .orElseThrow(()-> new ResourceNotFoundException("alerte inexistant"));
//
//        alert.setRead(true);
//        alerteRepository.save(alert);
//    }
//    @Override
//    public void generateAlertCreateConge(CongeDTO congeDTO) {
//        var typeAlerte = TypeAlerte.DEMANDE_GONGE;
//        var template = templateRepository.findByTypeAlerte(typeAlerte);
//        if(template.isPresent()) {
//            var model = template.get();
//            var profiles = model.getProfiles();
//            profiles.forEach(profile -> {
//                var users = userRepository.findByProfile(profile);
//                users.forEach(userEntity -> {
//                    var message = model.getMessage();
//                    message = message.replace("DATE_DEBUT", new SimpleDateFormat("dd/MM/yyyy").format(congeDTO.getDateDebut()));
//                    String dureeMessage;
//                    if (congeDTO.getTypeConge() == TypeConge.ADMINISTRATIF) {
//                        dureeMessage = congeDTO.getDuree() + " jours";
//                    } else if (congeDTO.getTypeConge() == TypeConge.MATERNITE) {
//                        dureeMessage = congeDTO.getDuree() + " semaines";
//                    } else if (congeDTO.getTypeConge() == TypeConge.MALADIE) {
//                        dureeMessage = congeDTO.getDuree() + " semaines";
//                    } else if (congeDTO.getTypeConge() == TypeConge.AUTRES) {
//                        dureeMessage = congeDTO.getDuree() + " ans";
//                    } else {
//                        dureeMessage = String.valueOf(congeDTO.getDuree());
//                    }
//                    message = message.replace("DUREE", dureeMessage);
//                    var agentNomComplet = congeDTO.getAgent().getPrenom() + " " + congeDTO.getAgent().getNom() + " "  + " Matricule de Solde : " + congeDTO.getAgent().getMatricule();
//                    message = message.replace("AGENT", agentNomComplet);
//                    message = message.replace("TYPE_CONGE", congeDTO.getTypeConge().getDescription());
//                    message = message.replace("USER", userEntity.getAgent().getPrenom() + " " + userEntity.getAgent().getNom());
//                    var alert = AlerteEntity.builder()
//                            .message(message)
//                            .priority(model.getPriority())
//                            .date(new Date())
//                            .read(false)
//                            .user(userEntity)
//                            .build();
//                    alerteRepository.save(alert);
//                });
//            });
//        }
//    }
//
//    @Override
//    public void newOrdreMission(OrdreMissionEntity ordreMission) {
//        var template = templateRepository.findByTypeAlerte(TypeAlerte.NOUVEL_ORDRE_DE_MISSION);
//
//        if(template.isPresent()) {
//            List<UserEntity> users = (List<UserEntity>) userRepository
//                    .findAll(QUserEntity.userEntity.profile.in(template.get().getProfiles()));
//            List<AlerteEntity> alertes = new ArrayList<>();
//
//            if(ordreMission.getStatut().equals(StatutType.TRAITEMENT_ENCOUR)) {
//                for(var user : users) {
//                    var message = buildMessageOrdreMission(template.get().getMessage(), ordreMission, user);
//                    alertes.add(AlerteEntity.builder()
//                            .message(message)
//                            .read(Boolean.FALSE)
//                            .priority(template.get().getPriority())
//                            .user(user)
//                            .build());
//                }
//            }
//
//            alerteRepository.saveAll(alertes);
//        }
//    }
//
//
//    @Override
//    public void generateAlertCreateCessationService(CessationFonctionDTO cessationFonctionDTO) {
//        var typeAlerte = TypeAlerte.CESSATION_SERVICE;
//        var template = templateRepository.findByTypeAlerte(typeAlerte);
//        log.info("template");
//        if(template.isPresent()) {
//            var model = template.get();
//            var profiles = model.getProfiles();
//            profiles.forEach(profile -> {
//                var users = userRepository.findByProfile(profile);
//                users.forEach(userEntity -> {
//                    var message = model.getMessage();
//                    message = message.replace("DATE_CESSATION", new SimpleDateFormat("dd/MM/yyyy").format(cessationFonctionDTO.getDateCessation()));
//                    message = message.replace("NOMBRE_JOUR_CESSATION", String.valueOf(cessationFonctionDTO.getNombreDeJoursdemande()));
//                    if (cessationFonctionDTO.getConge() != null && cessationFonctionDTO.getConge().getAgent() != null) {
//                        var agentNomComplet = cessationFonctionDTO.getConge().getAgent().getPrenom() + " " + cessationFonctionDTO.getConge().getAgent().getNom() + " "  + "Matricule de Solde : " + cessationFonctionDTO.getConge().getAgent().getMatricule();
//                        message = message.replace("AGENT", agentNomComplet);
//                        log.info("Agent name in alert message: " + agentNomComplet);
//                    } else {
//                        log.warn("No agent found in the CongeDTO!");
//                    }
//                    message = message.replace("USER", userEntity.getAgent().getPrenom() + " " + userEntity.getAgent().getNom());
//                    var alert = AlerteEntity.builder()
//                            .message(message)
//                            .priority(model.getPriority())
//                            .date(new Date())
//                            .read(false)
//                            .user(userEntity)
//                            .build();
//                    alerteRepository.save(alert);
//                });
//            });
//        }
//    }
//
//    public void generateAlerteForCongeAccepte(CongeEntity conge) {
//        if (conge.getStatutType().equals(StatutType.ACCEPTER)) {
//            var template = templateRepository.findByTypeAlerte(TypeAlerte.VALIDATION_CONGE);
//            if (template.isPresent()) {
//                var profiles = template.get().getProfiles();
//                List<UserEntity> users = (List<UserEntity>) userRepository
//                        .findAll(QUserEntity.userEntity.profile.in(profiles));
//                List<AlerteEntity> alertes = new ArrayList<>();
//                for (var user : users) {
//                    var message = template.get().getMessage();
//                    message = message.replace("DATE_DEBUT", new SimpleDateFormat("dd MMMM yyyy", Locale.FRENCH).format(conge.getDateDebut()));
//                    message = message.replace("DATE_FIN", new SimpleDateFormat("dd MMMM yyyy", Locale.FRENCH).format(conge.getDateReprise()));
//                    String dureeMessage;
//                    if (conge.getTypeConge() == TypeConge.ADMINISTRATIF) {
//                        dureeMessage = conge.getDuree() + " jours";
//                    } else if (conge.getTypeConge() == TypeConge.MATERNITE) {
//                        dureeMessage = conge.getDuree() + " semaines";
//                    } else if (conge.getTypeConge() == TypeConge.MALADIE) {
//                        dureeMessage = conge.getDuree() + " semaines";
//                    } else if (conge.getTypeConge() == TypeConge.AUTRES) {
//                        dureeMessage = conge.getDuree() + " ans";
//                    } else {
//                        dureeMessage = String.valueOf(conge.getDuree());
//                    }
//                    message = message.replace("DUREE", dureeMessage);
//                    message = message.replace("TYPE_CONGE", conge.getTypeConge().getDescription());
//                    message = message.replace("PERSONNE_EN_CONGE", conge.getAgent().getPrenom() + " " + conge.getAgent().getNom()+ " "  + "Matricule de Solde : " + conge.getAgent().getMatricule());
//
//                    alertes.add(AlerteEntity.builder()
//                            .message(message)
//                            .priority(template.get().getPriority())
//                            .date(new Date())
//                            .read(false)
//                            .user(user)
//                            .build());
//                }
//                alerteRepository.saveAll(alertes);
//            } else {
//                log.warn("Template not found for TypeAlerte: " + TypeAlerte.VALIDATION_CONGE);
//            }
//        }
//    }
//
//    // ======================= RECRUTEMENT =======================
//    @Override
//    public void generateAlertNouveauRecrutement(RecrutementDTO recrutementDTO) {
//        var typeAlerte = TypeAlerte.NOUVEAU_RECRUTEMENT;
//        var templateOpt = templateRepository.findByTypeAlerte(typeAlerte);
//        if (templateOpt.isEmpty()) {
//            log.warn("Template non trouvé pour TypeAlerte: {}", typeAlerte);
//            return;
//        }
//
//        var model = templateOpt.get();
//        var profiles = model.getProfiles();
//
//        profiles.forEach(profile -> {
//            var users = userRepository.findByProfile(profile);
//            users.forEach(userEntity -> {
//                var message = model.getMessage();
//
//                message = message.replace("LIBELLE", recrutementDTO.getLibelle());
//                message = message.replace("DATE", new SimpleDateFormat("dd/MM/yyyy").format(recrutementDTO.getDateRecrutement()));
//                message = message.replace("TYPE_CONTRAT", Optional.ofNullable(recrutementDTO.getTypeContrat())
//                        .map(LabelDTO::getLibelle)
//                        .orElse("Non spécifié"));
//
//                var alert = AlerteEntity.builder()
//                        .message(message)
//                        .priority(model.getPriority())
//                        .date(new Date())
//                        .read(false)
//                        .user(userEntity)
//                        .build();
//                alerteRepository.save(alert);
//            });
//        });
//    }
//
//    // ======================= BUDGET =======================
//    @Override
//    public void generateAlertNouveauBudget(BudgetDgpsnDTO budgetDTO) {
//        var typeAlerte = TypeAlerte.NOUVEAU_BUDGET;
//        var templateOpt = templateRepository.findByTypeAlerte(typeAlerte);
//        if (templateOpt.isEmpty()) {
//            log.warn("Template non trouvé pour TypeAlerte: {}", typeAlerte);
//            return;
//        }
//
//        var model = templateOpt.get();
//        var profiles = model.getProfiles();
//
//        profiles.forEach(profile -> {
//            var users = userRepository.findByProfile(profile);
//            users.forEach(userEntity -> {
//                var message = model.getMessage();
//
//                message = message.replace("LIBELLE", budgetDTO.getLibelle());
//                message = message.replace("MONTANT_BUDGET", String.format("%,.2f", budgetDTO.getMontant()));
//                message = message.replace("DATE", budgetDTO.getAnnee().toString());
//
//                var alert = AlerteEntity.builder()
//                        .message(message)
//                        .priority(model.getPriority())
//                        .date(new Date())
//                        .read(false)
//                        .user(userEntity)
//                        .build();
//                alerteRepository.save(alert);
//            });
//        });
//    }
//
//    @Override
//    public void generateAlertUpdateBudget(BudgetDgpsnDTO budgetDTO) {
//        var typeAlerte = TypeAlerte.UPDATE__BUDGET;
//        var templateOpt = templateRepository.findByTypeAlerte(typeAlerte);
//        if (templateOpt.isEmpty()) {
//            log.warn("Template non trouvé pour TypeAlerte: {}", typeAlerte);
//            return;
//        }
//
//        var model = templateOpt.get();
//        var profiles = model.getProfiles();
//
//        profiles.forEach(profile -> {
//            var users = userRepository.findByProfile(profile);
//            users.forEach(userEntity -> {
//                var message = model.getMessage();
//
//                message = message.replace("LIBELLE", budgetDTO.getLibelle());
//                message = message.replace("MONTANT_BUDGET", String.format("%,.2f", budgetDTO.getMontant()));
//                message = message.replace("DATE", budgetDTO.getAnnee().toString());
//
//                var alert = AlerteEntity.builder()
//                        .message(message)
//                        .priority(model.getPriority())
//                        .date(new Date())
//                        .read(false)
//                        .user(userEntity)
//                        .build();
//                alerteRepository.save(alert);
//            });
//        });
//    }
//
//    @Override
//    public void generateAlertDeleteBudget(BudgetDgpsnDTO budgetDTO) {
//        var typeAlerte = TypeAlerte.DELETE_BUDGET;
//        var templateOpt = templateRepository.findByTypeAlerte(typeAlerte);
//        if (templateOpt.isEmpty()) {
//            log.warn("Template non trouvé pour TypeAlerte: {}", typeAlerte);
//            return;
//        }
//
//        var model = templateOpt.get();
//        var profiles = model.getProfiles();
//
//        profiles.forEach(profile -> {
//            var users = userRepository.findByProfile(profile);
//            users.forEach(userEntity -> {
//                var message = model.getMessage();
//
//                message = message.replace("LIBELLE", budgetDTO.getLibelle());
//                message = message.replace("MONTANT_BUDGET", String.format("%,.2f", budgetDTO.getMontant()));
//                message = message.replace("DATE", budgetDTO.getAnnee().toString());
//
//                var alert = AlerteEntity.builder()
//                        .message(message)
//                        .priority(model.getPriority())
//                        .date(new Date())
//                        .read(false)
//                        .user(userEntity)
//                        .build();
//                alerteRepository.save(alert);
//            });
//        });
//    }
//
//    // ======================= LIGNE BUDGÉTAIRE =======================
//    @Override
//    public void generateAlertNouvelleLigneBudget(LigneBudgetaireDTO ligneDTO) {
//        var typeAlerte = TypeAlerte.NOUVELLE_LIGNE_BUDGET;
//        var templateOpt = templateRepository.findByTypeAlerte(typeAlerte);
//        if (templateOpt.isEmpty()) {
//            log.warn("Template non trouvé pour TypeAlerte: {}", typeAlerte);
//            return;
//        }
//
//        var model = templateOpt.get();
//        var profiles = model.getProfiles();
//
//        // Récupérer les infos de rubrique via le DTO
//        String rubriqueLibelle = Optional.ofNullable(ligneDTO.getRubrique())
//                .map(PlanComptableElementDTO::getLibelle)
//                .orElse("Rubrique inconnue");
//
//        String compteCode = Optional.ofNullable(ligneDTO.getRubrique())
//                .map(PlanComptableElementDTO::getCode)
//                .orElse("N/A");
//
//        profiles.forEach(profile -> {
//            var users = userRepository.findByProfile(profile);
//            users.forEach(userEntity -> {
//                var message = model.getMessage();
//
//                message = message.replace("LIBELLE", rubriqueLibelle);
//                message = message.replace("MONTANT_LIGNE_BUDGET", String.format("%,.2f", ligneDTO.getMontant()));
//                message = message.replace("DATE", LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
//                message = message.replace("COMPTE", compteCode);
//
//                var alert = AlerteEntity.builder()
//                        .message(message)
//                        .priority(model.getPriority())
//                        .date(new Date())
//                        .read(false)
//                        .user(userEntity)
//                        .build();
//                alerteRepository.save(alert);
//            });
//        });
//    }
//
//    @Override
//    public void generateAlertMultipleLignesBudgetAjoutees(List<LigneBudgetaireDTO> lignesDTOs, Long budgetId, double montantTotalAjoute) {
//        var typeAlerte = TypeAlerte.NOUVELLE_LIGNE_BUDGET;
//        var templateOpt = templateRepository.findByTypeAlerte(typeAlerte);
//        if (templateOpt.isEmpty()) {
//            log.warn("Template non trouvé pour TypeAlerte: {}", typeAlerte);
//            return;
//        }
//
//        var model = templateOpt.get();
//        var profiles = model.getProfiles();
//
//        // Récupérer le libellé du budget
//        var budget = entityManager.find(BudgetDgpsnEntity.class, budgetId);
//        String budgetLibelle = budget != null ? budget.getLibelle() : "Budget inconnu";
//
//        // Compter les lignes
//        int nombreLignes = lignesDTOs.size();
//
//        profiles.forEach(profile -> {
//            var users = userRepository.findByProfile(profile);
//            users.forEach(userEntity -> {
//                var message = model.getMessage();
//
//                message = message.replace("LIBELLE", budgetLibelle);
//                message = message.replace("MONTANT_LIGNE_BUDGET", String.format("%,.2f", montantTotalAjoute));
//                message = message.replace("DATE", LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
//                message = message.replace("COMPTE", String.format("%d ligne(s)", nombreLignes));
//
//                var alert = AlerteEntity.builder()
//                        .message(message)
//                        .priority(model.getPriority())
//                        .date(new Date())
//                        .read(false)
//                        .user(userEntity)
//                        .build();
//                alerteRepository.save(alert);
//            });
//        });
//    }
//    @Override
//    public void generateAlertUpdateLigneBudget(LigneBudgetaireDTO ligneDTO) {
//        var typeAlerte = TypeAlerte.UPDATE_LIGNE_BUDGET;
//        var templateOpt = templateRepository.findByTypeAlerte(typeAlerte);
//        if (templateOpt.isEmpty()) {
//            log.warn("Template non trouvé pour TypeAlerte: {}", typeAlerte);
//            return;
//        }
//
//        var model = templateOpt.get();
//        var profiles = model.getProfiles();
//
//        String rubriqueLibelle = Optional.ofNullable(ligneDTO.getRubrique())
//                .map(PlanComptableElementDTO::getLibelle)
//                .orElse("Rubrique inconnue");
//
//        String compteCode = Optional.ofNullable(ligneDTO.getRubrique())
//                .map(PlanComptableElementDTO::getCode)
//                .orElse("N/A");
//
//        profiles.forEach(profile -> {
//            var users = userRepository.findByProfile(profile);
//            users.forEach(userEntity -> {
//                var message = model.getMessage();
//
//                message = message.replace("RUBRIQUE", rubriqueLibelle);
//                message = message.replace("MONTANT_LIGNE_BUDGET", String.format("%,.2f", ligneDTO.getMontant()));
//                message = message.replace("DATE", LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
//                message = message.replace("COMPTE", compteCode);
//
//                var alert = AlerteEntity.builder()
//                        .message(message)
//                        .priority(model.getPriority())
//                        .date(new Date())
//                        .read(false)
//                        .user(userEntity)
//                        .build();
//                alerteRepository.save(alert);
//            });
//        });
//    }
//
//    @Override
//    public void generateAlertDeleteLigneBudget(LigneBudgetaireDTO ligneDTO) {
//        var typeAlerte = TypeAlerte.DELETE_LIGNE_BUDGET;
//        var templateOpt = templateRepository.findByTypeAlerte(typeAlerte);
//        if (templateOpt.isEmpty()) {
//            log.warn("Template non trouvé pour TypeAlerte: {}", typeAlerte);
//            return;
//        }
//
//        var model = templateOpt.get();
//        var profiles = model.getProfiles();
//
//        String rubriqueLibelle = Optional.ofNullable(ligneDTO.getRubrique())
//                .map(PlanComptableElementDTO::getLibelle)
//                .orElse("Rubrique inconnue");
//
//        String compteCode = Optional.ofNullable(ligneDTO.getRubrique())
//                .map(PlanComptableElementDTO::getCode)
//                .orElse("N/A");
//
//        profiles.forEach(profile -> {
//            var users = userRepository.findByProfile(profile);
//            users.forEach(userEntity -> {
//                var message = model.getMessage();
//
//                message = message.replace("LIBELLE", rubriqueLibelle);
//                message = message.replace("MONTANT_BUDGET", String.format("%,.2f", ligneDTO.getMontant()));
//                message = message.replace("DATE", LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
//                message = message.replace("COMPTE", compteCode);
//
//                var alert = AlerteEntity.builder()
//                        .message(message)
//                        .priority(model.getPriority())
//                        .date(new Date())
//                        .read(false)
//                        .user(userEntity)
//                        .build();
//                alerteRepository.save(alert);
//            });
//        });
//    }
//    private String buildMessageOrdreMission(String message, OrdreMissionEntity ordreMission, UserEntity user) {
//        if(message.contains("LIBELLE")) {
//            message = message.replaceAll("LIBELLE", ordreMission.getObjectMission());
//        }
//        if(message.contains("DATE_DEBUT")) {
////            String date = LocalDate.parse(ordreMission.getDateDepartOrdre().toString())
////                    .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
//            message = message.replaceAll("DATE_DEBUT", ordreMission.getDateRetourOrdre().toString());
//        }
//        if(message.contains("DATE_FIN")) {
////            String date = LocalDate.parse(ordreMission.getDateRetourOrdre().toString())
////                    .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
//            message = message.replaceAll("DATE_FIN", ordreMission.getDateRetourOrdre().toString());
//        }
//
//
//        if(message.contains("AGENT")) {
//            message = message.replaceAll("AGENT", user.getAgent().getPrenom() + " " + user.getAgent().getNom());
//        }
//        return message;
//    }
//}
package com.webgram.dgpsn.services.Impl;

import com.webgram.dgpsn.entities.*;
import com.webgram.dgpsn.entities.enums.*;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.AlerteMapper;
import com.webgram.dgpsn.models.*;
import com.webgram.dgpsn.repositories.*;
import com.webgram.dgpsn.services.AlerteService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<AlerteDTO> readAll(Pageable pageable, String message, Date date, String critere, Date endDate, Boolean read, Priority priority, String username) {
        var pageableshort = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("date").descending());
        log.info("date {} ", date);
        var alertes = alerteRepository.readAllByFilters(pageableshort, message, date, critere, endDate, read, priority, username)
                .map(alerteMapper::asDto);
        log.trace("list alert get ok {}", alertes);
        return alertes;
    }

    @Override
    public void setAlertToRead(Long alertId) {
        var alert = alerteRepository.findById(alertId)
                .orElseThrow(() -> new ResourceNotFoundException("alerte inexistant"));
        alert.setRead(true);
        alerteRepository.save(alert);
    }

    @Override
    public void generateAlertCreateConge(CongeDTO congeDTO) {
        var typeAlerte = TypeAlerte.DEMANDE_GONGE;
        var template = templateRepository.findByTypeAlerte(typeAlerte);
        if (template.isPresent()) {
            var model = template.get();
            var profiles = model.getProfiles();
            profiles.forEach(profile -> {
                var users = userRepository.findByProfile(profile);
                users.forEach(userEntity -> {
                    // Vérifier que l'utilisateur a un agent associé
                    if (userEntity.getAgent() == null) {
                        log.warn("Utilisateur sans agent associé (ID: {}), alerte non générée", userEntity.getId());
                        return;
                    }

                    var message = model.getMessage();
                    message = message.replace("DATE_DEBUT", new SimpleDateFormat("dd/MM/yyyy").format(congeDTO.getDateDebut()));

                    String dureeMessage = getDureeMessage(congeDTO.getTypeConge(), congeDTO.getDuree());
                    message = message.replace("DUREE", dureeMessage);

                    var agentNomComplet = String.format("%s %s - Matricule de Solde : %s",
                            congeDTO.getAgent().getPrenom(),
                            congeDTO.getAgent().getNom(),
                            congeDTO.getAgent().getMatricule());
                    message = message.replace("AGENT", agentNomComplet);
                    message = message.replace("TYPE_CONGE", congeDTO.getTypeConge().getDescription());
                    message = message.replace("USER", userEntity.getAgent().getPrenom() + " " + userEntity.getAgent().getNom());

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

    @Override
    public void newOrdreMission(OrdreMissionEntity ordreMission) {
        var template = templateRepository.findByTypeAlerte(TypeAlerte.NOUVEL_ORDRE_DE_MISSION);
        if (template.isPresent()) {
            List<UserEntity> users = (List<UserEntity>) userRepository
                    .findAll(QUserEntity.userEntity.profile.in(template.get().getProfiles()));
            List<AlerteEntity> alertes = new ArrayList<>();

            if (ordreMission.getStatut().equals(StatutType.TRAITEMENT_ENCOUR)) {
                for (var user : users) {
                    var message = buildMessageOrdreMission(template.get().getMessage(), ordreMission, user);
                    alertes.add(AlerteEntity.builder()
                            .message(message)
                            .read(Boolean.FALSE)
                            .priority(template.get().getPriority())
                            .date(new Date())
                            .user(user)
                            .build());
                }
            }
            alerteRepository.saveAll(alertes);
        }
    }

    @Override
    public void generateAlertCreateCessationService(CessationFonctionDTO cessationFonctionDTO) {
        var typeAlerte = TypeAlerte.CESSATION_SERVICE;
        var template = templateRepository.findByTypeAlerte(typeAlerte);

        if (template.isPresent()) {
            var model = template.get();
            var profiles = model.getProfiles();
            profiles.forEach(profile -> {
                var users = userRepository.findByProfile(profile);
                users.forEach(userEntity -> {
                    // Vérifier que l'utilisateur a un agent associé
                    if (userEntity.getAgent() == null) {
                        log.warn("Utilisateur sans agent associé (ID: {}), alerte non générée", userEntity.getId());
                        return;
                    }

                    var message = model.getMessage();
                    message = message.replace("DATE_CESSATION", new SimpleDateFormat("dd/MM/yyyy").format(cessationFonctionDTO.getDateCessation()));
                    message = message.replace("NOMBRE_JOUR_CESSATION", String.valueOf(cessationFonctionDTO.getNombreDeJoursdemande()));

                    if (cessationFonctionDTO.getConge() != null && cessationFonctionDTO.getConge().getAgent() != null) {
                        var agentNomComplet = String.format("%s %s - Matricule de Solde : %s",
                                cessationFonctionDTO.getConge().getAgent().getPrenom(),
                                cessationFonctionDTO.getConge().getAgent().getNom(),
                                cessationFonctionDTO.getConge().getAgent().getMatricule());
                        message = message.replace("AGENT", agentNomComplet);
                        log.info("Agent name in alert message: {}", agentNomComplet);
                    } else {
                        log.warn("No agent found in the CongeDTO!");
                    }

                    message = message.replace("USER", userEntity.getAgent().getPrenom() + " " + userEntity.getAgent().getNom());

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

    @Override
    public void generateAlerteForCongeAccepte(CongeEntity conge) {
        if (conge.getStatutType().equals(StatutType.ACCEPTER)) {
            var template = templateRepository.findByTypeAlerte(TypeAlerte.VALIDATION_CONGE);
            if (template.isPresent()) {
                var profiles = template.get().getProfiles();
                List<UserEntity> users = (List<UserEntity>) userRepository
                        .findAll(QUserEntity.userEntity.profile.in(profiles));
                List<AlerteEntity> alertes = new ArrayList<>();

                for (var user : users) {
                    var message = template.get().getMessage();
                    message = message.replace("DATE_DEBUT", new SimpleDateFormat("dd MMMM yyyy", Locale.FRENCH).format(conge.getDateDebut()));
                    message = message.replace("DATE_FIN", new SimpleDateFormat("dd MMMM yyyy", Locale.FRENCH).format(conge.getDateReprise()));

                    String dureeMessage = getDureeMessage(conge.getTypeConge(), conge.getDuree());
                    message = message.replace("DUREE", dureeMessage);
                    message = message.replace("TYPE_CONGE", conge.getTypeConge().getDescription());

                    var agentNomComplet = String.format("%s %s - Matricule de Solde : %s",
                            conge.getAgent().getPrenom(),
                            conge.getAgent().getNom(),
                            conge.getAgent().getMatricule());
                    message = message.replace("PERSONNE_EN_CONGE", agentNomComplet);

                    alertes.add(AlerteEntity.builder()
                            .message(message)
                            .priority(template.get().getPriority())
                            .date(new Date())
                            .read(false)
                            .user(user)
                            .build());
                }
                alerteRepository.saveAll(alertes);
            } else {
                log.warn("Template not found for TypeAlerte: {}", TypeAlerte.VALIDATION_CONGE);
            }
        }
    }

    // ======================= RECRUTEMENT =======================
    @Override
    public void generateAlertNouveauRecrutement(RecrutementDTO recrutementDTO) {
        var typeAlerte = TypeAlerte.NOUVEAU_RECRUTEMENT;
        var templateOpt = templateRepository.findByTypeAlerte(typeAlerte);
        if (templateOpt.isEmpty()) {
            log.warn("Template non trouvé pour TypeAlerte: {}", typeAlerte);
            return;
        }

        var model = templateOpt.get();
        var profiles = model.getProfiles();

        profiles.forEach(profile -> {
            var users = userRepository.findByProfile(profile);
            users.forEach(userEntity -> {
                // Vérifier que l'utilisateur a un agent associé
                if (userEntity.getAgent() == null) {
                    log.warn("Utilisateur sans agent associé (ID: {}), alerte non générée", userEntity.getId());
                    return;
                }

                var message = model.getMessage();
                message = message.replace("LIBELLE", recrutementDTO.getLibelle());
                message = message.replace("DATE", new SimpleDateFormat("dd/MM/yyyy").format(recrutementDTO.getDateRecrutement()));
                message = message.replace("TYPE_CONTRAT", Optional.ofNullable(recrutementDTO.getTypeContrat())
                        .map(LabelDTO::getLibelle)
                        .orElse("Non spécifié"));

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

    // ======================= BUDGET =======================
    @Override
    public void generateAlertNouveauBudget(BudgetDgpsnDTO budgetDTO) {
        generateBudgetAlert(TypeAlerte.NOUVEAU_BUDGET, budgetDTO);
    }

    @Override
    public void generateAlertUpdateBudget(BudgetDgpsnDTO budgetDTO) {
        generateBudgetAlert(TypeAlerte.UPDATE__BUDGET, budgetDTO);
    }

    @Override
    public void generateAlertDeleteBudget(BudgetDgpsnDTO budgetDTO) {
        generateBudgetAlert(TypeAlerte.DELETE_BUDGET, budgetDTO);
    }

    private void generateBudgetAlert(TypeAlerte typeAlerte, BudgetDgpsnDTO budgetDTO) {
        var templateOpt = templateRepository.findByTypeAlerte(typeAlerte);
        if (templateOpt.isEmpty()) {
            log.warn("Template non trouvé pour TypeAlerte: {}", typeAlerte);
            return;
        }

        var model = templateOpt.get();
        var profiles = model.getProfiles();

        profiles.forEach(profile -> {
            var users = userRepository.findByProfile(profile);
            users.forEach(userEntity -> {
                // Vérifier que l'utilisateur a un agent associé
                if (userEntity.getAgent() == null) {
                    log.warn("Utilisateur sans agent associé (ID: {}), alerte non générée", userEntity.getId());
                    return;
                }

                var message = model.getMessage();
                message = message.replace("LIBELLE", budgetDTO.getLibelle());
                message = message.replace("MONTANT_BUDGET", String.format("%,.2f", budgetDTO.getMontant()));
                message = message.replace("DATE", budgetDTO.getAnnee().toString());

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

    // ======================= LIGNE BUDGÉTAIRE =======================
    @Override
    public void generateAlertNouvelleLigneBudget(LigneBudgetaireDTO ligneDTO) {
        var typeAlerte = TypeAlerte.NOUVELLE_LIGNE_BUDGET;
        var templateOpt = templateRepository.findByTypeAlerte(typeAlerte);
        if (templateOpt.isEmpty()) {
            log.warn("Template non trouvé pour TypeAlerte: {}", typeAlerte);
            return;
        }

        var model = templateOpt.get();
        var profiles = model.getProfiles();

        String rubriqueLibelle = Optional.ofNullable(ligneDTO.getRubrique())
                .map(PlanComptableElementDTO::getLibelle)
                .orElse("Rubrique inconnue");

        String compteCode = Optional.ofNullable(ligneDTO.getRubrique())
                .map(PlanComptableElementDTO::getCode)
                .orElse("N/A");

        profiles.forEach(profile -> {
            var users = userRepository.findByProfile(profile);
            users.forEach(userEntity -> {
                // Vérifier que l'utilisateur a un agent associé
                if (userEntity.getAgent() == null) {
                    log.warn("Utilisateur sans agent associé (ID: {}), alerte non générée", userEntity.getId());
                    return;
                }

                var message = model.getMessage();
                message = message.replace("LIBELLE", rubriqueLibelle);
                message = message.replace("MONTANT_LIGNE_BUDGET", String.format("%,.2f", ligneDTO.getMontant()));
                message = message.replace("DATE", LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                message = message.replace("COMPTE", compteCode);

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

    @Override
    public void generateAlertMultipleLignesBudgetAjoutees(List<LigneBudgetaireDTO> lignesDTOs, Long budgetId, double montantTotalAjoute) {
        var typeAlerte = TypeAlerte.NOUVELLE_LIGNE_BUDGET;
        var templateOpt = templateRepository.findByTypeAlerte(typeAlerte);
        if (templateOpt.isEmpty()) {
            log.warn("Template non trouvé pour TypeAlerte: {}", typeAlerte);
            return;
        }

        var model = templateOpt.get();
        var profiles = model.getProfiles();

        var budget = entityManager.find(BudgetDgpsnEntity.class, budgetId);
        String budgetLibelle = budget != null ? budget.getLibelle() : "Budget inconnu";
        int nombreLignes = lignesDTOs.size();

        profiles.forEach(profile -> {
            var users = userRepository.findByProfile(profile);
            users.forEach(userEntity -> {
                // Vérifier que l'utilisateur a un agent associé
                if (userEntity.getAgent() == null) {
                    log.warn("Utilisateur sans agent associé (ID: {}), alerte non générée", userEntity.getId());
                    return;
                }

                var message = model.getMessage();
                message = message.replace("LIBELLE", budgetLibelle);
                message = message.replace("MONTANT_LIGNE_BUDGET", String.format("%,.2f", montantTotalAjoute));
                message = message.replace("DATE", LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                message = message.replace("COMPTE", String.format("%d ligne(s)", nombreLignes));

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

    @Override
    public void generateAlertUpdateLigneBudget(LigneBudgetaireDTO ligneDTO) {
        var typeAlerte = TypeAlerte.UPDATE_LIGNE_BUDGET;
        var templateOpt = templateRepository.findByTypeAlerte(typeAlerte);
        if (templateOpt.isEmpty()) {
            log.warn("Template non trouvé pour TypeAlerte: {}", typeAlerte);
            return;
        }

        var model = templateOpt.get();
        var profiles = model.getProfiles();

        String rubriqueLibelle = Optional.ofNullable(ligneDTO.getRubrique())
                .map(PlanComptableElementDTO::getLibelle)
                .orElse("Rubrique inconnue");

        String compteCode = Optional.ofNullable(ligneDTO.getRubrique())
                .map(PlanComptableElementDTO::getCode)
                .orElse("N/A");

        profiles.forEach(profile -> {
            var users = userRepository.findByProfile(profile);
            users.forEach(userEntity -> {
                // Vérifier que l'utilisateur a un agent associé
                if (userEntity.getAgent() == null) {
                    log.warn("Utilisateur sans agent associé (ID: {}), alerte non générée", userEntity.getId());
                    return;
                }

                var message = model.getMessage();
                message = message.replace("RUBRIQUE", rubriqueLibelle);
                message = message.replace("MONTANT_LIGNE_BUDGET", String.format("%,.2f", ligneDTO.getMontant()));
                message = message.replace("DATE", LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                message = message.replace("COMPTE", compteCode);

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

    @Override
    public void generateAlertDeleteLigneBudget(LigneBudgetaireDTO ligneDTO) {
        var typeAlerte = TypeAlerte.DELETE_LIGNE_BUDGET;
        var templateOpt = templateRepository.findByTypeAlerte(typeAlerte);
        if (templateOpt.isEmpty()) {
            log.warn("Template non trouvé pour TypeAlerte: {}", typeAlerte);
            return;
        }

        var model = templateOpt.get();
        var profiles = model.getProfiles();

        String rubriqueLibelle = Optional.ofNullable(ligneDTO.getRubrique())
                .map(PlanComptableElementDTO::getLibelle)
                .orElse("Rubrique inconnue");

        String compteCode = Optional.ofNullable(ligneDTO.getRubrique())
                .map(PlanComptableElementDTO::getCode)
                .orElse("N/A");

        profiles.forEach(profile -> {
            var users = userRepository.findByProfile(profile);
            users.forEach(userEntity -> {
                // Vérifier que l'utilisateur a un agent associé
                if (userEntity.getAgent() == null) {
                    log.warn("Utilisateur sans agent associé (ID: {}), alerte non générée", userEntity.getId());
                    return;
                }

                var message = model.getMessage();
                message = message.replace("LIBELLE", rubriqueLibelle);
                message = message.replace("MONTANT_BUDGET", String.format("%,.2f", ligneDTO.getMontant()));
                message = message.replace("DATE", LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                message = message.replace("COMPTE", compteCode);

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

    // ======================= MÉTHODES UTILITAIRES =======================

    private String getDureeMessage(TypeConge typeConge, Integer duree) {
        if (typeConge == TypeConge.ADMINISTRATIF) {
            return duree + " jours";
        } else if (typeConge == TypeConge.MATERNITE || typeConge == TypeConge.MALADIE) {
            return duree + " semaines";
        } else if (typeConge == TypeConge.AUTRES) {
            return duree + " ans";
        } else {
            return String.valueOf(duree);
        }
    }

    private String buildMessageOrdreMission(String message, OrdreMissionEntity ordreMission, UserEntity user) {
        if (message.contains("LIBELLE")) {
            message = message.replace("LIBELLE", ordreMission.getObjectMission());
        }
        if (message.contains("DATE_DEBUT")) {
            // CORRECTION: Utiliser getDateDepartOrdre() au lieu de getDateRetourOrdre()
            message = message.replace("DATE_DEBUT",
                    ordreMission.getDateDepartOrdre() != null ? ordreMission.getDateDepartOrdre().toString() : "");
        }
        if (message.contains("DATE_FIN")) {
            message = message.replace("DATE_FIN",
                    ordreMission.getDateRetourOrdre() != null ? ordreMission.getDateRetourOrdre().toString() : "");
        }
        if (message.contains("AGENT") && user.getAgent() != null) {
            message = message.replace("AGENT", user.getAgent().getPrenom() + " " + user.getAgent().getNom());
        }
        return message;
    }
}