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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.webgram.dgpsn.entities.ManagementUnitEntity;
import com.webgram.dgpsn.entities.ValueIndicatorEntity;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.ValueIndicatorMapper;
import com.webgram.dgpsn.models.ValueIndicatorDTO;
import com.webgram.dgpsn.repositories.IndicatorProjetRepository;
import com.webgram.dgpsn.repositories.ValueIndicatorRepository;
import com.webgram.dgpsn.services.ValueIndicatorService;
import com.webgram.dgpsn.services.modelExcel.ValueIndicatorExcelDTO;

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
public class ValueIndicatorServiceImpl implements ValueIndicatorService {
    private final ValueIndicatorRepository valueIndicatorRepository;
    private final ValueIndicatorMapper valueIndicatorMapper;
    private final WorkbookService workbookService;
    private final IndicatorProjetRepository indicatorProjetRepository;


    private String VALUE_INDICATOR_IDENTIFIER_NOT_FOUND_MESSAGE = "Invalide id valueIndicator: {}";

    @Override
    public ValueIndicatorDTO create(ValueIndicatorDTO valueIndicatorDTO) {
        var createdvalueIndicator = valueIndicatorRepository.save(valueIndicatorMapper.asEntity(valueIndicatorDTO));

        log.info("createdvalueIndicator end ok - create createdvalueIndicatorId: {}", createdvalueIndicator.getId());
        log.trace("createdvalueIndicator end ok - create createdvalueIndicator: {}", createdvalueIndicator);

        return valueIndicatorMapper.asDto(createdvalueIndicator);
    }

    @Override
    public ValueIndicatorDTO update(ValueIndicatorDTO valueIndicatorDTO) {
        if(!valueIndicatorRepository.existsById(valueIndicatorDTO.getId())){
            throw new ResourceNotFoundException(MessageFormat.format(VALUE_INDICATOR_IDENTIFIER_NOT_FOUND_MESSAGE, valueIndicatorDTO.getId()));
        }

        var updatedValueIndicator = valueIndicatorRepository.save(valueIndicatorMapper.asEntity(valueIndicatorDTO));

        log.info("updatedValueIndicator end ok - create ValueIndicatorId: {}", updatedValueIndicator.getId());
        log.trace("updatedValueIndicator end ok - create ValueIndicator: {}", updatedValueIndicator);

        return valueIndicatorMapper.asDto(updatedValueIndicator);
    }

    @Override
    public ValueIndicatorDTO read(Long id) {
        var valueIndicator = valueIndicatorRepository
                .findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(MessageFormat.format(VALUE_INDICATOR_IDENTIFIER_NOT_FOUND_MESSAGE, id)));

        log.info("read valueIndicator end ok - Id: {}", id);
        log.trace("read valueIndicator end ok - valueIndicator: {}", valueIndicator);;

        return valueIndicatorMapper.asDto(valueIndicator);
    }

    @Override
    public void delete(Long id) {
        if(!valueIndicatorRepository.existsById(id)){
            throw new ResourceNotFoundException(MessageFormat.format(VALUE_INDICATOR_IDENTIFIER_NOT_FOUND_MESSAGE, id));
        }
        valueIndicatorRepository.deleteById(id);
        log.info("delete valueIndicator ok id {}", id);
    }

    @Override
    public Page<ValueIndicatorDTO> readAll(Pageable pageable, Long projetId, Long indicatorId, String period, Double targetValue, Double valueReched, Date startDate, Date endDate) {
        return valueIndicatorRepository
                .readAllByFilters(pageable, projetId, indicatorId, period, targetValue, valueReched, startDate, endDate)
                .map(valueIndicatorMapper::asDto);
    }

    @Override
    public void importIndicator(MultipartFile file, Long projectId) {
        try (Workbook workbook = workbookService.findWorkBook(file.getInputStream(), ExcelContentType.fromContentType(file.getContentType()))) {
            Sheet sheet = workbook.getSheetAt(0);
            ExcelBean<ValueIndicatorExcelDTO> valueIndicatorExcelDTOBean = new ExcelBeanBuilder<>(sheet, ValueIndicatorExcelDTO.class)
                    .skipLines(0)
                    .build();
            List<ValueIndicatorExcelDTO> valueIndicatorExcelDTOS = valueIndicatorExcelDTOBean.parse();
            List<ValueIndicatorEntity> valueIndicators = valueIndicatorExcelDTOS.stream()
                    .map(valueIndicatorExcelDTO -> valueIndicatorExcelDTO.setProjectId(projectId))
                    .map(valueIndicatorMapper::asEntity)
                    .collect(Collectors.toList());
            valueIndicatorRepository.saveAll(valueIndicators);
            log.info("importIndicator end ok");
            log.trace("importIndicator end ok - indicators: {}", valueIndicators);
        } catch (IOException e) {
            log.info("Exceptions handle import file =============== {0}", e);
            throw new InvalidParameterException(MessageFormat.format("Exceptions handle import file ", "Banner", "idexists"));
        }
    }

    @Override
    public void export(PrintWriter writer) {

        /* Creating header */
        writer.append(Arrays.stream(ValueIndicatorExcelDTO.class.getDeclaredFields())
                .filter(f -> Objects.nonNull(f.getAnnotation(CsvBindByPosition.class)) && Objects.nonNull(f.getAnnotation(CsvBindByName.class)))
                .sorted(Comparator.comparing(f -> f.getAnnotation(CsvBindByPosition.class).position()))
                .map(f -> f.getAnnotation(CsvBindByName.class).column())
                .collect(Collectors.joining(";"))).append("\n");

        StatefulBeanToCsv<ValueIndicatorExcelDTO> beanToCsv = new StatefulBeanToCsvBuilder<ValueIndicatorExcelDTO>(writer)
                .withSeparator(';')
                .withQuotechar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .build();

        List<ValueIndicatorExcelDTO> valueIndicatorExcelDTOS = valueIndicatorRepository.findAll().stream()
                .map(valueIndicatorMapper::asExcelDto)
                .collect(Collectors.toList());

        try {
            beanToCsv.write(valueIndicatorExcelDTOS);
            log.info("export ok");
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            log.error("error");
            // throw new ValidateCassetteException("Export error");
        }

    }

    @Override
    public List<ValueIndicatorDTO> findLast(ManagementUnitEntity projet) {
        List<ValueIndicatorDTO> valueIndicatorDTOList = new ArrayList<>();
        var indicatorProjects = indicatorProjetRepository.findByProjet(projet);
        if(indicatorProjects.isPresent()){
            indicatorProjects.get().forEach( indicatorProjet -> {
                var valueIndicators = valueIndicatorRepository.findByIndicatorProjet(indicatorProjet);
                if(valueIndicators.isPresent()){
                    var valueIndicatorList = valueIndicators.get().stream()
                            .map(valueIndicatorMapper::asDto)
//                            .sorted(Comparator.comparing(f -> f.getPeriod().getOrdre()))
                            .collect(Collectors.toList());
                    valueIndicatorDTOList.add(valueIndicatorList.get(valueIndicatorList.size()-1));
                }
                    });
        }
        return valueIndicatorDTOList;
    }


}
