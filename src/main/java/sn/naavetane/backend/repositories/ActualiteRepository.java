package sn.naavetane.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.naavetane.backend.entities.ActualiteEntity;
import java.util.UUID;
import java.util.List;

@Repository
public interface ActualiteRepository extends JpaRepository<ActualiteEntity, UUID> {
    List<ActualiteEntity> findByActifTrueOrderByDatePublicationDesc();
    List<ActualiteEntity> findByActifTrueAndTypeOrderByDatePublicationDesc(String type);
}
