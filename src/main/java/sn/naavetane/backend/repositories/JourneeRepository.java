package sn.naavetane.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.naavetane.backend.entities.JourneeEntity;

import java.util.UUID;

import java.util.List;
import java.time.LocalDate;

public interface JourneeRepository extends JpaRepository<JourneeEntity, UUID> {
    List<JourneeEntity> findByDate(LocalDate date);
    List<JourneeEntity> findByDateGreaterThanEqualOrderByDateAsc(LocalDate date);
}
