package org.javiermf.features.services.rest

import org.javiermf.features.models.ProductConfiguration
import org.javiermf.features.services.ProductsConfigurationsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.net.URI
import java.net.URISyntaxException
import javax.ws.rs.*
import javax.ws.rs.core.Response

@Component
@Produces("application/json")
class ProductsConfigurationResource {

    @Autowired
    internal var configurationsService: ProductsConfigurationsService? = null

    @Autowired
    internal var productsConfigurationFeaturesResource: ProductsConfigurationFeaturesResource? = null

    @GET
    fun getConfigurationsForProduct(@PathParam("productName") productName: String): List<String> {
        return configurationsService!!.getConfigurationsNamesForProduct(productName)

    }

    @Path("/{configurationName}")
    @GET
    fun getConfigurationWithNameForProduct(@PathParam("productName") productName: String,
                                           @PathParam("configurationName") configurationName: String): ProductConfiguration? {
        return configurationsService!!.findByNameAndProductName(productName, configurationName)
    }

    @POST
    @Path("/{configurationName}")
    @Throws(URISyntaxException::class)
    fun addConfiguration(@PathParam("productName") productName: String,
                         @PathParam("configurationName") configurationName: String): Response {
        configurationsService!!.add(productName, configurationName)
        return Response.created(URI("/products/$productName/configurations/$configurationName")).build()
    }

    @DELETE
    @Path("/{configurationName}")
    @Throws(URISyntaxException::class)
    fun deleteConfiguration(@PathParam("productName") productName: String,
                            @PathParam("configurationName") configurationName: String): Response {
        configurationsService!!.deleteByName(productName, configurationName)
        return Response.noContent().build()
    }

    @Path("/{configurationName}/features")
    fun getConfigurationActivedFeatures(@PathParam("productName") productName: String,
                                        @PathParam("configurationName") configurationName: String): ProductsConfigurationFeaturesResource? {
        return productsConfigurationFeaturesResource

    }


}
