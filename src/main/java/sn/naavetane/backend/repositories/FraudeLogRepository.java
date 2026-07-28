package sn.naavetane.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sn.naavetane.backend.entities.FraudeLogEntity;

import java.time.LocalDateTime;
import java.util.UUID;

public interface FraudeLogRepository extends JpaRepository<FraudeLogEntity, UUID> {
    long countByMatchId(UUID matchId);

    @Query("SELECT COUNT(f) FROM FraudeLogEntity f WHERE f.dateFraude >= :startDate AND f.dateFraude < :endDate")
    long countByDateFraudeBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT COUNT(f) FROM FraudeLogEntity f WHERE f.dateFraude >= :startDate AND f.dateFraude < :endDate AND f.matchId IS NULL")
    long countByDateFraudeBetweenAndMatchIdIsNull(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
