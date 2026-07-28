package sn.naavetane.backend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sn.naavetane.backend.models.ProfileDTO;
import sn.naavetane.backend.services.ProfileService;

@RestController
@RequestMapping("/profiles")
@Tag(name = "profile-controller", description = "profile controller")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;

    @Operation(summary = "Create profile", description = "this endpoint take input profile and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the type cadre logique was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProfileDTO createProfile(@RequestBody ProfileDTO profile) {
        return profileService.create(profile);
    }

    @PutMapping("/{profileId}")
    @ResponseStatus(HttpStatus.OK)
    public ProfileDTO updateProfile(@Parameter(name = "profileId", description = "the profile id updated") @PathVariable Long profileId, @RequestBody ProfileDTO profileDTO) {
        profileDTO.setId(profileId);
        return profileService.update(profileDTO);
    }

    @Operation(summary = "Read the profile", description = "This endpoint is used to read profile it take input id profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{profileId}")
    @ResponseStatus(HttpStatus.OK)
    public ProfileDTO readProfile(@Parameter(name = "profileId", description = "the type profile id to read") @PathVariable Long profileId) {
        return profileService.read(profileId);
    }

    @Operation(summary = "delete the profile", description = "Delete profile, it take input id profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the shelf was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{profileId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProfile(@Parameter(name = "profileId", description = "the profile id deleted") @PathVariable Long profileId) {
        profileService.delete(profileId);
    }

    @Operation(summary = "Read all profiles", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<ProfileDTO> readAllProfile(
            Pageable pageable,
            @Parameter(name = "code", description = "value of label used to filter list cadre logique") @RequestParam(value = "code", required = false) String code,
            @Parameter(name = "libelle", description = "value of label used to filter list cadre logique") @RequestParam(value = "libelle", required = false) String libelle
    ) {
        return profileService.readAll(pageable, code, libelle);
    }

}
