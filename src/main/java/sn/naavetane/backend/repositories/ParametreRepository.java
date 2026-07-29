package sn.naavetane.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.naavetane.backend.entities.ParametreEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ParametreRepository extends JpaRepository<ParametreEntity, UUID> {
    Optional<ParametreEntity> findByCle(String cle);
}
