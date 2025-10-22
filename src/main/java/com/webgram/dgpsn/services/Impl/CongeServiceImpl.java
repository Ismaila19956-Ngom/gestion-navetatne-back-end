package com.webgram.dgpsn.services.Impl;

import com.querydsl.core.BooleanBuilder;
import com.webgram.dgpsn.entities.enums.*;
import com.webgram.dgpsn.exceptions.UserNotAllowedToValidWorkflowException;
import com.webgram.dgpsn.mappers.WorkflowStepMapper;
import com.webgram.dgpsn.models.WorkflowValidationHistoriqueDTO;
import com.webgram.dgpsn.security.SecurityUtils;
import com.webgram.dgpsn.services.WorkflowStepService;
import com.webgram.dgpsn.services.WorkflowValidationHistoriqueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.webgram.dgpsn.annotations.FilterAgent;
import com.webgram.dgpsn.annotations.FilterData;
import com.webgram.dgpsn.entities.*;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.CongeMapper;
import com.webgram.dgpsn.mappers.DocumentMapper;
import com.webgram.dgpsn.models.AgentDTO;
import com.webgram.dgpsn.models.CongeDTO;
import com.webgram.dgpsn.repositories.*;
import com.webgram.dgpsn.services.AlerteService;
import com.webgram.dgpsn.services.CongeService;
//import com.webgram.dgpsn.services.utils.GeneredNumeroReference;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CongeServiceImpl implements CongeService {
    private final CongeRepository congeRepository;

//    private final NumeroReferenceRepository numeroReferenceRepository;
    private final CongeMapper congeMapper;

    private final DocumentServiceImpl documentService;

    private final DocumentMapper documentMapper;

    private final CategorieDocumentRepository categorieDocumentRepository;
    private final DocumentRepository documentRepository;
    private final AutreDocumentRepository autreDocumentRepository;
    private final AgentRepository agentRepository;
    private final AlerteService alerteService;
    private final WorkflowStepRepository workflowStepRepository;
    private final WorkflowValidationHistoriqueRepository workflowHistoriqueRepository;
//    private final GeneredNumeroReference congeReferenceService;
    private final UserRepository userRepository;
    private final WorkflowStepValidationRepository workflowStepValidationRepository;
    private final WorkflowStepService workflowStepService;
    private final WorkflowStepValidationUserRepository validationUserRepository;
    private final WorkflowValidationHistoriqueService validationHistoriqueService;
    private final WorkflowRepository workflowRepository;
    private final WorkflowStepMapper workflowStepMapper;
    @Value("${storage-document.file-storage-root-path}")
    private String pdfOutputDirectory;
    private static final String FORMATION_NOT_FOUND = "La conge n'existe pas";
    private static final String AGENT_NOT_FOUND = "L'agent n'existe pas";
    private static final String NUMERO_FICHE_PREFIX = "DOD";

    @Override
