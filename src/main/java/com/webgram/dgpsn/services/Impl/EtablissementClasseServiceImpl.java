package com.webgram.dgpsn.services.Impl;

import com.webgram.dgpsn.tools.ActionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.webgram.dgpsn.annotations.Journal;
import com.webgram.dgpsn.entities.EtablissementClasseEntity;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.EtablissementClasseMapper;
import com.webgram.dgpsn.models.EtablissementClasseDTO;
import com.webgram.dgpsn.repositories.EtablissementClasseRepository;
import com.webgram.dgpsn.services.EtablissementClasseService;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class EtablissementClasseServiceImpl implements EtablissementClasseService {

    private final EtablissementClasseRepository etablissementClasseRepository;
    private final EtablissementClasseMapper etablissementClasseMapper;

    @Override
    @Journal(actionType = ActionType.ADD_ETABLISSEMENT_CLASSE)
    public EtablissementClasseDTO create(EtablissementClasseDTO etablissementClasseDTO) {
        EtablissementClasseEntity savedEtablissement = etablissementClasseRepository.save(etablissementClasseMapper.asEntity(etablissementClasseDTO));

        log.info("Établissement classé ajouté avec succès {}", savedEtablissement);

        return etablissementClasseMapper.asDto(savedEtablissement);
    }

    @Override
    @Journal(actionType = ActionType.UPDATE_ETABLISSEMENT_CLASSE)
    public EtablissementClasseDTO update(EtablissementClasseDTO etablissementClasseDTO) {
        try {
            if (etablissementClasseRepository.existsById(etablissementClasseDTO.getId())) {
                EtablissementClasseEntity entity = etablissementClasseMapper.asEntity(etablissementClasseDTO);

                EtablissementClasseDTO updatedEtablissement = etablissementClasseMapper.asDto(etablissementClasseRepository.save(entity));

                log.info("Établissement classé modifié avec succès {}", entity.getId());

                return updatedEtablissement;
            } else {
                throw new ResourceNotFoundException("Établissement classé Id ", etablissementClasseDTO.getId());
            }
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("Établissement classé Id ", etablissementClasseDTO.getId());
        }
    }

    @Override
    @Journal(actionType = ActionType.READ_ETABLISSEMENT_CLASSE)
    public EtablissementClasseDTO read(Long etablissementClasseId) {
        EtablissementClasseEntity entity = etablissementClasseRepository
                .findById(etablissementClasseId)
                .orElseThrow(() -> new ResourceNotFoundException("Établissement classé Id ", etablissementClasseId));

        log.info("Lecture de l’établissement classé id {}", etablissementClasseId);

        return etablissementClasseMapper.asDto(entity);
    }

    @Override
    @Journal(actionType = ActionType.DELETE_ETABLISSEMENT_CLASSE)
    public void delete(Long etablissementClasseId) {
        try {
            etablissementClasseRepository.deleteById(etablissementClasseId);
            log.info("Établissement classé avec l'id {} a été supprimé", etablissementClasseId);
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("Établissement classé Id ", etablissementClasseId);
        }
    }

    @Override
    @Journal(actionType = ActionType.READ_ETABLISSEMENT_CLASSE)
    public Page<EtablissementClasseDTO> readAll(
            Pageable pageable,
            String code,
            String libelle,
            String latitude,
            String longitude,
            String contactPerson,
            String contactRole,
            String contactInfo,
            String mainActivity,
            Long typeEtablissementId,
            Long regionId,
            Long departementId,
            Long categoryICPEId,
            String sortBy,
            Boolean ascending
    ) {
        return etablissementClasseRepository
                .readAllByFiltering(pageable, code, libelle, latitude, longitude, contactPerson, contactRole, contactInfo, mainActivity, typeEtablissementId, regionId, departementId, categoryICPEId, sortBy, ascending)
                .map(etablissementClasseMapper::asDto);
    }
}