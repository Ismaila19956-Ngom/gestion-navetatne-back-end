package sn.naavetane.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.naavetane.backend.entities.TypeActualiteEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TypeActualiteRepository extends JpaRepository<TypeActualiteEntity, UUID> {
    Optional<TypeActualiteEntity> findByCode(String code);
}
