package com.webgram.dgpsn.services.Impl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.InstructionMapper;
import com.webgram.dgpsn.models.InstructionDTO;
import com.webgram.dgpsn.models.requests.UpdateEtapeDTO;
import com.webgram.dgpsn.repositories.InstructionRepository;
import com.webgram.dgpsn.services.InstructionService;
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class InstructionServiceImpl implements InstructionService {

    private final InstructionRepository instructionRepository;
    private final InstructionMapper instructionMapper;

    @Override
    public InstructionDTO create(InstructionDTO instructionDTO) {
        var entity = instructionMapper.asEntity(instructionDTO);
        var savedEntity = instructionRepository.save(entity);
        log.info("Instruction créée avec succès avec l'ID {}", savedEntity.getId());
        return instructionMapper.asDto(savedEntity);
    }

    @Override
    public InstructionDTO update(Long id, InstructionDTO instructionDTO) {
        var existingEntity = instructionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Instruction non trouvée avec l'ID: " + id));

        instructionDTO.setId(id);
        var entityToUpdate = instructionMapper.asEntity(instructionDTO);
        var updatedEntity = instructionRepository.save(entityToUpdate);
        log.info("Instruction mise à jour avec succès pour l'ID {}", updatedEntity.getId());
        return instructionMapper.asDto(updatedEntity);
    }

    @Override
    public void delete(Long id) {
        if (!instructionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Instruction non trouvée avec l'ID: " + id);
        }
        instructionRepository.deleteById(id);
        log.info("Instruction supprimée avec succès pour l'ID {}", id);
    }

    @Override
    public Page<InstructionDTO> readAll(Long promoteurId, Long regionId, String intitule, Pageable pageable) {
        return instructionRepository.findByCriteria(promoteurId, regionId, intitule, pageable)
                .map(instructionMapper::asDto);
    }

    @Override
    public InstructionDTO updateEtape(Long instructionId, UpdateEtapeDTO etapeDTO) {
        // 1. Rechercher l'entité existante
        var instruction = instructionRepository.findById(instructionId)
                .orElseThrow(() -> new ResourceNotFoundException("Instruction non trouvée avec l'ID: " + instructionId));

        // 2. Mettre à jour uniquement le champ concerné
        instruction.setNiveauInstruction(etapeDTO.getEtape());

        // 3. Sauvegarder l'entité mise à jour
        var updatedInstruction = instructionRepository.save(instruction);
        log.info("Niveau d'instruction mis à jour pour l'ID {} avec la nouvelle étape {}", updatedInstruction.getId(), updatedInstruction.getNiveauInstruction());

        // 4. Retourner le DTO de l'entité mise à jour
        return instructionMapper.asDto(updatedInstruction);
    }
}