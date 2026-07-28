package sn.naavetane.backend.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.naavetane.backend.models.RoleDTO;

import java.util.Set;


public interface RoleService {
    RoleDTO createRole(RoleDTO roleDTO);
    RoleDTO updateRole(RoleDTO roleDTO);
    RoleDTO readRole(Long id);
    RoleDTO readRole(String code);
    void deleteRole(Long id);
    Page<RoleDTO> readAllRole(Pageable pageable, String code, String libelle, Boolean ugp, String sortBy,
                              Boolean ascending);

}
