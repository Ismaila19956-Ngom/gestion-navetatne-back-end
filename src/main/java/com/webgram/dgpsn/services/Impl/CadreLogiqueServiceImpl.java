package com.webgram.dgpsn.services.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.webgram.dgpsn.entities.enums.CadreLogiqueType;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.CadreLogiqueMapper;
import com.webgram.dgpsn.models.responses.TreeNode;
import com.webgram.dgpsn.repositories.CadreLogiqueRepository;
import com.webgram.dgpsn.models.CadreLogiqueDTO;
import com.webgram.dgpsn.repositories.GeographicalLocationRepository;
import com.webgram.dgpsn.services.CadreLogiqueService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class CadreLogiqueServiceImpl implements CadreLogiqueService {
    private final CadreLogiqueRepository cadreLogiqueRepository;
    private final CadreLogiqueMapper cadreLogiqueMapper;
    private final GeographicalLocationRepository geographicalLocationRepository;
    private static final String ENTITY_EXCEPTION = "CadreLogique {0}";



    @Override
    public CadreLogiqueDTO createCadreLogique(CadreLogiqueDTO cadreLogiqueDTO) {
        var cadreLoqique = cadreLogiqueMapper.asEntity(cadreLogiqueDTO);
        var savedCadreLogique = cadreLogiqueRepository.save(cadreLoqique);
        log.info("create cadre logique id {}", savedCadreLogique.getId());
        log.trace("created cadre logique {}", savedCadreLogique);
        return cadreLogiqueMapper.asDto(savedCadreLogique);
    }

    @Override
    public CadreLogiqueDTO readCadreLogique(Long id) {
        var cadreLogique = cadreLogiqueRepository
                .findById(id)
                .map(cadreLogiqueMapper::asDto)
                .orElseThrow(()-> new ResourceNotFoundException(ENTITY_EXCEPTION, id));
        log.info("read cadre logique id {}", id);
        log.trace("read cadre logique {}", cadreLogique);
        return cadreLogique;
    }

    @Override
    public CadreLogiqueDTO readCadreLogique(String libelle) {
        var cadreLogique = cadreLogiqueRepository
                .findByLibelle(libelle)
                .map(cadreLogiqueMapper::asDto)
                .orElseThrow(()-> new ResourceNotFoundException(ENTITY_EXCEPTION, libelle));
        log.info("read cadre logique libelle {}", libelle);
        log.trace("read cadre logique {}", cadreLogique);
        return cadreLogique;
    }

    @Override
    public CadreLogiqueDTO updateCadreLogique(CadreLogiqueDTO cadreLogiqueDTO) {
        //var cadreLogique = readCadreLogique(cadreLogiqueDTO.getId());
        var updateCadreLogique = cadreLogiqueRepository
                .save( cadreLogiqueMapper.asEntity(cadreLogiqueDTO));
        log.info("update cadre logique id {}", updateCadreLogique.getId());
        log.trace("update cadre logique {}", updateCadreLogique);
        return cadreLogiqueMapper.asDto(updateCadreLogique);
    }

    @Override
    public Page<CadreLogiqueDTO> readAllCadreLogique(Pageable pageable,String code, String libelle, CadreLogiqueType codeType, Long regionId, Long departementId, Long arrondissementId) {
        var allCadreLogique = cadreLogiqueRepository
                .readAllByFiltering(pageable,code, libelle, codeType, regionId,departementId, arrondissementId)
                .map(cadreLogiqueMapper::asDto);
        log.trace("rad all type cadre logique {}", allCadreLogique);
        return allCadreLogique;
    }


    @Override
    public void deleteCadreLogique(Long id) {
        cadreLogiqueMapper.asEntity(readCadreLogique(id));
        log.info("delete phase ok id {}", id);
        cadreLogiqueRepository.deleteById(id);
    }

    @Override
    public List<TreeNode> readTreeCadreLogique(Long projectId) {
        var pageable = PageRequest.of(0,100000, Sort.by("libelle").ascending());
            List<TreeNode> nodeRegions = new ArrayList<>();
                var regions = cadreLogiqueRepository.getRegions(pageable, CadreLogiqueType.REGION).map(cadreLogiqueMapper::asDto).getContent();
                regions.forEach(aRegion -> {
                    var nodeRegion = TreeNode.builder().data(aRegion).build();
                    List<TreeNode> nodeDepartements = new ArrayList<>();
                    if(Objects.nonNull(cadreLogiqueRepository.getByParent(pageable, aRegion.getId(), projectId))) {
                        var departements = cadreLogiqueRepository.getByParent(pageable, aRegion.getId(), projectId).map(cadreLogiqueMapper::asDto).getContent();
                        departements.forEach(aDepartement -> {
                            var nodeDepartement = TreeNode.builder().data(aDepartement).build();
                            List<TreeNode> nodeArrondissements = new ArrayList<>();
                            if(Objects.nonNull(cadreLogiqueRepository.getByParent(pageable, aDepartement.getId(), projectId))){
                                var arrondissements = cadreLogiqueRepository.getByParent(pageable, aDepartement.getId(), projectId).map(cadreLogiqueMapper::asDto).getContent();
                                arrondissements.forEach(aArrondissement -> {
                                    var nodeArrondissement = TreeNode.builder().data(aArrondissement).build();
                                    List<TreeNode> nodeCommunes = new ArrayList<>();
                                    if(Objects.nonNull(cadreLogiqueRepository.getByParent(pageable, aArrondissement.getId(), projectId))){
                                        var communes = cadreLogiqueRepository.getByParent(pageable, aArrondissement.getId(), projectId).map(cadreLogiqueMapper::asDto).getContent();
                                        communes.forEach(aCommune -> {
                                            nodeCommunes.add(TreeNode.builder().data(aCommune).build());
                                        });
                                    }
                                    nodeArrondissement.setChildren(nodeCommunes);
                                    nodeArrondissements.add(nodeArrondissement);

                                });
                            }
                            nodeDepartement.setChildren(nodeArrondissements);
                            nodeDepartements.add(nodeDepartement);

                        });
                    }
                    nodeRegion.setChildren(nodeDepartements);
                    nodeRegions.add(nodeRegion);

                });
          //  }
       //     nodeParent.setChildren(nodeRegions);
       //     nodes.add(nodeParent);

      //  });
        return nodeRegions;
    }

    @Override
    public List<CadreLogiqueDTO> readRegions(Pageable pageable, Long projectId) {
        var pageable1 = PageRequest.of(0,10000, Sort.by("libelle").ascending());
        var pageable2 = PageRequest.of(0,10000);
        var regions = cadreLogiqueRepository
                .getRegions(pageable, CadreLogiqueType.REGION)
                .getContent()
                .stream()
                .map(cadreLogiqueMapper::asDto)
                .collect(Collectors.toList());
        log.trace("rad all type cadre logique {}", regions);
        var alladdeds = geographicalLocationRepository.readAllByFilters(pageable2, null, null, null, projectId)
                .stream().map(localisation -> localisation.getCadreLogique())
                .collect(Collectors.toList());
        log.info("rad all added cadre logique {}", alladdeds);

        return regions.stream()
                .filter(cadre -> (alladdeds.stream().noneMatch(addedCadre -> cadre.getId().equals(addedCadre.getId()))))
                .collect(Collectors.toList());
    }

    @Override
    public List<CadreLogiqueDTO> readByParent(Long cadreLogiqueId,Long projectId) {
        var pageable = PageRequest.of(0,100000, Sort.by("libelle").ascending());
        var pageable2 = PageRequest.of(0,100000);
        var alladdeds = geographicalLocationRepository.readAllByFilters(pageable2, null, null, null, projectId)
                .stream().map(localisation -> localisation.getCadreLogique())
                .collect(Collectors.toList());
        log.info("projectId {}", projectId);
        var cadreLogiques = cadreLogiqueRepository
                .getByParent(pageable, cadreLogiqueId, null)
                .stream()
                .filter(cadre -> (alladdeds.stream().noneMatch(addedCadre -> cadre.getId().equals(addedCadre.getId()))))
                .map(cadreLogiqueMapper::asDto)
                .collect(Collectors.toList());
        log.info("rad all type cadre logique {}", cadreLogiques);

        return cadreLogiques;
    }

}
