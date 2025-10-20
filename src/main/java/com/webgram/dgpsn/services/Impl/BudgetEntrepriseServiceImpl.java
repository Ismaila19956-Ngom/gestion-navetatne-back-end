package com.webgram.dgpsn.services.Impl;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.webgram.dgpsn.entities.BudgetEntrepriseEntity;
import com.webgram.dgpsn.entities.QBudgetEntrepriseEntity;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.BudgetEntrepriseMapper;
import com.webgram.dgpsn.models.BudgetEntrepriseDto;
import com.webgram.dgpsn.repositories.BudgetEntrepriseRepository;
import com.webgram.dgpsn.services.BudgetEntrepriseService;
import com.webgram.dgpsn.services.Impl.modelExcelDTO.BudgetExcelDTO;

import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class BudgetEntrepriseServiceImpl implements BudgetEntrepriseService {

    private final BudgetEntrepriseRepository budgetEntrepriseRepository;
    private final BudgetEntrepriseMapper budgetEntrepriseMapper;

    @Override
    public BudgetEntrepriseDto create(BudgetEntrepriseDto dto) {
        var entity = budgetEntrepriseMapper.asEntity(dto);
        var savedEntity = budgetEntrepriseRepository.save(entity);
        return budgetEntrepriseMapper.asDto(savedEntity);
    }

    @Override
    public List<BudgetEntrepriseDto> readByEntrepriseId(Long entrepriseId) {
        List<BudgetEntrepriseEntity> budgets = budgetEntrepriseRepository.findByEntrepriseId(entrepriseId);
        return budgets.stream()
                .map(budgetEntrepriseMapper::asDto)
                .collect(Collectors.toList());
    }

    @Override
    public BudgetEntrepriseDto update(BudgetEntrepriseDto dto) {
        var budget = read(dto.getId());
        var entity = budgetEntrepriseMapper.asEntity(dto);
        var updatedEntity = budgetEntrepriseRepository.save(entity);
        return budgetEntrepriseMapper.asDto(updatedEntity);
    }

    @Override
    public BudgetEntrepriseDto read(Long id) {
        var entity = budgetEntrepriseRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return budgetEntrepriseMapper.asDto(entity);
    }

    @Override
    public void delete(Long id) {
        budgetEntrepriseRepository.deleteById(id);
    }

    @Override
    public Page<BudgetEntrepriseDto> readAll(Map<String, String> searchParams, Pageable pageable) {
        var booleanBuilder = new BooleanBuilder();
        buildSearch(searchParams, booleanBuilder);
        return budgetEntrepriseRepository.findAll(booleanBuilder, pageable)
            .map(budgetEntrepriseMapper::asDto);
    }

  
    public void exportBudget(PrintWriter writer) {
        /* Creating header */
        writer.append(Arrays.stream(BudgetExcelDTO.class.getDeclaredFields())
                .filter(f -> Objects.nonNull(f.getAnnotation(CsvBindByPosition.class)) && Objects.nonNull(f.getAnnotation(CsvBindByName.class)))
                .sorted(Comparator.comparing(f -> f.getAnnotation(CsvBindByPosition.class).position()))
                .map(f -> f.getAnnotation(CsvBindByName.class).column())
                .collect(Collectors.joining(";"))).append("\n");

        StatefulBeanToCsv<BudgetExcelDTO> beanToCsv = new StatefulBeanToCsvBuilder<BudgetExcelDTO>(writer)
                .withSeparator(';')
                .withQuotechar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .build();

        var entities = budgetEntrepriseRepository.findAll();
        var dtos = entities.stream().map(budgetEntrepriseMapper::asExcelDto).collect(Collectors.toList());
        try {
            beanToCsv.write(dtos);
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            log.error("Error writing CSV", e);
        }
    }
    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
          if (Objects.nonNull(searchParams)) {
               var qEntity = QBudgetEntrepriseEntity.budgetEntrepriseEntity;
			if (searchParams.containsKey("code"))
				booleanBuilder.and(qEntity.code.containsIgnoreCase(searchParams.get("code")));
			if (searchParams.containsKey("libelle"))
				booleanBuilder.and(qEntity.libelle.containsIgnoreCase(searchParams.get("libelle")));
			if (searchParams.containsKey("anneefiscale"))
				booleanBuilder.and(qEntity.anneefiscale.containsIgnoreCase(searchParams.get("anneefiscale")));
			if (searchParams.containsKey("projetassocisier"))
				booleanBuilder.and(qEntity.projetassocisier.containsIgnoreCase(searchParams.get("projetassocisier")));
			if (searchParams.containsKey("datelimitedutilisation")){
				Date date = null;
				try {
					date = new SimpleDateFormat("yyyy-MM-dd").parse(searchParams.get("datelimitedutilisation"));
				} catch (ParseException e) {
					throw new RuntimeException(e);
				}
				booleanBuilder.and(qEntity.datelimitedutilisation.eq(date));
			}
			if (searchParams.containsKey("statutdubudget"))
				booleanBuilder.and(qEntity.statutdubudget.containsIgnoreCase(searchParams.get("statutdubudget")));
			if (searchParams.containsKey("description"))
				booleanBuilder.and(qEntity.description.containsIgnoreCase(searchParams.get("description")));
			if (searchParams.containsKey("categoriebudgetaireId"))
				booleanBuilder.and(qEntity.categoriebudgetaire.id.eq(Long.valueOf(searchParams.get("categoriebudgetaireId"))));
			if (searchParams.containsKey("periodeId"))
				booleanBuilder.and(qEntity.periode.id.eq(Long.valueOf(searchParams.get("periodeId"))));
			if (searchParams.containsKey("periodiciteId"))
				booleanBuilder.and(qEntity.periodicite.id.eq(Long.valueOf(searchParams.get("periodiciteId"))));
              if (searchParams.containsKey("entrepriseId")) {
                  Long entrepriseId = Long.parseLong(searchParams.get("entrepriseId"));
                  booleanBuilder.and(qEntity.entreprise.id.eq(entrepriseId));
              }
          }
   }
}
