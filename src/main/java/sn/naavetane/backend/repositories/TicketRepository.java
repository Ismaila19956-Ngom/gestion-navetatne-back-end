package sn.naavetane.backend.repositories;

import sn.naavetane.backend.entities.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sn.naavetane.backend.entities.enums.StatutTicket;
import sn.naavetane.backend.entities.enums.TypeAchat;

@Repository
public interface TicketRepository extends JpaRepository<TicketEntity, UUID> {
    Optional<TicketEntity> findByQrCodePayload(String qrCodePayload);
    
    // Anti-doublon : vérifie si un ticket existe déjà pour cette référence de paiement
    Optional<TicketEntity> findByPaymentReference(String paymentReference);
    
    // Pour la synchronisation hors-ligne (téléchargement des nouveaux billets)
    List<TicketEntity> findByMatchIdAndDateAchatAfter(UUID matchId, LocalDateTime lastSyncTime);

    List<TicketEntity> findByUserIdOrderByDateAchatDesc(Long userId);

    List<TicketEntity> findByMatchId(UUID matchId);

    // --- REQUÊTES D'AGRÉGATION OPTIMISÉES ---

    long countByMatchId(UUID matchId);

    long countByMatchIdAndStatut(UUID matchId, StatutTicket statut);
    
    long countByStatut(StatutTicket statut);

    long countByMatchIdAndTypeAchat(UUID matchId, TypeAchat typeAchat);
    
    long countByTypeAchat(TypeAchat typeAchat);

    @Query("SELECT COALESCE(SUM(t.prix), 0) FROM TicketEntity t WHERE t.matchId = :matchId AND t.typeAchat = :typeAchat")
    Double sumPrixByMatchIdAndTypeAchat(@Param("matchId") UUID matchId, @Param("typeAchat") TypeAchat typeAchat);

    @Query("SELECT COALESCE(SUM(t.prix), 0) FROM TicketEntity t WHERE t.typeAchat = :typeAchat")
    Double sumPrixByTypeAchat(@Param("typeAchat") TypeAchat typeAchat);

    @Query("SELECT t.prix, COUNT(t) FROM TicketEntity t WHERE t.matchId = :matchId AND t.prix IS NOT NULL GROUP BY t.prix")
    List<Object[]> countTicketsByPriceAndMatchId(@Param("matchId") UUID matchId);

    @Query("SELECT t.prix, COUNT(t) FROM TicketEntity t WHERE t.prix IS NOT NULL GROUP BY t.prix")
    List<Object[]> countTicketsByPrice();
}
