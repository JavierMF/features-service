package org.javiermf.features.daos;

import org.javiermf.features.exceptions.ObjectNotFoundException;
import org.javiermf.features.models.Configuration;
import org.javiermf.features.models.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

@Repository
public class ProductsDAO {

    List<Product> allProducts;

    List<Configuration> allConfigurations;

    public ProductsDAO() {
        Product product1 = Product.buildWithFeatures("FEATURE_1", "FEATURE_2");
        product1.setName("Product_1");
        Product product2 = Product.buildWithFeatures("FEATURE_A", "FEATURE_B");
        product2.setName("Product_2");
        allProducts = asList(product1, product2);

        Configuration configuration1 = new Configuration();
        configuration1.setProduct(product1);
        configuration1.setName("Product_1_Configuration_1");
        configuration1.active("FEATURE_1");

        Configuration configuration2 = new Configuration();
        configuration2.setProduct(product1);
        configuration2.setName("Product_1_Configuration_2");
        configuration2.active("FEATURE_1");
        configuration2.active("FEATURE_2");
        allConfigurations = asList(configuration1, configuration2);
    }

    public List<Product> getAllProducts() {
        return allProducts;
    }

    public Product getProductByName(String productName) {
        for (Product product : allProducts) {
            if (productName.equalsIgnoreCase(product.getName())) return product;
        }

        throw new ObjectNotFoundException(productName);
    }


    public List<Configuration> getConfigurationsForProduct(String productName) {
        List<Configuration> configurationList = new ArrayList<Configuration>();
        for (Configuration configuration : allConfigurations) {
            if (productName.equalsIgnoreCase(configuration.getProduct().getName()))
                configurationList.add(configuration);
        }

        return configurationList;
    }

    public Configuration getConfigurationWithNameForProduct(String productName, String configurationName) {
        for (Configuration configuration : allConfigurations) {
            if (configuration.getName().equalsIgnoreCase(configurationName) &&
                    configuration.getProduct().getName().equalsIgnoreCase(productName)) {
                return configuration;
            }
        }
        throw new ObjectNotFoundException(productName + "," + configurationName);
    }
}
