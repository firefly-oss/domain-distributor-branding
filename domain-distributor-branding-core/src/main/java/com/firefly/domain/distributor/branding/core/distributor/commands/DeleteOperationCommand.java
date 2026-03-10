package com.firefly.domain.distributor.branding.core.distributor.commands;

import org.fireflyframework.cqrs.command.Command;

import java.util.UUID;

/**
 * Command to delete an operation from a distributor.
 */
public record DeleteOperationCommand(
        UUID distributorId,
        UUID operationId
) implements Command<Void> {}
