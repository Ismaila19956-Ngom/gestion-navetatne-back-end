package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.entities.enums.CadreLogiqueType;
import com.webgram.dgpsn.models.CadreLogiqueDTO;
import com.webgram.dgpsn.models.responses.TreeNode;

import java.util.List;

public interface CadreLogiqueService {
    CadreLogiqueDTO createCadreLogique(CadreLogiqueDTO cadreLogiqueDTO);
    CadreLogiqueDTO readCadreLogique(Long id);
    CadreLogiqueDTO readCadreLogique(String libelle);
    CadreLogiqueDTO updateCadreLogique(CadreLogiqueDTO cadreLogiqueDTO);
    Page<CadreLogiqueDTO> readAllCadreLogique(Pageable pageable,String code, String libelle, CadreLogiqueType codeType, Long regionId, Long departementId, Long arrondissementId);
    void deleteCadreLogique(Long id);
    List<TreeNode> readTreeCadreLogique(Long projetId);

    List<CadreLogiqueDTO> readRegions(Pageable pageable, Long projectId);

    List<CadreLogiqueDTO> readByParent(Long cadreLogiqueId, Long projectId);
}
