package org.javiermf.features.services.rest

import org.javiermf.features.services.ProductsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.net.URI
import java.net.URISyntaxException
import javax.ws.rs.*
import javax.ws.rs.core.Response

@Component
@Produces("application/json")
class ProductsConstraintsResource {

    @Autowired
    internal var productsService: ProductsService? = null

    @POST
    @Path("requires")
    @Throws(URISyntaxException::class)
    fun addRequiresConstraintToProduct(@PathParam("productName") productName: String,
                                       @FormParam("sourceFeature") sourceFeatureName: String,
                                       @FormParam("requiredFeature") requiredFeatureName: String
    ): Response {
        val newConstraint = productsService!!.addRequiresConstraintToProduct(productName, sourceFeatureName, requiredFeatureName)
        return Response.created(URI("/products/" + productName + "/constraints/" + newConstraint.id)).build()

    }

    @POST
    @Path("excludes")
    @Throws(URISyntaxException::class)
    fun addExcludesConstraintToProduct(@PathParam("productName") productName: String,
                                       @FormParam("sourceFeature") sourceFeatureName: String,
                                       @FormParam("excludedFeature") excludedFeatureName: String
    ): Response {
        val newConstraint = productsService!!.addExcludesConstraintToProduct(productName, sourceFeatureName, excludedFeatureName)
        return Response.created(URI("/products/" + productName + "/constraint/" + newConstraint.id)).build()

    }

    @DELETE
    @Path("{constraintId}")
    @Throws(URISyntaxException::class)
    fun deleteConstraint(@PathParam("productName") productName: String,
                         @PathParam("constraintId") constraintId: Long?
    ): Response {
        productsService!!.deleteConstraintFromProduct(productName, constraintId)
        return Response.noContent().build()

    }
}
