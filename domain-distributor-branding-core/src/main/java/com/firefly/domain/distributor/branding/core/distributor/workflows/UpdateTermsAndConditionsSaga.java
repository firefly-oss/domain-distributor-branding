package com.firefly.domain.distributor.branding.core.distributor.workflows;

import com.firefly.common.cqrs.command.CommandBus;
import com.firefly.domain.distributor.branding.core.distributor.commands.ReviseTermsAndConditionsCommand;
import com.firefly.transactional.saga.annotations.Saga;
import com.firefly.transactional.saga.annotations.SagaStep;
import com.firefly.transactional.saga.annotations.StepEvent;
import com.firefly.transactional.saga.core.SagaContext;
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
    public Mono<UUID> reviseTermsAndConditions(ReviseTermsAndConditionsCommand cmd, SagaContext ctx) {
        return commandBus.send(cmd);
    }


}

