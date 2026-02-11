package com.firefly.domain.distributor.branding.infra;

import com.firefly.core.distributor.sdk.api.*;
import com.firefly.core.distributor.sdk.invoker.ApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Default implementation of the ClientFactory interface.
 * Creates client service instances using the appropriate API clients and dependencies.
 */
@Component
public class ClientFactory {

    private final ApiClient apiClient;

    @Autowired
    public ClientFactory(
            DistributorCatalogProperties distributorCatalogProperties) {
        this.apiClient = new ApiClient();
        this.apiClient.setBasePath(distributorCatalogProperties.getBasePath());
    }

    @Bean
    public DistributorApi distributorApi() {
        return new DistributorApi(apiClient);
    }

    @Bean
    public TermsAndConditionsTemplatesApi termsAndConditionsTemplatesApi() {
        return new TermsAndConditionsTemplatesApi(apiClient);
    }

    @Bean
    public DistributorTermsAndConditionsApi distributorTermsAndConditionsApi() {
        return new DistributorTermsAndConditionsApi(apiClient);
    }

    @Bean
    public DistributorAuditLogApi distributorAuditLogApi() {
        return new DistributorAuditLogApi(apiClient);
    }

    @Bean
    public DistributorBrandingApi distributorBrandingApi() {
        return new DistributorBrandingApi(apiClient);
    }


}
