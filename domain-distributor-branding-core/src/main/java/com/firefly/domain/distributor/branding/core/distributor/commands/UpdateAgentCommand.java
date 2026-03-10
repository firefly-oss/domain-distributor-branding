package com.firefly.domain.distributor.branding.core.distributor.commands;

import com.firefly.core.distributor.sdk.model.DistributorAgentDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fireflyframework.cqrs.command.Command;

import java.util.UUID;

/**
 * Command to update an existing agent for a distributor.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UpdateAgentCommand extends DistributorAgentDTO implements Command<UUID> {
    private UUID distributorId;
    private UUID id;

    public UpdateAgentCommand withDistributorId(UUID distributorId) {
        this.distributorId = distributorId;
        return this;
    }

    public UpdateAgentCommand withId(UUID id) {
        this.id = id;
        return this;
    }
}
