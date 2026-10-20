package io.fireflysoftware.domain.distributor.branding.core.distributor.handlers;

import org.fireflyframework.cqrs.annotations.CommandHandlerComponent;
import org.fireflyframework.cqrs.command.CommandHandler;
import io.fireflysoftware.core.distributor.sdk.api.TermsAndConditionsTemplatesApi;
import io.fireflysoftware.domain.distributor.branding.core.distributor.commands.RemoveTAndCTemplateCommand;
import reactor.core.publisher.Mono;
import java.util.UUID;

@CommandHandlerComponent
public class RemoveTAndCTemplateHandler extends CommandHandler<RemoveTAndCTemplateCommand, Void> {

    private final TermsAndConditionsTemplatesApi termsAndConditionsTemplatesApi;

    public RemoveTAndCTemplateHandler(TermsAndConditionsTemplatesApi termsAndConditionsTemplatesApi) {
        this.termsAndConditionsTemplatesApi = termsAndConditionsTemplatesApi;
    }

    @Override
    protected Mono<Void> doHandle(RemoveTAndCTemplateCommand cmd) {
        return termsAndConditionsTemplatesApi.deleteTemplate(cmd.templateId(), UUID.randomUUID().toString());
    }
}

