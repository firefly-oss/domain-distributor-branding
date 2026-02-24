package com.firefly.domain.distributor.branding.core.distributor.workflows;

import org.fireflyframework.cqrs.command.CommandBus;
import com.firefly.domain.distributor.branding.core.distributor.commands.ReviseTermsAndConditionsCommand;
import org.fireflyframework.orchestration.saga.annotation.Saga;
import org.fireflyframework.orchestration.saga.annotation.SagaStep;
import org.fireflyframework.orchestration.saga.annotation.StepEvent;
import org.fireflyframework.orchestration.core.context.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static com.firefly.domain.distributor.branding.core.utils.constants.DistributorConstants.*;

@Saga(name = SAGA_UPDATE_TERMS_AND_CONDITIONS)
@Service
public class UpdateTermsAndConditionsSaga {

    private final CommandBus commandBus;

    @Autowired
    public UpdateTermsAndConditionsSaga(CommandBus commandBus) {
        this.commandBus = commandBus;
    }

    @SagaStep(id = STEP_UPDATE_TERMS_AND_CONDITIONS)
    @StepEvent(type = EVENT_TERMS_AND_CONDITIONS_UPDATED)
    public Mono<UUID> reviseTermsAndConditions(ReviseTermsAndConditionsCommand cmd, ExecutionContext ctx) {
        return commandBus.send(cmd);
    }


}

