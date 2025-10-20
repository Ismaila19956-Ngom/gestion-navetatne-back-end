package com.webgram.dgpsn.services.Impl;

import com.khoutech.openexcel.services.WorkbookService;
import com.webgram.dgpsn.tools.ActionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.webgram.dgpsn.annotations.Journal;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.FundingSourceMapper;
import com.webgram.dgpsn.models.FundingSourceDTO;
import com.webgram.dgpsn.repositories.FundingSourceRepository;
import com.webgram.dgpsn.repositories.ManagementUnitRepository;
import com.webgram.dgpsn.services.FundingSourceService;

import java.text.ParseException;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class FundingSourceServiceImpl implements FundingSourceService {
    private final FundingSourceRepository fundingSourceRepository;
    private final FundingSourceMapper fundingSourceMapper;
    private final ManagementUnitRepository managementUnitRepository;
    private final WorkbookService workbookService;

    @Override
    @Journal(actionType = ActionType.ADD_DATES_IMPORTANTE)
    public FundingSourceDTO create(FundingSourceDTO fundingSourceDTO) {
         var savedFunding = fundingSourceRepository.save(fundingSourceMapper.asEntity(fundingSourceDTO));
        log.info("savedFunding successfully added {}", savedFunding);

        return fundingSourceMapper.asDto(savedFunding);
    }
    @Override
    @Journal(actionType = ActionType.UPDATE_DATES_IMPORTANTE)
    public FundingSourceDTO update(FundingSourceDTO fundingSourceDTO) {
        try{
            if(fundingSourceRepository.existsById(fundingSourceDTO.getId())) {
                var fundingActivity = fundingSourceMapper.asEntity(fundingSourceDTO);

                var updatedFunding = fundingSourceMapper.asDto(fundingSourceRepository.save(fundingActivity));
                log.info("fundingActivity successfully updated {} ", updatedFunding.getId());
                return updatedFunding;
            } else {
                throw new ResourceNotFoundException("fundingActivity", fundingSourceDTO.getId());
            }
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("fundingActivity", fundingSourceDTO.getId());
        }
    }
    @Override
    @Journal(actionType = ActionType.READ_DATES_IMPORTANTE)
    public FundingSourceDTO read(Long fundingSourceId) {
        var funding = fundingSourceRepository
                .findById(fundingSourceId)
                .orElseThrow(()-> new ResourceNotFoundException("Funding", fundingSourceId));

        log.info("reading fundingActivity id {}", funding);

        return fundingSourceMapper.asDto(funding);
    }

    @Override
    @Journal(actionType = ActionType.DELETE_DATES_IMPORTANTE)
    public void delete(Long fundingSourceId) {
        try {
            fundingSourceRepository.deleteById(fundingSourceId);
            log.info("The fundingActivity id {} is deleted", fundingSourceId);
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("FundingSource", fundingSourceId);
        }
    }

    @Override
    @Journal(actionType = ActionType.READ_DATES_IMPORTANTE)
    public Page<FundingSourceDTO> readAll(
            Pageable pageable,
            String montant,
            Long managementUnitId,
            Long structureId,
            Long budgetId
    ) throws ParseException {
        return fundingSourceRepository
                .readAllByFiltering(pageable, montant,managementUnitId,structureId,budgetId)
                .map(fundingSourceMapper::asDto);
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
