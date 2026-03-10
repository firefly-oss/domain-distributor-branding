package com.firefly.domain.distributor.branding.core.distributor.commands;

import com.firefly.core.distributor.sdk.model.DistributorConfigurationDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fireflyframework.cqrs.command.Command;

import java.util.UUID;

/**
 * Command to create a new configuration for a distributor.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CreateConfigurationCommand extends DistributorConfigurationDTO implements Command<UUID> {
    private UUID distributorId;

    public CreateConfigurationCommand withDistributorId(UUID distributorId) {
        this.distributorId = distributorId;
        return this;
    }
}
