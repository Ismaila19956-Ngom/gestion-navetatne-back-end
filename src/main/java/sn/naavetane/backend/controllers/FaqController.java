package sn.naavetane.backend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.naavetane.backend.entities.FaqEntity;
import sn.naavetane.backend.repositories.FaqRepository;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/portail/faqs")
@RequiredArgsConstructor
@CrossOrigin("*")
public class FaqController {

    private final FaqRepository repository;

    // Public API
    @GetMapping
    public ResponseEntity<List<FaqEntity>> getActifs() {
        return ResponseEntity.ok(repository.findAll());
    }

    // CMS API
    @GetMapping("/all")
    public ResponseEntity<List<FaqEntity>> getAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @PostMapping
    public ResponseEntity<FaqEntity> create(@RequestBody FaqEntity entity) {
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
