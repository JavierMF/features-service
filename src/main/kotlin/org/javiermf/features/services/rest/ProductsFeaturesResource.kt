package org.javiermf.features.services.rest

import org.javiermf.features.models.Feature
import org.javiermf.features.services.ProductsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.net.URI
import java.net.URISyntaxException
import javax.ws.rs.*
import javax.ws.rs.core.Response

@Component
@Produces("application/json")
class ProductsFeaturesResource {

    @Autowired
    internal var productsService: ProductsService? = null

    @GET
    fun getFeaturesForProduct(@PathParam("productName") productName: String): Set<Feature> {
        return productsService!!.getFeaturesForProduct(productName)

    }

    @POST
    @Path("/{featureName}")
    @Throws(URISyntaxException::class)
    fun addFeatureToProduct(@PathParam("productName") productName: String,
                            @PathParam("featureName") featureName: String,
                            @FormParam("description") featureDescription: String): Response {
        productsService!!.addFeatureToProduct(productName, featureName, featureDescription)
        return Response.created(URI("/products/$productName/features/$featureName")).build()
    }

    @PUT
    @Path("/{featureName}")
    @Throws(URISyntaxException::class)
    fun updateFeatureOfProduct(@PathParam("productName") productName: String,
                               @PathParam("featureName") featureName: String,
                               @FormParam("description") featureDescription: String): Feature {
        return productsService!!.updateFeatureOfProduct(productName, featureName, featureDescription)
    }

    @DELETE
    @Path("/{featureName}")
    @Throws(URISyntaxException::class)
    fun deleteFeatureOfProduct(@PathParam("productName") productName: String,
                               @PathParam("featureName") featureName: String): Response {
        productsService!!.deleteFeatureOfProduct(productName, featureName)
        return Response.noContent().build()
    }
}
