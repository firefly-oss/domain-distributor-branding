package com.firefly.domain.distributor.branding.core.distributor.commands;

import com.firefly.core.distributor.sdk.model.DistributorOperationDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fireflyframework.cqrs.command.Command;

import java.util.UUID;

/**
 * Command to update an existing operation for a distributor.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UpdateOperationCommand extends DistributorOperationDTO implements Command<UUID> {
    private UUID distributorId;
    private UUID id;

    public UpdateOperationCommand withDistributorId(UUID distributorId) {
        this.distributorId = distributorId;
        return this;
    }

    public UpdateOperationCommand withId(UUID id) {
        this.id = id;
        return this;
    }
}
