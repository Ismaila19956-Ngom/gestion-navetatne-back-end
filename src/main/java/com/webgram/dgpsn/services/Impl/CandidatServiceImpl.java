package com.webgram.dgpsn.services.Impl;

import com.webgram.dgpsn.entities.enums.StatusCadidature;
import com.webgram.dgpsn.services.CandidatService;

import com.webgram.dgpsn.entities.CandidatEntity;
import com.webgram.dgpsn.entities.enums.ExperienceProfessionnelle;
import com.webgram.dgpsn.entities.enums.NiveauEtude;
import com.webgram.dgpsn.mappers.CandidatMapper;
import com.webgram.dgpsn.models.CandidatDTO;
import com.webgram.dgpsn.repositories.CandidatRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor

public class CandidatServiceImpl implements CandidatService {

    private final CandidatRepository candidatRepository;

    @Override
    public Page<CandidatDTO> getCandidatsFiltered(
            Pageable pageable,
            List<Long> idsToIgnore,
            String nom,
            String prenom,
            String adresse,
            NiveauEtude niveauEtude,
            ExperienceProfessionnelle experience,
            StatusCadidature statusCadidature,
            String sortBy,
            Boolean ascending
    ) {
        Page<CandidatEntity> pageEntities = candidatRepository.readAllByFiltering(
                pageable,
                idsToIgnore,
                nom,
                prenom,
                adresse,
                niveauEtude,
                experience,
                statusCadidature,
                sortBy,
                ascending
        );

        return pageEntities.map(CandidatMapper::toDTO);
    }

    @Override
    public CandidatDTO getCandidatByMatricule(String matricule) {
        CandidatEntity entity = candidatRepository.findByMatricule(matricule)
                .orElseThrow(() -> new RuntimeException("Candidat non trouvé : " + matricule));
        return CandidatMapper.toDTO(entity);
    }


    @Override
    public CandidatDTO saveCandidat(CandidatDTO dto) {
        if(dto.getStatusCandidature() == null){
            dto.setStatusCandidature(StatusCadidature.IN_PROGRESS);
        }
        CandidatEntity entity = CandidatMapper.toEntity(dto);
        entity.setMatricule(generateMatricule());

        CandidatEntity saved = candidatRepository.save(entity);
        return CandidatMapper.toDTO(saved);
    }

    @Override
    public CandidatDTO updateCandidat(Long id, CandidatDTO dto) {
        if (id == null) {
            throw new IllegalArgumentException("L'ID dans l'URL est requis.");
        }
        if (!id.equals(dto.getId())) {
            throw new IllegalArgumentException(
                    String.format("L'ID dans l'URL (%d) ne correspond pas à l'ID dans le corps (%d).", id, dto.getId())
            );
        }
        CandidatEntity existing = candidatRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Candidat non trouvé avec l'ID : " + id));

        BeanUtils.copyProperties(dto, existing, getNullPropertyNames(dto));
        CandidatEntity saved = candidatRepository.save(existing);
        return CandidatMapper.toDTO(saved);
    }
    @Override
    public void deleteCandidat(Long id) {
        candidatRepository.deleteById(id);
    }

    @Override
    public CandidatDTO getCandidatById(Long id) {
        CandidatEntity entity = candidatRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Candidat non trouvé : " + id));
        return CandidatMapper.toDTO(entity);
    }

    private String[] getNullPropertyNames(Object source) {
        BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (PropertyDescriptor pd : pds) {
            if (pd.getName().equals("class")) continue;

            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        // TODO: Protect id and matricule
        emptyNames.add("id");
        emptyNames.add("matricule");
        return emptyNames.toArray(new String[0]);
    }

    private String generateMatricule() {
        return "CAND-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
