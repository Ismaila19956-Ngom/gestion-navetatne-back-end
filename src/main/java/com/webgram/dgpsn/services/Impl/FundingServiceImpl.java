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
import com.webgram.dgpsn.entities.FundingEntity;
import com.webgram.dgpsn.entities.ManagementUnitEntity;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.FundingMapper;
import com.webgram.dgpsn.models.FundingDTO;
import com.webgram.dgpsn.models.responses.TauxDecaissementAnnuelDTO;
import com.webgram.dgpsn.properties.DocumentProperties;
import com.webgram.dgpsn.repositories.FundingRepository;
import com.webgram.dgpsn.services.DataStorageService;
import com.webgram.dgpsn.services.FundingService;
import com.webgram.dgpsn.services.modelExcel.FundingExcelDTO;
import com.webgram.dgpsn.tools.ActionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.InvalidParameterException;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class FundingServiceImpl implements FundingService {
    private final FundingRepository fundingRepository;
    private final FundingMapper fundingMapper;

    private  final WorkbookService workbookService;
    String PROJECT_DIRECTORY = "//project";

    final DocumentProperties documentProperties;
    final Environment env;

    final DataStorageService dataStorageService;


//    private final DisbursementRateRepository disbursementRateRepository;

    @Override
    @Journal(actionType = ActionType.ADD_FUNDING)
    public FundingDTO create(FundingDTO fundingDTO) {
         var savedFunding = fundingRepository.save(fundingMapper.asEntity(fundingDTO));

        log.info("funding successfully added {}", savedFunding);

        return fundingMapper.asDto(savedFunding);
    }

    @Override
    @Journal(actionType = ActionType.UPDATE_FUNDING)
    public FundingDTO update(FundingDTO fundingDTO) {
        try{
            if(fundingRepository.existsById(fundingDTO.getId())) {
                var funding = fundingMapper.asEntity(fundingDTO);

                var updatedFUnding = fundingMapper.asDto(fundingRepository.save(funding));

                log.info("Funding successfully updated {} ", updatedFUnding.getId());

                return updatedFUnding;
            } else {
                throw new ResourceNotFoundException("Funding", fundingDTO.getId());
            }
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("Funding", fundingDTO.getId());
        }
    }

    @Override
    @Journal(actionType = ActionType.READ_FUNDING)
    public FundingDTO read(Long fundingId) {
        var funding = fundingRepository
                .findById(fundingId)
                .orElseThrow(()-> new ResourceNotFoundException("Funding", fundingId));

        log.info("reading funding id {}", fundingId);

        return fundingMapper.asDto(funding);
    }

    @Override
    @Journal(actionType = ActionType.DELETE_FUNDING)
    public void delete(Long fundingId) {
        try {
            fundingRepository.deleteById(fundingId);
            log.info("The funding id {} is deleted", fundingId);
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("Funding", fundingId);
        }
    }

    @Override
    @Journal(actionType = ActionType.READ_FUNDING)
    public Page<FundingDTO> readAll(
            Pageable pageable,
            String financingAgreement,
            Double amount,
            String cash,
            Double rate,
            Double equivalence,
            String approvalDate,
            String closingDate,
            String extentionDate,
            Long fundingTypeId,
            Long projetId,
            Long structureId,
            String sortBy,
            Boolean ascending
    ) throws ParseException {
        return fundingRepository
                .readAllByFilters(
                        pageable, financingAgreement, amount, cash, rate,
                        equivalence, approvalDate, closingDate, extentionDate,
                        fundingTypeId, projetId, structureId,sortBy,
                         ascending
                ).map(fundingMapper::asDto);

    }

    @Override
    public List<TauxDecaissementAnnuelDTO> getTauxDecaissement(Long projectId) {
        var fundingProject = fundingRepository.findByProjet(ManagementUnitEntity.builder().id(projectId).build());
        log.info("liste financement par projet {}", fundingProject);
        fundingProject.forEach(funding -> {
//            var taux = disbursementRateRepository.getTaux(funding.getId(), Arrays.asList("T12023", "T22023", "T32023", "T42023"));
           // log.info("taux decaissement {}", taux);
            /*   if(statStatusDTOS.stream().noneMatch(statStatusDTO -> status.getLibelle().equals(statStatusDTO.getLabel()))){
                statStatusDTOS.add(StatisticalDTO.builder().label(status.getLibelle()).value(0L).build());
            } */
        });


        return null;
    }

    @Override
    public Long readTotalFinancement() {
        return fundingRepository.getTotalFinancement();
    }

    @Override
    @Journal(actionType = ActionType.IMPORT_FUNDING)
    public void importFunding(MultipartFile file, Long projectId) {
        try (Workbook workbook = workbookService.findWorkBook(file.getInputStream(), ExcelContentType.fromContentType(file.getContentType()))) {
            Sheet sheet = workbook.getSheetAt(0);
            ExcelBean<FundingExcelDTO> FundingExcelDTOBean = new ExcelBeanBuilder<>(sheet, FundingExcelDTO.class)
                    .skipLines(0)
                    .build();
            List<FundingExcelDTO> fundingExcelDTOS= FundingExcelDTOBean.parse();
            List<FundingEntity> funding = fundingExcelDTOS.stream()
                    .map(fundingExcelDTO -> fundingExcelDTO.setProjectId(projectId))
                    .map(fundingMapper:: asEntity)
                    .collect(Collectors.toList());
            fundingRepository.saveAll(funding);
            log.info("importFunding end ok");
            log.trace("importFunding end ok - Funding: {}", funding);
        } catch (IOException e) {
            log.info("Exceptions handle import file =============== {0}", e);
            throw new InvalidParameterException(MessageFormat.format("Exceptions handle import file ", "Banner", "idexists"));
        }
}

    @Override
    @Journal(actionType = ActionType.EXPORT_FUNDING_TO_EXCEL)
    public void export(PrintWriter writer, Long projetId) {
        /* Creating header */
        writer.append(Arrays.stream(FundingExcelDTO.class.getDeclaredFields())
                .filter(f -> Objects.nonNull(f.getAnnotation(CsvBindByPosition.class)) && Objects.nonNull(f.getAnnotation(CsvBindByName.class)))
                .sorted(Comparator.comparing(f -> f.getAnnotation(CsvBindByPosition.class).position()))
                .map(f -> f.getAnnotation(CsvBindByName.class).column())
                .collect(Collectors.joining(";"))).append("\n");

        StatefulBeanToCsv<FundingExcelDTO> beanToCsv = new StatefulBeanToCsvBuilder<FundingExcelDTO>(writer)
                .withSeparator(';')
                .withQuotechar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .build();

        List<FundingExcelDTO> fundingExcelDTOS = fundingRepository.findAllByProjetId(projetId).stream()
                .map(fundingMapper::asExcelDto)
                .collect(Collectors.toList());

        try {
            beanToCsv.write(fundingExcelDTOS);
            log.info("export ok");
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            log.error("error");
            // throw new ValidateCassetteException("Export error");
        }

    }


}

