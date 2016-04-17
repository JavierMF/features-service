package org.javiermf.features.services.rest;

import org.javiermf.features.services.ProductsConfigurationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Component
@Produces("application/json")
public class ProductsConfigurationFeaturesResource {

    @Autowired
    ProductsConfigurationsService configurationsService;

    @GET
    public List<String> getConfigurationActivedFeatures(@PathParam("productName") String productName,
                                                        @PathParam("configurationName") String configurationName) {
        return configurationsService.getConfigurationActivedFeaturesNames(productName, configurationName);

    }

    @POST
    @Path("/{featureName}")
    public Response addFeatureToConfiguration(@PathParam("productName") String productName,
                                              @PathParam("configurationName") String configurationName,
                                              @PathParam("featureName") String featureName
    ) throws URISyntaxException {
        configurationsService.addFeatureFromConfiguration(productName, configurationName, featureName);
        return Response.created(new URI("/products/" + productName + "/configurations/" + configurationName + "/features/" + featureName)).build();
    }


    @DELETE
    @Path("/{featureName}")
    public Response deleteFeature(@PathParam("productName") String productName,
                                  @PathParam("configurationName") String configurationName,
                                  @PathParam("featureName") String featureName
    ) {
        configurationsService.removeFeatureFromConfiguration(productName, configurationName, featureName);
        return Response.noContent().build();
    }

}
