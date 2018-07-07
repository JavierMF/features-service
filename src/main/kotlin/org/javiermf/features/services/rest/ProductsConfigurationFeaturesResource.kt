package org.javiermf.features.services.rest

import org.javiermf.features.exceptions.WrongProductConfigurationException
import org.javiermf.features.services.ProductsConfigurationsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.net.URI
import java.net.URISyntaxException
import javax.ws.rs.*
import javax.ws.rs.core.Response

@Component
@Produces("application/json")
class ProductsConfigurationFeaturesResource {

    @Autowired
    internal var configurationsService: ProductsConfigurationsService? = null

    @GET
    fun getConfigurationActivedFeatures(@PathParam("productName") productName: String,
                                        @PathParam("configurationName") configurationName: String): List<String> {
        return configurationsService!!.getConfigurationActivedFeaturesNames(productName, configurationName)

    }

    @POST
    @Path("/{featureName}")
    @Throws(URISyntaxException::class, WrongProductConfigurationException::class)
    fun addFeatureToConfiguration(@PathParam("productName") productName: String,
                                  @PathParam("configurationName") configurationName: String,
                                  @PathParam("featureName") featureName: String
    ): Response {
        val result = configurationsService!!.addFeatureFromConfiguration(productName, configurationName, featureName)
        return if (result.isValid) {
            Response.created(URI("/products/$productName/configurations/$configurationName/features/$featureName")).build()
        } else {
            throw WrongProductConfigurationException(result.evaluationErrorMessages)
        }

    }


    @DELETE
    @Path("/{featureName}")
    @Throws(WrongProductConfigurationException::class)
    fun deleteFeature(@PathParam("productName") productName: String,
                      @PathParam("configurationName") configurationName: String,
                      @PathParam("featureName") featureName: String
    ): Response {
        val result = configurationsService!!.removeFeatureFromConfiguration(productName, configurationName, featureName)
        return if (result.isValid) {
            Response.noContent().build()
        } else {
            throw WrongProductConfigurationException(result.evaluationErrorMessages)
        }
    }

}
