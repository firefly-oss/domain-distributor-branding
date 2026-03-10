package com.firefly.domain.distributor.branding.core.distributor.commands;

import org.fireflyframework.cqrs.command.Command;

import java.util.UUID;

/**
 * Command to delete an agency from a distributor.
 */
public record DeleteAgencyCommand(
        UUID distributorId,
        UUID agencyId
) implements Command<Void> {}