//    public CongeDTO create(CongeDTO congeDTO) {
//        if (hasActiveConge(congeDTO.getAgentId(), congeDTO.getDateDemande(), congeDTO.getTypeConge(), congeDTO.getDateDepart(), congeDTO.getDateReprise())) {
//            AgentEntity agent = agentRepository.findById(congeDTO.getAgentId())
//                    .orElseThrow(() -> new ResourceNotFoundException(AGENT_NOT_FOUND));
//            throw new RuntimeException(
//                    String.format("L'agent %s (%s) a déjà un congé en cours.", agent.getPrenom(), agent.getNom(), agent.getMatricule())
//            );
//        }
//        if (TypeConge.ADMINISTRATIF.equals(congeDTO.getTypeConge())) {
//            if (isDateOverlap(congeDTO.getAgentId(), congeDTO.getDateDepart(), congeDTO.getDateReprise())) {
//                throw new IllegalStateException("Un chevauchement de dates a été détecté : l'agent a déjà un congé en cours . Veuillez vérifier la dates debut autorisation et de fin autorisation .");
//            }
//        }
////        congeReferenceService.setCongeReferences(congeDTO);
//        congeDTO.setStatutType(StatutType.TRAITEMENT_ENCOUR);
////        congeDTO.setNumFicheConge(generateNumeroFiche());
//        congeDTO.setDureeCessation(congeDTO.getDuree());
//        generateAlertCreateConge(congeDTO);
//
//        CongeDTO.setFinalStep(Boolean.FALSE);
//
//        /**
//         * Check if a workflow is configured for this module (WorkflowType.FLUX_TRESORERIE)
//         * then set the step of 'entity' to the first step
//         */
//        workflowRepository.findByType(WorkflowType.DEMANDE_CONGE)
//                .ifPresent(flow-> workflowStepRepository
//                        .findTopByWorkflowIdOrderByOrdreAsc(flow.getId())
//                        .ifPresent(CongeDTO::setWorkflowStep));
//        var savedConge = congeRepository.save(congeMapper.asEntity(congeDTO));
//
//        return congeMapper.asDto(savedConge);
//    }

    public CongeDTO create(CongeDTO congeDTO) {
        if (hasActiveConge(congeDTO.getAgentId(), congeDTO.getDateDemande(), congeDTO.getTypeConge(), congeDTO.getDateDepart(), congeDTO.getDateReprise())) {
            AgentEntity agent = agentRepository.findById(congeDTO.getAgentId())
                    .orElseThrow(() -> new ResourceNotFoundException(AGENT_NOT_FOUND));
            throw new RuntimeException(
                    String.format("L'agent %s (%s) a déjà un congé en cours.", agent.getPrenom(), agent.getNom(), agent.getMatricule())
            );
        }

        if (TypeConge.ADMINISTRATIF.equals(congeDTO.getTypeConge())) {
            if (isDateOverlap(congeDTO.getAgentId(), congeDTO.getDateDepart(), congeDTO.getDateReprise())) {
                throw new IllegalStateException("Un chevauchement de dates a été détecté : l'agent a déjà un congé en cours. Veuillez vérifier les dates de début et de fin d'autorisation.");
            }
        }

        congeDTO.setStatutType(StatutType.TRAITEMENT_ENCOUR);
        congeDTO.setDureeCessation(congeDTO.getDuree());
        generateAlertCreateConge(congeDTO);
        congeDTO.setFinalStep(Boolean.FALSE);

        workflowRepository.findByType(WorkflowType.DEMANDE_CONGE)
                .ifPresent(flow -> workflowStepRepository
                        .findTopByWorkflowIdOrderByOrdreAsc(flow.getId())
                        .ifPresent(step -> congeDTO.setWorkflowStep(workflowStepMapper.asDto(step))));
        var savedConge = congeRepository.save(congeMapper.asEntity(congeDTO));

        return congeMapper.asDto(savedConge);
    }


    @Override
    public CongeDTO createDocumentConge(Long congeId, MultipartFile file, String document) throws IOException {
        var documentConge = documentService.createDocument(file, document);
        var conge = congeRepository.findById(congeId).orElseThrow(() -> new ResourceNotFoundException("not found"));
        conge.getDocument().add(documentMapper.asEntity(documentConge));
        return congeMapper.asDto(conge);

    }

    @Override
    public CongeDTO update(Long congeId, CongeDTO congeDTO) {
        read(congeId);
        var agent = agentRepository.findById(congeDTO.getAgentId());
        if (agent.isEmpty()) {
            throw new ResourceNotFoundException(AGENT_NOT_FOUND);
        }
        congeDTO.setId(congeId);
        congeDTO.setDureeCessation(congeDTO.getDuree());
        var savedConge = congeRepository.save(congeMapper.asEntity(congeDTO));
        return congeMapper.asDto(savedConge);
    }

    @Override
    public CongeDTO read(Long congeId) {
        var conge = congeRepository.findById(congeId).
                orElseThrow(() -> new ResourceNotFoundException(FORMATION_NOT_FOUND));
        return congeMapper.asDto(conge);
    }

    @Override
    public List<CongeDTO> readAll() {
        return congeRepository.findAll()
                .stream()
                .map(congeMapper::asDto)
                .collect(Collectors.toList());
    }


