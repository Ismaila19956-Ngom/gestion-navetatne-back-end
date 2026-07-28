package sn.naavetane.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.naavetane.backend.entities.MatchEntity;

import java.util.UUID;

public interface MatchRepository extends JpaRepository<MatchEntity, UUID> {
}
