package com.webgram.dgpsn.services.Impl;

import com.webgram.dgpsn.entities.ManagementUnitEntity;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.RoleMapper;
import com.webgram.dgpsn.models.RoleDTO;
import com.webgram.dgpsn.repositories.RoleRepository;
import com.webgram.dgpsn.repositories.UgpProjetRepository;
import com.webgram.dgpsn.services.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    private final UgpProjetRepository ugpProjetRepository;

    String ROLE_IDENTIFIER_NOT_FOUND_MESSAGE = "Invalide id role {0}";

    @Override
    public RoleDTO createRole(RoleDTO roleDTO) {
        var createdRole = roleRepository.save(roleMapper.asEntity(roleDTO));
        log.info("createdRole end ok - createdRoleId: {}", createdRole.getId());
        log.trace("createdRole end ok - createdRole: {}", createdRole);
        return roleMapper.asDto(createdRole);
    }

    @Override
    public RoleDTO updateRole(RoleDTO roleDTO) {
        if(!roleRepository.existsById(roleDTO.getId())){
            throw new ResourceNotFoundException(MessageFormat.format(ROLE_IDENTIFIER_NOT_FOUND_MESSAGE, roleDTO.getId()));
        }
        var updatedRole = roleRepository.save(roleMapper.asEntity(roleDTO));
        log.info("updatedRole ok id {}", updatedRole.getId());
        log.trace("updatedRole ok  {}", updatedRole);
        return roleMapper.asDto(updatedRole);
    }

    @Override
    public RoleDTO readRole(Long id) {
        var role = roleRepository.findById(id)
                .map(roleMapper::asDto)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(ROLE_IDENTIFIER_NOT_FOUND_MESSAGE, id)));
        log.info("read role end ok - Id: {}", id);
        log.trace("read role end ok - role: {}", role);
        return role;
    }

    @Override
    public RoleDTO readRole(String code) {
        var role = roleRepository.findByCode(code)
                .map(roleMapper::asDto)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(ROLE_IDENTIFIER_NOT_FOUND_MESSAGE, code)));
        log.info("read role end ok - Code: {}", code);
        log.trace("read role end ok - role: {}", role);
        return role;
    }

    @Override
    public void deleteRole(Long id) {
        if(!roleRepository.existsById(id)){
            throw new ResourceNotFoundException(MessageFormat.format(ROLE_IDENTIFIER_NOT_FOUND_MESSAGE, id));
        }
        roleRepository.deleteById(id);
        log.info("delete role ok id {}", id);
    }

    @Override
    public Page<RoleDTO> readAllRole(Pageable pageable, String code, String libelle, Boolean ugp, String sortBy,
                                     Boolean ascending) {
        var roles = roleRepository
                .readAllByFilter(pageable, code, libelle, ugp,sortBy,ascending)
                .map(roleMapper::asDto);
        log.trace("list role ok {}", roles);
        return roles;
    }

    @Override
    public Set<RoleDTO> readAllRoleUgp(Long projectId) {
        log.info("projectId {} ", projectId);
        var addedRoles = ugpProjetRepository.findByProjet(ManagementUnitEntity.builder().id(projectId).build())
                .stream()
                .map(udpProjetEntity -> udpProjetEntity.getUgpRole())
                .collect(Collectors.toList());
        log.info("addedRoles {} ", addedRoles.size());

        return roleRepository.findByUgpTrue()
                .stream()
//                .filter(role -> (addedRoles.stream().noneMatch(addedRole -> role.equals(addedRole))))
                .map(roleMapper::asDto)
                .collect(Collectors.toSet());

    }


}
