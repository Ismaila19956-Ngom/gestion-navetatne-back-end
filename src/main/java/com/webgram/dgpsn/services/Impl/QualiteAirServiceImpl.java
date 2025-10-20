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
import com.webgram.dgpsn.entities.QualiteAirEntity;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.QualiteAirMapper;
import com.webgram.dgpsn.models.QualiteAirDTO;
import com.webgram.dgpsn.properties.DocumentProperties;
import com.webgram.dgpsn.repositories.QualiteAirRepository;
import com.webgram.dgpsn.services.DataStorageService;
import com.webgram.dgpsn.services.QualiteAirService;

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
public class QualiteAirServiceImpl implements QualiteAirService {

    private final QualiteAirRepository qualiteAirRepository;
    private final QualiteAirMapper qualiteAirMapper;
    private final DocumentProperties documentProperties;
    private final DataStorageService dataStorageService;

    private static final String DOCUMENT_ROOT_DIRECTORY = "qualite-air";
    private static final String DOCUMENT_PREFIX = "qualite-air-";
    private static final String INVALID_EXTENSION_MESSAGE = "File: {0} does not match expected extension: {1}";
    private static final String QUALITE_AIR_NOT_FOUND_MESSAGE = "Qualite Air not found with ID: {}";
    private static final String FILE_NOT_FOUND_MESSAGE = "File {0} not found for qualite air ID: {1}";

    @Override
    @Journal(actionType = ActionType.ADD_QUALITE_AIR)
    public QualiteAirDTO create(QualiteAirDTO qualiteAirDTO, MultipartFile[] bulletinsMonthly, MultipartFile[] bulletinsQuarterly, MultipartFile[] bulletinsAnnual, MultipartFile[] analysisReports) throws IOException {
        QualiteAirEntity entity = qualiteAirMapper.asEntity(qualiteAirDTO);
        QualiteAirEntity savedQualiteAir = qualiteAirRepository.save(entity);

        // Initialize file lists
        savedQualiteAir.setBulletinsMonthly(new ArrayList<>());
        savedQualiteAir.setBulletinsQuarterly(new ArrayList<>());
        savedQualiteAir.setBulletinsAnnual(new ArrayList<>());
        savedQualiteAir.setAnalysisReports(new ArrayList<>());

        // Save files
        if (bulletinsMonthly != null) {
            savedQualiteAir.setBulletinsMonthly(saveFiles(savedQualiteAir.getId(), bulletinsMonthly, "bulletin-monthly"));
        }
        if (bulletinsQuarterly != null) {
            savedQualiteAir.setBulletinsQuarterly(saveFiles(savedQualiteAir.getId(), bulletinsQuarterly, "bulletin-quarterly"));
        }
        if (bulletinsAnnual != null) {
            savedQualiteAir.setBulletinsAnnual(saveFiles(savedQualiteAir.getId(), bulletinsAnnual, "bulletin-annual"));
        }
        if (analysisReports != null) {
            savedQualiteAir.setAnalysisReports(saveFiles(savedQualiteAir.getId(), analysisReports, "report"));
        }

        QualiteAirEntity updatedQualiteAir = qualiteAirRepository.save(savedQualiteAir);
        log.info("Qualite Air added successfully: {}", updatedQualiteAir.getId());

        return qualiteAirMapper.asDto(updatedQualiteAir);
    }

    @Override
    @Journal(actionType = ActionType.UPDATE_QUALITE_AIR)
    public QualiteAirDTO update(QualiteAirDTO qualiteAirDTO, MultipartFile[] bulletinsMonthly, MultipartFile[] bulletinsQuarterly, MultipartFile[] bulletinsAnnual, MultipartFile[] analysisReports) throws IOException {
        if (!qualiteAirRepository.existsById(qualiteAirDTO.getId())) {
            throw new ResourceNotFoundException("Qualite Air Id", qualiteAirDTO.getId());
        }

        QualiteAirEntity entity = qualiteAirMapper.asEntity(qualiteAirDTO);

        // Preserve existing files or initialize if null
        QualiteAirEntity existingEntity = qualiteAirRepository.findById(qualiteAirDTO.getId()).orElseThrow();
        entity.setBulletinsMonthly(existingEntity.getBulletinsMonthly() != null ? existingEntity.getBulletinsMonthly() : new ArrayList<>());
        entity.setBulletinsQuarterly(existingEntity.getBulletinsQuarterly() != null ? existingEntity.getBulletinsQuarterly() : new ArrayList<>());
        entity.setBulletinsAnnual(existingEntity.getBulletinsAnnual() != null ? existingEntity.getBulletinsAnnual() : new ArrayList<>());
        entity.setAnalysisReports(existingEntity.getAnalysisReports() != null ? existingEntity.getAnalysisReports() : new ArrayList<>());

        // Add new files
        if (bulletinsMonthly != null) {
            entity.getBulletinsMonthly().addAll(saveFiles(qualiteAirDTO.getId(), bulletinsMonthly, "bulletin-monthly"));
        }
        if (bulletinsQuarterly != null) {
            entity.getBulletinsQuarterly().addAll(saveFiles(qualiteAirDTO.getId(), bulletinsQuarterly, "bulletin-quarterly"));
        }
        if (bulletinsAnnual != null) {
            entity.getBulletinsAnnual().addAll(saveFiles(qualiteAirDTO.getId(), bulletinsAnnual, "bulletin-annual"));
        }
        if (analysisReports != null) {
            entity.getAnalysisReports().addAll(saveFiles(qualiteAirDTO.getId(), analysisReports, "report"));
        }

        QualiteAirEntity updatedQualiteAir = qualiteAirRepository.save(entity);
        log.info("Qualite Air updated successfully: {}", updatedQualiteAir.getId());

        return qualiteAirMapper.asDto(updatedQualiteAir);
    }

