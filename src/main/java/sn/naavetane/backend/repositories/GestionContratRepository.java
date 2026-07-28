package sn.naavetane.backend.repositories;

import sn.naavetane.backend.entities.GestionContratEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GestionContratRepository extends JpaRepository<GestionContratEntity, Long> {

    /** 🔍 Trouver tous les contrats d’un agent */
    List<GestionContratEntity> findByAgent_Id(Long agentId);
    List<GestionContratEntity> findByAgent_IdAndIsActive(Long agentId, boolean isActive);

}
