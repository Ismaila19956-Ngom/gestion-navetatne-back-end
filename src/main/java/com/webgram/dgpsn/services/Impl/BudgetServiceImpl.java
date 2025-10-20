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
import com.webgram.dgpsn.entities.enums.SouceBudget;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.BudgetMapper;
import com.webgram.dgpsn.models.BudgetDTO;
import com.webgram.dgpsn.repositories.BudgetRepository;
import com.webgram.dgpsn.repositories.ManagementUnitRepository;
import com.webgram.dgpsn.services.BudgetService;

import java.text.ParseException;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class BudgetServiceImpl implements BudgetService {
    private final BudgetRepository budgetRepository;
    private final BudgetMapper budgetMapper;
    private final ManagementUnitRepository managementUnitRepository;
    private final WorkbookService workbookService;

    @Override
    @Journal(actionType = ActionType.ADD_DATES_IMPORTANTE)
    public BudgetDTO create(BudgetDTO budgetDTO) {
         var savedFunding = budgetRepository.save(budgetMapper.asEntity(budgetDTO));
        log.info("savedFunding successfully added {}", savedFunding);

        return budgetMapper.asDto(savedFunding);
    }

    @Override
    @Journal(actionType = ActionType.UPDATE_DATES_IMPORTANTE)
    public BudgetDTO update(BudgetDTO budgetDTO) {
        try{
            if(budgetRepository.existsById(budgetDTO.getId())) {
                var fundingActivity = budgetMapper.asEntity(budgetDTO);

                var updatedFunding = budgetMapper.asDto(budgetRepository.save(fundingActivity));
                log.info("fundingActivity successfully updated {} ", updatedFunding.getId());
                return updatedFunding;
            } else {
                throw new ResourceNotFoundException("fundingActivity", budgetDTO.getId());
            }
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("fundingActivity", budgetDTO.getId());
        }
    }

    @Override
    @Journal(actionType = ActionType.READ_DATES_IMPORTANTE)
    public BudgetDTO read(Long fundingActivityId) {
        var funding = budgetRepository
                .findById(fundingActivityId)
                .orElseThrow(()-> new ResourceNotFoundException("Funding", fundingActivityId));

        log.info("reading fundingActivity id {}", funding);

        return budgetMapper.asDto(funding);
    }

    @Override
    @Journal(actionType = ActionType.DELETE_DATES_IMPORTANTE)
    public void delete(Long budgetId) {
        try {
            budgetRepository.deleteById(budgetId);
            log.info("The fundingActivity id {} is deleted", budgetId);
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("budget", budgetId);
        }
    }

    @Override
    @Journal(actionType = ActionType.READ_DATES_IMPORTANTE)
    public Page<BudgetDTO> readAll(
            Pageable pageable,
            String libelle,
            SouceBudget souceBudget,
            String estimatedAmount,
            String actualAmount,
            Long managementUnitId
    ) throws ParseException {
        return budgetRepository
                .readAllByFiltering(pageable, libelle,  souceBudget, estimatedAmount,
                        actualAmount,managementUnitId)
                .map(budgetMapper::asDto);
    }

    @Override
    public Long totalFunding() {
        return budgetRepository.findTotalFunding();
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
