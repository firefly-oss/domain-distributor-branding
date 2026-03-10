package com.firefly.domain.distributor.branding.core.distributor.commands;

import com.firefly.core.distributor.sdk.model.DistributorAgentDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fireflyframework.cqrs.command.Command;

import java.util.UUID;

/**
 * Command to create a new agent for a distributor.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CreateAgentCommand extends DistributorAgentDTO implements Command<UUID> {
    private UUID distributorId;

    public CreateAgentCommand withDistributorId(UUID distributorId) {
        this.distributorId = distributorId;
        return this;
    }
}
