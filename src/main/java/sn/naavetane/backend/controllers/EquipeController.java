package sn.naavetane.backend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.naavetane.backend.dto.EquipeDTO;
import sn.naavetane.backend.entities.EquipeEntity;
import sn.naavetane.backend.repositories.EquipeRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/equipes")
@RequiredArgsConstructor
public class EquipeController {

    private final EquipeRepository equipeRepository;

    @GetMapping
    public ResponseEntity<List<EquipeDTO>> getEquipes() {
        List<EquipeEntity> equipes = equipeRepository.findAll();
        List<EquipeDTO> dtos = equipes.stream().map(e -> EquipeDTO.builder()
                .id(e.getId())
                .nom(e.getNom())
                .build()).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PostMapping
    public ResponseEntity<EquipeDTO> addEquipe(@RequestBody EquipeDTO dto) {
        EquipeEntity equipe = EquipeEntity.builder()
                .nom(dto.getNom())
                .build();
        EquipeEntity saved = equipeRepository.save(equipe);
        return ResponseEntity.ok(EquipeDTO.builder()
                .id(saved.getId())
                .nom(saved.getNom())
                .build());
    }
}
