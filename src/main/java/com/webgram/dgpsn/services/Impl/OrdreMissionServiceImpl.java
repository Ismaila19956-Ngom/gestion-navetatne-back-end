package com.webgram.dgpsn.services.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webgram.dgpsn.entities.enums.ResponsableMission;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.webgram.dgpsn.annotations.FilterAgent;
import com.webgram.dgpsn.annotations.FilterData;
import com.webgram.dgpsn.entities.CategorieDocumentEntity;
import com.webgram.dgpsn.entities.DocumentEntity;
import com.webgram.dgpsn.entities.OrdreMissionEntity;
import com.webgram.dgpsn.entities.enums.CategoryDocument;
import com.webgram.dgpsn.entities.enums.StatutType;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.DocumentMapper;
import com.webgram.dgpsn.mappers.OrdreMissionMapper;
import com.webgram.dgpsn.models.OrdreMissionDTO;
import com.webgram.dgpsn.properties.DocumentProperties;
import com.webgram.dgpsn.repositories.AgentRepository;
import com.webgram.dgpsn.repositories.CategorieDocumentRepository;
import com.webgram.dgpsn.repositories.DocumentRepository;
import com.webgram.dgpsn.repositories.OrdreMissionRepository;
import com.webgram.dgpsn.services.DataStorageService;
import com.webgram.dgpsn.services.OrdreMissionService;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class OrdreMissionServiceImpl implements OrdreMissionService {

    final OrdreMissionRepository ordreMissionRepository;
    final DocumentRepository documentRepository;
    final CategorieDocumentRepository categorieDocumentRepository;
    final AgentRepository agentRepository;
    final OrdreMissionMapper ordreMissionMapper;
    private final DocumentServiceImpl documentService;
    private final DocumentMapper documentMapper;

    private final AlerteServiceImpl alerteService;
    static final String DOCUMENT_ROOT_DIRECTORY = "documents";
    static final String DOCUMENT = "document-";
    static final String AGENT_NOT_FOUND = "L'agent n'existe pas";
    static final String FOLDER_NOT_FOUND_MESSAGE = "[Document] Not found Document {0}";
    static final String INVALID_EXTENSION_MESSAGE = "File: {0} does not match expected extension: {1}";

    private final DocumentProperties documentProperties;
    private final DataStorageService dataStorageService;
    private final ObjectMapper objectMapper;

    @Override
    public OrdreMissionDTO create(OrdreMissionDTO ordreMissionDTO) throws IOException {
        validateMissionDurationIfDGPSN(ordreMissionDTO);
       var savedOrdreMission=ordreMissionRepository.save(ordreMissionMapper.asEntity(ordreMissionDTO));
         savedOrdreMission.setStatut(StatutType.TRAITEMENT_ENCOUR);
       alerteService.newOrdreMission(savedOrdreMission);
        return ordreMissionMapper.asDto(savedOrdreMission);
    }

    @Override
    public OrdreMissionDTO createDocumentOrdreMission(Long ordreMissionId, MultipartFile file, String document) throws IOException {
                    var documentOrdreMission=documentService.createDocument(file,document);
                    var ordremission=ordreMissionRepository.findById(ordreMissionId).orElseThrow(()->new ResourceNotFoundException("not found"));
                    ordremission.getDocument().add(documentMapper.asEntity(documentOrdreMission));
                    return ordreMissionMapper.asDto(ordremission);

    }

    @Override
    public OrdreMissionDTO update(OrdreMissionDTO ordreMissionDTO) throws IOException {
        if(!ordreMissionRepository.existsById(ordreMissionDTO.getId())){
            throw new ResourceNotFoundException("not_found");
        }
        ordreMissionDTO.setId(ordreMissionDTO.getId());
        validateMissionDurationIfDGPSN(ordreMissionDTO);
        var updatedFamily= ordreMissionRepository.save(ordreMissionMapper.asEntity(ordreMissionDTO));
        return ordreMissionMapper.asDto(updatedFamily);
    }


    @Override
    public OrdreMissionDTO read(Long ordreMissionId) {
        var ordreMission= ordreMissionRepository.findById(ordreMissionId).orElseThrow(()->new ResourceNotFoundException("odreMissionOrdre",ordreMissionId));
        log.info("reading agent id {}", ordreMissionId);
        return ordreMissionMapper.asDto(ordreMission);
    }

//    @Override
//    public OrdreMissionDTO updateStatusOrdreMission(Long ordreMissionId,String statut) {
//        var ordreMission= ordreMissionRepository.findById(ordreMissionId).orElseThrow(()->new ResourceNotFoundException("odreMissionOrdre",ordreMissionId));
//        log.info("reading agent id {}", ordreMissionId);
//
//        if(Objects.nonNull(ordreMission) && statut ==null ){
//                    validationDocuments(ordreMission);
//                    ordreMission.setStatut(StatutType.valueOf("ACCEPTER"));
//                    System.out.println("laye need a change");
//                    return ordreMissionMapper.asDto(ordreMissionRepository.save(ordreMission));
//
//        }
//        if(Objects.nonNull(ordreMission.getStatut().name().equals("ACCEPTER")) && statut.equals("TRAITEMENT_ENCOUR")||Objects.nonNull(ordreMission.getStatut().name().equals("REFUSER")) && statut.equals("TRAITEMENT_ENCOUR")){
//            ordreMission.setStatut(StatutType.valueOf("TRAITEMENT_ENCOUR"));
//            return ordreMissionMapper.asDto(ordreMissionRepository.save(ordreMission));
//        }
//        if(Objects.nonNull(ordreMission.getStatut().name()) && statut.equals("REFUSER")){
//
//            ordreMission.setStatut(StatutType.valueOf("REFUSER"));
//            return ordreMissionMapper.asDto(ordreMissionRepository.save(ordreMission));
//
//        }
//            return null;
//    }
@Override
public OrdreMissionDTO updateStatusOrdreMission(Long ordreMissionId, String statut) {
    var ordreMission = ordreMissionRepository.findById(ordreMissionId)
            .orElseThrow(() -> new ResourceNotFoundException("odreMissionOrdre", ordreMissionId));
    log.info("reading agent id {}", ordreMissionId);

        if(Objects.nonNull(ordreMission)) {

            if (statut == null) {
//                validationDocuments(ordreMission);
                ordreMission.setStatut(StatutType.valueOf("ACCEPTER"));
                System.out.println("laye need a change");
                return ordreMissionMapper.asDto(ordreMissionRepository.save(ordreMission));

            }
            if (Objects.nonNull(ordreMission.getStatut()) && ordreMission.getStatut().name().equals("ACCEPTER") && statut.equals("TRAITEMENT_ENCOUR") || Objects.nonNull(ordreMission.getStatut()) && ordreMission.getStatut().name().equals("REFUSER") && statut.equals("TRAITEMENT_ENCOUR")) {
                ordreMission.setStatut(StatutType.valueOf("TRAITEMENT_ENCOUR"));
                return ordreMissionMapper.asDto(ordreMissionRepository.save(ordreMission));
            }
            if (Objects.nonNull(ordreMission.getStatut()) && statut.equals("REFUSER")) {

                ordreMission.setStatut(StatutType.valueOf("REFUSER"));
                return ordreMissionMapper.asDto(ordreMissionRepository.save(ordreMission));

            }
        }



    String currentStatut = ordreMission.getStatut().name();

    if ("ACCEPTER".equals(currentStatut) && "TRAITEMENT_ENCOUR".equals(statut)
            || "REFUSER".equals(currentStatut) && "TRAITEMENT_ENCOUR".equals(statut)) {
        ordreMission.setStatut(StatutType.TRAITEMENT_ENCOUR);
        return ordreMissionMapper.asDto(ordreMissionRepository.save(ordreMission));
    }
    if ("REFUSER".equals(currentStatut) && "REFUSER".equals(statut)) {
        ordreMission.setStatut(StatutType.REFUSER);
        return ordreMissionMapper.asDto(ordreMissionRepository.save(ordreMission));
    }

    throw new IllegalArgumentException("Statut non valide : " + statut);
}

    public boolean isNotPresent(List< DocumentEntity > documentJoints, CategorieDocumentEntity docObligatoire) {
        return documentJoints.stream().noneMatch(addedDocument -> docObligatoire.getTypeDocument().getId().equals(addedDocument.getDocumentType().getId()));
    }
    public void validationDocuments(OrdreMissionEntity ordreMission) {

        var documentObligatoires = categorieDocumentRepository.findByCategoryDocumentAndObligatoireTrue(CategoryDocument.ORDRE_DE_MISSION_DOCUMENT);
        var documentsJoints = documentRepository.findByCategoryAndCategoryId(CategoryDocument.ORDRE_DE_MISSION_DOCUMENT, ordreMission.getId());

        documentObligatoires.forEach(docObligatoire -> {
            if (isNotPresent(documentsJoints, docObligatoire)) {
                throw new ResourceNotFoundException("Attention il manque le document ", docObligatoire.getTypeDocument().getLibelle());
            }
        });
        List<Long> agentsWithDocument = new ArrayList<>();
        for (var doc : documentsJoints) {
//            if (doc.getAgent() != null) {
//                agentsWithDocument.add(doc.getAgent().getId());
//            }
        }

        for (var agent : ordreMission.getAgent()) {
            if (!agentsWithDocument.contains(agent.getId())) {
                throw new ResourceNotFoundException("Attention, l'agent " + agent.getPrenom() + " " + agent.getNom() + " n'a pas de document associé.");
            }
        }

    }
    @Override
    public void delete(Long ordreMissionId) {
        try {
            ordreMissionRepository.deleteById(ordreMissionId);
            log.info("The agent id {} is deleted", ordreMissionId);
        } catch (IllegalArgumentException ex) {
            log.info("The given id to delete must not be null");
        }
    }

    @Override
    public void deleteDocumentOrdreMission(Long documentId) {
        try {
            ordreMissionRepository.unlinkOrdreMission(documentId);
            documentService.deleteDocument(documentId);
            log.info("The document id {} is deleted", documentId);
        } catch (IllegalArgumentException ex) {
            log.info("The given id to delete must not be null");
        }
    }


    @Override
    @FilterData
    public Page<OrdreMissionDTO> readAll(Pageable pageable, String groupe, String indice, String objectMission, String priseEnCharge, String frais, Long ordreMissionId,Long agentId, String sortBy, Boolean ascending, @FilterAgent List<Long> agentIds) {
        return  ordreMissionRepository.readAllByFiltering(pageable,groupe,indice,objectMission,priseEnCharge,frais,ordreMissionId,agentId,sortBy,ascending, agentIds)
                .map(ordreMissionMapper::asDto);

    }

    private String addFile(Long id, MultipartFile file) {

        /* Checking file extension */
        if (documentProperties.getAcceptFileExtensions().contains(FilenameUtils.getExtension(file.getOriginalFilename()))) {

            try(var fileInputStream = file.getInputStream()) {

                /* Storing  file document */
                var path = dataStorageService.storeFile(DOCUMENT_ROOT_DIRECTORY, DOCUMENT + id, FilenameUtils.getExtension(file.getOriginalFilename()), fileInputStream);

                log.info("addFile end ok - documentId: {}", id);

                return path;

            } catch (IOException e) {
                log.error(MessageFormat.format("An error occurred with file: {0}", file.getOriginalFilename()), e);
                throw new ResourceNotFoundException(MessageFormat.format(FOLDER_NOT_FOUND_MESSAGE, id));
            }

        } else {
            throw new InvalidParameterException(MessageFormat.format(INVALID_EXTENSION_MESSAGE, file.getOriginalFilename(), documentProperties.getAcceptFileExtensions()));
        }
    }

//    private void validateMissionDurationIfDGPSN(OrdreMissionDTO dto) {
//        if (dto.getStructure() == ResponsableMission.DGPSN) {
//            Date debut = dto.getDateDepartOrdre();
//            Date fin = dto.getDateRetourOrdre();
//            if (debut == null || fin == null) {
//                throw new IllegalArgumentException("Les dates de départ et de retour de mission sont obligatoires pour le DGPSN.");
//            }
//            long diffInMillies = Math.abs(fin.getTime() - debut.getTime());
//            long diffInDays = diffInMillies / (1000 * 60 * 60 * 24);
//            if (diffInDays > 10) {
//                throw new IllegalArgumentException(
//                        String.format("La durée de la mission pour le DGPSN ne peut pas dépasser 10 jours. Durée actuelle : %d jour(s).", diffInDays)
//                );
//            }
//        }
//    }
private void validateMissionDurationIfDGPSN(OrdreMissionDTO dto) {
    // La règle s'applique SEULEMENT si DGPSN est la SEULE structure
    if (dto.getStructures() != null
            && dto.getStructures().size() == 1
            && dto.getStructures().contains(ResponsableMission.DGPSN)) {

        Date debut = dto.getDateDepartOrdre();
        Date fin = dto.getDateRetourOrdre();

        if (debut == null || fin == null) {
            throw new IllegalArgumentException(
                    "Les dates de départ et de retour de mission sont obligatoires pour le DGPSN."
            );
        }

        long diffInMillies = Math.abs(fin.getTime() - debut.getTime());
        long diffInDays = diffInMillies / (1000 * 60 * 60 * 24);

        if (diffInDays > 10) {
            throw new IllegalArgumentException(
                    String.format(
                            "La durée de la mission pour le DGPSN ne peut pas dépasser 10 jours. Durée actuelle : %d jour(s).",
                            diffInDays
                    )
            );
        }
    }
}
}
