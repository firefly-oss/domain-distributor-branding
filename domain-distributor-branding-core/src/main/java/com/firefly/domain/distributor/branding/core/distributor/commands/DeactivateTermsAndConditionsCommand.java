package com.firefly.domain.distributor.branding.core.distributor.commands;

import com.firefly.core.distributor.sdk.model.DistributorTermsAndConditionsDTO;
import org.fireflyframework.cqrs.command.Command;

import java.util.UUID;

/**
 * Command to deactivate terms and conditions for a distributor.
 */
public record DeactivateTermsAndConditionsCommand(
        UUID distributorId,
        UUID termsAndConditionsId,
        UUID deactivatedBy
) implements Command<DistributorTermsAndConditionsDTO> {}
