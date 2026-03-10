package com.firefly.domain.distributor.branding.web.controller;

import com.firefly.core.distributor.sdk.model.DistributorConfigurationDTO;
import com.firefly.core.distributor.sdk.model.PaginationResponse;
import com.firefly.domain.distributor.branding.core.distributor.commands.CreateConfigurationCommand;
import com.firefly.domain.distributor.branding.core.distributor.commands.UpdateConfigurationCommand;
import com.firefly.domain.distributor.branding.core.distributor.services.DistributorConfigService;
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
@RequestMapping("/api/v1/distributors/{distributorId}/configurations")
@RequiredArgsConstructor
@Tag(name = "Configuration", description = "CRUD operations for distributor configurations")
public class ConfigurationController {

    private final DistributorConfigService distributorConfigService;

    @Operation(summary = "List Configurations", description = "List all configurations for a distributor.")
    @GetMapping
    public Mono<ResponseEntity<PaginationResponse>> listConfigurations(@PathVariable UUID distributorId) {
        return distributorConfigService.listConfigurations(distributorId)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Create Configuration", description = "Create a new configuration for a distributor.")
    @PostMapping
    public Mono<ResponseEntity<UUID>> createConfiguration(
            @PathVariable UUID distributorId,
            @Valid @RequestBody CreateConfigurationCommand command) {
        return distributorConfigService.createConfiguration(command.withDistributorId(distributorId))
                .map(id -> ResponseEntity.status(HttpStatus.CREATED).body(id));
    }

    @Operation(summary = "Get Configuration", description = "Retrieve a single configuration by its identifier.")
    @GetMapping("/{configurationId}")
    public Mono<ResponseEntity<DistributorConfigurationDTO>> getConfiguration(
            @PathVariable UUID distributorId,
            @PathVariable UUID configurationId) {
        return distributorConfigService.getConfiguration(distributorId, configurationId)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Update Configuration", description = "Update an existing configuration.")
    @PutMapping("/{configurationId}")
    public Mono<ResponseEntity<UUID>> updateConfiguration(
            @PathVariable UUID distributorId,
            @PathVariable UUID configurationId,
            @Valid @RequestBody UpdateConfigurationCommand command) {
        return distributorConfigService.updateConfiguration(command.withDistributorId(distributorId).withId(configurationId))
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Delete Configuration", description = "Delete a configuration from a distributor.")
    @DeleteMapping("/{configurationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<ResponseEntity<Void>> deleteConfiguration(
            @PathVariable UUID distributorId,
            @PathVariable UUID configurationId) {
        return distributorConfigService.deleteConfiguration(distributorId, configurationId)
                .thenReturn(ResponseEntity.noContent().build());
    }
}
