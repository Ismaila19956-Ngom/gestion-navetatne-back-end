//package com.webgram.dgpsn.controllers;
//
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import io.swagger.v3.oas.annotations.responses.ApiResponses;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.*;
//import com.webgram.dgpsn.models.TemporaryAccessCodeDTO;
//import com.webgram.dgpsn.models.TemporaryCodeGenerationRequest;
//import com.webgram.dgpsn.models.requests.UpdateCodeStatusRequest;
//import com.webgram.dgpsn.services.TemporaryAccessCodeService;
//
//import java.util.List;
//
//
//@RestController
//@RequestMapping("/auth-code")
//@Tag(name = "authentification-controller", description = "authentification controller")
//@RequiredArgsConstructor
//@Slf4j
//public class AuthenticationCodeController {
//
//    private final TemporaryAccessCodeService temporaryAccessCodeService;
//
//
//    @Operation(summary = "Générer un code d'accès temporaire pour un utilisateur (admin)",
//            description = "Requiert des droits d'administration pour appeler cet endpoint.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Code généré avec succès"),
//            @ApiResponse(responseCode = "400", description = "Requête invalide"),
//            @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé"),
//            @ApiResponse(responseCode = "403", description = "Accès non autorisé à cette fonctionnalité")
//    })
//    @PostMapping("/generate-temporary-code")
//    @ResponseStatus(HttpStatus.OK)
//    public String generateTemporaryCode(@RequestBody TemporaryCodeGenerationRequest request) {
//        return temporaryAccessCodeService.generateTemporaryCode(request.getUsername(), request.getValidityInMinutes());
//    }
//
//    @Operation(summary = "Récupérer la liste des codes d'accès temporaires d'un utilisateur")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Codes d'accès temporaires récupérés"),
//            @ApiResponse(responseCode = "400", description = "Code invalide, expiré ou utilisateur désactivé")
//    })
//    @GetMapping("/temporary-code")
//    @ResponseStatus(HttpStatus.OK)
//    public List<TemporaryAccessCodeDTO> getTemporaryCodes(@RequestParam("username") String username) {
//        return temporaryAccessCodeService.getTemporaryCodes(username);
//    }
//
//    @Operation(summary = "Activer ou désactiver manuellement un code d'accès temporaire",
//            description = "Permet à un administrateur de forcer le statut d'un code.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Statut mis à jour avec succès"),
//            @ApiResponse(responseCode = "400", description = "Requête invalide"),
//            @ApiResponse(responseCode = "404", description = "Code non trouvé")
//    })
//    @PutMapping("/temporary-code/{code}/status")
//    @ResponseStatus(HttpStatus.OK)
//    public TemporaryAccessCodeDTO updateTemporaryCodeStatus(
//            @PathVariable String code,
//            @RequestBody UpdateCodeStatusRequest request) {
//        return temporaryAccessCodeService.updateCodeStatus(code, request.isActive());
//    }
//}
