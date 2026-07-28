package sn.naavetane.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.naavetane.backend.entities.audits.AuditEntity;

import java.util.UUID;

public interface AuditRepository extends JpaRepository<AuditEntity, UUID> {
}
