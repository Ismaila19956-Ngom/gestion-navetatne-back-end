package sn.naavetane.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.naavetane.backend.entities.TexteReglementaireEntity;

import java.util.UUID;

@Repository
public interface TexteReglementaireRepository extends JpaRepository<TexteReglementaireEntity, UUID> {
}
