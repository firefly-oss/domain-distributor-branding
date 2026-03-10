package com.firefly.domain.distributor.branding.core.distributor.commands;

import com.firefly.core.distributor.sdk.model.DistributorTermsAndConditionsDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fireflyframework.cqrs.command.Command;

import java.util.UUID;

/**
 * Command to create new terms and conditions for a distributor
 * outside the onboarding saga flow.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CreateTermsAndConditionsCommand extends DistributorTermsAndConditionsDTO implements Command<UUID> {
    private UUID distributorId;

    public CreateTermsAndConditionsCommand withDistributorId(UUID distributorId) {
        this.distributorId = distributorId;
        return this;
    }
}
