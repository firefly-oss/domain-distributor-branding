package com.firefly.domain.distributor.branding.web.controller;

import com.firefly.core.distributor.sdk.model.DistributorTermsAndConditionsDTO;
import com.firefly.domain.distributor.branding.core.distributor.commands.ActivateTermsAndConditionsCommand;
import com.firefly.domain.distributor.branding.core.distributor.commands.CreateTermsAndConditionsCommand;
import com.firefly.domain.distributor.branding.core.distributor.commands.DeactivateTermsAndConditionsCommand;
import com.firefly.domain.distributor.branding.core.distributor.commands.SignTermsAndConditionsCommand;
import com.firefly.domain.distributor.branding.core.distributor.services.DistributorTermsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/distributors/{distributorId}/terms-and-conditions")
@RequiredArgsConstructor
@Tag(name = "Terms and Conditions", description = "Lifecycle operations for distributor terms and conditions")
public class TermsAndConditionsController {

    private final DistributorTermsService distributorTermsService;

    @Operation(summary = "List Terms and Conditions", description = "List all terms and conditions for a distributor.")
    @GetMapping
    public Mono<ResponseEntity<DistributorTermsAndConditionsDTO>> listTermsAndConditions(
            @PathVariable UUID distributorId) {
        return distributorTermsService.listTermsAndConditions(distributorId)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Get Active Terms and Conditions", description = "Retrieve the active terms and conditions for a distributor.")
    @GetMapping("/active")
    public Mono<ResponseEntity<DistributorTermsAndConditionsDTO>> getActiveTermsAndConditions(
            @PathVariable UUID distributorId) {
        return distributorTermsService.getActiveTermsAndConditions(distributorId)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Get Latest Terms and Conditions", description = "Retrieve the latest terms and conditions for a distributor.")
    @GetMapping("/latest")
    public Mono<ResponseEntity<DistributorTermsAndConditionsDTO>> getLatestTermsAndConditions(
            @PathVariable UUID distributorId) {
        return distributorTermsService.getLatestTermsAndConditions(distributorId)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Create Terms and Conditions", description = "Create new terms and conditions for a distributor.")
    @PostMapping
    public Mono<ResponseEntity<UUID>> createTermsAndConditions(
            @PathVariable UUID distributorId,
            @Valid @RequestBody CreateTermsAndConditionsCommand command) {
        return distributorTermsService.createTermsAndConditions(command.withDistributorId(distributorId))
                .map(id -> ResponseEntity.status(HttpStatus.CREATED).body(id));
    }

    @Operation(summary = "Get Terms and Conditions Detail", description = "Retrieve the detail of a specific terms and conditions entry.")
    @GetMapping("/{tcId}")
    public Mono<ResponseEntity<DistributorTermsAndConditionsDTO>> getTermsAndConditionsDetail(
            @PathVariable UUID distributorId,
            @PathVariable UUID tcId) {
        return distributorTermsService.getTermsAndConditionsDetail(distributorId, tcId)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Delete Terms and Conditions", description = "Delete terms and conditions from a distributor.")
    @DeleteMapping("/{tcId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<ResponseEntity<Void>> deleteTermsAndConditions(
            @PathVariable UUID distributorId,
            @PathVariable UUID tcId) {
        return distributorTermsService.deleteTermsAndConditions(distributorId, tcId)
                .thenReturn(ResponseEntity.noContent().build());
    }

    @Operation(summary = "Sign Terms and Conditions", description = "Sign terms and conditions for a distributor.")
    @PatchMapping("/{tcId}/sign")
    public Mono<ResponseEntity<DistributorTermsAndConditionsDTO>> signTermsAndConditions(
            @PathVariable UUID distributorId,
            @PathVariable UUID tcId,
            @RequestParam UUID signedBy) {
        return distributorTermsService.signTermsAndConditions(
                        new SignTermsAndConditionsCommand(distributorId, tcId, signedBy))
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Activate Terms and Conditions", description = "Activate terms and conditions for a distributor.")
    @PatchMapping("/{tcId}/activate")
    public Mono<ResponseEntity<DistributorTermsAndConditionsDTO>> activateTermsAndConditions(
            @PathVariable UUID distributorId,
            @PathVariable UUID tcId,
            @RequestParam UUID activatedBy) {
        return distributorTermsService.activateTermsAndConditions(
                        new ActivateTermsAndConditionsCommand(distributorId, tcId, activatedBy))
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Deactivate Terms and Conditions", description = "Deactivate terms and conditions for a distributor.")
    @PatchMapping("/{tcId}/deactivate")
    public Mono<ResponseEntity<DistributorTermsAndConditionsDTO>> deactivateTermsAndConditions(
            @PathVariable UUID distributorId,
            @PathVariable UUID tcId,
            @RequestParam UUID deactivatedBy) {
        return distributorTermsService.deactivateTermsAndConditions(
                        new DeactivateTermsAndConditionsCommand(distributorId, tcId, deactivatedBy))
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Has Active Signed Terms", description = "Check whether a distributor has active signed terms and conditions.")
    @GetMapping("/has-active-signed")
    public Mono<ResponseEntity<Boolean>> hasActiveSignedTerms(@PathVariable UUID distributorId) {
        return distributorTermsService.hasActiveSignedTerms(distributorId)
                .map(ResponseEntity::ok);
    }
}
