package com.webgram.dgpsn.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.webgram.dgpsn.entities.CongeEntity;
import com.webgram.dgpsn.entities.enums.TypeConge;
import java.util.Date;
import java.util.List;

@Repository
public interface CongeRepository extends JpaRepository<CongeEntity, Long>, QuerydslPredicateExecutor<CongeEntity> {

    @Modifying
    @Query(value = "DELETE FROM cong_document WHERE document_id = :documentId", nativeQuery = true)
    void unlinkOrdreMission(@Param("documentId") Long documentId);

//    @Query("SELECT c.numFicheConge FROM CongeEntity c ORDER BY c.numFicheConge DESC")
//    List<String> findLastNumFicheConge();

    boolean existsByAgentIdAndDateDepartBeforeAndDateRepriseAfterAndTypeConge(Long agentId, Date dateDepart, Date dateReprise, TypeConge typeConge);

//    @Query("SELECT a.echelon.grade.corps.libelle FROM CongeEntity c JOIN c.agent a WHERE c.dateDebut = (SELECT MAX(c2.dateDebut) FROM CongeEntity c2 WHERE c2.agent.id = :agentId)")
//    List<String> findCorpsLibelleByAgentIdLatest(@Param("agentId") Long agentId);

    @Query("SELECT c.dateReprise FROM CongeEntity c WHERE c.agent.id = :agentId AND c.typeConge = :typeConge ORDER BY c.dateReprise DESC")
    List<Date> findLatestDateRepriseByAgentIdAndTypeConge(@Param("agentId") Long agentId, @Param("typeConge") TypeConge typeConge);

    @Query("SELECT c FROM CongeEntity c WHERE :annee BETWEEN EXTRACT(YEAR FROM c.dateDebut) AND EXTRACT(YEAR FROM c.dateReprise)")
    List<CongeEntity> findBySpecificYear(@Param("annee") int annee);

    boolean existsByNumeroDecisionAndIdNot(String numeroDecision, Long id);

//    @Query("SELECT MAX(c.numeroDecision) FROM CongeEntity c")
//     Integer findMaxNumeroDecision();

}


