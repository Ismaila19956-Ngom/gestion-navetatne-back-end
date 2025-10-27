package com.webgram.dgpsn.controllers;

import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.models.PlanComptableElementDTO;
import com.webgram.dgpsn.services.PlanComptableElementService;
import com.webgram.dgpsn.entities.enums.TypePlanComptable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/plancomptableelements")
@RequiredArgsConstructor
public class PlanComptableElementController {

    private final PlanComptableElementService service;

    @PostMapping
    public ResponseEntity<PlanComptableElementDTO> create(@RequestBody PlanComptableElementDTO dto) {
        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlanComptableElementDTO> update(
            @PathVariable Long id,
            @RequestBody PlanComptableElementDTO dto
    ) {
        dto.setId(id);
        return new ResponseEntity<>(service.update(dto), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanComptableElementDTO> read(@PathVariable Long id) {
        return new ResponseEntity<>(service.read(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            service.delete(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            log.error("Erreur suppression - id: {}", id, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de la suppression: " + ex.getMessage());
        }
    }

    @GetMapping
    public Page<PlanComptableElementDTO> readAll(
            Pageable pageable,
            @RequestParam(required = false) List<Long> idsToIgnore,
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String libelle,
            @RequestParam(required = false) TypePlanComptable type,
            @RequestParam(required = false) Long parent,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) Boolean ascending
    ) {
        return service.readAll(pageable, idsToIgnore, code, libelle, type, parent, sortBy, ascending);
    }
}