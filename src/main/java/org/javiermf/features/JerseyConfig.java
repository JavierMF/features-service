package org.javiermf.features;

import org.glassfish.jersey.server.ResourceConfig;
import org.javiermf.features.services.rest.ProductsResource;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        register(ProductsResource.class);
    }
}