public void validConge(Long congeId, StatutType statut) {
    var conge = congeRepository.findById(congeId)
            .orElseThrow(() -> new ResourceNotFoundException("Conge", congeId));
    if (Objects.nonNull(statut)) {
        if (StatutType.ACCEPTER.equals(statut)) {
            validationDocuments(conge);
            removeIndexDocuments(conge);
        } else if (StatutType.TRAITEMENT_ENCOUR.equals(statut)) {
            conge.setNumeroDecision(null);
        }
    }
    log.info("statut:{}", statut);
    conge.setStatutType(statut);
    var updateStatutConge = congeRepository.save(conge);
    log.info("Updated conge statut: {}", updateStatutConge.getStatutType());
    if (updateStatutConge.getStatutType() == StatutType.ACCEPTER) {
//        alerteService.generateAlerteForCongeAccepte(updateStatutConge);
    }
}

    @Override
    @FilterData
    public Page<CongeDTO> readPage(Map<String, String> searchParams, int page, int size, @FilterAgent List<Long> agentIds) throws ParseException {
        var booleanBuilder = new BooleanBuilder();
        filterAutorized(booleanBuilder, agentIds);
        if (Objects.nonNull(searchParams)) {
            var qConge = QCongeEntity.congeEntity;
            if (searchParams.containsKey("libelle"))
                booleanBuilder.and(qConge.libelle.containsIgnoreCase(searchParams.get("libelle")));

            if (searchParams.containsKey("duree")) {
                String dureeValueStr = searchParams.get("duree");
                Integer dureeValue = Integer.parseInt(dureeValueStr);
                booleanBuilder.and(qConge.duree.stringValue().eq(dureeValue.toString()));
            }
            if (searchParams.containsKey("dateDemande")) {
                var date = new SimpleDateFormat("yyyy-MM-dd").parse(searchParams.get("dateDemande"));
                booleanBuilder.and(qConge.dateDemande.eq(date));
            }
            if (searchParams.containsKey("dateDebut")) {
                var date = new SimpleDateFormat("yyyy-MM-dd").parse(searchParams.get("dateDebut"));
                booleanBuilder.and(qConge.dateDebut.eq(date));
            }
            if (searchParams.containsKey("dateDepart")) {
                var date = new SimpleDateFormat("yyyy-MM-dd").parse(searchParams.get("dateDepart"));
                booleanBuilder.and(qConge.dateDepart.eq(date));
            }
            if (searchParams.containsKey("dateReprise")) {
                var date = new SimpleDateFormat("yyyy-MM-dd").parse(searchParams.get("dateReprise"));
                booleanBuilder.and(qConge.dateReprise.eq(date));
            }
            String typeConge = searchParams.get("typeConge");
            if (typeConge != null && !typeConge.isEmpty()) {
                booleanBuilder.and(qConge.typeConge.stringValue().lower().containsIgnoreCase(typeConge.toLowerCase()));
            }
            if (searchParams.containsKey("description"))
                booleanBuilder.and(qConge.description.containsIgnoreCase(searchParams.get("description")));

            if (searchParams.containsKey("agentId"))
                booleanBuilder.and(qConge.agent.id.eq(Long.valueOf(searchParams.get("agentId"))));
        }
        Sort sort = Sort.by(Sort.Order.asc("typeConge"), Sort.Order.desc("id"));
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        return congeMapper.asPage(congeRepository.findAll(booleanBuilder, pageRequest));
    }

    @Override
    public void delete(Long congeId) {
        try {
            congeRepository.deleteById(congeId);
            log.info("The agent id {} is deleted", congeId);
        } catch (IllegalArgumentException ex) {
            log.info("The given id to delete must not be null");
        }
    }

    @Override
    public void deleteDocumentConge(Long documentId) {
        try {
            congeRepository.unlinkOrdreMission(documentId);
            documentService.deleteDocument(documentId);
        } catch (IllegalArgumentException ex) {
            log.info("The given id to delete must not be null");
        }
    }

    //private String generateNumeroFiche() {
