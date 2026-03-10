package com.firefly.domain.distributor.branding.core.distributor.commands;

import lombok.Data;
import org.fireflyframework.cqrs.command.Command;
import org.fireflyframework.orchestration.saga.engine.SagaResult;

/**
 * Composite command to create an agent and assign it to an agency
 * in a single transactional saga.
 */
@Data
public class RegisterAgentWithAssignmentCommand implements Command<SagaResult> {
    private CreateAgentCommand agent;
    private AssignAgentAgencyCommand assignment;
}
