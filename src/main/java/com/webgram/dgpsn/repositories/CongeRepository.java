package com.webgram.dgpsn.repositories;

import com.webgram.dgpsn.entities.CongeEntity;
import com.webgram.dgpsn.entities.enums.TypeConge;
import com.webgram.dgpsn.models.responses.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CongeRepository extends JpaRepository<CongeEntity, Long>, QuerydslPredicateExecutor<CongeEntity> {

    @Modifying
    @Query(value = "DELETE FROM cong_document WHERE document_id = :documentId", nativeQuery = true)
    void unlinkOrdreMission(@Param("documentId") Long documentId);

    boolean existsByAgentIdAndDateDepartBeforeAndDateRepriseAfterAndTypeConge(Long agentId, Date dateDepart, Date dateReprise, TypeConge typeConge);

    @Query("SELECT c.dateReprise FROM CongeEntity c WHERE c.agent.id = :agentId AND c.typeConge = :typeConge ORDER BY c.dateReprise DESC")
    List<Date> findLatestDateRepriseByAgentIdAndTypeConge(@Param("agentId") Long agentId, @Param("typeConge") TypeConge typeConge);

    @Query("SELECT c FROM CongeEntity c WHERE :annee BETWEEN EXTRACT(YEAR FROM c.dateDebut) AND EXTRACT(YEAR FROM c.dateReprise)")
    List<CongeEntity> findBySpecificYear(@Param("annee") int annee);

    boolean existsByNumeroDecisionAndIdNot(String numeroDecision, Long id);


    /// Trouve tous les congés d'un agent
    @Query("SELECT c FROM CongeEntity c WHERE c.agent.id = :agentId ORDER BY c.dateDebut DESC")
    List<CongeEntity> findByAgentId(@Param("agentId") Long agentId);

    /**
     * Trouve tous les congés d'un agent par type
     */
    @Query("SELECT c FROM CongeEntity c WHERE c.agent.id = :agentId AND c.typeConge = :typeConge ORDER BY c.dateDebut DESC")
    List<CongeEntity> findByAgentIdAndTypeConge(@Param("agentId") Long agentId,
                                                @Param("typeConge") TypeConge typeConge);


    // Trouve tous les congés entre deux dates
    @Query("SELECT c FROM CongeEntity c " +
            "WHERE c.dateDepart >= :dateDebut AND c.dateReprise <= :dateFin " +
            "ORDER BY c.dateDepart ASC")
    List<CongeEntity> findByDateDepartBetween(@Param("dateDebut") Date dateDebut,
                                              @Param("dateFin") Date dateFin);

    /**
     * Compte le nombre total de jours de congé pour un agent sur une période
     */
    @Query("SELECT SUM(c.dureeCessation) FROM CongeEntity c " +
            "WHERE c.agent.id = :agentId " +
            "AND c.dateDepart >= :dateDebut " +
            "AND c.dateReprise <= :dateFin")
    Integer countTotalJoursCongeByAgentAndPeriode(@Param("agentId") Long agentId,
                                                  @Param("dateDebut") Date dateDebut,
                                                  @Param("dateFin") Date dateFin);

    /**
     * Trouve tous les congés en cours
     */
    @Query("SELECT c FROM CongeEntity c WHERE :today BETWEEN c.dateDepart AND c.dateReprise")
    List<CongeEntity> findCongesEnCours(@Param("today") Date today);

    /**
     * Trouve tous les congés par direction
     */
    @Query("SELECT c FROM CongeEntity c " +
            "WHERE c.agent.direction.id = :directionId " +
            "ORDER BY c.dateDebut DESC")
    List<CongeEntity> findByDirection(@Param("directionId") Long directionId);

    /**
     * Statistiques par type de congé pour un agent
     */
    @Query("SELECT c.typeConge, SUM(c.dureeCessation) FROM CongeEntity c " +
            "WHERE c.agent.id = :agentId " +
            "GROUP BY c.typeConge")
    List<Object[]> findStatistiquesByAgent(@Param("agentId") Long agentId);

    /**
     * Statistiques globales par type de congé
     */
    @Query("SELECT c.typeConge, COUNT(c), SUM(c.dureeCessation) FROM CongeEntity c " +
            "GROUP BY c.typeConge")
    List<Object[]> findStatistiquesGlobales();

    // 1. Résumé
    @Query("SELECT new com.webgram.dgpsn.models.responses.CongeDashboardSummaryDTO(" +
            "COUNT(c), " +
            "SUM(CASE WHEN c.statutType = 'ACCEPTER' THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN c.statutType = 'TRAITEMENT_ENCOUR' THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN c.statutType = 'REFUSER' THEN 1 ELSE 0 END)) " +
            "FROM CongeEntity c")
    CongeDashboardSummaryDTO getDashboardSummary();

    // 2. Répartition par type congé
    @Query("SELECT new com.webgram.dgpsn.models.responses.TypeCongeCountDTO(c.typeConge, COUNT(c)) " +
            "FROM CongeEntity c GROUP BY c.typeConge")
    List<TypeCongeCountDTO> countByTypeConge();

    // 3. Répartition par statut
    @Query("SELECT new com.webgram.dgpsn.models.responses.StatutCountDTO(c.statutType, COUNT(c)) " +
            "FROM CongeEntity c GROUP BY c.statutType")
    List<StatutCountDTO> countByStatut();


    // 6. Top 5 agents
    @Query("""
            SELECT new com.webgram.dgpsn.models.responses.TopAgentDTO(
                CONCAT(LEFT(a.prenom, 1), '. ', a.nom),
                COUNT(c))
            FROM CongeEntity c
            JOIN c.agent a
            GROUP BY a.id, a.prenom, a.nom
            ORDER BY COUNT(c) DESC
            """)
    List<TopAgentDTO> findTop5Agents();

    // 7. Taux d'approbation par type
    @Query("""
            SELECT c.typeConge,
                   CASE 
                     WHEN COUNT(c) = 0 THEN 0.0 
                     ELSE 100.0 * SUM(CASE WHEN c.statutType = 'ACCEPTER' THEN 1 ELSE 0 END) / COUNT(c)
                   END
            FROM CongeEntity c
            GROUP BY c.typeConge
            """)
    List<Object[]> approvalRateByType();
}


