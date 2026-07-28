package sn.naavetane.backend.services.Impl;

import com.querydsl.core.BooleanBuilder;
import sn.naavetane.backend.entities.QDirectionEntity;
import sn.naavetane.backend.exceptions.ResourceNotFoundException;
import sn.naavetane.backend.mappers.DirectionMapper;
import sn.naavetane.backend.models.DirectionDTO;
import sn.naavetane.backend.repositories.DirectionRepository;
import sn.naavetane.backend.services.DirectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class DirectionServiceImpl implements DirectionService {
    private final DirectionRepository directionRepository;
    private final DirectionMapper directionMapper;

    @Override
    public DirectionDTO create(DirectionDTO directionDTO) {
        var directionEntity = directionMapper.asEntity(directionDTO);

        directionRepository.findById(directionDTO.getParentId()).ifPresent(direc -> {
            var parent = Objects.nonNull(direc) ? direc : null;
            directionEntity.setParent(parent);
        });

        var direction = directionRepository.save(directionEntity);

        log.info("direction successfully added {}", direction.getId());

        return directionMapper.asDto(direction);
    }

    @Override
    public DirectionDTO update(DirectionDTO directionDTO) {
        var direction = directionMapper.asEntity(directionDTO);

        directionRepository.findById(directionDTO.getId()).ifPresent(direc -> {
            var parent = Objects.nonNull(direc.getParent()) ? direc.getParent() : null;
            direction.setParent(parent);
        });

        var updatedDirection = directionMapper.asDto(directionRepository.save(direction));

        log.info("direction successfully updated {} ", updatedDirection.getId());

        return updatedDirection;
    }

    @Override
    public DirectionDTO read(Long directionId) {
        var direction = directionRepository
                .findById(directionId)
                .orElseThrow(()-> new ResourceNotFoundException("Direction", directionId));

        log.info("reading direction id {}", directionId);

        return directionMapper.asDto(direction);
    }

    @Override
    public void delete(Long directionId) {
        try {
            directionRepository.deleteById(directionId);
            log.info("The direction id {} is deleted", directionId);
        } catch (IllegalArgumentException ex) {
            log.info("The given id to delete must not be null");
        }
    }

    @Override
    public Page<DirectionDTO> readAll(Map<String, String> searchParams, int page, int size) {
        var searchBuilder = buildSearch(searchParams);
        var directionPage = directionRepository
                .findAll(searchBuilder, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")));
        return directionPage.map(directionMapper::asDto);
    }

    @Override
    public Page<DirectionDTO> loadOrganigramme(int page, int size) {
        var params = new HashMap<String, String>();
        params.put("tree", "true");
        return readAll(params, page, size);
    }

    private BooleanBuilder buildSearch(Map<String, String> searchParams) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if(Objects.nonNull(searchParams)) {
            var qDirection = QDirectionEntity.directionEntity;
            if(searchParams.containsKey("id")) {
                booleanBuilder.and(qDirection.id.eq(Long.parseLong(searchParams.get("id"))));
            }
            if(searchParams.containsKey("code")) {
                booleanBuilder.and(qDirection.code.containsIgnoreCase(searchParams.get("code")));
            }
            if(searchParams.containsKey("libelle")) {
                booleanBuilder.and(qDirection.libelle.containsIgnoreCase(searchParams.get("libelle")));
            }
            if(searchParams.containsKey("tree") && Boolean.parseBoolean(searchParams.get("tree"))) {
                booleanBuilder.and(qDirection.parent.isNull());
            }
        }
        return booleanBuilder;
    }
}

