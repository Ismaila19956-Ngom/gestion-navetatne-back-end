package sn.naavetane.backend.services.Impl;

import sn.naavetane.backend.exceptions.ResourceNotFoundException;
import sn.naavetane.backend.mappers.RoleMapper;
import sn.naavetane.backend.models.RoleDTO;
import sn.naavetane.backend.repositories.RoleRepository;    
import sn.naavetane.backend.services.RoleService;
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
                .readAllByFilters(pageable, code, libelle, ugp)
                .map(roleMapper::asDto);
        log.trace("list role ok {}", roles);
        return roles;
    }

}

