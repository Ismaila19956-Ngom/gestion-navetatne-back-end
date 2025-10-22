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
import com.webgram.dgpsn.entities.StructureEntity;
import com.webgram.dgpsn.entities.enums.TypeStructure;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.StructureMapper;
import com.webgram.dgpsn.models.StructureDTO;
import com.webgram.dgpsn.repositories.StructureRepository;
import com.webgram.dgpsn.services.StructureService;
import com.webgram.dgpsn.services.modelExcel.StructureExcelDTO;
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
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class StructureServiceImpl implements StructureService {
    private final StructureRepository structureRepository;
    private final StructureMapper structureMapper;
    private final WorkbookService workbookService;

    private String STRUCTURE_IDENTIFIER_NOT_FOUND_MESSAGE = "Invalide id structure: {}";

    @Override
    public StructureDTO createStructure(StructureDTO structureDTO) {
        var savedStructure = structureRepository.save(structureMapper.asEntity(structureDTO));

        log.info("Structure successfully added {}", savedStructure);

        return structureMapper.asDto(savedStructure);
    }

    @Override
    public StructureDTO updateStructure(StructureDTO structureDTO) {
        if(!structureRepository.existsById(structureDTO.getId())){
            throw new ResourceNotFoundException(MessageFormat.format(STRUCTURE_IDENTIFIER_NOT_FOUND_MESSAGE, structureDTO.getId()));
        }

        var structure = structureMapper.asEntity(structureDTO);

        var updatedStructure = structureMapper.asDto(structureRepository.save(structure));

        log.info("Structure successfully updated {} ", updatedStructure.getId());

        return updatedStructure;
    }

    @Override
    public StructureDTO readStructure(Long id) {
        var structure = structureRepository
                .findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Structure", id));

        log.info("reading structure id {}", id);

        return structureMapper.asDto(structure);
    }

    @Override
    public void deleteStructure(Long id) {
        try {
            structureRepository.deleteById(id);
            log.info("The structure id {} is deleted", id);
        } catch (IllegalArgumentException ex) {
            log.info("The given id to delete must not be null");
        }
    }

    @Override
    public Page<StructureDTO> readAllStructure(Pageable pageable, String code, String nom, TypeStructure typeStructure, List<Long> idsToIgnore, List<TypeStructure> structureTypeList, String sortBy, Boolean ascending) {
        return structureRepository
                .readAllByFiltering(pageable, code, nom, typeStructure, idsToIgnore, structureTypeList, sortBy, ascending)
                .map(structureMapper::asDto);
    }

    @Override
    public StructureDTO readStructure(String code) {
        var structure = structureRepository
                .findByCode(code)
                .orElseThrow(()-> new ResourceNotFoundException("Aucune structure trouvée pour ce code", code));

        log.info("reading structure code {}", code);

        return structureMapper.asDto(structure);
    }

    @Override
    public void importStructure(MultipartFile file) {
        try (Workbook workbook = workbookService.findWorkBook(file.getInputStream(), ExcelContentType.fromContentType(file.getContentType()))) {
            Sheet sheet = workbook.getSheetAt(0);
            ExcelBean<StructureExcelDTO> structureExcelDTOExcelBean = new ExcelBeanBuilder<>(sheet, StructureExcelDTO.class)
                    .skipLines(0)
                    .build();

            List<StructureExcelDTO> structureExcelDTOS = structureExcelDTOExcelBean.parse();

            List<StructureEntity> structures = structureExcelDTOS.stream()
                    .map(structureMapper::asEntity)
                    .collect(Collectors.toList());

            structureRepository.saveAll(structures);

            log.info("importStructure end ok");
            log.trace("importStructure end ok - projects: {}", structures);
        } catch (IOException e) {
            log.info("Exceptions handle import file =============== {0}", e);
            throw new InvalidParameterException(MessageFormat.format("Exceptions handle import file ", "Banner", "idexists"));
        }
    }

    @Override
    public void exportStructure(PrintWriter writer, TypeStructure typeStructure) {
        /* Creating header */
        writer.append(Arrays.stream(StructureExcelDTO.class.getDeclaredFields())
                .filter(f -> Objects.nonNull(f.getAnnotation(CsvBindByPosition.class)) && Objects.nonNull(f.getAnnotation(CsvBindByName.class)))
                .sorted(Comparator.comparing(f -> f.getAnnotation(CsvBindByPosition.class).position()))
                .map(f -> f.getAnnotation(CsvBindByName.class).column())
                .collect(Collectors.joining(";"))).append("\n");

        StatefulBeanToCsv<StructureExcelDTO> beanToCsv = new StatefulBeanToCsvBuilder<StructureExcelDTO>(writer)
                .withSeparator(';')
                .withQuotechar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .build();

        var structures = structureRepository
                .findByTypeStructure(typeStructure).get()
                .stream()
                .map(structureMapper::asExcelDto);

        try {
            beanToCsv.write(structures);
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            log.error("error");
//            throw new ValidateCassetteException("Export error");
        }
    }
}
