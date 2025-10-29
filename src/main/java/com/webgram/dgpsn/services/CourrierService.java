package com.webgram.dgpsn.services;

import com.webgram.dgpsn.entities.CourrierEntity;
import com.webgram.dgpsn.entities.enums.CourrierType;
import com.webgram.dgpsn.entities.enums.ReferentielType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CourrierService {

    CourrierEntity create(CourrierEntity courrier);

    CourrierEntity update(CourrierEntity courrier);

    void delete(Long id);

    Page<CourrierEntity> readAll(Pageable pageable, String keyword,
                                 CourrierType type, ReferentielType nature,
                                 ReferentielType statut);

    Optional<CourrierEntity> read(Long id);

    CourrierEntity archiver(Long id);

    CourrierEntity changerStatut(Long id, ReferentielType nouveauStatut);

    long countByTypeAndNatureAndStatut(CourrierType type, ReferentielType nature,
                                       ReferentielType statut);

    long countCourriersActifs(CourrierType type);

    Page<CourrierEntity> findByType(CourrierType type, Pageable pageable);

    Page<CourrierEntity> findByNature(ReferentielType nature, Pageable pageable);

    long countByNature(ReferentielType nature);
}