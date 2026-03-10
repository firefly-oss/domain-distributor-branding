package com.firefly.domain.distributor.branding.core.distributor.commands;

import com.firefly.core.distributor.sdk.model.DistributorAgencyDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fireflyframework.cqrs.command.Command;

import java.util.UUID;

/**
 * Command to update an existing agency for a distributor.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UpdateAgencyCommand extends DistributorAgencyDTO implements Command<UUID> {
    private UUID distributorId;
    private UUID id;

    public UpdateAgencyCommand withDistributorId(UUID distributorId) {
        this.distributorId = distributorId;
        return this;
    }

    public UpdateAgencyCommand withId(UUID id) {
        this.id = id;
        return this;
    }
}
