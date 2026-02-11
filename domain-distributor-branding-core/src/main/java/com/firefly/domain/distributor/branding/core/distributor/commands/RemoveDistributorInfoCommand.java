package com.firefly.domain.distributor.branding.core.distributor.commands;

import com.firefly.common.cqrs.command.Command;

import java.util.UUID;

public record RemoveDistributorInfoCommand(
        UUID distributorId
) implements Command<Void>{}

