/*
 * Copyright 2025 Firefly Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package io.fireflysoftware.domain.distributor.branding.core.distributor.commands;

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
