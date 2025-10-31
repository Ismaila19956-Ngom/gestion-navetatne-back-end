package com.webgram.dgpsn.controllers;

import com.webgram.dgpsn.models.FournisseurDTO;
import com.webgram.dgpsn.services.FournisseurService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fournisseurs")
@RequiredArgsConstructor
public class FournisseurController {

    private final FournisseurService fournisseurService;

    @PostMapping
    public ResponseEntity<FournisseurDTO> createFournisseur(@RequestBody FournisseurDTO fournisseurDTO) {
        return new ResponseEntity<>(fournisseurService.create(fournisseurDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FournisseurDTO> updateFournisseur(@PathVariable Long id, @RequestBody FournisseurDTO fournisseurDTO) {
        return ResponseEntity.ok(fournisseurService.update(id, fournisseurDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FournisseurDTO> readFournisseur(@PathVariable Long id) {
        return ResponseEntity.ok(fournisseurService.read(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFournisseur(@PathVariable Long id) {
        fournisseurService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<FournisseurDTO>> readAllFournisseurs(
            Pageable pageable,
            @RequestParam(required = false) String raisonSociale,
            @RequestParam(required = false) String codeFournisseur,
            @RequestParam(required = false) String ninea,
            @RequestParam(required = false) String categorieFournisseur,
            @RequestParam(required = false) String statut,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) Boolean ascending
    ) {
        Page<FournisseurDTO> fournisseurs = fournisseurService.readAll(
                pageable, raisonSociale, codeFournisseur, ninea, categorieFournisseur, statut, sortBy, ascending
        );
        return ResponseEntity.ok(fournisseurs);
    }
}