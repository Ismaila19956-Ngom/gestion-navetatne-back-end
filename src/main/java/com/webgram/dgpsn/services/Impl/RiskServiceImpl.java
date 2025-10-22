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
import com.webgram.dgpsn.entities.RiskEntity;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.RiskMapper;
import com.webgram.dgpsn.models.RiskDTO;
import com.webgram.dgpsn.repositories.*;
import com.webgram.dgpsn.services.RiskService;
import com.webgram.dgpsn.services.modelExcel.RiskExcelDTO;
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
public class RiskServiceImpl implements RiskService {
    private final RiskRepository riskRepository;
    private final RiskMapper riskMapper;

    private WorkbookService workbookService;

    String RISK_IDENTIFIER_NOT_FOUND_MESSAGE = "Invalide id risk {0}";

    @Override
    @Journal(actionType= ActionType.ADD_RISQUES_TO_AVANCEMENT)
    public RiskDTO create(RiskDTO riskDTO) {

        var riskSaved =  riskRepository.save(riskMapper.asEntity(riskDTO));

        log.info("IssueLog {} successfully added", riskSaved);

        return riskMapper.asDto(riskSaved);
    }

    @Override
    @Journal(actionType=ActionType.UPDATE_RISQUES_TO_AVANCEMENT)
    public RiskDTO update(RiskDTO riskDTO) {
        if(!riskRepository.existsById(riskDTO.getId())){
            throw new ResourceNotFoundException(MessageFormat.format(RISK_IDENTIFIER_NOT_FOUND_MESSAGE, riskDTO.getId()));
        }
        var updatedRisk = riskRepository.save(riskMapper.asEntity(riskDTO));
        log.info("updatedRisk ok id {}", updatedRisk.getId());
        log.trace("updatedRisk ok  {}", updatedRisk);
        return riskMapper.asDto(updatedRisk);
    }

    @Override
    @Journal(actionType=ActionType.READ_RISQUES_TO_AVANCEMENT)
    public RiskDTO read(Long id) {
        var risk = riskRepository.findById(id)
                .map(riskMapper::asDto)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(RISK_IDENTIFIER_NOT_FOUND_MESSAGE, id)));
        log.info("read risk end ok - Id: {}", id);
        log.trace("read risk end ok - risk: {}", risk);
        return risk;
    }

    @Override
    @Journal(actionType=ActionType.DELETE_RISQUES_TO_AVANCEMENT)
    public void delete(Long id) {
        if(!riskRepository.existsById(id)){
            throw new ResourceNotFoundException(MessageFormat.format(RISK_IDENTIFIER_NOT_FOUND_MESSAGE, id));
        }
        riskRepository.deleteById(id);
        log.info("delete risk ok id {}", id);
    }

    @Override
    @Journal(actionType=ActionType.READ_RISQUES_TO_AVANCEMENT)
    public Page<RiskDTO> readAll(Pageable pageable, String libelle, String author, String criticity, Long delayImpactId, Long projetId, Long financialImpactId, Long statusId, Date identificationDate, Date resolutionDate, Double probability, Long natureId) {
      //  log.info("dqte identificqtion {}", identificationDate);
        var pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("libelle").ascending());
        return riskRepository
                .readAllByFilters(
                        pageRequest, libelle, author, criticity, delayImpactId, projetId, financialImpactId, statusId, identificationDate, resolutionDate, probability, natureId
                )
                .map(riskMapper::asDto);
    }

    @Override
    @Journal(actionType=ActionType.IMPORT_RISQUES_TO_AVANCEMENT)
    public void importRisk(MultipartFile file, Long projectId) {
        try (Workbook workbook = workbookService.findWorkBook(file.getInputStream(), ExcelContentType.fromContentType(file.getContentType()))) {
            Sheet sheet = workbook.getSheetAt(0);
            ExcelBean<RiskExcelDTO> riskExcelDTOBean = new ExcelBeanBuilder<>(sheet, RiskExcelDTO.class)
                    .skipLines(0)
                    .build();
            List<RiskExcelDTO> riskExcelDTODTOS = riskExcelDTOBean.parse();
            List<RiskEntity> risks = riskExcelDTODTOS.stream()
                    .map(riskExcelDTO -> riskExcelDTO.setProjectId(projectId))
                    .map(riskMapper::asEntity)
                    .collect(Collectors.toList());
            riskRepository.saveAll(risks);
            log.info("importRisk end ok");
            log.trace("importRisk end ok - risks: {}", risks);
        } catch (IOException e) {
            log.info("Exceptions handle import file =============== {0}", e);
            throw new InvalidParameterException(MessageFormat.format("Exceptions handle import file ", "Banner", "idexists"));
        }
    }

    @Override
    @Journal(actionType=ActionType.EXPORT_RISQUES_TO_AVANCEMENT)
    public void export(PrintWriter writer) {

        /* Creating header */
        writer.append(Arrays.stream(RiskExcelDTO.class.getDeclaredFields())
                .filter(f -> Objects.nonNull(f.getAnnotation(CsvBindByPosition.class)) && Objects.nonNull(f.getAnnotation(CsvBindByName.class)))
                .sorted(Comparator.comparing(f -> f.getAnnotation(CsvBindByPosition.class).position()))
                .map(f -> f.getAnnotation(CsvBindByName.class).column())
                .collect(Collectors.joining(";"))).append("\n");

        StatefulBeanToCsv<RiskExcelDTO> beanToCsv = new StatefulBeanToCsvBuilder<RiskExcelDTO>(writer)
                .withSeparator(';')
                .withQuotechar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .build();

        List<RiskExcelDTO> riskExcelDTOS = riskRepository.findAll().stream()
                .map(riskMapper::asExcelDto)
                .collect(Collectors.toList());

        try {
            beanToCsv.write(riskExcelDTOS);
            log.info("export ok");
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            log.error("error");
            // throw new ValidateCassetteException("Export error");
        }

    }

}
