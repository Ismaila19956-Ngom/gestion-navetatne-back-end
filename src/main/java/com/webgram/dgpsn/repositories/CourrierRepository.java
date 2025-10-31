package com.webgram.dgpsn.repositories;

import com.webgram.dgpsn.entities.CourrierEntity;
import com.webgram.dgpsn.entities.enums.CourrierType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CourrierRepository extends JpaRepository<CourrierEntity, Long>,
        JpaSpecificationExecutor<CourrierEntity> {

    Optional<CourrierEntity> findByReference(String reference);

    // Filtre par TYPE seulement
    Page<CourrierEntity> findByType(CourrierType type, Pageable pageable);

    // Recherche par plage de dates
    List<CourrierEntity> findByDateCourrierBetween(LocalDateTime start, LocalDateTime end);
}