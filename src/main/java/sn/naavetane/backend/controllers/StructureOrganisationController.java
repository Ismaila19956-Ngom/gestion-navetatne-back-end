package sn.naavetane.backend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.naavetane.backend.entities.StructureOrganisationEntity;
import sn.naavetane.backend.repositories.StructureOrganisationRepository;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/portail/organisation")
@RequiredArgsConstructor
@CrossOrigin("*")
public class StructureOrganisationController {

    private final StructureOrganisationRepository repository;

    // Public API
    @GetMapping
    public ResponseEntity<List<StructureOrganisationEntity>> getActifs() {
        return ResponseEntity.ok(repository.findAll());
    }

    // CMS API
    @GetMapping("/all")
    public ResponseEntity<List<StructureOrganisationEntity>> getAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @PostMapping
    public ResponseEntity<StructureOrganisationEntity> create(@RequestBody StructureOrganisationEntity entity) {
        if (entity.getActif() == null) {
            entity.setActif(true);
        }
        return ResponseEntity.ok(repository.save(entity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
