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


package io.fireflysoftware.domain.distributor.branding.infra;

import io.fireflysoftware.core.distributor.sdk.api.*;
import io.fireflysoftware.core.distributor.sdk.invoker.ApiClient;
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

    @Bean
    public DistributorAuthorizedTerritoriesApi distributorAuthorizedTerritoriesApi() {
        return new DistributorAuthorizedTerritoriesApi(apiClient);
    }

    @Bean
    public DistributorAgenciesApi distributorAgenciesApi() {
        return new DistributorAgenciesApi(apiClient);
    }

    @Bean
    public DistributorAgentsApi distributorAgentsApi() {
        return new DistributorAgentsApi(apiClient);
    }

    @Bean
    public DistributorAgentAgencyApi distributorAgentAgencyApi() {
        return new DistributorAgentAgencyApi(apiClient);
    }

    @Bean
    public DistributorOperationsApi distributorOperationsApi() {
        return new DistributorOperationsApi(apiClient);
    }

    @Bean
    public DistributorConfigurationsApi distributorConfigurationsApi() {
        return new DistributorConfigurationsApi(apiClient);
    }

}
