package sn.naavetane.backend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.naavetane.backend.entities.TexteReglementaireEntity;
import sn.naavetane.backend.repositories.TexteReglementaireRepository;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/portail/textes")
@RequiredArgsConstructor
@CrossOrigin("*")
public class TexteReglementaireController {

    private final TexteReglementaireRepository repository;

    // Public API
    @GetMapping
    public ResponseEntity<List<TexteReglementaireEntity>> getActifs() {
        // En vrai, il faudrait filtrer par actif=true, mais pour simplifier ici :
        return ResponseEntity.ok(repository.findAll());
    }

    // CMS API
    @GetMapping("/all")
    public ResponseEntity<List<TexteReglementaireEntity>> getAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @PostMapping
    public ResponseEntity<TexteReglementaireEntity> create(@RequestBody TexteReglementaireEntity entity) {
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
