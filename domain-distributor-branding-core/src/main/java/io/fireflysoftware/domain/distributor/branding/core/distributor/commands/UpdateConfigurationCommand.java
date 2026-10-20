package io.fireflysoftware.domain.distributor.branding.core.distributor.commands;

import io.fireflysoftware.core.distributor.sdk.model.DistributorConfigurationDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fireflyframework.cqrs.command.Command;

import java.util.UUID;

/**
 * Command to update an existing configuration for a distributor.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UpdateConfigurationCommand extends DistributorConfigurationDTO implements Command<UUID> {
    private UUID distributorId;
    private UUID id;

    public UpdateConfigurationCommand withDistributorId(UUID distributorId) {
        this.distributorId = distributorId;
        return this;
    }

    public UpdateConfigurationCommand withId(UUID id) {
        this.id = id;
        return this;
    }
}
