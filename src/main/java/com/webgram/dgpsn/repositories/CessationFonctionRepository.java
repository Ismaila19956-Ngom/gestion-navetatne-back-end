package com.webgram.dgpsn.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.webgram.dgpsn.entities.CessationFonctionEntity;
import com.webgram.dgpsn.entities.CongeEntity;
import com.webgram.dgpsn.models.responses.StaticCessationDTO;

import java.util.Date;
import java.util.List;

@Repository
public interface CessationFonctionRepository extends JpaRepository<CessationFonctionEntity, Long>, QuerydslPredicateExecutor<CessationFonctionEntity> {

    @Query("SELECT c.numFicheCessation FROM CessationFonctionEntity c ORDER BY c.numFicheCessation DESC")
    List<String> findLastNumFicheCessation();
//    List<CessationFonctionEntity> findByCongeAndAnnee(CongeEntity conge, Integer annee);
    @Query("SELECT c FROM CessationFonctionEntity c WHERE c.conge = :conge AND EXTRACT(YEAR FROM c.dateCessation) = :annee")
    List<CessationFonctionEntity> findByCongeAndAnnee(@Param("conge") CongeEntity conge, @Param("annee") Integer annee);
    @Query("SELECT c.soldeAnnuel FROM CessationFonctionEntity c WHERE c.conge.id = :congeId ORDER BY c.dateCessation DESC")
    List<Integer> getLatestSoldeAnnuelByCongeIdAndMatricule(@Param("congeId") Long congeId);
    @Query("SELECT c.conge.dureeCessation FROM CessationFonctionEntity c WHERE c.conge.id = :congeId ORDER BY c.dateCessation DESC")
    List<Integer> findDureeCongeByCongeId(@Param("congeId") Long congeId);
    @Query("SELECT c FROM CessationFonctionEntity c WHERE c.conge.id = :congeId")
    List<CessationFonctionEntity> findByCongeId(@Param("congeId") Long congeId);

//    @Query("SELECT new com.webgram.dgpsn.models.responses.StaticCessationDTO(" +
//        "   a, " +
//        "   a.echelon.grade.corps.libelle, " +
//        "   cf.dateCessation, " +
//        "   cf.nombreDeJoursdemande) " +
//        "FROM CessationFonctionEntity cf " +
//        "JOIN cf.conge c " +
//        "JOIN c.agent a " +
//        "WHERE cf.dateCessation = (" +
//        "    SELECT MAX(cf2.dateCessation) " +
//        "    FROM CessationFonctionEntity cf2 " +
//        "    WHERE cf2.conge.id = cf.conge.id" +
//        ")")
//     List<StaticCessationDTO> findCurrentCessations();

     @Query("SELECT CASE WHEN :dateCessation BETWEEN c.dateDepart AND c.dateReprise THEN true ELSE false END " +
            "FROM CongeEntity c WHERE c = :conge")
     boolean isDateCessationValidForConge(@Param("conge") CongeEntity conge, @Param("dateCessation") Date dateCessation);

     /// export conge agent
    /**
     * Trouve toutes les cessations d'un agent pour une année donnée
     */
    @Query("SELECT c FROM CessationFonctionEntity c " +
            "WHERE c.conge.agent.id = :agentId " +
            "AND c.annee = :annee " +
            "ORDER BY c.dateCessation ASC")
    List<CessationFonctionEntity> findByAgentIdAndAnnee(@Param("agentId") Long agentId,
                                                        @Param("annee") Integer annee);

    /**
     * Trouve toutes les cessations pour une année donnée
     */
    @Query("SELECT c FROM CessationFonctionEntity c " +
            "WHERE c.annee = :annee " +
            "ORDER BY c.conge.agent.direction.libelle, c.conge.agent.nom ASC")
    List<CessationFonctionEntity> findByAnnee(@Param("annee") Integer annee);

    /**
     * Calcule le solde total d'un agent pour une année
     */
    @Query("SELECT SUM(c.nombreDeJoursdemande) FROM CessationFonctionEntity c " +
            "WHERE c.conge.agent.id = :agentId " +
            "AND c.annee = :annee")
    Integer sumJoursDemandesByAgentAndAnnee(@Param("agentId") Long agentId,
                                            @Param("annee") Integer annee);

    /**
     * Trouve le solde annuel le plus récent d'un agent
     */
    @Query("SELECT c.soldeAnnuel FROM CessationFonctionEntity c " +
            "WHERE c.conge.agent.id = :agentId " +
            "AND c.annee = :annee " +
            "ORDER BY c.dateCessation DESC")
    List<Integer> findSoldeAnnuelByAgentAndAnnee(@Param("agentId") Long agentId,@Param("annee") Integer annee);


}