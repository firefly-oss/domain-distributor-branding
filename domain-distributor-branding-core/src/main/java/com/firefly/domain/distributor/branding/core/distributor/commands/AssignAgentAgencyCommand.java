package com.firefly.domain.distributor.branding.core.distributor.commands;

import com.firefly.core.distributor.sdk.model.DistributorAgentAgencyDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fireflyframework.cqrs.command.Command;

import java.util.UUID;

/**
 * Command to assign an agent to an agency within a distributor.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AssignAgentAgencyCommand extends DistributorAgentAgencyDTO implements Command<UUID> {
    private UUID distributorId;

    public AssignAgentAgencyCommand withDistributorId(UUID distributorId) {
        this.distributorId = distributorId;
        return this;
    }
}
