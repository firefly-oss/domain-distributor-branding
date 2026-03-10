package com.firefly.domain.distributor.branding.core.distributor.commands;

import com.firefly.core.distributor.sdk.model.DistributorAuthorizedTerritoryDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fireflyframework.cqrs.command.Command;

import java.util.UUID;

/**
 * Command to update an existing authorized territory for a distributor.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UpdateTerritoryCommand extends DistributorAuthorizedTerritoryDTO implements Command<UUID> {
    private UUID distributorId;
    private UUID id;

    public UpdateTerritoryCommand withDistributorId(UUID distributorId) {
        this.distributorId = distributorId;
        return this;
    }

    public UpdateTerritoryCommand withId(UUID id) {
        this.id = id;
        return this;
    }
}
