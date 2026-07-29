package sn.naavetane.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.naavetane.backend.entities.SaisonEntity;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

@Repository
public interface SaisonRepository extends JpaRepository<SaisonEntity, UUID> {
    Optional<SaisonEntity> findByIsActiveTrue();
    List<SaisonEntity> findAllByOrderByCreatedDateDesc();
}
