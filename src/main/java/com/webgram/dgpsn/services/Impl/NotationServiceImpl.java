package com.webgram.dgpsn.services.Impl;

import com.webgram.dgpsn.entities.CandidatEntity;
import com.webgram.dgpsn.entities.NotationEntity;
import com.webgram.dgpsn.mappers.NotationMapper;
import com.webgram.dgpsn.models.NotationDTO;
import com.webgram.dgpsn.repositories.CandidatRepository;
import com.webgram.dgpsn.repositories.NotationRepository;
import com.webgram.dgpsn.services.NotationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotationServiceImpl implements NotationService {

    private final NotationRepository notationRepository;
    private final CandidatRepository candidatRepository;
    private final NotationMapper mapper;

    @Override
    public NotationDTO createNotation(NotationDTO dto) {
        // Vérifie que le candidat existe
        CandidatEntity candidat = candidatRepository.findById(dto.getCandidatId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Candidat non trouvé avec ID: " + dto.getCandidatId()));

        // Convertit le DTO en Entity et lie le candidat
        NotationEntity entity = mapper.toEntity(dto);
        entity.setCandidat(candidat);

        // Sauvegarde et retourne le DTO mis à jour
        NotationEntity saved = notationRepository.save(entity);
        return mapper.toDto(saved);
    }

    @Override
    public NotationDTO updateNotation(Long id, NotationDTO dto) {
        NotationEntity existing = notationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Notation non trouvée avec ID: " + id));

        existing.setCritere(dto.getCritere());
        existing.setValeur(dto.getValeur());

        NotationEntity updated = notationRepository.save(existing);
        return mapper.toDto(updated);
    }

    @Override
    public void deleteNotation(Long id) {
        if (!notationRepository.existsById(id)) {
            throw new EntityNotFoundException("Notation non trouvée avec ID: " + id);
        }
        notationRepository.deleteById(id);
    }

    @Override
    public NotationDTO getNotationById(Long id) {
        NotationEntity entity = notationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Notation non trouvée avec ID: " + id));
        return mapper.toDto(entity);
    }

    @Override
    public List<NotationDTO> getNotationsByCandidat(Long candidatId) {
        List<NotationEntity> list = notationRepository.findByCandidatId(candidatId);
        // Assure que toutes les notations sont mappées correctement
        return mapper.toDtoList(list);
    }

    @Override
    public List<NotationDTO> getAllNotations() {
        return mapper.toDtoList(notationRepository.findAll());
    }
}
