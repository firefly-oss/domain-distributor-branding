package com.firefly.domain.distributor.branding.core.distributor.workflows;

import org.fireflyframework.cqrs.command.CommandBus;
import com.firefly.domain.distributor.branding.core.distributor.commands.*;
import org.fireflyframework.orchestration.saga.annotation.Saga;
import org.fireflyframework.orchestration.saga.annotation.SagaStep;
import org.fireflyframework.orchestration.saga.annotation.StepEvent;
import org.fireflyframework.orchestration.core.context.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static com.firefly.domain.distributor.branding.core.utils.constants.DistributorConstants.*;
import static com.firefly.domain.distributor.branding.core.utils.constants.GlobalConstants.*;


@Saga(name = SAGA_REGISTER_DISTRIBUTOR)
@Service
public class RegisterDistributorSaga {

    private final CommandBus commandBus;

    @Autowired
    public RegisterDistributorSaga(CommandBus commandBus) {
        this.commandBus = commandBus;
    }


    @SagaStep(id = STEP_REGISTER_DISTRIBUTOR, compensate = COMPENSATE_REMOVE_DISTRIBUTOR)
    @StepEvent(type = EVENT_DISTRIBUTOR_REGISTERED)
    public Mono<UUID> registerDistributor(RegisterDistributorInfoCommand cmd, ExecutionContext ctx) {
        return commandBus.send(cmd)
                .doOnNext(distributorId -> ctx.putVariable(CTX_DISTRIBUTOR_ID, distributorId));
    }

    public Mono<Void> removeDistributor(UUID distributorId) {
        return commandBus.send(new RemoveDistributorInfoCommand(distributorId));
    }

    @SagaStep(id = STEP_REGISTER_TANDC_TEMPLATE, compensate = COMPENSATE_REMOVE_TANDC_TEMPLATE)
    @StepEvent(type = EVENT_TANDC_TEMPLATE_REGISTERED)
    public Mono<UUID> registerTAndCTemplate(RegisterTandCTemplateCommand cmd, ExecutionContext ctx) {
        return commandBus.send(cmd)
                .doOnNext(templateId -> ctx.putVariable(CTX_TEMPLATE_ID, templateId));
    }

    public Mono<Void> removeTAndCTemplate(UUID templateId) {
        return commandBus.send(new RemoveTAndCTemplateCommand(templateId));
    }

    @SagaStep(id = STEP_REGISTER_TERMS_AND_CONDITIONS, compensate = COMPENSATE_REMOVE_TERMS_AND_CONDITIONS, dependsOn = {STEP_REGISTER_TANDC_TEMPLATE, STEP_REGISTER_DISTRIBUTOR})
    @StepEvent(type = EVENT_TERMS_AND_CONDITIONS_REGISTERED)
    public Mono<UUID> registerTermsAndConditions(RegisterTermsAndConditionsCommand cmd, ExecutionContext ctx) {
        return commandBus.send(cmd
                        .withDistributorId(ctx.getVariableAs(CTX_DISTRIBUTOR_ID, UUID.class))
                        .withTemplate(ctx.getVariableAs(CTX_TEMPLATE_ID, UUID.class)));
    }

    public Mono<Void> removeTermsAndConditions(UUID termsAndConditionsId, ExecutionContext ctx) {
        return commandBus.send(new RemoveTermsAndConditionsCommand(ctx.getVariableAs(CTX_DISTRIBUTOR_ID, UUID.class), termsAndConditionsId));
    }

    @SagaStep(id = STEP_REGISTER_AUDIT_LOG, compensate = COMPENSATE_REMOVE_AUDIT_LOG, dependsOn = {STEP_REGISTER_DISTRIBUTOR})
    @StepEvent(type = EVENT_AUDIT_LOG_REGISTERED)
    public Mono<UUID> registerAuditLog(RegisterDistributorAuditLogCommand cmd, ExecutionContext ctx) {
        return commandBus.send(cmd.withDistributorId(ctx.getVariableAs(CTX_DISTRIBUTOR_ID, UUID.class)))
                .doOnNext(auditLogId -> ctx.putVariable(CTX_AUDIT_LOG_ID, auditLogId));
    }

    public Mono<Void> removeAuditLog(UUID auditLogId, ExecutionContext ctx) {
        return commandBus.send(new RemoveAuditLogCommand(ctx.getVariableAs(CTX_DISTRIBUTOR_ID, UUID.class), auditLogId));
    }

    @SagaStep(id = STEP_REGISTER_BRANDING, compensate = COMPENSATE_REMOVE_BRANDING, dependsOn = {STEP_REGISTER_DISTRIBUTOR})
    @StepEvent(type = EVENT_BRANDING_REGISTERED)
    public Mono<UUID> registerBranding(RegisterDistributorBrandingCommand cmd, ExecutionContext ctx) {
        return commandBus.send(cmd.withDistributorId(ctx.getVariableAs(CTX_DISTRIBUTOR_ID, UUID.class)))
                .doOnNext(brandingId -> ctx.putVariable(CTX_BRANDING_ID, brandingId));
    }

    public Mono<Void> removeBranding(UUID brandingId, ExecutionContext ctx) {
        return commandBus.send(new RemoveBrandingCommand(ctx.getVariableAs(CTX_DISTRIBUTOR_ID, UUID.class), brandingId));
    }


}


