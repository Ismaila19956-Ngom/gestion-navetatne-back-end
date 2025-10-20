package com.webgram.dgpsn.services.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khoutech.openexcel.services.WorkbookService;
import com.webgram.dgpsn.tools.ActionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.webgram.dgpsn.annotations.Journal;
import com.webgram.dgpsn.entities.AssignmentEntity;
import com.webgram.dgpsn.entities.enums.StructureProjectType;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.AssignmentMapper;

import com.webgram.dgpsn.models.AssignmentDTO;

import com.webgram.dgpsn.properties.DocumentProperties;
import com.webgram.dgpsn.repositories.AssignmentRepository;
import com.webgram.dgpsn.repositories.ManagementUnitRepository;
import com.webgram.dgpsn.services.AssignmentService;
import com.webgram.dgpsn.services.DataStorageService;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AssignmentServiceImpl implements AssignmentService {
    private final AssignmentRepository assignmentRepository;
    private final AssignmentMapper assignmentMapper;

    static final String INVALID_EXTENSION_MESSAGE = "File: {0} does not match expected extension: {1}";

    static final String DOCUMENT_ROOT_DIRECTORY = "documents";

    static final String DOCUMENT = "assignment-";

    final DocumentProperties documentProperties;

    final DataStorageService dataStorageService;

    private final ObjectMapper objectMapper;

    private final WorkbookService workbookService;

    private final ManagementUnitRepository managementUnitRepository;

    private String ASSIGNMENT_IDENTIFIER_NOT_FOUND_MESSAGE = "Invalide id assignment: {}";

    @Override
    @Journal(actionType = ActionType.ADD_ASSIGNMENT)
    public AssignmentDTO create(MultipartFile file, String assignment) throws IOException {
         var assignmentDTO = objectMapper.readValue(assignment, AssignmentDTO.class);
         var savedAssignment = assignmentRepository.save(assignmentMapper.asEntity(assignmentDTO));
        if(Objects.nonNull(file)){
            addFile(savedAssignment.getId(), file);
        }

        log.info("Assignment successfully added {}", savedAssignment);

        return assignmentMapper.asDto(savedAssignment);
    }

    public AssignmentDTO create(AssignmentDTO assignmentDTO) {
        var savedAssigment = assignmentRepository.save(assignmentMapper.asEntity(assignmentDTO));

        log.info("Assignment successfully added {}", savedAssigment);

        return assignmentMapper.asDto(savedAssigment);
    }

    @Override
    @Journal(actionType = ActionType.UPDATE_ASSIGNMENT)

    public AssignmentDTO update(AssignmentDTO assignmentDTO) {
        try{
            if(assignmentRepository.existsById(assignmentDTO.getId())) {
                var assignment = assignmentMapper.asEntity(assignmentDTO);

                var updatedAssignment= assignmentMapper.asDto(assignmentRepository.save(assignment));

                log.info("Assignment successfully updated {} ", updatedAssignment.getId());

                return updatedAssignment;
            } else {
                throw new ResourceNotFoundException("Assignment", assignmentDTO.getId());
            }
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("Assignment", assignmentDTO.getId());
        }
   }
    public AssignmentDTO update(MultipartFile file, AssignmentDTO assignmentDTO) throws IOException {
        if(!assignmentRepository.existsById(assignmentDTO.getId())){
            throw new ResourceNotFoundException(MessageFormat.format(ASSIGNMENT_IDENTIFIER_NOT_FOUND_MESSAGE, assignmentDTO.getId()));
        }

        var assignmentEntity = assignmentMapper.asEntity(assignmentDTO);
        assignmentEntity.setPath(assignmentRepository.findById(assignmentDTO.getId()).get().getPath());
        var updatedAssignment = assignmentMapper.asDto(assignmentRepository.save(assignmentEntity));
        if(Objects.nonNull(file)){
            addFile(updatedAssignment.getId(), file);
        }
        log.info("Assignment successfully updated {} ", updatedAssignment.getId());

        return updatedAssignment;
    }

    @Override
    public AssignmentDTO read(Long assignmentId) {
        var assignment = assignmentRepository
                .findById(assignmentId)
                .orElseThrow(()-> new ResourceNotFoundException("Assignment", assignmentId));

        log.info("reading assignment id {}", assignmentId);

        return assignmentMapper.asDto(assignment);
    }

    @Override
    @Journal(actionType = ActionType.DELETE_ASSIGNMENT)
    public void delete(Long assignmentId) {
        try {
            assignmentRepository.deleteById(assignmentId);
            log.info("The assignment id {} is deleted", assignmentId);
        } catch (IllegalArgumentException ex) {
            log.info("The given id to delete must not be null");
        }
    }

    @Override
    @Journal(actionType = ActionType.READ_ASSIGNMENT)
    public Page<AssignmentDTO> readAll(
            Pageable pageable,
            String libelle,
            String startDate,
            String endDate,
            StructureProjectType structureProjectType,
            Long assignmentTypeId,
            Long structureId,
            Long projetId
    ) throws ParseException {
        return assignmentRepository
                .readAllByFilters(pageable,
                        libelle,
                        startDate,
                        endDate,
                        structureProjectType,
                        assignmentTypeId,
                        structureId,
                        projetId)
                .map(assignmentMapper::asDto);
    }

//    @Override
//    @Journal(actionType = ActionType.IMPORT_ASSIGNMENT)
//    public void importAssignment(MultipartFile file, Long projectId) {
//        try (Workbook workbook = workbookService.findWorkBook(file.getInputStream(), ExcelContentType.fromContentType(file.getContentType()))) {
//            Sheet sheet = workbook.getSheetAt(0);
//            ExcelBean<AssignmentExcelDTO> assignmentExcelDTOExcelBean = new ExcelBeanBuilder<>(sheet, AssignmentExcelDTO.class)
//                    .skipLines(0)
//                    .build();
//            List<AssignmentExcelDTO> assignmentExcelDTOS = assignmentExcelDTOExcelBean.parse();
//
//            var projet = managementUnitRepository.findById(projectId)
//                    .orElseThrow(() -> new ResourceNotFoundException("Project avec id {} introuvable"));
//
//            List<AssignmentEntity> assignments = assignmentExcelDTOS.stream()
//                    .map(assignmentMapper::asEntity)
//                    .map(assignment -> assignment.setProjet(projet))
//                    .collect(Collectors.toList());
//
//            assignmentRepository.saveAll(assignments);
//
//            log.info("importAssignment end ok");
//            log.trace("importAssignment end ok - projects: {}", assignments);
//        } catch (IOException e) {
//            log.info("Exceptions handle import file =============== {0}", e);
//            throw new InvalidParameterException(MessageFormat.format("Exceptions handle import file ", "Banner", "idexists"));
//        }
//    }

//    @Override
//    @Journal(actionType = ActionType.EXPORT_ASSIGNMENT_TO_EXCEL)
//    public void exportAssignment(PrintWriter writer) {
//        /* Creating header */
//        writer.append(Arrays.stream(AssignmentExcelDTO.class.getDeclaredFields())
//                .filter(f -> Objects.nonNull(f.getAnnotation(CsvBindByPosition.class)) && Objects.nonNull(f.getAnnotation(CsvBindByName.class)))
//                .sorted(Comparator.comparing(f -> f.getAnnotation(CsvBindByPosition.class).position()))
//                .map(f -> f.getAnnotation(CsvBindByName.class).column())
//                .collect(Collectors.joining(";"))).append("\n");
//
//        StatefulBeanToCsv<AssignmentExcelDTO> beanToCsv = new StatefulBeanToCsvBuilder<AssignmentExcelDTO>(writer)
//                .withSeparator(';')
//                .withQuotechar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
//                .build();
//
//        var assignments = assignmentRepository
//                .findAll().stream().map(assignmentMapper::asExcelDto);
//
//        try {
//            beanToCsv.write(assignments);
//        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
//            log.error("error");
////            throw new ValidateCassetteException("Export error");
//        }
//    }
//
//    @Override
//    public DownloadFile readFile(Long id) {
//
//        AssignmentDTO assignment = read(id);
//
//        /* Getting downloadFile */
//        DownloadFile downloadFile = DownloadFileUtils.generateDownloadFile(assignment.getPath());
//
//        log.info("readFile end ok - assignmentId: {}", id);
//        log.trace("readFile end ok - downloadFile: {}", downloadFile);
//
//        return downloadFile;
//    }
//
    public AssignmentDTO addFile(Long id, MultipartFile file) {

        /* Checking file extension */
        if (documentProperties.getAcceptFileExtensions().contains(FilenameUtils.getExtension(file.getOriginalFilename()))) {

            try(var fileInputStream = file.getInputStream()) {

                AssignmentEntity assignment = assignmentRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(ASSIGNMENT_IDENTIFIER_NOT_FOUND_MESSAGE, id)));


                /* Storing  file document */
                assignment.setPath(dataStorageService.storeFile(DOCUMENT_ROOT_DIRECTORY, DOCUMENT+assignment.getId(), FilenameUtils.getExtension(file.getOriginalFilename()), fileInputStream));

                AssignmentDTO assignmentUpdated = assignmentMapper.asDto(assignmentRepository.save(assignment));

                log.info("addFile end ok - assignmentId: {}", assignment.getId());
                log.trace("addFile end ok - assignment: {}", assignmentUpdated);

                return assignmentUpdated;

            } catch (IOException e) {
                log.error(MessageFormat.format("An error occurred with file: {0}", file.getOriginalFilename()), e);
                throw new ResourceNotFoundException(MessageFormat.format(ASSIGNMENT_IDENTIFIER_NOT_FOUND_MESSAGE, id));
            }

        } else {
            throw new InvalidParameterException(MessageFormat.format(INVALID_EXTENSION_MESSAGE, file.getOriginalFilename(), documentProperties.getAcceptFileExtensions()));
        }
    }
}
