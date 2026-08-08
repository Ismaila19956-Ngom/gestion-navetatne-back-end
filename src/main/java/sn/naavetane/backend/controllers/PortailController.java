package sn.naavetane.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.naavetane.backend.entities.ActualiteEntity;
import sn.naavetane.backend.entities.SliderEntity;
import sn.naavetane.backend.entities.SocialNetworkEntity;
import sn.naavetane.backend.repositories.ActualiteRepository;
import sn.naavetane.backend.repositories.SliderRepository;
import sn.naavetane.backend.repositories.SocialNetworkRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/portail")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PortailController {

    @Autowired
    private SliderRepository sliderRepository;

    @Autowired
    private SocialNetworkRepository socialNetworkRepository;

    @Autowired
    private ActualiteRepository actualiteRepository;

    @Autowired
    private sn.naavetane.backend.services.DataStorageService dataStorageService;

    // --- SLIDERS ---
    @GetMapping("/sliders")
    public ResponseEntity<List<SliderEntity>> getActiveSliders() {
        return ResponseEntity.ok(sliderRepository.findByActifTrueOrderByOrdreAsc());
    }
    
    @GetMapping("/sliders/all")
    public ResponseEntity<List<SliderEntity>> getAllSliders() {
        return ResponseEntity.ok(sliderRepository.findAll());
    }

    @PostMapping("/sliders")
    public ResponseEntity<SliderEntity> createSlider(@RequestBody SliderEntity slider) {
        return ResponseEntity.ok(sliderRepository.save(slider));
    }
    
    @DeleteMapping("/sliders/{id}")
    public ResponseEntity<?> deleteSlider(@PathVariable UUID id) {
        sliderRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    // --- SOCIAL NETWORKS ---
    @GetMapping("/socials")
    public ResponseEntity<List<SocialNetworkEntity>> getActiveSocials() {
        return ResponseEntity.ok(socialNetworkRepository.findByActifTrue());
    }

    @PostMapping("/socials")
    public ResponseEntity<SocialNetworkEntity> createSocial(@RequestBody SocialNetworkEntity social) {
        return ResponseEntity.ok(socialNetworkRepository.save(social));
    }
    
    @DeleteMapping("/socials/{id}")
    public ResponseEntity<?> deleteSocial(@PathVariable UUID id) {
        socialNetworkRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    // --- ACTUALITES / COMMUNIQUES ---
    @GetMapping("/actualites")
    public ResponseEntity<List<ActualiteEntity>> getActiveActualites(@RequestParam(required = false) String type) {
        if (type != null && !type.isEmpty()) {
            return ResponseEntity.ok(actualiteRepository.findByActifTrueAndTypeOrderByDatePublicationDesc(type));
        }
        return ResponseEntity.ok(actualiteRepository.findByActifTrueOrderByDatePublicationDesc());
    }

    @GetMapping("/actualites/all")
    public ResponseEntity<List<ActualiteEntity>> getAllActualites() {
        return ResponseEntity.ok(actualiteRepository.findAll());
    }

    @PostMapping("/actualites")
    public ResponseEntity<ActualiteEntity> createActualite(@RequestBody ActualiteEntity actualite) {
        if (actualite.getDatePublication() == null) {
            actualite.setDatePublication(java.time.LocalDateTime.now());
        }
        return ResponseEntity.ok(actualiteRepository.save(actualite));
    }
    
    @DeleteMapping("/actualites/{id}")
    public ResponseEntity<?> deleteActualite(@PathVariable UUID id) {
        actualiteRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    // --- UPLOAD FICHIER (CMS) ---
    @PostMapping(value = "/upload", consumes = org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file") org.springframework.web.multipart.MultipartFile file) {
        try {
            String extension = org.apache.commons.io.FilenameUtils.getExtension(file.getOriginalFilename());
            if (extension == null || extension.isEmpty()) {
                extension = "unknown";
            }
            String reference = UUID.randomUUID().toString();
            String path = dataStorageService.storeFileRelativePath("portail", reference, extension, file.getInputStream());
            
            Map<String, String> response = new HashMap<>();
            response.put("url", path);
            return ResponseEntity.ok(response);
        } catch (java.io.IOException e) {
            return ResponseEntity.status(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // --- DASHBOARD PORTAIL ---
    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Object>> getPortailDashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("sliders", sliderRepository.findByActifTrueOrderByOrdreAsc());
        data.put("socials", socialNetworkRepository.findByActifTrue());
        data.put("actualites", actualiteRepository.findByActifTrueAndTypeOrderByDatePublicationDesc("ACTUALITE"));
        data.put("communiques", actualiteRepository.findByActifTrueAndTypeOrderByDatePublicationDesc("COMMUNIQUE"));
        return ResponseEntity.ok(data);
    }
}
