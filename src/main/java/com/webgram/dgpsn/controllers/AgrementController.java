package com.webgram.dgpsn.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.webgram.dgpsn.models.AgrementDTO;
import com.webgram.dgpsn.services.AgrementService;

@RestController
@RequestMapping("/agrements")
@Tag(name = "agrements-controller", description = "API de gestion des Agréments")
@RequiredArgsConstructor
public class AgrementController {

    private final AgrementService agrementService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AgrementDTO create(@RequestBody AgrementDTO agrementDTO) {
        return agrementService.create(agrementDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AgrementDTO update(@PathVariable Long id, @RequestBody AgrementDTO agrementDTO) {
        return agrementService.update(id, agrementDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        agrementService.delete(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<AgrementDTO> getAll(
            @RequestParam(required = false) Long promoteurId,
            @RequestParam(required = false) String objet,
            Pageable pageable) {
        return agrementService.readAll(promoteurId, objet, pageable);
    }
}