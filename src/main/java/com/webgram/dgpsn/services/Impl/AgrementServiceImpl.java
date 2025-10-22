package com.webgram.dgpsn.services.Impl;

import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.AgrementMapper;
import com.webgram.dgpsn.models.AgrementDTO;
import com.webgram.dgpsn.repositories.AgrementRepository;
import com.webgram.dgpsn.services.AgrementService;
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
public class AgrementServiceImpl implements AgrementService {

    private final AgrementRepository agrementRepository;
    private final AgrementMapper agrementMapper;

    @Override
    public AgrementDTO create(AgrementDTO agrementDTO) {
        var entity = agrementMapper.asEntity(agrementDTO);
        var savedEntity = agrementRepository.save(entity);
        log.info("Agrément créé avec succès avec l'ID {}", savedEntity.getId());
        return agrementMapper.asDto(savedEntity);
    }

    @Override
    public AgrementDTO update(Long id, AgrementDTO agrementDTO) {
        agrementRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Agrément non trouvé avec l'ID: " + id));

        agrementDTO.setId(id);
        var entityToUpdate = agrementMapper.asEntity(agrementDTO);
        var updatedEntity = agrementRepository.save(entityToUpdate);
        log.info("Agrément mis à jour avec succès pour l'ID {}", updatedEntity.getId());
        return agrementMapper.asDto(updatedEntity);
    }

    @Override
    public void delete(Long id) {
        if (!agrementRepository.existsById(id)) {
            throw new ResourceNotFoundException("Agrément non trouvé avec l'ID: " + id);
        }
        agrementRepository.deleteById(id);
        log.info("Agrément supprimé avec succès pour l'ID {}", id);
    }

    @Override
    public Page<AgrementDTO> readAll(Long promoteurId, String objet, Pageable pageable) {
        return agrementRepository.findByCriteria(promoteurId, objet, pageable)
                                  .map(agrementMapper::asDto);
    }
}