package com.webgram.dgpsn.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.webgram.dgpsn.entities.AlerteEntity;
import com.webgram.dgpsn.entities.enums.Priority;
import com.webgram.dgpsn.entities.enums.TypeAlerte;

import java.util.Set;


@RestController
@RequestMapping("/profile-template")
@Tag(name = "profile-template-controller", description = "profile-template controller")
@RequiredArgsConstructor
public class ProfileTemplateController {

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<AlerteEntity> readAllAlerteByProfile(Pageable pageable) {
     return null;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/readAllTypeAlerte")
    public Set<TypeAlerte> readAllTypeAlerte() {
        return TypeAlerte.getAllTypesAlerte();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/readAllPriorities")
    public Set<Priority> readAllPriorities() {
        return Priority.getAllPriorities();
    }
}
