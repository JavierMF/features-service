package org.javiermf.features

import io.swagger.jaxrs.config.BeanConfig
import io.swagger.jaxrs.listing.ApiListingResource
import org.glassfish.jersey.server.ResourceConfig
import org.javiermf.features.services.rest.ProductsResource
import org.springframework.context.annotation.Configuration

@Configuration
class JerseyConfig : ResourceConfig() {
    init {
        register(ProductsResource::class.java)
        configureSwagger()
    }

    private fun configureSwagger() {
        register(ApiListingResource::class.java)
        val beanConfig = BeanConfig()
        beanConfig.version = "1.0"
        beanConfig.schemes = arrayOf("http")
        beanConfig.host = "localhost:8080"
        beanConfig.basePath = "/"
        beanConfig.resourcePackage = "org.javiermf.features.services.rest"
        beanConfig.prettyPrint = true
        beanConfig.scan = true
    }
}

