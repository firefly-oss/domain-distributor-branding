/*
 * Copyright 2025 Firefly Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package io.fireflysoftware.domain.distributor.branding.web.controller;

import io.fireflysoftware.domain.distributor.branding.core.distributor.commands.RegisterDistributorCommand;
import io.fireflysoftware.domain.distributor.branding.core.distributor.commands.ReviseBrandingCommand;
import io.fireflysoftware.domain.distributor.branding.core.distributor.commands.ReviseTermsAndConditionsCommand;
import io.fireflysoftware.domain.distributor.branding.core.distributor.commands.SetDefaultBrandingCommand;
import io.fireflysoftware.domain.distributor.branding.core.distributor.services.DistributorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/distributors")
@RequiredArgsConstructor
@Tag(name = "Distributor", description = "CQ queries and registration for Distributors")
public class DistributorController {

    private final DistributorService distributorService;

    @Operation(summary = "Onboard Distributor", description = "Onboard a new distributor with branding and terms & conditions.")
    @PostMapping
    public Mono<ResponseEntity<Object>> onboardDistributor(@Valid @RequestBody RegisterDistributorCommand command) {
        return distributorService.onboardDistributor(command)
                .map(result -> {
                    UUID id = result.resultOf("registerDistributor", UUID.class).orElse(null);
                    Map<String, Object> body = new LinkedHashMap<>();
                    body.put("id", id);
                    return ResponseEntity.ok((Object) body);
                });
    }

    @Operation(summary = "Revise Branding", description = "Revise distributor's branding configuration.")
    @PutMapping("/{distributorId}/branding/{brandingId}")
    public Mono<ResponseEntity<Object>> reviseBranding(
            @PathVariable UUID distributorId,
            @PathVariable UUID brandingId,
            @Valid @RequestBody ReviseBrandingCommand command) {
        return distributorService.reviseBranding(command.withDistributorId(distributorId).withId(brandingId))
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Set Default Branding", description = "Mark branding as default for the distributor.")
    @PutMapping("/{distributorId}/branding/{brandingId}/set-default")
    public Mono<ResponseEntity<Object>> setDefaultBranding(
            @PathVariable UUID distributorId,
            @PathVariable UUID brandingId,
            @Valid @RequestBody SetDefaultBrandingCommand command) {
        return distributorService.setDefaultBranding(command.withDistributorId(distributorId).withId(brandingId))
                .thenReturn(ResponseEntity.ok().build());
    }

    @Operation(summary = "Revise Terms and Conditions", description = "Revise or version distributor's terms & conditions.")
    @PutMapping("/{distributorId}/terms-and-conditions/{tcId}")
    public Mono<ResponseEntity<Object>> reviseTermsAndConditions(@PathVariable UUID distributorId, @PathVariable UUID tcId, @Valid @RequestBody ReviseTermsAndConditionsCommand command) {
        return distributorService.reviseTermsAndConditions(command.withDistributorId(distributorId).withId(tcId))
                .map(ResponseEntity::ok);
    }


}
