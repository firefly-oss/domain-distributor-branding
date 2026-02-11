package com.firefly.domain.distributor.branding.core.distributor.handlers;

import com.firefly.common.cqrs.annotations.CommandHandlerComponent;
import com.firefly.common.cqrs.command.CommandHandler;
import com.firefly.core.distributor.sdk.api.TermsAndConditionsTemplatesApi;
import com.firefly.domain.distributor.branding.core.distributor.commands.RemoveTAndCTemplateCommand;
import reactor.core.publisher.Mono;

@CommandHandlerComponent
public class RemoveTAndCTemplateHandler extends CommandHandler<RemoveTAndCTemplateCommand, Void> {

    private final TermsAndConditionsTemplatesApi termsAndConditionsTemplatesApi;

    public RemoveTAndCTemplateHandler(TermsAndConditionsTemplatesApi termsAndConditionsTemplatesApi) {
        this.termsAndConditionsTemplatesApi = termsAndConditionsTemplatesApi;
    }

    @Override
    protected Mono<Void> doHandle(RemoveTAndCTemplateCommand cmd) {
        return termsAndConditionsTemplatesApi.deleteTemplate(cmd.templateId());
    }
}

