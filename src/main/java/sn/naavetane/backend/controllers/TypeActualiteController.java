package sn.naavetane.backend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.naavetane.backend.entities.TypeActualiteEntity;
import sn.naavetane.backend.repositories.TypeActualiteRepository;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/referentiels/types-actualites")
@RequiredArgsConstructor
@CrossOrigin("*") // Si la configuration globale n'est pas déjà appliquée
public class TypeActualiteController {

    private final TypeActualiteRepository repository;

    @GetMapping
    public ResponseEntity<List<TypeActualiteEntity>> getAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @PostMapping
    public ResponseEntity<TypeActualiteEntity> create(@RequestBody TypeActualiteEntity entity) {
        if (entity.getCode() == null || entity.getCode().isEmpty()) {
            entity.setCode(entity.getLibelle().toUpperCase().replaceAll("\\s+", "_"));
        }
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
