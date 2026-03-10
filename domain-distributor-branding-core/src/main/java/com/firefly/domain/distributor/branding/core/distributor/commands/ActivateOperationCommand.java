package com.firefly.domain.distributor.branding.core.distributor.commands;

import com.firefly.core.distributor.sdk.model.DistributorOperationDTO;
import org.fireflyframework.cqrs.command.Command;

import java.util.UUID;

/**
 * Command to activate an operation for a distributor.
 */
public record ActivateOperationCommand(
        UUID distributorId,
        UUID operationId,
        UUID activatedBy
) implements Command<DistributorOperationDTO> {}
