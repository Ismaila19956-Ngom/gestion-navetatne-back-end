package com.webgram.dgpsn.controllers;
import com.webgram.dgpsn.annotations.Journal;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.models.PlanComptableElementDTO;
import com.webgram.dgpsn.services.PlanComptableElementService;
import com.webgram.dgpsn.entities.enums.TypePlanComptable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/plancomptableelements")
@RequiredArgsConstructor
public class PlanComptableElementController {

    private final PlanComptableElementService service;

    @PostMapping
  /*  @Journal(actionType = "ADD")*/
    public ResponseEntity<PlanComptableElementDTO> create(@RequestBody PlanComptableElementDTO dto) {
        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
   /* @Journal(actionType = "EDIT")*/
    public ResponseEntity<PlanComptableElementDTO> update(@PathVariable Long id, @RequestBody PlanComptableElementDTO dto) {
        dto.setId(id);
        return new ResponseEntity<>(service.update(dto), HttpStatus.OK);
    }

    @GetMapping("/{id}")
   /* @Journal(actionType = "READ")*/
    public ResponseEntity<PlanComptableElementDTO> read(@PathVariable Long id) {
        return new ResponseEntity<>(service.read(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    /* @Journal(actionType = "DELETE") */
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            log.info("Tentative de suppression de l'élément avec l'ID: {}", id);
            service.delete(id);
            log.info("Élément avec l'ID {} supprimé avec succès", id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException ex) {
            log.error("Élément non trouvé pour la suppression - ID: {}", id, ex);
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            log.error("Erreur lors de la suppression de l'élément avec l'ID: " + id, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Une erreur est survenue lors de la suppression: " + ex.getMessage());
        }
    }

    @GetMapping
   /* @Journal(actionType = "READ")*/
    public Page<PlanComptableElementDTO> readAll(
            Pageable pageable,
            @RequestParam(required = false) List<Long> idsToIgnore,
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String libelle,
            @RequestParam(required = false) TypePlanComptable type,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) Boolean ascending
    ) {
        return service.readAll(pageable, idsToIgnore, code, libelle, type, sortBy, ascending);
    }
}
