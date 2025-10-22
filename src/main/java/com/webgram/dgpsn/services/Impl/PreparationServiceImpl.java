package com.webgram.dgpsn.services.Impl;

import com.khoutech.openexcel.beans.ExcelBean;
import com.khoutech.openexcel.beans.ExcelBeanBuilder;
import com.khoutech.openexcel.models.ExcelContentType;
import com.khoutech.openexcel.services.WorkbookService;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.webgram.dgpsn.annotations.Journal;
import com.webgram.dgpsn.entities.PreparationEntity;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.PreparationMapper;
import com.webgram.dgpsn.models.PreparationDTO;
import com.webgram.dgpsn.repositories.ManagementUnitRepository;
import com.webgram.dgpsn.repositories.PreparationRepository;
import com.webgram.dgpsn.services.PreparationService;
import com.webgram.dgpsn.services.modelExcel.PreparationExcelDTO;
import com.webgram.dgpsn.tools.ActionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.InvalidParameterException;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PreparationServiceImpl implements PreparationService {
    private final PreparationRepository preparationRepository;
    private final PreparationMapper preparationMapper;
    private final WorkbookService workbookService;
    private final ManagementUnitRepository managementUnitRepository;

    @Override
    @Journal(actionType = ActionType.ADD_PREPARATION)
    public PreparationDTO create(PreparationDTO preparationDTO) {
         var savedPreparation = preparationRepository.save(preparationMapper.asEntity(preparationDTO));

        log.info("Preparation successfully added {}", savedPreparation);

        return preparationMapper.asDto(savedPreparation);
    }

    @Override
    @Journal(actionType = ActionType.UPDATE_PREPARATION)
    public PreparationDTO update(PreparationDTO preparationDTO) {
        try{
            if(preparationRepository.existsById(preparationDTO.getId())) {
                var preparation = preparationMapper.asEntity(preparationDTO);

                var updatedPreparation= preparationMapper.asDto(preparationRepository.save(preparation));

                log.info("Preparation successfully updated {} ", preparation.getId());

                return updatedPreparation;
            } else {
                throw new ResourceNotFoundException("Preparation", preparationDTO.getId());
            }
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("Preparation", preparationDTO.getId());
        }
    }

    @Override
    public PreparationDTO read(Long preparationId) {
        var preparation = preparationRepository
                .findById(preparationId)
                .orElseThrow(()-> new ResourceNotFoundException("Preparation", preparationId));

        log.info("reading preparation id {}", preparationId);

        return preparationMapper.asDto(preparation);
    }

    @Override
    @Journal(actionType = ActionType.DELETE_PREPARATION)
    public void delete(Long preparationId) {
        try {
            preparationRepository.deleteById(preparationId);
            log.info("The preparation id {} is deleted", preparationId);
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("Preparation", preparationId);
        }
    }

    @Override
    @Journal(actionType = ActionType.READ_PREPARATION)
    public Page<PreparationDTO> readAll(
            Pageable pageable,
            String libelle,
            Date deadline,
            Long phaseId,
            Long agentId,
            Long projetId
    ) {
        return preparationRepository
                .readAllByFilters(pageable, libelle, deadline, phaseId, agentId, projetId)
                .map(preparationMapper::asDto);
    }

    @Override
    @Journal(actionType = ActionType.IMPORT_PREPARATION)
    public void importPreparation(MultipartFile file, Long porjectId) {
        try (Workbook workbook = workbookService.findWorkBook(file.getInputStream(), ExcelContentType.fromContentType(file.getContentType()))) {
            Sheet sheet = workbook.getSheetAt(0);
            ExcelBean<PreparationExcelDTO> preparationExcelDTOExcelBean = new ExcelBeanBuilder<>(sheet, PreparationExcelDTO.class)
                    .skipLines(0)
                    .build();
            List<PreparationExcelDTO> preparationExcelDTOS = preparationExcelDTOExcelBean.parse();

            var projet = managementUnitRepository.findById(porjectId)
                    .orElseThrow(() -> new ResourceNotFoundException("Project avec id {} introuvable"));

            List<PreparationEntity> preparations = preparationExcelDTOS.stream()
                    .map(preparationMapper::asEntity)
                    .map(preparation -> preparation.setProjet(projet))
                    .collect(Collectors.toList());

            preparationRepository.saveAll(preparations);

            log.info("importPreparation end ok");
            log.trace("importPreparation end ok - projects: {}", preparations);
        } catch (IOException e) {
            log.info("Exceptions handle import file =============== {0}", e);
            throw new InvalidParameterException(MessageFormat.format("Exceptions handle import file ", "Banner", "idexists"));
        }
    }

    @Override
    @Journal(actionType = ActionType.EXPORT_PREPARATION_TO_EXCEL)
    public void exportPreparation(PrintWriter writer) {
        /* Creating header */
        writer.append(Arrays.stream(PreparationExcelDTO.class.getDeclaredFields())
                .filter(f -> Objects.nonNull(f.getAnnotation(CsvBindByPosition.class)) && Objects.nonNull(f.getAnnotation(CsvBindByName.class)))
                .sorted(Comparator.comparing(f -> f.getAnnotation(CsvBindByPosition.class).position()))
                .map(f -> f.getAnnotation(CsvBindByName.class).column())
                .collect(Collectors.joining(";"))).append("\n");

        StatefulBeanToCsv<PreparationExcelDTO> beanToCsv = new StatefulBeanToCsvBuilder<PreparationExcelDTO>(writer)
                .withSeparator(';')
                .withQuotechar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .build();

        var preparations = preparationRepository
                .findAll().stream().map(preparationMapper::asExcelDto);

        try {
            beanToCsv.write(preparations);
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            log.error("error");
//            throw new ValidateCassetteException("Export error");
        }
    }
}
