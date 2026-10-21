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


package io.fireflysoftware.domain.distributor.branding.core.distributor.services.impl;

import io.fireflysoftware.domain.distributor.branding.core.distributor.commands.RegisterDistributorCommand;
import io.fireflysoftware.domain.distributor.branding.core.distributor.commands.ReviseBrandingCommand;
import io.fireflysoftware.domain.distributor.branding.core.distributor.commands.ReviseTermsAndConditionsCommand;
import io.fireflysoftware.domain.distributor.branding.core.distributor.commands.SetDefaultBrandingCommand;
import io.fireflysoftware.domain.distributor.branding.core.distributor.services.DistributorService;
import io.fireflysoftware.domain.distributor.branding.core.distributor.workflows.RegisterDistributorSaga;
import io.fireflysoftware.domain.distributor.branding.core.distributor.workflows.SetDefaultBrandingSaga;
import io.fireflysoftware.domain.distributor.branding.core.distributor.workflows.UpdateBrandingSaga;
import io.fireflysoftware.domain.distributor.branding.core.distributor.workflows.UpdateTermsAndConditionsSaga;
import org.fireflyframework.orchestration.saga.engine.SagaResult;
import org.fireflyframework.orchestration.saga.engine.SagaEngine;
import org.fireflyframework.orchestration.saga.engine.StepInputs;
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
                .forStepId("registerDistributor", command.getDistributorInfo())
                .forStepId("registerTAndCTemplate", command.getTermsAndConditionsTemplate())
                .forStepId("registerTermsAndConditions", command.getTermsAndConditions())
                .forStepId("registerAuditLog", command.getAuditLog())
                .forStepId("registerBranding", command.getBranding())

                .build();

        return engine.execute("RegisterDistributorSaga", inputs);
    }

    @Override
    public Mono<SagaResult> reviseBranding(ReviseBrandingCommand command) {
        StepInputs inputs = StepInputs.builder()
                .forStepId("updateBranding", command).build();

        return engine.execute("UpdateBrandingSaga", inputs);
    }

    @Override
    public Mono<SagaResult> setDefaultBranding(SetDefaultBrandingCommand command) {
        StepInputs inputs = StepInputs.builder()
                .forStepId("setDefaultBranding", command).build();
        return engine.execute("SetDefaultBrandingSaga", inputs);
    }

    @Override
    public Mono<SagaResult> reviseTermsAndConditions(ReviseTermsAndConditionsCommand command) {
        StepInputs inputs = StepInputs.builder()
                .forStepId("updateTermsAndConditions", command).build();

        return engine.execute("UpdateTermsAndConditionsSaga", inputs);
    }

}
