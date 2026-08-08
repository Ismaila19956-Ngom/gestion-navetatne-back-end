package sn.naavetane.backend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.naavetane.backend.entities.ContactAnnuaireEntity;
import sn.naavetane.backend.repositories.ContactAnnuaireRepository;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/portail/contacts")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ContactAnnuaireController {

    private final ContactAnnuaireRepository repository;

    // Public API
    @GetMapping
    public ResponseEntity<List<ContactAnnuaireEntity>> getActifs() {
        return ResponseEntity.ok(repository.findAll());
    }

    // CMS API
    @GetMapping("/all")
    public ResponseEntity<List<ContactAnnuaireEntity>> getAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @PostMapping
    public ResponseEntity<ContactAnnuaireEntity> create(@RequestBody ContactAnnuaireEntity entity) {
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
