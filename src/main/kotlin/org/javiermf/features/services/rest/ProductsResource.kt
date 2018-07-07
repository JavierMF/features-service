package org.javiermf.features.services.rest


import io.swagger.annotations.Api
import org.javiermf.features.models.Product
import org.javiermf.features.services.ProductsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.net.URI
import java.net.URISyntaxException
import javax.ws.rs.*
import javax.ws.rs.core.Response


@Component
@Path("/products")
@Produces("application/json")
@Api
class ProductsResource {

    @Autowired
    internal var productsService: ProductsService? = null

    @Autowired
    internal var productsConfigurationResource: ProductsConfigurationResource? = null

    @Autowired
    internal var productsFeaturesResource: ProductsFeaturesResource? = null

    @Autowired
    internal var productsConstraintsResource: ProductsConstraintsResource? = null


    val allProducts: List<String>
        @GET
        get() = productsService!!.allProductNames

    @Path("{productName}")
    @GET
    fun getProductByName(@PathParam("productName") productName: String): Product {
        return productsService!!.findByName(productName)
    }

    @Path("{productName}")
    @DELETE
    fun deleteProductByName(@PathParam("productName") productName: String): Response {
        productsService!!.deleteByName(productName)
        return Response.noContent().build()
    }

    @Path("{productName}")
    @POST
    @Throws(URISyntaxException::class)
    fun addProduct(@PathParam("productName") productName: String): Response {
        productsService!!.add(productName)
        return Response.created(URI("/products/$productName")).build()
    }


    @Path("{productName}/configurations")
    fun productsConfigurationResource(): ProductsConfigurationResource? {
        return productsConfigurationResource
    }

    @Path("{productName}/features")
    fun productsFeaturesResource(): ProductsFeaturesResource? {
        return productsFeaturesResource
    }

    @Path("{productName}/constraints")
    fun productsConstraintsResource(): ProductsConstraintsResource? {
        return productsConstraintsResource
    }

}