//    List<String> numFiches = congeRepository.findLastNumFicheConge();
//    int numero = 300;
//    if (!numFiches.isEmpty()) {
//        String lastNumeroFiche = numFiches.get(0);
//        if (lastNumeroFiche != null) {
//            numero = Integer.parseInt(lastNumeroFiche.substring(NUMERO_FICHE_PREFIX.length())) + 1;
//        }
//    }
//    return NUMERO_FICHE_PREFIX + numero;
//}
    private boolean isDateOverlap(Long agentId, Date dateDepart, Date dateReprise) {
        return congeRepository.existsByAgentIdAndDateDepartBeforeAndDateRepriseAfterAndTypeConge(
                agentId, dateReprise, dateDepart, TypeConge.ADMINISTRATIF
        );
    }

    private boolean hasActiveConge(Long agentId, Date dateDemande, TypeConge typeConge, Date dateDepart, Date dateReprise) {
        List<Date> latestDateReprise = congeRepository.findLatestDateRepriseByAgentIdAndTypeConge(agentId, typeConge);
        if (latestDateReprise != null && !latestDateReprise.isEmpty()) {
            Date lastDateReprise = latestDateReprise.get(0);
            if (lastDateReprise != null) {
                if (dateDepart.after(lastDateReprise) && dateReprise.before(dateDemande)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isNotPresent(List<DocumentEntity> documentJoints, CategorieDocumentEntity docObligatoire) {
        return documentJoints.stream().noneMatch(addedDocument -> docObligatoire.getTypeDocument().getId().equals(addedDocument.getDocumentType().getId()));
    }

    public void validationDocuments(CongeEntity conge) {
        var catDoc = CategoryDocument.valueOf(conge.getTypeConge().name());
        log.info("name type: {} ", catDoc);
        var documentObligatoires = categorieDocumentRepository.findByCategoryDocumentAndObligatoireTrue(catDoc);
        var documentsJoints = documentRepository.findByCategoryAndCategoryId(catDoc, conge.getId());
        documentObligatoires.forEach(docObligatoire -> {
            if (isNotPresent(documentsJoints, docObligatoire)) {
                throw new ResourceNotFoundException("Attention il manque le document ", docObligatoire.getTypeDocument().getLibelle());
            }
        });
    }

    public void removeIndexDocuments(CongeEntity conge) {
        var catDoc = CategoryDocument.valueOf(conge.getTypeConge().name());
        var documentsJoints = documentRepository.findByCategoryAndCategoryId(catDoc, conge.getId());
        documentsJoints.forEach(doc -> {
            var autres = autreDocumentRepository.findByDocumentId(doc.getId());
            if (autres.isPresent()) {
                autreDocumentRepository.deleteAll(autres.get());
            }
        });
    }

    public void filterAutorized(BooleanBuilder booleanBuilder, List<Long> agentIds) {
        if (Objects.nonNull(agentIds) && agentIds.size() > 0) {
            booleanBuilder.and(QCongeEntity.congeEntity.agent.id.in(agentIds));
        }
    }

    private void generateAlertCreateConge(CongeDTO congeDTO) {
        AgentEntity agent = agentRepository.findById(congeDTO.getAgentId())
                .orElseThrow(() -> new ResourceNotFoundException(AGENT_NOT_FOUND));
        AgentDTO agentDTO = new AgentDTO();
        agentDTO.setPrenom(agent.getPrenom());
        agentDTO.setNom(agent.getNom());
        agentDTO.setMatricule(agent.getMatricule());
        congeDTO.setAgent(agentDTO);
//        alerteService.generateAlertCreateConge(congeDTO);

    }


    @Override
    public CongeDTO updateNumeroDecision(Long congeId, String numeroDecision) {
        var conge = congeRepository.findById(congeId)
                .orElseThrow(() -> new ResourceNotFoundException("Congé non trouvé avec l'ID : " + congeId));
        validateNumeroDecision(congeId, numeroDecision);
        conge.setNumeroDecision(numeroDecision);
        var savedConge = congeRepository.save(conge);
        return congeMapper.asDto(savedConge);
    }

    private void validateNumeroDecision(Long congeId, String numeroDecision) {
        // Vérifier l'unicité du numéro de décision
        if (congeRepository.existsByNumeroDecisionAndIdNot(numeroDecision, congeId)) {
            throw new IllegalArgumentException("Le numéro de décision " + numeroDecision + " est déjà attribué à un autre congé");
        }
         }

    @Transactional
    @Override
    public void validateStepWorkflow(WorkflowValidationHistoriqueDTO historiqueDTO) {
        var fluxtresorerieToValidate = congeRepository.findById(historiqueDTO.getEntityId())
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Flux de trésorerie id '%d' n'existe pas", historiqueDTO.getEntityId())));

        var stepToValid =  workflowStepRepository.findById(historiqueDTO.getEtapeId())
                .orElseThrow(() -> new ResourceNotFoundException(String.format("L'etape avec l'id '%d' n'existe pas!", historiqueDTO.getEtapeId())));

        var user = SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findByLogin);

        var recentUserValidations = workflowHistoriqueRepository
                .findRecentUserValidation(user.get().getId(), WorkflowType.DEMANDE_CONGE, stepToValid.getId());

        var stepConfig = workflowStepValidationRepository.findByWorkflowStepId(stepToValid.getId())
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Aucune configuration de validation n'existe pour cette étape id '%d'", stepToValid.getId())));

        var nextStep = workflowStepService.getNextStep(stepToValid.getWorkflow().getId(), stepToValid.getOrdre());

        if(Objects.isNull(fluxtresorerieToValidate.getWorkflowStep())) {
            throw new UserNotAllowedToValidWorkflowException("Aucun workflow n'est rattaché a ce flux de tresorerie !");
        }

        if (fluxtresorerieToValidate.getWorkflowStep().getOrdre() > stepToValid.getOrdre()) {
            throw new UserNotAllowedToValidWorkflowException(String.format("L'étape %s est déja validée", stepToValid.getLibelle()));
        }


        if(fluxtresorerieToValidate.getFinalStep().equals(Boolean.TRUE)) {
            throw new UserNotAllowedToValidWorkflowException("D'apres la configuration cette étape est la dernière!");
        }

        if(!recentUserValidations.isEmpty() && recentUserValidations.get(0).getValidation().equals(historiqueDTO.getValidation())) {
            var validationString = historiqueDTO.getValidation() ? "OUI" : "NON";
            throw new UserNotAllowedToValidWorkflowException(String.format("Votre plus récente validation est  déja '%s' ", validationString));
        }

        if(stepConfig.getValidationType().equals(WorkflowValidationType.INDIVIDUELLE) && historiqueDTO.getValidation().equals(Boolean.TRUE)) {
            if(Objects.nonNull(nextStep)) {
                fluxtresorerieToValidate.setWorkflowStep(nextStep);
            } else {
                fluxtresorerieToValidate.setFinalStep(Boolean.TRUE);
            }

//>>>>>>> dev
            congeRepository.save(fluxtresorerieToValidate);
        } else if (stepConfig.getValidationType().equals(WorkflowValidationType.COMMUNE)) {
            var configUserSize = validationUserRepository.findAllByWorkflowStepValidationId(stepConfig.getId())
                    .stream().map(WorkflowStepValidationUserEntity::getUser)
                    .toList()
                    .size();

            var historiqueUserSize = workflowHistoriqueRepository
                    .findRecentUsersValidation(WorkflowType.DEMANDE_CONGE, stepToValid.getId())
                    .stream().filter(historique -> historique.getValidation().equals(Boolean.TRUE))
                    .toList()
                    .size();

            if(historiqueDTO.getValidation().equals(Boolean.TRUE)) {
                historiqueUserSize++;
            }

//<<<<<<< HEAD
//            if (configUserSize == historiqueUserSize) {
////                fluxtresorerieToValidate.setWorkflowStep(nextStep);
//=======
            if(configUserSize == historiqueUserSize) {
                if(Objects.nonNull(nextStep)) {
                    fluxtresorerieToValidate.setWorkflowStep(nextStep);
                } else {
                    fluxtresorerieToValidate.setFinalStep(Boolean.TRUE);
                }

//>>>>>>> dev
                congeRepository.save(fluxtresorerieToValidate);
            }
        }

        // we crete a historique for this validation
        validationHistoriqueService.createHistorique(historiqueDTO);
    }


}
