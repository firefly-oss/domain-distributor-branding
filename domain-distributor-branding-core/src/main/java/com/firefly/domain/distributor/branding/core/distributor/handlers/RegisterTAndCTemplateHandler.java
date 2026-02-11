package com.firefly.domain.distributor.branding.core.distributor.handlers;

import com.firefly.common.cqrs.annotations.CommandHandlerComponent;
import com.firefly.common.cqrs.command.CommandHandler;
import com.firefly.core.distributor.sdk.api.TermsAndConditionsTemplatesApi;
import com.firefly.domain.distributor.branding.core.distributor.commands.RegisterTandCTemplateCommand;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.UUID;

@CommandHandlerComponent
public class RegisterTAndCTemplateHandler extends CommandHandler<RegisterTandCTemplateCommand, UUID> {

    private final TermsAndConditionsTemplatesApi termsAndConditionsTemplatesApi;

    public RegisterTAndCTemplateHandler(TermsAndConditionsTemplatesApi termsAndConditionsTemplatesApi) {
        this.termsAndConditionsTemplatesApi = termsAndConditionsTemplatesApi;
    }

    @Override
    protected Mono<UUID> doHandle(RegisterTandCTemplateCommand cmd) {
        return termsAndConditionsTemplatesApi.createTemplate(cmd, UUID.randomUUID().toString())
                .mapNotNull(templateDTO ->
                        Objects.requireNonNull(Objects.requireNonNull(templateDTO)).getId());
    }
}

