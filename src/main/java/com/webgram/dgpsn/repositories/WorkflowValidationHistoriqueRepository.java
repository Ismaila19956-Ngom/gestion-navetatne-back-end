package com.webgram.dgpsn.repositories;

import com.webgram.dgpsn.entities.WorkflowValidationHistoriqueEntity;
import com.webgram.dgpsn.entities.enums.WorkflowType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface WorkflowValidationHistoriqueRepository extends JpaRepository<WorkflowValidationHistoriqueEntity, Long>, QuerydslPredicateExecutor<WorkflowValidationHistoriqueEntity> {
    @Query("SELECT w FROM WorkflowValidationHistoriqueEntity w " +
            "WHERE w.date = (SELECT MAX(w2.date) FROM WorkflowValidationHistoriqueEntity w2 " +
                "WHERE w2.user.id = w.user.id AND w2.workflowType = :workflowType AND w2.etape.id = :etapeId)")
    List<WorkflowValidationHistoriqueEntity> findRecentUsersValidation(@Param("workflowType") WorkflowType workflowType, @Param("etapeId") Long etapeId);
    @Query("SELECT w FROM WorkflowValidationHistoriqueEntity w WHERE w.user.id = :userId AND w.workflowType = :workflowType AND w.etape.id = :etapeId ORDER BY w.date DESC")
    List<WorkflowValidationHistoriqueEntity> findRecentUserValidation(
            @Param("userId") Long userId,
            @Param("workflowType") WorkflowType workflowType,
            @Param("etapeId") Long etapeId);

    Optional<WorkflowValidationHistoriqueEntity> findTopByWorkflowTypeAndYearAndMonthOrderByDateDesc(
            WorkflowType workflowType, Integer year, Integer month);
    List<WorkflowValidationHistoriqueEntity> findByWorkflowTypeAndYearAndMonth(
            WorkflowType workflowType, Integer year, Integer month);

    // Validation individuelle avec année et mois
    List<WorkflowValidationHistoriqueEntity> findByUserIdAndWorkflowTypeAndEtapeIdAndYearAndMonth(
            Long userId, WorkflowType workflowType, Long stepId, Integer year, Integer month);
    // Validation commune avec année et mois
    List<WorkflowValidationHistoriqueEntity> findByWorkflowTypeAndEtapeIdAndYearAndMonth(
            WorkflowType workflowType, Long stepId, Integer year, Integer month);

    Optional<WorkflowValidationHistoriqueEntity> findByWorkflowTypeAndDateCalcul(WorkflowType workflowType, LocalDate dateCalcul);

//    @Query("""
//    SELECT wvh FROM WorkflowValidationHistoriqueEntity wvh
//    WHERE wvh.workflowType = :workflowType
//      AND wvh.dateCalcul = :dateCalcul
//      AND wvh.validation = true
//      AND wvh.etape IS NOT NULL
//      AND wvh.etape.ordre = (
//          SELECT MAX(wvh2.etape.ordre)
//          FROM WorkflowValidationHistoriqueEntity wvh2
//          WHERE wvh2.workflowType = :workflowType
//            AND wvh2.dateCalcul = :dateCalcul
//            AND wvh2.etape IS NOT NULL
//      )
//""")
//    Optional<WorkflowValidationHistoriqueEntity> findMostRecentValidationHistorique(
//            @Param("workflowType") WorkflowType workflowType,
//            @Param("dateCalcul") LocalDate dateCalcul
//    );
    @Query("""
        SELECT wvh FROM WorkflowValidationHistoriqueEntity wvh
        WHERE wvh.workflowType = :workflowType
          AND wvh.dateCalcul = :dateCalcul
          AND wvh.validation = true
          AND wvh.etape IS NOT NULL
          AND wvh.etape.ordre = (
              SELECT MAX(wvh2.etape.ordre)
              FROM WorkflowValidationHistoriqueEntity wvh2
              WHERE wvh2.workflowType = :workflowType
                AND wvh2.dateCalcul = :dateCalcul
                AND wvh2.etape IS NOT NULL
          )
        ORDER BY wvh.date DESC
    """)
    List<WorkflowValidationHistoriqueEntity> findMostRecentValidationHistorique(
            @Param("workflowType") WorkflowType workflowType,
            @Param("dateCalcul") LocalDate dateCalcul
    );


    @Query("""
        SELECT wvh FROM WorkflowValidationHistoriqueEntity wvh
        WHERE wvh.workflowType = :workflowType
          AND wvh.dateCalcul = :dateCalcul
          AND wvh.etape IS NOT NULL
          AND wvh.etape.ordre = (
              SELECT MAX(wvh2.etape.ordre)
              FROM WorkflowValidationHistoriqueEntity wvh2
              WHERE wvh2.workflowType = :workflowType
                AND wvh2.dateCalcul = :dateCalcul
                AND wvh2.etape IS NOT NULL
          )
        ORDER BY wvh.date DESC
    """)
    List<WorkflowValidationHistoriqueEntity> findMostRecentHistorique(
            @Param("workflowType") WorkflowType workflowType,
            @Param("dateCalcul") LocalDate dateCalcul
    );

    List<WorkflowValidationHistoriqueEntity> findAllByWorkflowTypeAndDateCalcul(WorkflowType type, LocalDate dateCalcul);
}
