package com.webgram.dgpsn.services.Impl;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.querydsl.core.BooleanBuilder;
import com.webgram.dgpsn.entities.ParticipantagEntity;
import com.webgram.dgpsn.entities.QParticipantagEntity;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.ParticipantagMapper;
import com.webgram.dgpsn.models.ParticipantagDto;
import com.webgram.dgpsn.repositories.ParticipantagRepository;
import com.webgram.dgpsn.services.Impl.modelExcelDTO.ParticipantagExcelDTO;
import com.webgram.dgpsn.services.ParticipantagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ParticipantagServiceImpl implements ParticipantagService {

    private final ParticipantagRepository participantagRepository;
    private final ParticipantagMapper participantagMapper;

    @Override
    public ParticipantagDto create(ParticipantagDto dto) {
        var entity = participantagMapper.asEntity(dto);
        var savedEntity = participantagRepository.save(entity);
        return participantagMapper.asDto(savedEntity);
    }

    @Override
    public List<ParticipantagDto> readByAssemblegeneralId(Long assemblegeneralId) {
        List<ParticipantagEntity> participantags = participantagRepository.findByAssemblegeneralId(assemblegeneralId);
        return participantags.stream()
                .map(participantagMapper::asDto)
                .collect(Collectors.toList());
    }

    @Override
    public ParticipantagDto update(ParticipantagDto dto) {
        var participantag = read(dto.getId());
        var entity = participantagMapper.asEntity(dto);
        var updatedEntity = participantagRepository.save(entity);
        return participantagMapper.asDto(updatedEntity);
    }

    @Override
    public ParticipantagDto read(Long id) {
        var entity = participantagRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return participantagMapper.asDto(entity);
    }

    @Override
    public void delete(Long id) {
        participantagRepository.deleteById(id);
    }

    @Override
    public Page<ParticipantagDto> readAll(Map<String, String> searchParams, Pageable pageable) {
        var booleanBuilder = new BooleanBuilder();
        buildSearch(searchParams, booleanBuilder);
        return participantagRepository.findAll(booleanBuilder, pageable)
            .map(participantagMapper::asDto);
    }

  
    public void exportParticipantag(PrintWriter writer) {
        /* Creating header */
        writer.append(Arrays.stream(ParticipantagExcelDTO.class.getDeclaredFields())
                .filter(f -> Objects.nonNull(f.getAnnotation(CsvBindByPosition.class)) && Objects.nonNull(f.getAnnotation(CsvBindByName.class)))
                .sorted(Comparator.comparing(f -> f.getAnnotation(CsvBindByPosition.class).position()))
                .map(f -> f.getAnnotation(CsvBindByName.class).column())
                .collect(Collectors.joining(";"))).append("\n");

        StatefulBeanToCsv<ParticipantagExcelDTO> beanToCsv = new StatefulBeanToCsvBuilder<ParticipantagExcelDTO>(writer)
                .withSeparator(';')
                .withQuotechar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .build();

        var entities = participantagRepository.findAll();
        var dtos = entities.stream().map(participantagMapper::asExcelDto).collect(Collectors.toList());
        try {
            beanToCsv.write(dtos);
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            log.error("Error writing CSV", e);
        }
    }
    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
          if (Objects.nonNull(searchParams)) {
               var qEntity = QParticipantagEntity.participantagEntity;
			if (searchParams.containsKey("particpants"))
				booleanBuilder.and(qEntity.particpants.containsIgnoreCase(searchParams.get("particpants")));
			if (searchParams.containsKey("role"))
				booleanBuilder.and(qEntity.role.containsIgnoreCase(searchParams.get("role")));
			if (searchParams.containsKey("coment"))
				booleanBuilder.and(qEntity.coment.containsIgnoreCase(searchParams.get("coment")));
			if (searchParams.containsKey("presence"))
				booleanBuilder.and(qEntity.presence.eq(Boolean.valueOf(searchParams.get("presence"))));
           }
   }
}
