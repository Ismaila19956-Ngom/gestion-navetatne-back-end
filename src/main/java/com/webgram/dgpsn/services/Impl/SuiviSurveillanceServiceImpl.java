package com.webgram.dgpsn.services.Impl;

import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.SuiviSurveillanceMapper;
import com.webgram.dgpsn.models.SuiviSurveillanceDTO;
import com.webgram.dgpsn.repositories.SuiviSurveillanceRepository;
import com.webgram.dgpsn.services.SuiviSurveillanceService;
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
public class SuiviSurveillanceServiceImpl implements SuiviSurveillanceService {

    private final SuiviSurveillanceRepository suiviSurveillanceRepository;
    private final SuiviSurveillanceMapper suiviSurveillanceMapper;

    @Override
    public SuiviSurveillanceDTO create(SuiviSurveillanceDTO suiviSurveillanceDTO) {
        var entity = suiviSurveillanceMapper.asEntity(suiviSurveillanceDTO);
        var savedEntity = suiviSurveillanceRepository.save(entity);
        log.info("Suivi/Surveillance créé avec succès avec l'ID {}", savedEntity.getId());
        return suiviSurveillanceMapper.asDto(savedEntity);
    }

    @Override
    public SuiviSurveillanceDTO update(Long id, SuiviSurveillanceDTO suiviSurveillanceDTO) {
        suiviSurveillanceRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Suivi/Surveillance non trouvé avec l'ID: " + id));

        suiviSurveillanceDTO.setId(id);
        var entityToUpdate = suiviSurveillanceMapper.asEntity(suiviSurveillanceDTO);
        var updatedEntity = suiviSurveillanceRepository.save(entityToUpdate);
        log.info("Suivi/Surveillance mis à jour avec succès pour l'ID {}", updatedEntity.getId());
        return suiviSurveillanceMapper.asDto(updatedEntity);
    }

    @Override
    public void delete(Long id) {
        if (!suiviSurveillanceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Suivi/Surveillance non trouvé avec l'ID: " + id);
        }
        suiviSurveillanceRepository.deleteById(id);
        log.info("Suivi/Surveillance supprimé avec succès pour l'ID {}", id);
    }

    @Override
    public Page<SuiviSurveillanceDTO> readAll(Long promoteurId, String intitule, Pageable pageable) {
        return suiviSurveillanceRepository.findByCriteria(promoteurId, intitule, pageable)
                                           .map(suiviSurveillanceMapper::asDto);
    }
}