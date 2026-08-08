package sn.naavetane.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.naavetane.backend.entities.StructureOrganisationEntity;

import java.util.UUID;

@Repository
public interface StructureOrganisationRepository extends JpaRepository<StructureOrganisationEntity, UUID> {
}
