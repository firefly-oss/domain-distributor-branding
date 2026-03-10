package com.firefly.domain.distributor.branding.core.distributor.commands;

import com.firefly.core.distributor.sdk.model.DistributorTermsAndConditionsDTO;
import org.fireflyframework.cqrs.command.Command;

import java.util.UUID;

/**
 * Command to activate terms and conditions for a distributor.
 */
public record ActivateTermsAndConditionsCommand(
        UUID distributorId,
        UUID termsAndConditionsId,
        UUID activatedBy
) implements Command<DistributorTermsAndConditionsDTO> {}
