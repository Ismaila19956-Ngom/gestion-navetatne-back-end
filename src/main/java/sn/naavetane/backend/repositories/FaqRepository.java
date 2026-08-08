package sn.naavetane.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.naavetane.backend.entities.FaqEntity;

import java.util.UUID;

@Repository
public interface FaqRepository extends JpaRepository<FaqEntity, UUID> {
}
