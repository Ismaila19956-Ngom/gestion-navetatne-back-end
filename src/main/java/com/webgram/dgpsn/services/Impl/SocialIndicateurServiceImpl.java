package com.webgram.dgpsn.services.Impl;

import com.webgram.dgpsn.annotations.Journal;
import com.webgram.dgpsn.entities.SocialIndicateurEntity;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.SocialIndicateurMapper;
import com.webgram.dgpsn.models.SocialIndicateurDTO;
import com.webgram.dgpsn.repositories.SocialIndicateurRepository;
import com.webgram.dgpsn.services.SocialIndicateurService;
import com.webgram.dgpsn.tools.ActionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class SocialIndicateurServiceImpl implements SocialIndicateurService {
    private final SocialIndicateurRepository socialIndicateurRepository;
    private final SocialIndicateurMapper socialIndicateurMapper;

    @Override
    @Journal(actionType = ActionType.ADD_SOCIAL_INDICATEUR)
    public SocialIndicateurDTO create(SocialIndicateurDTO socialIndicateurDTO) {
         SocialIndicateurEntity savedEnvIndicateur = socialIndicateurRepository.save(socialIndicateurMapper.asEntity(socialIndicateurDTO));

        log.info("Indicateur social ajouté avec succès {}", savedEnvIndicateur);

        return socialIndicateurMapper.asDto(savedEnvIndicateur);
    }

    @Override
    @Journal(actionType = ActionType.UPDATE_SOCIAL_INDICATEUR)
    public SocialIndicateurDTO update(SocialIndicateurDTO socialIndicateurDTO) {
        try{
            if(socialIndicateurRepository.existsById(socialIndicateurDTO.getId())) {
                var indicateurEnvir = socialIndicateurMapper.asEntity(socialIndicateurDTO);

                var updatedIndicateurEnvir = socialIndicateurMapper.asDto(socialIndicateurRepository.save(indicateurEnvir));

                log.info("Indicateur social modifié avec succès {} ", indicateurEnvir.getId());

                return updatedIndicateurEnvir;
            } else {
                throw new ResourceNotFoundException("Indicateur social Id ", socialIndicateurDTO.getId());
            }
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("Indicateur social Id ", socialIndicateurDTO.getId());
        }
    }

    @Override
    @Journal(actionType = ActionType.READ_SOCIAL_INDICATEUR)
    public SocialIndicateurDTO read(Long socialIndicateurId) {
        var indicateurEnvir = socialIndicateurRepository
                .findById(socialIndicateurId)
                .orElseThrow(()-> new ResourceNotFoundException("Indicateur social Id ", socialIndicateurId));

        log.info("reading indicateur social id {}", socialIndicateurId);

        return socialIndicateurMapper.asDto(indicateurEnvir);
    }

    @Override
    @Journal(actionType = ActionType.DELETE_SOCIAL_INDICATEUR)
    public void delete(Long socialIndicateurId) {
        try {
            socialIndicateurRepository.deleteById(socialIndicateurId);
            log.info("Indicateur social avec l'id {} a été supprimé", socialIndicateurId);
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("social Indicateur Id", socialIndicateurId);
        }
    }

    @Override
    @Journal(actionType = ActionType.READ_SOCIAL_INDICATEUR)
    public Page<SocialIndicateurDTO> readAll(
            Pageable pageable,
            String code,
            String libelle,
            String valeur,
            String source,
            String startDate,
            String endDate,
            Long indicateurId,
            Long projetId,
            String sortBy,
            Boolean ascending
    ) {
        return socialIndicateurRepository
                .readAllByFiltering(pageable, code, libelle, valeur, source, startDate, endDate, indicateurId, projetId,sortBy,ascending)
                .map(socialIndicateurMapper::asDto);
    }

}
