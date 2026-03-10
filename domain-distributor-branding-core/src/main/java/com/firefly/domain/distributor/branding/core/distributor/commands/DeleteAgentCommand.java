package com.firefly.domain.distributor.branding.core.distributor.commands;

import org.fireflyframework.cqrs.command.Command;

import java.util.UUID;

/**
 * Command to delete an agent from a distributor.
 */
public record DeleteAgentCommand(
        UUID distributorId,
        UUID agentId
) implements Command<Void> {}
