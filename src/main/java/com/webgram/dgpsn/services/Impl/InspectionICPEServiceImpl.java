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
import com.webgram.dgpsn.entities.InspectionICPEEntity;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.InspectionICPEMapper;
import com.webgram.dgpsn.models.InspectionICPEDTO;
import com.webgram.dgpsn.properties.DocumentProperties;
import com.webgram.dgpsn.repositories.InspectionICPERepository;
import com.webgram.dgpsn.services.DataStorageService;
import com.webgram.dgpsn.services.InspectionICPEService;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class InspectionICPEServiceImpl implements InspectionICPEService {

    private final InspectionICPERepository inspectionICPERepository;
    private final InspectionICPEMapper inspectionICPEMapper;
    private final DocumentProperties documentProperties;
    private final DataStorageService dataStorageService;

    private static final String DOCUMENT_ROOT_DIRECTORY = "inspection-icpe";
    private static final String DOCUMENT_PREFIX = "inspection-";
    private static final String INVALID_EXTENSION_MESSAGE = "File: {0} does not match expected extension: {1}";
    private static final String INSPECTION_NOT_FOUND_MESSAGE = "Inspection ICPE not found with ID: {}";
    private static final String FILE_NOT_FOUND_MESSAGE = "File {0} not found for inspection ID: {1}";

    @Override
    @Journal(actionType = ActionType.ADD_INSPECTION_ICPE)
    public InspectionICPEDTO create(InspectionICPEDTO inspectionICPEDTO, MultipartFile[] photos, MultipartFile[] documents, MultipartFile[] analysisReports) throws IOException {
        InspectionICPEEntity entity = inspectionICPEMapper.asEntity(inspectionICPEDTO);
        InspectionICPEEntity savedInspection = inspectionICPERepository.save(entity);

        // Initialize file lists
        savedInspection.setPhotos(new ArrayList<>());
        savedInspection.setDocuments(new ArrayList<>());
        savedInspection.setAnalysisReports(new ArrayList<>());

        // Save files
        if (photos != null) {
            savedInspection.setPhotos(saveFiles(savedInspection.getId(), photos, "photo"));
        }
        if (documents != null) {
            savedInspection.setDocuments(saveFiles(savedInspection.getId(), documents, "document"));
        }
        if (analysisReports != null) {
            savedInspection.setAnalysisReports(saveFiles(savedInspection.getId(), analysisReports, "report"));
        }

        InspectionICPEEntity updatedInspection = inspectionICPERepository.save(savedInspection);
        log.info("Inspection ICPE added successfully: {}", updatedInspection.getId());

        return inspectionICPEMapper.asDto(updatedInspection);
    }

    @Override
    @Journal(actionType = ActionType.UPDATE_INSPECTION_ICPE)
    public InspectionICPEDTO update(InspectionICPEDTO inspectionICPEDTO, MultipartFile[] photos, MultipartFile[] documents, MultipartFile[] analysisReports) throws IOException {
        if (!inspectionICPERepository.existsById(inspectionICPEDTO.getId())) {
            throw new ResourceNotFoundException("Inspection ICPE Id", inspectionICPEDTO.getId());
        }

        InspectionICPEEntity entity = inspectionICPEMapper.asEntity(inspectionICPEDTO);

        // Preserve existing files or initialize if null
        InspectionICPEEntity existingEntity = inspectionICPERepository.findById(inspectionICPEDTO.getId()).orElseThrow();
        entity.setPhotos(existingEntity.getPhotos() != null ? existingEntity.getPhotos() : new ArrayList<>());
        entity.setDocuments(existingEntity.getDocuments() != null ? existingEntity.getDocuments() : new ArrayList<>());
        entity.setAnalysisReports(existingEntity.getAnalysisReports() != null ? existingEntity.getAnalysisReports() : new ArrayList<>());

        // Add new files
        if (photos != null) {
            entity.getPhotos().addAll(saveFiles(inspectionICPEDTO.getId(), photos, "photo"));
        }
        if (documents != null) {
            entity.getDocuments().addAll(saveFiles(inspectionICPEDTO.getId(), documents, "document"));
        }
        if (analysisReports != null) {
            entity.getAnalysisReports().addAll(saveFiles(inspectionICPEDTO.getId(), analysisReports, "report"));
        }

        InspectionICPEEntity updatedInspection = inspectionICPERepository.save(entity);
        log.info("Inspection ICPE updated successfully: {}", updatedInspection.getId());

        return inspectionICPEMapper.asDto(updatedInspection);
    }

    @Override
    @Journal(actionType = ActionType.READ_INSPECTION_ICPE)
    public InspectionICPEDTO read(Long inspectionICPEId) {
        InspectionICPEEntity entity = inspectionICPERepository
                .findById(inspectionICPEId)
                .orElseThrow(() -> new ResourceNotFoundException("Inspection ICPE Id", inspectionICPEId));

        log.info("Reading inspection ICPE ID: {}", inspectionICPEId);
        return inspectionICPEMapper.asDto(entity);
    }

    @Override
    @Journal(actionType = ActionType.DELETE_INSPECTION_ICPE)
    public void delete(Long inspectionICPEId) {
        try {
            inspectionICPERepository.deleteById(inspectionICPEId);
            log.info("Inspection ICPE with ID {} deleted", inspectionICPEId);
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("Inspection ICPE Id", inspectionICPEId);
        }
    }

    @Override
    @Journal(actionType = ActionType.READ_INSPECTION_ICPE)
    public Page<InspectionICPEDTO> readAll(
            Pageable pageable,
            String code,
            Date dateInspection,
            Long typeInspectionId,
            String ref,
            Long etablissementId,
            Long teamLeadId,
            Long complianceLevelId,
            Long environmentalRiskId,
            String preparedBy,
            Date preparationDate,
            String sortBy,
            Boolean ascending
    ) {
        return inspectionICPERepository
                .readAllByFiltering(pageable, code, dateInspection, typeInspectionId, ref, etablissementId, teamLeadId, complianceLevelId, environmentalRiskId, preparedBy, preparationDate, sortBy, ascending)
                .map(inspectionICPEMapper::asDto);
    }

    @Override
    public Resource downloadFile(Long inspectionICPEId, String docType) throws IOException {
        InspectionICPEEntity entity = inspectionICPERepository
                .findById(inspectionICPEId)
                .orElseThrow(() -> new ResourceNotFoundException("Inspection ICPE Id", inspectionICPEId));

        List<String> files;
        String fileType;
        switch (docType.toUpperCase()) {
            case "PH":
                files = entity.getPhotos() != null ? entity.getPhotos() : new ArrayList<>();
                fileType = "photo";
                break;
            case "DOC":
                files = entity.getDocuments() != null ? entity.getDocuments() : new ArrayList<>();
                fileType = "document";
                break;
            case "REP":
                files = entity.getAnalysisReports() != null ? entity.getAnalysisReports() : new ArrayList<>();
                fileType = "report";
                break;
            default:
                throw new InvalidParameterException("Invalid document type: " + docType);
        }

        if (files.isEmpty()) {
            throw new ResourceNotFoundException(MessageFormat.format(FILE_NOT_FOUND_MESSAGE, inspectionICPEId, docType));
        }

        // Select the most recent file (based on timestamp in filename)
        String selectedFile = files.stream()
                .max((f1, f2) -> {
                    long t1 = extractTimestamp(f1);
                    long t2 = extractTimestamp(f2);
                    return Long.compare(t1, t2);
                })
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(FILE_NOT_FOUND_MESSAGE, inspectionICPEId, docType)));

        Resource fileResource = dataStorageService.loadFileRelativePath(selectedFile);
        log.info("File retrieved for inspection ICPE ID: {}, docType: {}, file: {}", inspectionICPEId, docType, selectedFile);
        return fileResource;
    }

    private long extractTimestamp(String filePath) {
        try {
            String timestampStr = filePath.substring(filePath.lastIndexOf("-") + 1, filePath.lastIndexOf("."));
            return Long.parseLong(timestampStr);
        } catch (Exception e) {
            return 0;
        }
    }

    private List<String> saveFiles(Long inspectionId, MultipartFile[] files, String fileType) throws IOException {
        List<String> filePaths = new ArrayList<>();
        for (MultipartFile file : files) {
            if (!documentProperties.getAcceptFileExtensions().contains(FilenameUtils.getExtension(file.getOriginalFilename()))) {
                throw new InvalidParameterException(MessageFormat.format(
                        INVALID_EXTENSION_MESSAGE,
                        file.getOriginalFilename(),
                        documentProperties.getAcceptFileExtensions()
                ));
            }
            try (var fileInputStream = file.getInputStream()) {
                String fileName = DOCUMENT_PREFIX + inspectionId + "-" + fileType + "-" + System.currentTimeMillis();
                String filePath = dataStorageService.storeFileRelativePath(
                        DOCUMENT_ROOT_DIRECTORY,
                        fileName,
                        FilenameUtils.getExtension(file.getOriginalFilename()),
                        fileInputStream
                );
                filePaths.add(filePath);
                log.info("File saved for inspection ID: {}, path: {}", inspectionId, filePath);
            }
        }
        return filePaths;
    }
}