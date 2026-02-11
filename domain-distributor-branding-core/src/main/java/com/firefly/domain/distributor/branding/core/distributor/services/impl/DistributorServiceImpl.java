package com.firefly.domain.distributor.branding.core.distributor.services.impl;

import com.firefly.domain.distributor.branding.core.distributor.commands.RegisterDistributorCommand;
import com.firefly.domain.distributor.branding.core.distributor.commands.ReviseBrandingCommand;
import com.firefly.domain.distributor.branding.core.distributor.commands.ReviseTermsAndConditionsCommand;
import com.firefly.domain.distributor.branding.core.distributor.commands.SetDefaultBrandingCommand;
import com.firefly.domain.distributor.branding.core.distributor.services.DistributorService;
import com.firefly.domain.distributor.branding.core.distributor.workflows.RegisterDistributorSaga;
import com.firefly.domain.distributor.branding.core.distributor.workflows.SetDefaultBrandingSaga;
import com.firefly.domain.distributor.branding.core.distributor.workflows.UpdateBrandingSaga;
import com.firefly.domain.distributor.branding.core.distributor.workflows.UpdateTermsAndConditionsSaga;
import com.firefly.transactional.saga.core.SagaResult;
import com.firefly.transactional.saga.engine.SagaEngine;
import com.firefly.transactional.saga.engine.StepInputs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DistributorServiceImpl implements DistributorService {

    private final SagaEngine engine;

    @Autowired
    public DistributorServiceImpl(SagaEngine engine){
        this.engine=engine;
    }

    @Override
    public Mono<SagaResult> onboardDistributor(RegisterDistributorCommand command) {
        StepInputs inputs = StepInputs.builder()
                .forStep(RegisterDistributorSaga::registerDistributor, command.getDistributorInfo())
                .forStep(RegisterDistributorSaga::registerTAndCTemplate, command.getTermsAndConditionsTemplate())
                .forStep(RegisterDistributorSaga::registerTermsAndConditions, command.getTermsAndConditions())
                .forStep(RegisterDistributorSaga::registerAuditLog, command.getAuditLog())
                .forStep(RegisterDistributorSaga::registerBranding, command.getBranding())

                .build();

        return engine.execute(RegisterDistributorSaga.class, inputs);
    }

    @Override
    public Mono<SagaResult> reviseBranding(ReviseBrandingCommand command) {
        StepInputs inputs = StepInputs.builder()
                .forStep(UpdateBrandingSaga::reviseBranding, command).build();

        return engine.execute(UpdateBrandingSaga.class, inputs);
    }

    @Override
    public Mono<SagaResult> setDefaultBranding(SetDefaultBrandingCommand command) {
        StepInputs inputs = StepInputs.builder()
                .forStep(SetDefaultBrandingSaga::setDefaultBranding, command).build();
        return engine.execute(SetDefaultBrandingSaga.class, inputs);
    }

    @Override
    public Mono<SagaResult> reviseTermsAndConditions(ReviseTermsAndConditionsCommand command) {
        StepInputs inputs = StepInputs.builder()
                .forStep(UpdateTermsAndConditionsSaga::reviseTermsAndConditions, command).build();

        return engine.execute(UpdateTermsAndConditionsSaga.class, inputs);
    }

}
