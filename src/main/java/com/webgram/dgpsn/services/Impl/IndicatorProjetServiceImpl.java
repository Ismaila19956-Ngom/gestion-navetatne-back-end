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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.webgram.dgpsn.annotations.Journal;
import com.webgram.dgpsn.entities.IndicatorProjetEntity;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.IndicatorProjetMapper;
import com.webgram.dgpsn.models.IndicatorProjetDTO;
import com.webgram.dgpsn.repositories.IndicatorProjetRepository;
import com.webgram.dgpsn.repositories.ManagementUnitRepository;
import com.webgram.dgpsn.services.IndicatorProjetService;
import com.webgram.dgpsn.services.modelExcel.IndicatorProjectExcelDTO;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.InvalidParameterException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class IndicatorProjetServiceImpl implements IndicatorProjetService {
    private final IndicatorProjetRepository indicatorProjetRepository;
    private final IndicatorProjetMapper indicatorProjetMapper;

    private final WorkbookService workbookService;

    private final ManagementUnitRepository managementUnitRepository;

    @Override
    @Journal(actionType = ActionType.ADD_INDICATEUR)
    public IndicatorProjetDTO create(IndicatorProjetDTO indicatorProjetDTO) {
         var savedIndicatorProjet = indicatorProjetRepository.save(indicatorProjetMapper.asEntity(indicatorProjetDTO));

        log.info("indicatorProjet successfully added {}", savedIndicatorProjet);

        return indicatorProjetMapper.asDto(savedIndicatorProjet);
    }

    @Override
    @Journal(actionType = ActionType.UPDATE_INDICATEUR)
    public IndicatorProjetDTO update(IndicatorProjetDTO indicatorProjetDTO) {
        try{
            if(indicatorProjetRepository.existsById(indicatorProjetDTO.getId())) {
                var indicatorProjet = indicatorProjetMapper.asEntity(indicatorProjetDTO);

                var updatedIndicatorProjet = indicatorProjetRepository.save(indicatorProjet);

                log.info("IndicatorProjet successfully updated {} ", updatedIndicatorProjet.getId());

                return indicatorProjetMapper.asDto(updatedIndicatorProjet);
            } else {
                throw new ResourceNotFoundException("IndicatorPojet", indicatorProjetDTO.getId());
            }
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("IndicatorProjet", indicatorProjetDTO.getId());
        }
    }

    @Override
    @Journal(actionType = ActionType.READ_INDICATEUR)
    public IndicatorProjetDTO read(Long indicatorProjetId) {
        var indicatorProjet = indicatorProjetRepository
                .findById(indicatorProjetId)
                .orElseThrow(()-> new ResourceNotFoundException("IndicatorProjet", indicatorProjetId));

        log.info("reading indicatorProjet id {}", indicatorProjetId);

        return indicatorProjetMapper.asDto(indicatorProjet);
    }

    @Override
    @Journal(actionType = ActionType.DELETE_INDICATEUR)
    public void delete(Long indicatorProjetId) {
        try {
            indicatorProjetRepository.deleteById(indicatorProjetId);
            log.info("The indicatorProjet id {} is deleted", indicatorProjetId);
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("IndicatorProjet", indicatorProjetId);
        }
    }

    @Override
    @Journal(actionType = ActionType.READ_INDICATEUR)
    public Page<IndicatorProjetDTO> readAll(
            Pageable pageable,
            Double targetValue,
            Long indicatorId,
            Long projetId,
            Long periodicityId
    ) {
        var pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("indicator.libelle").ascending());
        return indicatorProjetRepository
                .readAllByFiltering(pageRequest, targetValue, indicatorId, projetId, periodicityId)
                .map(indicatorProjetMapper::asDto);
    }

    @Override
    @Journal(actionType = ActionType.IMPORT_INDICATEUR)
    public void importIndicator(MultipartFile file, Long projectId) {
        try (Workbook workbook = workbookService.findWorkBook(file.getInputStream(), ExcelContentType.fromContentType(file.getContentType()))) {
            Sheet sheet = workbook.getSheetAt(0);
            ExcelBean<IndicatorProjectExcelDTO> indicatorProjectExcelDTOExcelBean = new ExcelBeanBuilder<>(sheet, IndicatorProjectExcelDTO.class)
                    .skipLines(0)
                    .build();
            List<IndicatorProjectExcelDTO> indicatorProjectExcelDTOS = indicatorProjectExcelDTOExcelBean.parse();

            var projet = managementUnitRepository.findById(projectId)
                    .orElseThrow(() -> new ResourceNotFoundException("Project avec id {} introuvable"));

            List<IndicatorProjetEntity> indicatorProjets = indicatorProjectExcelDTOS.stream()
                    .map(indicatorProjetMapper::asEntity)
                    .map(indicatorProjet -> indicatorProjet.setProjet(projet))
                    .collect(Collectors.toList());

            indicatorProjetRepository.saveAll(indicatorProjets);

            log.info("importIndicator end ok");
            log.trace("importIndicator end ok - projects: {}", indicatorProjets);
        } catch (IOException e) {
            log.info("Exceptions handle import file =============== {0}", e);
            throw new InvalidParameterException(MessageFormat.format("Exceptions handle import file ", "Banner", "idexists"));
        }
    }

    @Override
    @Journal(actionType = ActionType.EXPORT_INDICATEUR_TO_EXCEL)
    public void exportIndicator(PrintWriter writer) {
        /* Creating header */
        writer.append(Arrays.stream(IndicatorProjectExcelDTO.class.getDeclaredFields())
                .filter(f -> Objects.nonNull(f.getAnnotation(CsvBindByPosition.class)) && Objects.nonNull(f.getAnnotation(CsvBindByName.class)))
                .sorted(Comparator.comparing(f -> f.getAnnotation(CsvBindByPosition.class).position()))
                .map(f -> f.getAnnotation(CsvBindByName.class).column())
                .collect(Collectors.joining(";"))).append("\n");

        StatefulBeanToCsv<IndicatorProjectExcelDTO> beanToCsv = new StatefulBeanToCsvBuilder<IndicatorProjectExcelDTO>(writer)
                .withSeparator(';')
                .withQuotechar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .build();

        var indicatorProject = indicatorProjetRepository
                .findAll().stream().map(indicatorProjetMapper::asExcelDto);

        try {
            beanToCsv.write(indicatorProject);
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            log.error("error");
//            throw new ValidateCassetteException("Export error");
        }
    }
}
