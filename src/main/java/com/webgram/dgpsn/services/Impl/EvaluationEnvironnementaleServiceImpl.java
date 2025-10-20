package com.webgram.dgpsn.services.Impl;

import com.webgram.dgpsn.tools.ActionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.webgram.dgpsn.annotations.Journal;
import com.webgram.dgpsn.entities.EvaluationEnvironnementaleEntity;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.EvaluationEnvironnementaleMapper;
import com.webgram.dgpsn.models.EvaluationEnvironnementaleDTO;
import com.webgram.dgpsn.properties.DocumentProperties;
import com.webgram.dgpsn.repositories.EvaluationEnvironnementaleRepository;
import com.webgram.dgpsn.services.DataStorageService;
import com.webgram.dgpsn.services.EvaluationEnvironnementaleService;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class EvaluationEnvironnementaleServiceImpl implements EvaluationEnvironnementaleService {

    private final EvaluationEnvironnementaleRepository evaluationEnvironnementaleRepository;
    private final EvaluationEnvironnementaleMapper evaluationEnvironnementaleMapper;
    private final DocumentProperties documentProperties;
    private final DataStorageService dataStorageService;
    private final AlerteServiceImpl alerteServiceImpl;

    private static final String DOCUMENT_ROOT_DIRECTORY = "evaluation-environnementale";
    private static final String DOCUMENT_PREFIX = "evaluation-";
    private static final String INVALID_EXTENSION_MESSAGE = "File: {0} does not match expected extension: {1}";
    private static final String EVALUATION_NOT_FOUND_MESSAGE = "Evaluation Environnementale not found with ID: {}";
    private static final String FILE_NOT_FOUND_MESSAGE = "File {0} not found for evaluation ID: {1}";

    @Override
    @Journal(actionType = ActionType.ADD_EVALUATION_ENV)
    public EvaluationEnvironnementaleDTO create(EvaluationEnvironnementaleDTO evaluationEnvironnementaleDTO,
                                                MultipartFile carteGeographique,
                                                MultipartFile planMasse,
                                                MultipartFile planSituation,
                                                MultipartFile planInstallations,
                                                MultipartFile planReseaux,
                                                MultipartFile tdrEtude,
                                                MultipartFile attestationDomaine,
                                                MultipartFile bilanEau,
                                                MultipartFile[] autresDocuments) throws IOException {
        EvaluationEnvironnementaleEntity entity = evaluationEnvironnementaleMapper.asEntity(evaluationEnvironnementaleDTO);
        EvaluationEnvironnementaleEntity savedEvaluation = evaluationEnvironnementaleRepository.save(entity);

        // Initialize file lists
        savedEvaluation.setAutresDocuments(new ArrayList<>());

        // Save single files
        if (carteGeographique != null) {
            savedEvaluation.setCarteGeographique(saveSingleFile(savedEvaluation.getId(), carteGeographique, "carte-geographique"));
        }
        if (planMasse != null) {
            savedEvaluation.setPlanMasse(saveSingleFile(savedEvaluation.getId(), planMasse, "plan-masse"));
        }
        if (planSituation != null) {
            savedEvaluation.setPlanSituation(saveSingleFile(savedEvaluation.getId(), planSituation, "plan-situation"));
        }
        if (planInstallations != null) {
            savedEvaluation.setPlanInstallations(saveSingleFile(savedEvaluation.getId(), planInstallations, "plan-installations"));
        }
        if (planReseaux != null) {
            savedEvaluation.setPlanReseaux(saveSingleFile(savedEvaluation.getId(), planReseaux, "plan-reseaux"));
        }
        if (tdrEtude != null) {
            savedEvaluation.setTdrEtude(saveSingleFile(savedEvaluation.getId(), tdrEtude, "tdr-etude"));
        }
        if (attestationDomaine != null) {
            savedEvaluation.setAttestationDomaine(saveSingleFile(savedEvaluation.getId(), attestationDomaine, "attestation-domaine"));
        }
        if (bilanEau != null) {
            savedEvaluation.setBilanEau(saveSingleFile(savedEvaluation.getId(), bilanEau, "bilan-eau"));
        }
        if (autresDocuments != null) {
            savedEvaluation.setAutresDocuments(saveFiles(savedEvaluation.getId(), autresDocuments, "autre-document"));
        }

        EvaluationEnvironnementaleEntity updatedEvaluation = evaluationEnvironnementaleRepository.save(savedEvaluation);
        // Generate alert for creation
        alerteServiceImpl.generateAlertForEvaluationEnvironnementale(evaluationEnvironnementaleDTO);
        log.info("Evaluation Environnementale added successfully: {}", updatedEvaluation.getId());

        return evaluationEnvironnementaleMapper.asDto(updatedEvaluation);
    }

    @Override
    @Journal(actionType = ActionType.UPDATE_EVALUATION_ENV)
    public EvaluationEnvironnementaleDTO update(EvaluationEnvironnementaleDTO evaluationEnvironnementaleDTO,
                                                MultipartFile carteGeographique,
                                                MultipartFile planMasse,
                                                MultipartFile planSituation,
                                                MultipartFile planInstallations,
                                                MultipartFile planReseaux,
                                                MultipartFile tdrEtude,
                                                MultipartFile attestationDomaine,
                                                MultipartFile bilanEau,
                                                MultipartFile[] autresDocuments) throws IOException {
        if (!evaluationEnvironnementaleRepository.existsById(evaluationEnvironnementaleDTO.getId())) {
            throw new ResourceNotFoundException("Evaluation Environnementale Id", evaluationEnvironnementaleDTO.getId());
        }

        EvaluationEnvironnementaleEntity entity = evaluationEnvironnementaleMapper.asEntity(evaluationEnvironnementaleDTO);

        // Preserve existing files or initialize if null
        EvaluationEnvironnementaleEntity existingEntity = evaluationEnvironnementaleRepository.findById(evaluationEnvironnementaleDTO.getId()).orElseThrow();
        entity.setCarteGeographique(existingEntity.getCarteGeographique());
        entity.setPlanMasse(existingEntity.getPlanMasse());
        entity.setPlanSituation(existingEntity.getPlanSituation());
        entity.setPlanInstallations(existingEntity.getPlanInstallations());
        entity.setPlanReseaux(existingEntity.getPlanReseaux());
        entity.setTdrEtude(existingEntity.getTdrEtude());
        entity.setAttestationDomaine(existingEntity.getAttestationDomaine());
        entity.setBilanEau(existingEntity.getBilanEau());
        entity.setAutresDocuments(existingEntity.getAutresDocuments() != null ? existingEntity.getAutresDocuments() : new ArrayList<>());

        // Update with new files
        if (carteGeographique != null) {
            entity.setCarteGeographique(saveSingleFile(evaluationEnvironnementaleDTO.getId(), carteGeographique, "carte-geographique"));
        }
        if (planMasse != null) {
            entity.setPlanMasse(saveSingleFile(evaluationEnvironnementaleDTO.getId(), planMasse, "plan-masse"));
        }
        if (planSituation != null) {
            entity.setPlanSituation(saveSingleFile(evaluationEnvironnementaleDTO.getId(), planSituation, "plan-situation"));
        }
        if (planInstallations != null) {
            entity.setPlanInstallations(saveSingleFile(evaluationEnvironnementaleDTO.getId(), planInstallations, "plan-installations"));
        }
        if (planReseaux != null) {
            entity.setPlanReseaux(saveSingleFile(evaluationEnvironnementaleDTO.getId(), planReseaux, "plan-reseaux"));
        }
        if (tdrEtude != null) {
            entity.setTdrEtude(saveSingleFile(evaluationEnvironnementaleDTO.getId(), tdrEtude, "tdr-etude"));
        }
        if (attestationDomaine != null) {
            entity.setAttestationDomaine(saveSingleFile(evaluationEnvironnementaleDTO.getId(), attestationDomaine, "attestation-domaine"));
        }
        if (bilanEau != null) {
            entity.setBilanEau(saveSingleFile(evaluationEnvironnementaleDTO.getId(), bilanEau, "bilan-eau"));
        }
        if (autresDocuments != null) {
            entity.getAutresDocuments().addAll(saveFiles(evaluationEnvironnementaleDTO.getId(), autresDocuments, "autre-document"));
        }

        EvaluationEnvironnementaleEntity updatedEvaluation = evaluationEnvironnementaleRepository.save(entity);
        log.info("Evaluation Environnementale updated successfully: {}", updatedEvaluation.getId());

        return evaluationEnvironnementaleMapper.asDto(updatedEvaluation);
    }

    @Override
    @Journal(actionType = ActionType.READ_EVALUATION_ENV)
    public EvaluationEnvironnementaleDTO read(Long evaluationEnvironnementaleId) {
        EvaluationEnvironnementaleEntity entity = evaluationEnvironnementaleRepository
                .findById(evaluationEnvironnementaleId)
                .orElseThrow(() -> new ResourceNotFoundException("Evaluation Environnementale Id", evaluationEnvironnementaleId));

        log.info("Reading evaluation environnementale ID: {}", evaluationEnvironnementaleId);
        return evaluationEnvironnementaleMapper.asDto(entity);
    }

    @Override
    @Journal(actionType = ActionType.DELETE_EVALUATION_ENV)
    public void delete(Long evaluationEnvironnementaleId) {
        try {
            evaluationEnvironnementaleRepository.deleteById(evaluationEnvironnementaleId);
            log.info("Evaluation Environnementale with ID {} deleted", evaluationEnvironnementaleId);
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("Evaluation Environnementale Id", evaluationEnvironnementaleId);
        }
    }

    @Override
    @Journal(actionType = ActionType.READ_EVALUATION_ENV)
    public Page<EvaluationEnvironnementaleDTO> readAll(Pageable pageable,
                                                       Long programmeId,
                                                       Long projetId,
                                                       Long activiteId,
                                                       Long directionId,
                                                       Long promoteurId,
                                                       String titreProjet,
                                                       String sortBy,
                                                       Boolean ascending) {
        return evaluationEnvironnementaleRepository
                .readAllByFiltering(
                        programmeId, projetId, activiteId, directionId, promoteurId, titreProjet, pageable, sortBy, ascending)
                .map(evaluationEnvironnementaleMapper::asDto);
    }

    @Override
    public Resource downloadFile(Long evaluationEnvironnementaleId, String docType) throws IOException {
        EvaluationEnvironnementaleEntity entity = evaluationEnvironnementaleRepository
                .findById(evaluationEnvironnementaleId)
                .orElseThrow(() -> new ResourceNotFoundException("Evaluation Environnementale Id", evaluationEnvironnementaleId));

        String filePath;
        String fileType;
        switch (docType.toUpperCase()) {
            case "MAP":
                filePath = entity.getCarteGeographique();
                fileType = "carte-geographique";
                break;
            case "PM":
                filePath = entity.getPlanMasse();
                fileType = "plan-masse";
                break;
            case "PS":
                filePath = entity.getPlanSituation();
                fileType = "plan-situation";
                break;
            case "PI":
                filePath = entity.getPlanInstallations();
                fileType = "plan-installations";
                break;
            case "PR":
                filePath = entity.getPlanReseaux();
                fileType = "plan-reseaux";
                break;
            case "TDR":
                filePath = entity.getTdrEtude();
                fileType = "tdr-etude";
                break;
            case "AD":
                filePath = entity.getAttestationDomaine();
                fileType = "attestation-domaine";
                break;
            case "BE":
                filePath = entity.getBilanEau();
                fileType = "bilan-eau";
                break;
            case "AUT":
                List<String> autresDocuments = entity.getAutresDocuments() != null ? entity.getAutresDocuments() : new ArrayList<>();
                if (autresDocuments.isEmpty()) {
                    throw new ResourceNotFoundException(MessageFormat.format(FILE_NOT_FOUND_MESSAGE, evaluationEnvironnementaleId, docType));
                }
                filePath = autresDocuments.stream()
                        .max((f1, f2) -> Long.compare(extractTimestamp(f1), extractTimestamp(f2)))
                        .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(FILE_NOT_FOUND_MESSAGE, evaluationEnvironnementaleId, docType)));
                fileType = "autre-document";
                break;
            default:
                throw new InvalidParameterException("Invalid document type: " + docType);
        }

        if (filePath == null) {
            throw new ResourceNotFoundException(MessageFormat.format(FILE_NOT_FOUND_MESSAGE, evaluationEnvironnementaleId, docType));
        }

        Resource fileResource = dataStorageService.loadFile(filePath);
        log.info("File retrieved for evaluation environnementale ID: {}, docType: {}, file: {}", evaluationEnvironnementaleId, docType, filePath);
        return fileResource;
    }

    private String saveSingleFile(Long evaluationId, MultipartFile file, String fileType) throws IOException {
        if (!documentProperties.getAcceptFileExtensions().contains(FilenameUtils.getExtension(file.getOriginalFilename()))) {
            throw new InvalidParameterException(MessageFormat.format(
                    INVALID_EXTENSION_MESSAGE,
                    file.getOriginalFilename(),
                    documentProperties.getAcceptFileExtensions()
            ));
        }
        try (var fileInputStream = file.getInputStream()) {
            String fileName = DOCUMENT_PREFIX + evaluationId + "-" + fileType + "-" + System.currentTimeMillis();
            String filePath = dataStorageService.storeFile(
                    DOCUMENT_ROOT_DIRECTORY,
                    fileName,
                    FilenameUtils.getExtension(file.getOriginalFilename()),
                    fileInputStream
            );
            log.info("File saved for evaluation ID: {}, path: {}", evaluationId, filePath);
            return filePath;
        }
    }

    private List<String> saveFiles(Long evaluationId, MultipartFile[] files, String fileType) throws IOException {
        List<String> filePaths = new ArrayList<>();
        for (MultipartFile file : files) {
            filePaths.add(saveSingleFile(evaluationId, file, fileType));
        }
        return filePaths;
    }

    private long extractTimestamp(String filePath) {
        try {
            String timestampStr = filePath.substring(filePath.lastIndexOf("-") + 1, filePath.lastIndexOf("."));
            return Long.parseLong(timestampStr);
        } catch (Exception e) {
            return 0;
        }
    }
}