    @Override
    @Journal(actionType = ActionType.READ_QUALITE_AIR)
    public QualiteAirDTO read(Long qualiteAirId) {
        QualiteAirEntity entity = qualiteAirRepository
                .findById(qualiteAirId)
                .orElseThrow(() -> new ResourceNotFoundException("Qualite Air Id", qualiteAirId));

        log.info("Reading qualite air ID: {}", qualiteAirId);
        return qualiteAirMapper.asDto(entity);
    }

    @Override
    @Journal(actionType = ActionType.DELETE_QUALITE_AIR)
    public void delete(Long qualiteAirId) {
        try {
            qualiteAirRepository.deleteById(qualiteAirId);
            log.info("Qualite Air with ID {} deleted", qualiteAirId);
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("Qualite Air Id", qualiteAirId);
        }
    }

    @Override
    @Journal(actionType = ActionType.READ_QUALITE_AIR)
    public Page<QualiteAirDTO> readAll(
            Pageable pageable,
            Long stationId,
            Date measurementDate,
            String iqa,
            Long mainPollutantId,
            String preparedBy,
            Date preparationDate,
            String sortBy,
            Boolean ascending
    ) {
        return qualiteAirRepository
                .readAllByFiltering(pageable, stationId, measurementDate, iqa, mainPollutantId, preparedBy, preparationDate, sortBy, ascending)
                .map(qualiteAirMapper::asDto);
    }

    @Override
    public Resource downloadFile(Long qualiteAirId, String docType) throws IOException {
        QualiteAirEntity entity = qualiteAirRepository
                .findById(qualiteAirId)
                .orElseThrow(() -> new ResourceNotFoundException("Qualite Air Id", qualiteAirId));

        List<String> files;
        String fileType;
        switch (docType.toUpperCase()) {
            case "BM":
                files = entity.getBulletinsMonthly() != null ? entity.getBulletinsMonthly() : new ArrayList<>();
                fileType = "bulletin-monthly";
                break;
            case "BQ":
                files = entity.getBulletinsQuarterly() != null ? entity.getBulletinsQuarterly() : new ArrayList<>();
                fileType = "bulletin-quarterly";
                break;
            case "BA":
                files = entity.getBulletinsAnnual() != null ? entity.getBulletinsAnnual() : new ArrayList<>();
                fileType = "bulletin-annual";
                break;
            case "REP":
                files = entity.getAnalysisReports() != null ? entity.getAnalysisReports() : new ArrayList<>();
                fileType = "report";
                break;
            default:
                throw new InvalidParameterException("Invalid document type: " + docType);
        }

        if (files.isEmpty()) {
            throw new ResourceNotFoundException(MessageFormat.format(FILE_NOT_FOUND_MESSAGE, qualiteAirId, docType));
        }

        // Select the most recent file (based on timestamp in filename)
        String selectedFile = files.stream()
                .max((f1, f2) -> {
                    long t1 = extractTimestamp(f1);
                    long t2 = extractTimestamp(f2);
                    return Long.compare(t1, t2);
                })
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(FILE_NOT_FOUND_MESSAGE, qualiteAirId, docType)));

        Resource fileResource = dataStorageService.loadFileRelativePath(selectedFile);
        log.info("File retrieved for qualite air ID: {}, docType: {}, file: {}", qualiteAirId, docType, selectedFile);
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

    private List<String> saveFiles(Long qualiteAirId, MultipartFile[] files, String fileType) throws IOException {
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
                String fileName = DOCUMENT_PREFIX + qualiteAirId + "-" + fileType + "-" + System.currentTimeMillis();
                String filePath = dataStorageService.storeFileRelativePath(
                        DOCUMENT_ROOT_DIRECTORY,
                        fileName,
                        FilenameUtils.getExtension(file.getOriginalFilename()),
                        fileInputStream
                );
                filePaths.add(filePath);
                log.info("File saved for qualite air ID: {}, path: {}", qualiteAirId, filePath);
            }
        }
        return filePaths;
    }
}