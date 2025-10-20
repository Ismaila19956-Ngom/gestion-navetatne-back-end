package com.webgram.dgpsn.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.webgram.dgpsn.entities.enums.Statut;
import com.webgram.dgpsn.entities.enums.TypeDemande;
import com.webgram.dgpsn.models.ContactRequestDTO;
import com.webgram.dgpsn.services.ContactRequestService;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import org.springframework.core.io.Resource;

@RestController
@RequestMapping("/contact-requests")
@Tag(name = "contact-request-controller", description = "Contact Request Controller")
@RequiredArgsConstructor
public class ContactRequestController {

    private final ContactRequestService contactRequestService;

    @Operation(summary = "Create contact request", description = "This endpoint takes input contact request and saves it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @PostMapping(value="/create", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public ContactRequestDTO createContactRequest(
            @RequestPart(name = "file", required = false) MultipartFile file,
            @RequestPart(name = "ContactRequestDTO", required = true) ContactRequestDTO contactRequestDTO
    ) throws IOException {
        return contactRequestService.create(file, contactRequestDTO);
    }

    @Operation(summary = "Read contact request", description = "This endpoint is used to read a contact request by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping("/{requestId}")
    @ResponseStatus(HttpStatus.OK)
    public ContactRequestDTO readContactRequest(
            @Parameter(name = "requestId", description = "The contact request ID to read") @PathVariable Long requestId
    ) {
        return contactRequestService.read(requestId);
    }

    @Operation(summary = "Read all contact requests", description = "This endpoint retrieves a paginated list of contact requests with optional filters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<ContactRequestDTO> readAllContactRequests(
            Pageable pageable,
            @Parameter(name = "idsToIgnore", description = "List of IDs to exclude") @RequestParam(value = "idsToIgnore", required = false) List<Long> idsToIgnore,
            @Parameter(name = "requestType", description = "Type of request to filter") @RequestParam(value = "requestType", required = false) TypeDemande requestType,
            @Parameter(name = "lastName", description = "Last name to filter") @RequestParam(value = "lastName", required = false) String lastName,
            @Parameter(name = "firstName", description = "First name to filter") @RequestParam(value = "firstName", required = false) String firstName,
            @Parameter(name = "email", description = "Email to filter") @RequestParam(value = "email", required = false) String email,
            @Parameter(name = "phone", description = "Phone number to filter") @RequestParam(value = "phone", required = false) String phone,
            @Parameter(name = "organization", description = "Organization to filter") @RequestParam(value = "organization", required = false) String organization,
            @Parameter(name = "subject", description = "Subject to filter") @RequestParam(value = "subject", required = false) String subject,
            @Parameter(name = "statut", description = "Statut to filter") @RequestParam(value = "statut", required = false) Statut statut,
            @Parameter(name = "dateCreation", description = "Date de création to filter") @RequestParam(value = "dateCreation", required = false) Date dateCreation,
            @Parameter(name = "serviceId", description = "Service ID to filter") @RequestParam(value = "serviceId", required = false) Long serviceId,
            @Parameter(name = "sortBy", description = "Field to sort by") @RequestParam(value = "sortBy", required = false) String sortBy,
            @Parameter(name = "ascending", description = "Sort direction") @RequestParam(value = "ascending", required = false) Boolean ascending
    ) {
        return contactRequestService.readAll(pageable, idsToIgnore, requestType, lastName, firstName, email, phone, organization, subject, statut, dateCreation, serviceId, sortBy, ascending);
    }

    @Operation(summary = "Delete contact request", description = "This endpoint deletes a contact request by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @DeleteMapping("/{requestId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteContactRequest(
            @Parameter(name = "requestId", description = "The contact request ID to delete") @PathVariable Long requestId
    ) {
        contactRequestService.delete(requestId);
    }

    @Operation(summary = "Change Statut", description = "this endpoint takes input contact request id and new statut and updates it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the phase was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PutMapping("/{requestId}/statut")
    @ResponseStatus(HttpStatus.OK)
    public void changeStatutContactRequest(
            @Parameter(name = "requestId", description = "The contact request ID to update") @PathVariable Long requestId,
            @Parameter(name = "statut", description = "The new statut for the contact request")  @RequestParam Statut statut) {
        contactRequestService.changeStatut(requestId, statut);
    }

    @GetMapping("/{id}/_download")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long id) throws IOException {
        Resource fileResource = contactRequestService.downloadFile(id);

        // Extract the file name from the file path
        String fileName = fileResource.getFilename() != null ? fileResource.getFilename() : "document_" + id;

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(fileResource);
    }
}