package com.firefly.domain.distributor.branding.core.distributor.commands;

import org.fireflyframework.cqrs.command.Command;

import java.util.UUID;

public record RemoveDistributorInfoCommand(
        UUID distributorId
) implements Command<Void>{}

