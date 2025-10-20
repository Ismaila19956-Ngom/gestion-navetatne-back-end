package com.webgram.dgpsn.services.Impl;

import com.khoutech.openexcel.services.WorkbookService;
import com.webgram.dgpsn.tools.ActionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.webgram.dgpsn.annotations.Journal;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.ConditionnalityMapper;
import com.webgram.dgpsn.models.ConditionnalityDTO;
import com.webgram.dgpsn.repositories.ConditionnalityRepository;
import com.webgram.dgpsn.repositories.ManagementUnitRepository;
import com.webgram.dgpsn.services.ConditionnalityService;


import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ConditionnalityServiceImpl implements ConditionnalityService {
    private final ConditionnalityRepository conditionnalityRepository;
    private final ConditionnalityMapper conditionnalityMapper;
    private final WorkbookService workbookService;
    private final ManagementUnitRepository managementUnitRepository;

    private String CONDITIONALITY_IDENTIFIER_NOT_FOUND_MESSAGE = "Invalide id conditionnality: {}";

    @Override
    @Journal(actionType = ActionType.ADD_CONDITIONALITY)
    public ConditionnalityDTO create(ConditionnalityDTO conditionnalityDTO) {
        var createdConditionnality = conditionnalityRepository.save(conditionnalityMapper.asEntity(conditionnalityDTO));

        log.info("create Conditionnality end ok - create ConditionnalityId: {}", createdConditionnality.getId());
        log.trace("create Conditionnality end ok - create Condirionnality: {}", createdConditionnality);

        return conditionnalityMapper.asDto(createdConditionnality);
    }

    @Override
    @Journal(actionType = ActionType.UPDATE_CONDITIONALITY)
    public ConditionnalityDTO update(ConditionnalityDTO conditionnalityDTO) {
        if(!conditionnalityRepository.existsById(conditionnalityDTO.getId())){
            throw new ResourceNotFoundException(MessageFormat.format(CONDITIONALITY_IDENTIFIER_NOT_FOUND_MESSAGE, conditionnalityDTO.getId()));
        }

        var updatedConditionnality = conditionnalityRepository.save(conditionnalityMapper.asEntity(conditionnalityDTO));

        log.info("update Conditionnality end ok - create ConditionnalityId: {}", updatedConditionnality.getId());
        log.trace("update Conditionnality end ok - create Condirionnality: {}", updatedConditionnality);

        return conditionnalityMapper.asDto(updatedConditionnality);
    }

    @Override
    public ConditionnalityDTO read(Long conditionnalityId) {
        var conditionnality = conditionnalityRepository
                .findById(conditionnalityId)
                .orElseThrow(()-> new ResourceNotFoundException(MessageFormat.format(CONDITIONALITY_IDENTIFIER_NOT_FOUND_MESSAGE, conditionnalityId)));

        log.info("read conditionality end ok - Id: {}", conditionnalityId);
        log.trace("read conditionnality end ok - conditionnality: {}", conditionnality);;

        return conditionnalityMapper.asDto(conditionnality);
    }

    @Override
    @Journal(actionType = ActionType.DELETE_CONDITIONALITY)
    public void delete(Long conditionnalityId) {
        if(!conditionnalityRepository.existsById(conditionnalityId)){
            throw new ResourceNotFoundException(MessageFormat.format(CONDITIONALITY_IDENTIFIER_NOT_FOUND_MESSAGE, conditionnalityId));
        }
        conditionnalityRepository.deleteById(conditionnalityId);
        log.info("delete conditionnality ok id {}", conditionnalityId);
    }

    @Override
    @Journal(actionType = ActionType.READ_CONDITIONALITY)
    public Page<ConditionnalityDTO> readAll(Pageable pageable, String libelle, Long conditionnalityTypeId, Long stateProgressId, Long agentId, Long projetId, Date date) {
        return conditionnalityRepository
                .readAllByFilters(pageable, libelle, conditionnalityTypeId, stateProgressId, agentId, projetId, date)
                .map(conditionnalityMapper::asDto);
    }

    @Override
    @Journal(actionType = ActionType.IMPORT_CONDITIONALITY)
    public void importConditionnalite(MultipartFile file, Long projectId) {
//        try (Workbook workbook = workbookService.findWorkBook(file.getInputStream(), ExcelContentType.fromContentType(file.getContentType()))) {
//            Sheet sheet = workbook.getSheetAt(0);
//            ExcelBean<ConditionnaliteExcelDTO> conditionnaliteExcelDTOExcelBean = new ExcelBeanBuilder<>(sheet, ConditionnaliteExcelDTO.class)
//                    .skipLines(0)
//                    .build();
//            List<ConditionnaliteExcelDTO> conditionnaliteExcelDTOS = conditionnaliteExcelDTOExcelBean.parse();
//
//            var projet = managementUnitRepository.findById(projectId)
//                    .orElseThrow(() -> new ResourceNotFoundException("Project avec id {} introuvable"));
//
//            List<ConditionnalityEntity> conditionnalities = conditionnaliteExcelDTOS.stream()
//                    .map(conditionnalityMapper::asEntity)
//                    .map(conditionnality -> conditionnality.setProjet(projet))
//                    .collect(Collectors.toList());
//
//            conditionnalityRepository.saveAll(conditionnalities);
//
//            log.info("importConditionnalite end ok");
//            log.trace("importConditionnalite end ok - projects: {}", conditionnalities);
//        } catch (IOException e) {
//            log.info("Exceptions handle import file =============== {0}", e);
//            throw new InvalidParameterException(MessageFormat.format("Exceptions handle import file ", "Banner", "idexists"));
//        }

    }

    @Override
    @Journal(actionType = ActionType.EXPORT_CONDITIONALITY_TO_EXCEL)
    public void exportConditionnalite(PrintWriter writer) {
        /* Creating header */
//        writer.append(Arrays.stream(ConditionnaliteExcelDTO.class.getDeclaredFields())
//                .filter(f -> Objects.nonNull(f.getAnnotation(CsvBindByPosition.class)) && Objects.nonNull(f.getAnnotation(CsvBindByName.class)))
//                .sorted(Comparator.comparing(f -> f.getAnnotation(CsvBindByPosition.class).position()))
//                .map(f -> f.getAnnotation(CsvBindByName.class).column())
//                .collect(Collectors.joining(";"))).append("\n");
//
//        StatefulBeanToCsv<ConditionnaliteExcelDTO> beanToCsv = new StatefulBeanToCsvBuilder<ConditionnaliteExcelDTO>(writer)
//                .withSeparator(';')
//                .withQuotechar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
//                .build();
//
//        var conditionnalites = conditionnalityRepository
//                .findAll().stream().map(conditionnalityMapper::asExcelDto);
//
//        try {
//            beanToCsv.write(conditionnalites);
//        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
//            log.error("error");
////            throw new ValidateCassetteException("Export error");
//        }
    }

}
