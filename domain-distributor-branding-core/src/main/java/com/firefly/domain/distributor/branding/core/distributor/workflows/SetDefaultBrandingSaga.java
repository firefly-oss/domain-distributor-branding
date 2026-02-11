package com.firefly.domain.distributor.branding.core.distributor.workflows;

import com.firefly.common.cqrs.command.CommandBus;
import com.firefly.domain.distributor.branding.core.distributor.commands.SetDefaultBrandingCommand;
import com.firefly.transactional.saga.annotations.Saga;
import com.firefly.transactional.saga.annotations.SagaStep;
import com.firefly.transactional.saga.annotations.StepEvent;
import com.firefly.transactional.saga.core.SagaContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static com.firefly.domain.distributor.branding.core.utils.constants.DistributorConstants.*;

@Saga(name = SAGA_SET_DEFAULT_BRANDING)
@Service
public class SetDefaultBrandingSaga {

    private final CommandBus commandBus;

    @Autowired
    public SetDefaultBrandingSaga(CommandBus commandBus) {
        this.commandBus = commandBus;
    }

    @SagaStep(id = STEP_SET_DEFAULT_BRANDING)
    @StepEvent(type = EVENT_BRANDING_UPDATED)
    public Mono<UUID> setDefaultBranding(SetDefaultBrandingCommand cmd, SagaContext ctx) {
        return commandBus.send(cmd);
    }


}

