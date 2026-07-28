package sn.naavetane.backend.repositories;

import sn.naavetane.backend.entities.PointDeVenteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PointDeVenteRepository extends JpaRepository<PointDeVenteEntity, UUID> {
}
