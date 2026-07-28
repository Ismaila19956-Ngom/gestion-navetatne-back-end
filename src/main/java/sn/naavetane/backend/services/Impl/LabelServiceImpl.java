package sn.naavetane.backend.services.Impl;

import sn.naavetane.backend.entities.enums.ReferentielType;
import sn.naavetane.backend.exceptions.ResourceNotFoundException;
import sn.naavetane.backend.mappers.LabelMapper;
import sn.naavetane.backend.models.LabelDTO;
import sn.naavetane.backend.repositories.LabelRepository;
import sn.naavetane.backend.services.LabelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class LabelServiceImpl implements LabelService {
    private final LabelRepository labelRepository;
    private final LabelMapper labelMapper;

    @Override
    public LabelDTO create(LabelDTO labelDTO) {
        var label = labelRepository.save(labelMapper.asEntity(labelDTO));

        log.info("label successfully added {}", label.getId());

        return labelMapper.asDto(label);
    }

    @Override
    public LabelDTO update(LabelDTO labelDTO) {
        var label = labelMapper.asEntity(labelDTO);

        var updatedLabel = labelMapper.asDto(labelRepository.save(label));

        log.info("label successfully updated {} ", updatedLabel.getId());

        return updatedLabel;
    }

    @Override
    public LabelDTO read(Long labelId) {
        var label = labelRepository
                .findById(labelId)
                .orElseThrow(()-> new ResourceNotFoundException("Label", labelId));

        log.info("reading label id {}", labelId);

        return labelMapper.asDto(label);
    }

    @Override
    public void delete(Long labelId) {
        try {
            labelRepository.deleteById(labelId);
            log.info("The label id {} is deleted", labelId);
        } catch (IllegalArgumentException ex) {
            log.info("The given id to delete must not be null");
        }
    }

    @Override
    public Page<LabelDTO> readAll(Pageable pageable, ReferentielType referentielType, String label) {
        return labelRepository
                .readByFiltering(pageable, referentielType, label)
                .map(labelMapper::asDto);
    }

    @Override
    public Page<ReferentielType> readAllReferentielType(Pageable pageable, String label, String description) {
        // Récupérer toutes les valeurs de l'énumération
        List<ReferentielType> referentielTypes = Arrays.asList(ReferentielType.values());

        // Appliquer les filtres
        List<ReferentielType> filteredList = referentielTypes.stream()
                .filter(ref -> label == null || ref.getLabel().toLowerCase().contains(label.toLowerCase()))
                .filter(ref -> description == null || ref.getDescription().toLowerCase().contains(description.toLowerCase()))
                .collect(Collectors.toList());

        // Calculer les indices pour la pagination
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), filteredList.size());

        // Créer une sous-liste pour la page demandée
        List<ReferentielType> pagedList = filteredList.subList(
                Math.min(start, filteredList.size()),
                end
        );

        // Retourner une page
        return new PageImpl<>(pagedList, pageable, filteredList.size());
    }
}
