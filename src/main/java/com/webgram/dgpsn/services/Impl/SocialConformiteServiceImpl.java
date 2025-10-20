package com.webgram.dgpsn.services.Impl;

import com.webgram.dgpsn.tools.ActionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.webgram.dgpsn.annotations.Journal;
import com.webgram.dgpsn.entities.SocialConformiteEntity;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.SocialConformiteMapper;
import com.webgram.dgpsn.models.SocialConformiteDTO;
import com.webgram.dgpsn.repositories.SocialConformiteRepository;
import com.webgram.dgpsn.services.SocialConformiteService;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class SocialConformiteServiceImpl implements SocialConformiteService {
    private final SocialConformiteRepository socialConformiteRepository;
    private final SocialConformiteMapper socialConformiteMapper;

    @Override
    @Journal(actionType = ActionType.ADD_SOCIAL_CONFORMITE)
    public SocialConformiteDTO create(SocialConformiteDTO socialConformiteDTO) {
         SocialConformiteEntity savedEnvConformite = socialConformiteRepository.save(socialConformiteMapper.asEntity(socialConformiteDTO));

        log.info("Conformite social ajouté avec succès {}", savedEnvConformite);

        return socialConformiteMapper.asDto(savedEnvConformite);
    }

    @Override
    @Journal(actionType = ActionType.UPDATE_SOCIAL_CONFORMITE)
    public SocialConformiteDTO update(SocialConformiteDTO socialConformiteDTO) {
        try{
            if(socialConformiteRepository.existsById(socialConformiteDTO.getId())) {
                var conformiteEnvir = socialConformiteMapper.asEntity(socialConformiteDTO);

                var updatedConformiteEnvir = socialConformiteMapper.asDto(socialConformiteRepository.save(conformiteEnvir));

                log.info("Conformite social modifié avec succès {} ", conformiteEnvir.getId());

                return updatedConformiteEnvir;
            } else {
                throw new ResourceNotFoundException("Conformite social Id ", socialConformiteDTO.getId());
            }
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("Conformite social Id ", socialConformiteDTO.getId());
        }
    }

    @Override
    @Journal(actionType = ActionType.READ_SOCIAL_CONFORMITE)
    public SocialConformiteDTO read(Long socialConformiteId) {
        var conformiteEnvir = socialConformiteRepository
                .findById(socialConformiteId)
                .orElseThrow(()-> new ResourceNotFoundException("Conformite social Id ", socialConformiteId));

        log.info("reading conformite social id {}", socialConformiteId);

        return socialConformiteMapper.asDto(conformiteEnvir);
    }

    @Override
    @Journal(actionType = ActionType.DELETE_SOCIAL_CONFORMITE)
    public void delete(Long socialConformiteId) {
        try {
            socialConformiteRepository.deleteById(socialConformiteId);
            log.info("Conformite social avec l'id {} a été supprimé", socialConformiteId);
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("social Conformite Id", socialConformiteId);
        }
    }

    @Override
    @Journal(actionType = ActionType.READ_SOCIAL_CONFORMITE)
    public Page<SocialConformiteDTO> readAll(
            Pageable pageable,
            String code,
            String libelle,
            String source,
            String etat,
            String startDate,
            String endDate,
            Long categorieId,
            Long typeReferenceId,
            Long projetId,
            String sortBy,
            Boolean ascending
    ) {
        return socialConformiteRepository
                .readAllByFiltering(pageable, code, libelle, source, etat, startDate, endDate, categorieId, typeReferenceId, projetId,sortBy,ascending)
                .map(socialConformiteMapper::asDto);
    }

}
