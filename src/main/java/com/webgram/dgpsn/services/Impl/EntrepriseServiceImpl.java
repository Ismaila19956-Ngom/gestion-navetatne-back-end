package com.webgram.dgpsn.services.Impl;

import com.querydsl.core.BooleanBuilder;
import com.webgram.dgpsn.entities.QEntrepriseEntity;
import com.webgram.dgpsn.services.Impl.modelExcelDTO.EntrepriseExcelDTO;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvBindByName;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.mappers.EntrepriseMapper;

import com.webgram.dgpsn.models.EntrepriseDto;
import com.webgram.dgpsn.repositories.EntrepriseRepository;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.services.EntrepriseService;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class EntrepriseServiceImpl implements EntrepriseService {

    private final EntrepriseRepository entrepriseRepository;
    private final EntrepriseMapper entrepriseMapper;

    @Override
    public EntrepriseDto create(EntrepriseDto dto) {
        var entity = entrepriseMapper.asEntity(dto);
        var savedEntity = entrepriseRepository.save(entity);
        return entrepriseMapper.asDto(savedEntity);
    }

    @Override
    public EntrepriseDto update(EntrepriseDto dto) {
        var entreprise = read(dto.getId());
        var entity = entrepriseMapper.asEntity(dto);
        var updatedEntity = entrepriseRepository.save(entity);
        return entrepriseMapper.asDto(updatedEntity);
    }

    @Override
    public EntrepriseDto read(Long id) {
        var entity = entrepriseRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return entrepriseMapper.asDto(entity);
    }

    @Override
    public void delete(Long id) {
        entrepriseRepository.deleteById(id);
    }

    @Override
    public Page<EntrepriseDto> readAll(Map<String, String> searchParams, Pageable pageable) {
        var booleanBuilder = new BooleanBuilder();
        buildSearch(searchParams, booleanBuilder);
        return entrepriseRepository.findAll(booleanBuilder, pageable)
            .map(entrepriseMapper::asDto);
    }

  
    public void exportEntreprise(PrintWriter writer) {
        /* Creating header */
        writer.append(Arrays.stream(EntrepriseExcelDTO.class.getDeclaredFields())
                .filter(f -> Objects.nonNull(f.getAnnotation(CsvBindByPosition.class)) && Objects.nonNull(f.getAnnotation(CsvBindByName.class)))
                .sorted(Comparator.comparing(f -> f.getAnnotation(CsvBindByPosition.class).position()))
                .map(f -> f.getAnnotation(CsvBindByName.class).column())
                .collect(Collectors.joining(";"))).append("\n");

        StatefulBeanToCsv<EntrepriseExcelDTO> beanToCsv = new StatefulBeanToCsvBuilder<EntrepriseExcelDTO>(writer)
                .withSeparator(';')
                .withQuotechar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .build();

        var entities = entrepriseRepository.findAll();
        var dtos = entities.stream().map(entrepriseMapper::asExcelDto).collect(Collectors.toList());
        try {
            beanToCsv.write(dtos);
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            log.error("Error writing CSV", e);
        }
    }
    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
          if (Objects.nonNull(searchParams)) {
               var qEntity = QEntrepriseEntity.entrepriseEntity;
			if (searchParams.containsKey("code"))
				booleanBuilder.and(qEntity.code.containsIgnoreCase(searchParams.get("code")));
			if (searchParams.containsKey("denomination"))
				booleanBuilder.and(qEntity.denomination.containsIgnoreCase(searchParams.get("denomination")));
			if (searchParams.containsKey("capitalsocial"))
				booleanBuilder.and(qEntity.capitalsocial.containsIgnoreCase(searchParams.get("capitalsocial")));
			if (searchParams.containsKey("participationetat"))
				booleanBuilder.and(qEntity.participationetat.containsIgnoreCase(searchParams.get("participationetat")));
			if (searchParams.containsKey("droitvote"))
				booleanBuilder.and(qEntity.droitvote.containsIgnoreCase(searchParams.get("droitvote")));
			if (searchParams.containsKey("vnp"))
				booleanBuilder.and(qEntity.vnp.containsIgnoreCase(searchParams.get("vnp")));
			if (searchParams.containsKey("vcp"))
				booleanBuilder.and(qEntity.vcp.containsIgnoreCase(searchParams.get("vcp")));
			if (searchParams.containsKey("vmp"))
				booleanBuilder.and(qEntity.vmp.containsIgnoreCase(searchParams.get("vmp")));
			if (searchParams.containsKey("longitude"))
				booleanBuilder.and(qEntity.longitude.containsIgnoreCase(searchParams.get("longitude")));
			if (searchParams.containsKey("latitude"))
				booleanBuilder.and(qEntity.latitude.containsIgnoreCase(searchParams.get("latitude")));
			if (searchParams.containsKey("responsable"))
				booleanBuilder.and(qEntity.responsable.containsIgnoreCase(searchParams.get("responsable")));
			if (searchParams.containsKey("telephoneresponsable"))
				booleanBuilder.and(qEntity.telephoneresponsable.containsIgnoreCase(searchParams.get("telephoneresponsable")));
			if (searchParams.containsKey("emailresponsable"))
				booleanBuilder.and(qEntity.emailresponsable.containsIgnoreCase(searchParams.get("emailresponsable")));
			if (searchParams.containsKey("adresse"))
				booleanBuilder.and(qEntity.adresse.containsIgnoreCase(searchParams.get("adresse")));
			if (searchParams.containsKey("website"))
				booleanBuilder.and(qEntity.website.containsIgnoreCase(searchParams.get("website")));
			if (searchParams.containsKey("telephone"))
				booleanBuilder.and(qEntity.telephone.containsIgnoreCase(searchParams.get("telephone")));
			if (searchParams.containsKey("email"))
				booleanBuilder.and(qEntity.email.containsIgnoreCase(searchParams.get("email")));
			if (searchParams.containsKey("secteuractiviteId"))
				booleanBuilder.and(qEntity.secteuractivite.id.eq(Long.valueOf(searchParams.get("secteuractiviteId"))));
			if (searchParams.containsKey("formejuridiqueId"))
				booleanBuilder.and(qEntity.formejuridique.id.eq(Long.valueOf(searchParams.get("formejuridiqueId"))));
			if (searchParams.containsKey("regionId"))
				booleanBuilder.and(qEntity.region.id.eq(Long.valueOf(searchParams.get("regionId"))));
			if (searchParams.containsKey("departementId"))
				booleanBuilder.and(qEntity.departement.id.eq(Long.valueOf(searchParams.get("departementId"))));
           }
   }
}
