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
import com.webgram.dgpsn.annotations.Journal;
import com.webgram.dgpsn.entities.MilestoneEntity;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.MilestoneMapper;
import com.webgram.dgpsn.models.MilestoneDTO;
import com.webgram.dgpsn.repositories.MilestoneRepository;
import com.webgram.dgpsn.repositories.ManagementUnitRepository;
import com.webgram.dgpsn.services.MilestoneService;
import com.webgram.dgpsn.services.modelExcel.MilestoneExcelDTO;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.InvalidParameterException;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MilestoneServiceImpl implements MilestoneService {
    private final MilestoneRepository milestoneRepository;
    private final MilestoneMapper milestoneMapper;
    private final ManagementUnitRepository managementUnitRepository;
    private final WorkbookService workbookService;

    @Override
    @Journal(actionType = ActionType.ADD_DATES_IMPORTANTE)
    public MilestoneDTO create(MilestoneDTO milestoneDTO) {
         var savedMilestone = milestoneRepository.save(milestoneMapper.asEntity(milestoneDTO));

        log.info("Milestone successfully added {}", savedMilestone);

        return milestoneMapper.asDto(savedMilestone);
    }

    @Override
    @Journal(actionType = ActionType.UPDATE_DATES_IMPORTANTE)
    public MilestoneDTO update(MilestoneDTO milestoneDTO) {
        try{
            if(milestoneRepository.existsById(milestoneDTO.getId())) {
                var milestone = milestoneMapper.asEntity(milestoneDTO);

                var updatedMilestone = milestoneMapper.asDto(milestoneRepository.save(milestone));

                log.info("Milestone successfully updated {} ", updatedMilestone.getId());

                return updatedMilestone;
            } else {
                throw new ResourceNotFoundException("Milestone", milestoneDTO.getId());
            }
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("Milestone", milestoneDTO.getId());
        }
    }

    @Override
    @Journal(actionType = ActionType.READ_DATES_IMPORTANTE)
    public MilestoneDTO read(Long milestoneId) {
        var milestone = milestoneRepository
                .findById(milestoneId)
                .orElseThrow(()-> new ResourceNotFoundException("Milestone", milestoneId));

        log.info("reading milestone id {}", milestone);

        return milestoneMapper.asDto(milestone);
    }

    @Override
    @Journal(actionType = ActionType.DELETE_DATES_IMPORTANTE)
    public void delete(Long milestoneId) {
        try {
            milestoneRepository.deleteById(milestoneId);
            log.info("The milestone id {} is deleted", milestoneId);
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("Milestone", milestoneId);
        }
    }

    @Override
    @Journal(actionType = ActionType.READ_DATES_IMPORTANTE)
    public Page<MilestoneDTO> readAll(
            Pageable pageable,
            String libelle,
            String predicatedDate,
            String readDate,
            Long projetId
    ) throws ParseException {
        return milestoneRepository
                .readAllByFiltering(pageable, libelle,  predicatedDate, readDate, projetId)
                .map(milestoneMapper::asDto);
    }

    @Override
    @Journal(actionType = ActionType.IMPORT_DATES_IMPORTANTE)
    public void importMilestone(MultipartFile file, Long projectId) {
        try (Workbook workbook = workbookService.findWorkBook(file.getInputStream(), ExcelContentType.fromContentType(file.getContentType()))) {
            Sheet sheet = workbook.getSheetAt(0);
            ExcelBean<MilestoneExcelDTO> milestoneExcelDTOExcelBean = new ExcelBeanBuilder<>(sheet, MilestoneExcelDTO.class)
                    .skipLines(0)
                    .build();
            List<MilestoneExcelDTO> milestoneExcelDTOS = milestoneExcelDTOExcelBean.parse();

            var projet = managementUnitRepository.findById(projectId)
                    .orElseThrow(() -> new ResourceNotFoundException("Project avec id {} introuvable"));

            List<MilestoneEntity> milestones = milestoneExcelDTOS.stream()
                    .map(milestoneMapper::asEntity)
                    .map(milestone -> milestone.setProjet(projet))
                    .collect(Collectors.toList());

            milestoneRepository.saveAll(milestones);

            log.info("importMilestone end ok");
            log.trace("importMilestone end ok - projects: {}", milestones);
        } catch (IOException e) {
            log.info("Exceptions handle import file =============== {0}", e);
            throw new InvalidParameterException(MessageFormat.format("Exceptions handle import file ", "Banner", "idexists"));
        }
    }

    @Override
    @Journal(actionType = ActionType.EXPORT_PROJECT_TO_EXCEL)
    public void exportMilsstone(PrintWriter writer) {
        /* Creating header */
        writer.append(Arrays.stream(MilestoneExcelDTO.class.getDeclaredFields())
                .filter(f -> Objects.nonNull(f.getAnnotation(CsvBindByPosition.class)) && Objects.nonNull(f.getAnnotation(CsvBindByName.class)))
                .sorted(Comparator.comparing(f -> f.getAnnotation(CsvBindByPosition.class).position()))
                .map(f -> f.getAnnotation(CsvBindByName.class).column())
                .collect(Collectors.joining(";"))).append("\n");

        StatefulBeanToCsv<MilestoneExcelDTO> beanToCsv = new StatefulBeanToCsvBuilder<MilestoneExcelDTO>(writer)
                .withSeparator(';')
                .withQuotechar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .build();

        var milestones = milestoneRepository
                .findAll().stream().map(milestoneMapper::asExcelDto);

        try {
            beanToCsv.write(milestones);
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            log.error("error");
//            throw new ValidateCassetteException("Export error");
        }
    }
}
