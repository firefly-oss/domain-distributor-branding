package com.firefly.domain.distributor.branding.core.distributor.commands;

import org.fireflyframework.cqrs.command.Command;

import java.util.UUID;

/**
 * Command to remove an agent-agency assignment from a distributor.
 */
public record RemoveAgentAgencyCommand(
        UUID distributorId,
        UUID agentAgencyId
) implements Command<Void> {}
