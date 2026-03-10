package com.firefly.domain.distributor.branding.core.distributor.commands;

import com.firefly.core.distributor.sdk.model.DistributorOperationDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fireflyframework.cqrs.command.Command;

import java.util.UUID;

/**
 * Command to create a new operation for a distributor.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CreateOperationCommand extends DistributorOperationDTO implements Command<UUID> {
    private UUID distributorId;

    public CreateOperationCommand withDistributorId(UUID distributorId) {
        this.distributorId = distributorId;
        return this;
    }
}
