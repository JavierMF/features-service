package org.javiermf.features.services.rest;

import org.javiermf.features.services.ProductsConfigurationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
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

    @DELETE
    @Path("/{featureName}")
    public Response deleteConfiguration(@PathParam("productName") String productName,
                                        @PathParam("configurationName") String configurationName,
                                        @PathParam("featureName") String featureName
    ) {
        configurationsService.removeFeatureFromConfiguration(productName, configurationName, featureName);
        return Response.noContent().build();
    }

}
