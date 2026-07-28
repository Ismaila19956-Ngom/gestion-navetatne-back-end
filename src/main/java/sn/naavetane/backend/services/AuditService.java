package sn.naavetane.backend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sn.naavetane.backend.dto.AuditDTO;
import sn.naavetane.backend.entities.audits.AuditEntity;
import sn.naavetane.backend.repositories.AuditRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuditService {

    private final AuditRepository auditRepository;

    public void logAction(String acteur, String action, String ressource, String details, String adresseIp) {
        AuditEntity audit = AuditEntity.builder()
                .acteur(acteur)
                .action(action)
                .ressource(ressource)
                .details(details)
                .adresseIp(adresseIp)
                .build();
        auditRepository.save(audit);
    }

    public List<AuditDTO> getAllAudits() {
        return auditRepository.findAll(Sort.by(Sort.Direction.DESC, "createdDate"))
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private AuditDTO mapToDto(AuditEntity entity) {
        return AuditDTO.builder()
                .id(entity.getId())
                .acteur(entity.getActeur())
                .action(entity.getAction())
                .ressource(entity.getRessource())
                .details(entity.getDetails())
                .adresseIp(entity.getAdresseIp())
                .createdDate(entity.getCreatedDate())
                .build();
    }
}
