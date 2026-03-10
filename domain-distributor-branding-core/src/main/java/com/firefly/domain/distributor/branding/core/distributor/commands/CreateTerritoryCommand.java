package com.firefly.domain.distributor.branding.core.distributor.commands;

import com.firefly.core.distributor.sdk.model.DistributorAuthorizedTerritoryDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fireflyframework.cqrs.command.Command;

import java.util.UUID;

/**
 * Command to create a new authorized territory for a distributor.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CreateTerritoryCommand extends DistributorAuthorizedTerritoryDTO implements Command<UUID> {
    private UUID distributorId;

    public CreateTerritoryCommand withDistributorId(UUID distributorId) {
        this.distributorId = distributorId;
        return this;
    }
}
