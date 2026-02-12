package com.firefly.domain.distributor.branding.web;

import org.fireflyframework.transactional.shared.annotations.EnableTransactionalEngine;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication(
        scanBasePackages = {
                "com.firefly.domain.distributor.branding",
                "com.firefly.common.web"  // Scan common web library configurations
        }
)
@EnableWebFlux
@ConfigurationPropertiesScan
@OpenAPIDefinition(
        info = @Info(
                title = "${spring.application.name}",
                version = "${spring.application.version}",
                description = "${spring.application.description}",
                contact = @Contact(
                        name = "${spring.application.team.name}",
                        email = "${spring.application.team.email}"
                )
        ),
        servers = {
                @Server(
                        url = "http://core.getfirefly.io/domain-distributor-branding",
                        description = "Development Environment"
                ),
                @Server(
                        url = "/",
                        description = "Local Development Environment"
                )
        }
)
@EnableTransactionalEngine
public class DistributorDomainBrandingApplication {
    public static void main(String[] args) {
        SpringApplication.run(DistributorDomainBrandingApplication.class, args);
    }
}