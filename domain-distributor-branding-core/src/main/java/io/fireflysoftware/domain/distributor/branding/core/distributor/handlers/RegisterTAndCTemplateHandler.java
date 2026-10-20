package io.fireflysoftware.domain.distributor.branding.core.distributor.handlers;

import org.fireflyframework.cqrs.annotations.CommandHandlerComponent;
import org.fireflyframework.cqrs.command.CommandHandler;
import io.fireflysoftware.core.distributor.sdk.api.TermsAndConditionsTemplatesApi;
import io.fireflysoftware.domain.distributor.branding.core.distributor.commands.RegisterTandCTemplateCommand;
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

