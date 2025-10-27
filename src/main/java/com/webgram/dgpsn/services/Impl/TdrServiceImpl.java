package com.webgram.dgpsn.services.Impl;

import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.TdrMapper;
import com.webgram.dgpsn.models.TdrDTO;
import com.webgram.dgpsn.repositories.TdrRepository;
import com.webgram.dgpsn.services.TdrService;
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
public class TdrServiceImpl implements TdrService {

    private final TdrRepository tdrRepository;
    private final TdrMapper tdrMapper;

    @Override
    public TdrDTO create(TdrDTO tdrDTO) {
        var entity = tdrMapper.asEntity(tdrDTO);
        var savedEntity = tdrRepository.save(entity);
        log.info("TDR créé avec succès avec l'ID {}", savedEntity.getId());
        return tdrMapper.asDto(savedEntity);
    }

    @Override
    public TdrDTO update(Long id, TdrDTO tdrDTO) {
        var existingEntity = tdrRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("TDR non trouvé avec l'ID: " + id));

        tdrDTO.setId(id);
        var entityToUpdate = tdrMapper.asEntity(tdrDTO);
        var updatedEntity = tdrRepository.save(entityToUpdate);
        log.info("TDR mis à jour avec succès pour l'ID {}", updatedEntity.getId());
        return tdrMapper.asDto(updatedEntity);
    }

    @Override
    public void delete(Long id) {
        if (!tdrRepository.existsById(id)) {
            throw new ResourceNotFoundException("TDR non trouvé avec l'ID: " + id);
        }
        tdrRepository.deleteById(id);
        log.info("TDR supprimé avec succès pour l'ID {}", id);
    }

    @Override
    public Page<TdrDTO> readAll(Long instructionId, String intitule, Pageable pageable) {
        return tdrRepository.findByCriteria(instructionId, intitule, pageable)
                             .map(tdrMapper::asDto);
    }
}