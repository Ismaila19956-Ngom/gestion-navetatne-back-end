package com.webgram.dgpsn.repositories;

import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import com.webgram.dgpsn.entities.InstructionEntity;
import com.webgram.dgpsn.entities.QInstructionEntity;
import com.webgram.dgpsn.models.responses.DataPoint;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Repository
public interface InstructionRepository extends JpaRepository<InstructionEntity, Long>, QuerydslPredicateExecutor<InstructionEntity> {

    default Page<InstructionEntity> findByCriteria(Long promoteurId, Long regionId, String intitule, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();
        QInstructionEntity qInstruction = QInstructionEntity.instructionEntity;

        if (Objects.nonNull(promoteurId)) {
            builder.and(qInstruction.promoteur.id.eq(promoteurId));
        }
        if (Objects.nonNull(regionId)) {
            builder.and(qInstruction.region.id.eq(regionId));
        }
        if (Objects.nonNull(intitule) && !intitule.isEmpty()) {
            builder.and(qInstruction.intitule.containsIgnoreCase(intitule));
        }

        return findAll(builder, pageable);
    }

    @Query("SELECT COUNT(i) FROM InstructionEntity i WHERE i.niveauInstruction <> 'VALIDATION'")
    Long countDossiersEnCours();

    @Query(value = "SELECT AVG(EXTRACT(EPOCH FROM (i.date_delivrance_arrete - i.date_depot)) / (60 * 60 * 24)) " +
            "FROM instruction_entity i " +
            "WHERE i.niveau_instruction = 'VALIDATION' AND i.date_delivrance_arrete >= :oneYearAgo", nativeQuery = true)
    Double findDelaiMoyenTraitement(LocalDate oneYearAgo);

    @Query("SELECT new com.webgram.dgpsn.models.responses.DataPoint(FUNCTION('DATE_FORMAT', i.dateDepot, '%Y-%m'), COUNT(i)) " +
            "FROM InstructionEntity i WHERE i.dateDepot >= :oneYearAgo " +
            "GROUP BY FUNCTION('DATE_FORMAT', i.dateDepot, '%Y-%m') " +
            "ORDER BY FUNCTION('DATE_FORMAT', i.dateDepot, '%Y-%m')")
    List<DataPoint<String, Long>> countFluxDossiersMensuel(LocalDate oneYearAgo);

    @Query("SELECT new com.webgram.dgpsn.models.responses.DataPoint(r.libelle, COUNT(i)) " +
            "FROM InstructionEntity i JOIN i.region r " +
            "GROUP BY r.libelle")
    List<DataPoint<String, Long>> countProjetsParRegion();

    @Query("SELECT new com.webgram.dgpsn.models.responses.DataPoint(i.niveauInstruction, COUNT(i)) " +
            "FROM InstructionEntity i " +
            "GROUP BY i.niveauInstruction")
    List<DataPoint<String, Long>> countByNiveauInstruction();
}