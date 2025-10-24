package com.webgram.dgpsn.controllers;
import com.webgram.dgpsn.annotations.Journal;
import com.webgram.dgpsn.models.PlanComptableElementDTO;
import com.webgram.dgpsn.services.PlanComptableElementService;
import com.webgram.dgpsn.entities.enums.TypePlanComptable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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
   /* @Journal(actionType = "DELETE")*/
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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
