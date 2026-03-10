package com.firefly.domain.distributor.branding.core.distributor.commands;

import com.firefly.core.distributor.sdk.model.DistributorOperationDTO;
import org.fireflyframework.cqrs.command.Command;

import java.util.UUID;

/**
 * Command to deactivate an operation for a distributor.
 */
public record DeactivateOperationCommand(
        UUID distributorId,
        UUID operationId,
        UUID deactivatedBy
) implements Command<DistributorOperationDTO> {}
