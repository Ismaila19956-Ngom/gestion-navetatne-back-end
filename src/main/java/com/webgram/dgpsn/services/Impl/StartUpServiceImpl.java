package com.webgram.dgpsn.services.Impl;

import com.querydsl.core.BooleanBuilder;
import com.webgram.dgpsn.tools.ActionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.webgram.dgpsn.annotations.Journal;
import com.webgram.dgpsn.entities.QStartUpEntity;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.StartUpMapper;
import com.webgram.dgpsn.models.StartUpDTO;
import com.webgram.dgpsn.repositories.StartUpRepository;
import com.webgram.dgpsn.services.StartUpService;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class StartUpServiceImpl implements StartUpService {
    private final StartUpRepository startUpRepository;
    private final StartUpMapper startUpMapper;

    @Override
    @Journal(actionType = ActionType.ADD_ACTEUR)
    public StartUpDTO create(StartUpDTO startUpDTO) {
         var saveStartUp = startUpRepository
                 .save(startUpMapper.asEntity(startUpDTO));
        log.info("actorProject successfully added {}", saveStartUp);
        return startUpMapper.asDto(saveStartUp);
    }

    @Override
    @Journal(actionType = ActionType.UPDATE_ACTEUR)
    public StartUpDTO update(StartUpDTO startUpDTO) {
        var startUpSaved = startUpMapper.asEntity(startUpDTO);

        var updatedStartUp = startUpMapper.asDto(startUpRepository.save(startUpSaved));

        log.info("startup successfully updated {} ", updatedStartUp.getId());
        return updatedStartUp;
    }

    @Override
    @Journal(actionType = ActionType.READ_ACTEUR)
    public StartUpDTO read(Long startUpId) {
        var startup = startUpRepository
                .findById(startUpId)
                .orElseThrow(()-> new ResourceNotFoundException("ActorProjet", startUpId));

        log.info("reading actorProject id {}", startUpId);

        return startUpMapper.asDto(startup);
    }

    @Override
    @Journal(actionType = ActionType.DELETE_ACTEUR)
    public void delete(Long saveId) {
        try {
            startUpRepository.deleteById(saveId);
            log.info("The actorProject id {} is deleted", saveId);
        } catch (IllegalArgumentException ex) {
            log.info("The given id to delete must not be null");
        }
    }

    public Page<StartUpDTO> readAllStartUp(Map<String, String> searchParams, Pageable pageable) {
        var booleanBuilder = new BooleanBuilder();
        buildSearch(searchParams, booleanBuilder);
        return startUpRepository.findAll(booleanBuilder, pageable)
                .map(startUpMapper::asDto);
    }

private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
    if (Objects.nonNull(searchParams)) {
        var qEntity = QStartUpEntity.startUpEntity;

        // Existing fields
        if (searchParams.containsKey("nomCompagnie")) {
            booleanBuilder.and(qEntity.nomCompagnie.containsIgnoreCase(searchParams.get("nomCompagnie")));
        }
        if (searchParams.containsKey("region")) {
            booleanBuilder.and(qEntity.region.containsIgnoreCase(searchParams.get("region")));
        }
        if (searchParams.containsKey("email")) {
            booleanBuilder.and(qEntity.email.containsIgnoreCase(searchParams.get("email")));
        }

        // New fields
        if (searchParams.containsKey("latitude")) {
            try {
                Double latitude = Double.parseDouble(searchParams.get("latitude"));
                booleanBuilder.and(qEntity.latitude.eq(latitude));
            } catch (NumberFormatException e) {
                throw new RuntimeException("Invalid latitude format", e);
            }
        }
        if (searchParams.containsKey("longitude")) {
            try {
                Double longitude = Double.parseDouble(searchParams.get("longitude"));
                booleanBuilder.and(qEntity.longitude.eq(longitude));
            } catch (NumberFormatException e) {
                throw new RuntimeException("Invalid longitude format", e);
            }
        }
        if (searchParams.containsKey("adresse")) {
            booleanBuilder.and(qEntity.adresse.containsIgnoreCase(searchParams.get("adresse")));
        }
        if (searchParams.containsKey("anneeCreation")) {
            try {
                Date date = (Date) new SimpleDateFormat("yyyy-MM-dd").parse(searchParams.get("anneeCreation"));
                booleanBuilder.and(qEntity.anneeCreation.eq(date));
            } catch (ParseException e) {
                throw new RuntimeException("Invalid anneeCreation date format", e);
            }
        }
        if (searchParams.containsKey("siteWeb")) {
            booleanBuilder.and(qEntity.siteWeb.containsIgnoreCase(searchParams.get("siteWeb")));
        }
        if (searchParams.containsKey("dirigeParFemme")) {
            Boolean dirigeParFemme = Boolean.parseBoolean(searchParams.get("dirigeParFemme"));
            booleanBuilder.and(qEntity.dirigeParFemme.eq(dirigeParFemme));
        }
        if (searchParams.containsKey("fondeeParFemme")) {
            Boolean fondeeParFemme = Boolean.parseBoolean(searchParams.get("fondeeParFemme"));
            booleanBuilder.and(qEntity.fondeeParFemme.eq(fondeeParFemme));
        }
        if (searchParams.containsKey("coFondeeParFemme")) {
            Boolean coFondeeParFemme = Boolean.parseBoolean(searchParams.get("coFondeeParFemme"));
            booleanBuilder.and(qEntity.coFondeeParFemme.eq(coFondeeParFemme));
        }
        if (searchParams.containsKey("nombreEmployes")) {
            try {
                Integer nombreEmployes = Integer.parseInt(searchParams.get("nombreEmployes"));
                booleanBuilder.and(qEntity.nombreEmployes.eq(nombreEmployes));
            } catch (NumberFormatException e) {
                throw new RuntimeException("Invalid nombreEmployes format", e);
            }
        }
        if (searchParams.containsKey("statutJuridique")) {
            booleanBuilder.and(qEntity.statutJuridique.containsIgnoreCase(searchParams.get("statutJuridique")));
        }
        if (searchParams.containsKey("selection")) {
            Boolean selection = Boolean.parseBoolean(searchParams.get("selection"));
            booleanBuilder.and(qEntity.selection.eq(selection));
        }
        if (searchParams.containsKey("prenom")) {
            booleanBuilder.and(qEntity.prenom.containsIgnoreCase(searchParams.get("prenom")));
        }
        if (searchParams.containsKey("nom")) {
            booleanBuilder.and(qEntity.nom.containsIgnoreCase(searchParams.get("nom")));
        }
//
        if (searchParams.containsKey("tel")) {
            booleanBuilder.and(qEntity.tel.containsIgnoreCase(searchParams.get("tel")));
        }
        if (searchParams.containsKey("emailSoumissionnaire")) {
            booleanBuilder.and(qEntity.emailSoumissionnaire.containsIgnoreCase(searchParams.get("emailSoumissionnaire")));
        }
        if (searchParams.containsKey("position")) {
            booleanBuilder.and(qEntity.position.containsIgnoreCase(searchParams.get("position")));
        }
        if (searchParams.containsKey("categorieTechnologique")) {
            booleanBuilder.and(qEntity.categorieTechnologique.containsIgnoreCase(searchParams.get("categorieTechnologique")));
        }
        if (searchParams.containsKey("stadeDeveloppement")) {
            booleanBuilder.and(qEntity.stadeDeveloppement.containsIgnoreCase(searchParams.get("stadeDeveloppement")));
        }
        if (searchParams.containsKey("protectionIntellectuelle")) {
            Boolean protectionIntellectuelle = Boolean.parseBoolean(searchParams.get("protectionIntellectuelle"));
            booleanBuilder.and(qEntity.protectionIntellectuelle.eq(protectionIntellectuelle));
        }
        if (searchParams.containsKey("marche")) {
            booleanBuilder.and(qEntity.marche.containsIgnoreCase(searchParams.get("marche")));
        }
    }
}

}
