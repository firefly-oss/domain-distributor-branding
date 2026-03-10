package com.firefly.domain.distributor.branding.core.distributor.commands;

import com.firefly.core.distributor.sdk.model.DistributorAgencyDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fireflyframework.cqrs.command.Command;

import java.util.UUID;

/**
 * Command to create a new agency for a distributor.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CreateAgencyCommand extends DistributorAgencyDTO implements Command<UUID> {
    private UUID distributorId;

    public CreateAgencyCommand withDistributorId(UUID distributorId) {
        this.distributorId = distributorId;
        return this;
    }
}
