package sn.naavetane.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sn.naavetane.backend.entities.TransactionEntity;
import sn.naavetane.backend.entities.TransactionStatus;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
    Optional<TransactionEntity> findByTransactionReference(String transactionReference);
    Optional<TransactionEntity> findByPaydunyaToken(String paydunyaToken);

    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM TransactionEntity t WHERE t.status = :status")
    Double sumAmountByStatus(@Param("status") TransactionStatus status);
}
