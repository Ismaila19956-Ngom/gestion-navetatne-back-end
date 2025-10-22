package com.webgram.dgpsn.services.Impl;

import com.khoutech.openexcel.services.WorkbookService;
import com.webgram.dgpsn.annotations.Journal;
import com.webgram.dgpsn.entities.enums.FundingTypeConfig;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.FundingConfigMapper;
import com.webgram.dgpsn.models.FundingConfigDTO;
import com.webgram.dgpsn.repositories.FundingConfigRepository;
import com.webgram.dgpsn.repositories.ManagementUnitRepository;
import com.webgram.dgpsn.services.FundingConfigService;
import com.webgram.dgpsn.tools.ActionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class FundingConfigServiceImpl implements FundingConfigService {
    private final FundingConfigRepository fundingConfigRepository;
    private final FundingConfigMapper fundingConfigMapper;
    private final ManagementUnitRepository managementUnitRepository;
    private final WorkbookService workbookService;

    @Override
    @Journal(actionType = ActionType.ADD_DATES_IMPORTANTE)
    public FundingConfigDTO create(FundingConfigDTO fundingConfigDTO) {
         var savedFunding = fundingConfigRepository.save(fundingConfigMapper.asEntity(fundingConfigDTO));
        log.info("savedFunding successfully added {}", savedFunding);

        return fundingConfigMapper.asDto(savedFunding);
    }

    @Override
    @Journal(actionType = ActionType.UPDATE_DATES_IMPORTANTE)
    public FundingConfigDTO update(FundingConfigDTO fundingConfigDTO) {
        try{
            if(fundingConfigRepository.existsById(fundingConfigDTO.getId())) {
                var fundingconfig = fundingConfigMapper.asEntity(fundingConfigDTO);

                var updatedFunding = fundingConfigMapper.asDto(fundingConfigRepository.save(fundingconfig));
                log.info("fundingconfig successfully updated {} ", updatedFunding.getId());
                return updatedFunding;
            } else {
                throw new ResourceNotFoundException("fundingconfig", fundingConfigDTO.getId());
            }
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("fundingconfig", fundingConfigDTO.getId());
        }
    }

    @Override
    @Journal(actionType = ActionType.READ_DATES_IMPORTANTE)
    public FundingConfigDTO read(Long fundingConfigId) {
        var funding = fundingConfigRepository
                .findById(fundingConfigId)
                .orElseThrow(()-> new ResourceNotFoundException("Funding", fundingConfigId));

        log.info("reading fundingActivity id {}", funding);

        return fundingConfigMapper.asDto(funding);
    }

    @Override
    @Journal(actionType = ActionType.DELETE_DATES_IMPORTANTE)
    public void delete(Long fundingId) {
        try {
            fundingConfigRepository.deleteById(fundingId);
            log.info("The fundingActivity id {} is deleted", fundingId);
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("FundingConf", fundingId);
        }
    }

    @Override
    @Journal(actionType = ActionType.READ_DATES_IMPORTANTE)
    public Page<FundingConfigDTO> readAll(
            Pageable pageable,
            String libelle,
            String annee,
            FundingTypeConfig fundingTypeConfig,
            String startingDate,
            String endingDate,
            String estimatedAmount,
            String actualAmount,
            Long managementUnitId
    ) throws ParseException {
        return fundingConfigRepository
                .readAllByFiltering(pageable, libelle,  annee,fundingTypeConfig,startingDate,endingDate, estimatedAmount, actualAmount,managementUnitId)
                .map(fundingConfigMapper::asDto);
    }

//    @Override
//    @Journal(actionType = ActionType.IMPORT_DATES_IMPORTANTE)
//    public void importMilestone(MultipartFile file, Long projectId) {
//        try (Workbook workbook = workbookService.findWorkBook(file.getInputStream(), ExcelContentType.fromContentType(file.getContentType()))) {
//            Sheet sheet = workbook.getSheetAt(0);
//            ExcelBean<MilestoneExcelDTO> milestoneExcelDTOExcelBean = new ExcelBeanBuilder<>(sheet, MilestoneExcelDTO.class)
//                    .skipLines(0)
//                    .build();
//            List<MilestoneExcelDTO> milestoneExcelDTOS = milestoneExcelDTOExcelBean.parse();
//
//            var projet = managementUnitRepository.findById(projectId)
//                    .orElseThrow(() -> new ResourceNotFoundException("Project avec id {} introuvable"));
//
//            List<MilestoneEntity> milestones = milestoneExcelDTOS.stream()
//                    .map(milestoneMapper::asEntity)
//                    .map(milestone -> milestone.setProjet(projet))
//                    .collect(Collectors.toList());
//
//            milestoneRepository.saveAll(milestones);
//
//            log.info("importMilestone end ok");
//            log.trace("importMilestone end ok - projects: {}", milestones);
//        } catch (IOException e) {
//            log.info("Exceptions handle import file =============== {0}", e);
//            throw new InvalidParameterException(MessageFormat.format("Exceptions handle import file ", "Banner", "idexists"));
//        }
//    }
//
//    @Override
//    @Journal(actionType = ActionType.EXPORT_PROJECT_TO_EXCEL)
//    public void exportMilsstone(PrintWriter writer) {
//        /* Creating header */
//        writer.append(Arrays.stream(MilestoneExcelDTO.class.getDeclaredFields())
//                .filter(f -> Objects.nonNull(f.getAnnotation(CsvBindByPosition.class)) && Objects.nonNull(f.getAnnotation(CsvBindByName.class)))
//                .sorted(Comparator.comparing(f -> f.getAnnotation(CsvBindByPosition.class).position()))
//                .map(f -> f.getAnnotation(CsvBindByName.class).column())
//                .collect(Collectors.joining(";"))).append("\n");
//
//        StatefulBeanToCsv<MilestoneExcelDTO> beanToCsv = new StatefulBeanToCsvBuilder<MilestoneExcelDTO>(writer)
//                .withSeparator(';')
//                .withQuotechar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
//                .build();
//
//        var milestones = milestoneRepository
//                .findAll().stream().map(milestoneMapper::asExcelDto);
//
//        try {
//            beanToCsv.write(milestones);
//        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
//            log.error("error");
////            throw new ValidateCassetteException("Export error");
//        }
//    }
}
