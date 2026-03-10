package com.firefly.domain.distributor.branding.core.distributor.commands;

import lombok.Data;
import org.fireflyframework.cqrs.command.Command;
import org.fireflyframework.orchestration.saga.engine.SagaResult;

/**
 * Composite command to register a complete agency with its authorized territory
 * and operational coverage in a single transactional saga.
 */
@Data
public class RegisterAgencyCommand implements Command<SagaResult> {
    private CreateTerritoryCommand territory;
    private CreateAgencyCommand agency;
    private CreateOperationCommand operation;
}
