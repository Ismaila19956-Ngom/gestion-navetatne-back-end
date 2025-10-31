package com.webgram.dgpsn.services.Impl;

import com.querydsl.core.BooleanBuilder;
import com.webgram.dgpsn.entities.QCaracteristiqueExigeEntity;
import com.webgram.dgpsn.entities.enums.TypeContrat;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.CaracteristiqueExigeMapper;
import com.webgram.dgpsn.models.CaracteristiqueExigeDTO;
import com.webgram.dgpsn.repositories.CaracteristiqueExigeRepository;
import com.webgram.dgpsn.services.CaracteristiqueExigeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class CaracteristiqueExigeServiceImpl implements CaracteristiqueExigeService {
    private final CaracteristiqueExigeRepository caracteristiqueExigeRepository;
    private final CaracteristiqueExigeMapper caracteristiqueExigeMapper;


    String ROLE_IDENTIFIER_NOT_FOUND_MESSAGE = "Invalide id caracteristiqueExige {0}";

//    @Override
//    public CaracteristiqueExigeDTO createCaracteristiqueExige(CaracteristiqueExigeDTO caracteristiqueExigeDTO) {
//        var entity = caracteristiqueExigeMapper.asEntity(caracteristiqueExigeDTO);
//        var savedEntity = caracteristiqueExigeRepository.save(entity);
//        return caracteristiqueExigeMapper.asDto(savedEntity);
//    }

    @Override
    public CaracteristiqueExigeDTO create(CaracteristiqueExigeDTO caracteristiqueExigeDTO) {
        var entity = caracteristiqueExigeMapper.asEntity(caracteristiqueExigeDTO);


        var savedEntity = caracteristiqueExigeRepository.save(entity);
        return caracteristiqueExigeMapper.asDto(savedEntity);
    }

    @Override
    public CaracteristiqueExigeDTO update(CaracteristiqueExigeDTO caracteristiqueExigeDTO) {
        return null;
    }


//    @Override
//    public CaracteristiqueExigeDTO updateCaracteristiqueExige(CaracteristiqueExigeDTO caracteristiqueExigeDTO) {
//        return null;
//    }


    @Override
    public CaracteristiqueExigeDTO read(Long id) {
        var entity = caracteristiqueExigeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return caracteristiqueExigeMapper.asDto(entity);    }

    @Override
    public List<CaracteristiqueExigeDTO> readAll() {
        return List.of();
    }

    @Override
    public Page<CaracteristiqueExigeDTO> readPageCaracteristiqueExige(Map<String, String> searchParams, int page, int size) throws ParseException {
        return null;
    }

    @Override
    public void delete(Long caracteristiqueExigeId) {

    }


    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
        if (Objects.nonNull(searchParams)) {
            var qEntity = QCaracteristiqueExigeEntity.caracteristiqueExigeEntity;
            if (searchParams.containsKey("exige"))
                booleanBuilder.and(qEntity.exige);

            if (searchParams.containsKey("exige"))
                booleanBuilder.and(qEntity.exige);


        }
    }

}
