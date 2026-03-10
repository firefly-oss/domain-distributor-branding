package com.firefly.domain.distributor.branding.core.distributor.commands;

import org.fireflyframework.cqrs.command.Command;

import java.util.UUID;

/**
 * Command to delete a configuration from a distributor.
 */
public record DeleteConfigurationCommand(
        UUID distributorId,
        UUID configurationId
) implements Command<Void> {}
