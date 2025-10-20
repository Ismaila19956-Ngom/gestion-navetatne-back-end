package com.webgram.dgpsn.services.Impl;

import com.webgram.dgpsn.tools.ActionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.webgram.dgpsn.annotations.Journal;
import com.webgram.dgpsn.entities.SocialImpactEntity;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.SocialImpactMapper;
import com.webgram.dgpsn.models.SocialImpactDTO;
import com.webgram.dgpsn.repositories.SocialImpactRepository;
import com.webgram.dgpsn.services.SocialImpactService;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class SocialImpactServiceImpl implements SocialImpactService {
    private final SocialImpactRepository socialImpactRepository;
    private final SocialImpactMapper socialImpactMapper;

    @Override
    @Journal(actionType = ActionType.ADD_SOCIAL_IMPACT)
    public SocialImpactDTO create(SocialImpactDTO socialImpactDTO) {
         SocialImpactEntity savedEnvImpact = socialImpactRepository.save(socialImpactMapper.asEntity(socialImpactDTO));

        log.info("Impact environnemental ajouté avec succès {}", savedEnvImpact);

        return socialImpactMapper.asDto(savedEnvImpact);
    }

    @Override
    @Journal(actionType = ActionType.UPDATE_SOCIAL_IMPACT)
    public SocialImpactDTO update(SocialImpactDTO socialImpactDTO) {
        try{
            if(socialImpactRepository.existsById(socialImpactDTO.getId())) {
                var impactSocial = socialImpactMapper.asEntity(socialImpactDTO);

                var updatedImpactSocial = socialImpactMapper.asDto(socialImpactRepository.save(impactSocial));

                log.info("Impact environnemental modifié avec succès {} ", impactSocial.getId());

                return updatedImpactSocial;
            } else {
                throw new ResourceNotFoundException("Impact environnemental Id ", socialImpactDTO.getId());
            }
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("Impact environnemental Id ", socialImpactDTO.getId());
        }
    }

    @Override
    @Journal(actionType = ActionType.READ_SOCIAL_IMPACT)
    public SocialImpactDTO read(Long socialImpactId) {
        var impactSocial = socialImpactRepository
                .findById(socialImpactId)
                .orElseThrow(()-> new ResourceNotFoundException("Impact environnemental Id ", socialImpactId));

        log.info("reading impact environnemental id {}", socialImpactId);

        return socialImpactMapper.asDto(impactSocial);
    }

    @Override
    @Journal(actionType = ActionType.DELETE_SOCIAL_IMPACT)
    public void delete(Long socialImpactId) {
        try {
            socialImpactRepository.deleteById(socialImpactId);
            log.info("Impact environnemental avec l'id {} a été supprimé", socialImpactId);
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("social Impact Id", socialImpactId);
        }
    }

    @Override
    @Journal(actionType = ActionType.READ_SOCIAL_IMPACT)
    public Page<SocialImpactDTO> readAll(
            Pageable pageable,
            String code,
            String libelle,
            Number nbPersonnesAffectees,
            Number nbMenagesAffectees,
            String source,
            String natureImpact,
            String importanceImpact,
            String startDate,
            String endDate,
            Long categorieId,
            Long typeImpactId,
            Long projetId,
            String sortBy,
            Boolean ascending
    ) {
        return socialImpactRepository
                .readAllByFiltering(pageable, code, libelle, nbPersonnesAffectees, nbMenagesAffectees, source, natureImpact, importanceImpact, startDate, endDate,categorieId,typeImpactId,projetId,sortBy,ascending)
                .map(socialImpactMapper::asDto);
    }

}
