package com.firefly.domain.distributor.branding.core.distributor.commands;

import org.fireflyframework.cqrs.command.Command;

import java.util.UUID;

/**
 * Command to delete an authorized territory from a distributor.
 */
public record DeleteTerritoryCommand(
        UUID distributorId,
        UUID territoryId
) implements Command<Void> {}
