package com.webgram.dgpsn.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.webgram.dgpsn.models.SuiviSurveillanceDTO;
import com.webgram.dgpsn.services.SuiviSurveillanceService;

@RestController
@RequestMapping("/suivi-surveillance")
@Tag(name = "suivi-surveillance-controller", description = "API de gestion des Suivis et Surveillances")
@RequiredArgsConstructor
public class SuiviSurveillanceController {

    private final SuiviSurveillanceService suiviSurveillanceService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SuiviSurveillanceDTO create(@RequestBody SuiviSurveillanceDTO suiviSurveillanceDTO) {
        return suiviSurveillanceService.create(suiviSurveillanceDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SuiviSurveillanceDTO update(@PathVariable Long id, @RequestBody SuiviSurveillanceDTO suiviSurveillanceDTO) {
        return suiviSurveillanceService.update(id, suiviSurveillanceDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        suiviSurveillanceService.delete(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<SuiviSurveillanceDTO> getAll(
            @RequestParam(required = false) Long promoteurId,
            @RequestParam(required = false) String intitule,
            Pageable pageable) {
        return suiviSurveillanceService.readAll(promoteurId, intitule, pageable);
    }
}