package com.webgram.dgpsn.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.webgram.dgpsn.models.TdrDTO;
import com.webgram.dgpsn.services.TdrService;

@RestController
@RequestMapping("/tdr")
@Tag(name = "tdr-controller", description = "API de gestion des Termes de Référence (TDR)")
@RequiredArgsConstructor
public class TdrController {

    private final TdrService tdrService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TdrDTO createTdr(@RequestBody TdrDTO tdrDTO) {
        return tdrService.create(tdrDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TdrDTO updateTdr(@PathVariable Long id, @RequestBody TdrDTO tdrDTO) {
        return tdrService.update(id, tdrDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTdr(@PathVariable Long id) {
        tdrService.delete(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<TdrDTO> getAllTdrs(
            @RequestParam(required = false) Long instructionId,
            @RequestParam(required = false) String intitule,
            Pageable pageable) {
        return tdrService.readAll(instructionId, intitule, pageable);
    }
}