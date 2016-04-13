package org.javiermf.features.daos;

import org.javiermf.features.exceptions.ObjectNotFoundException;
import org.javiermf.features.models.Product;
import org.javiermf.features.models.ProductConfiguration;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

@Repository
public class ProductsDAO {

    List<Product> allProducts;

    List<ProductConfiguration> allProductConfigurations;

    public ProductsDAO() {
        Product product1 = Product.buildWithFeatures("FEATURE_1", "FEATURE_2");
        product1.setName("Product_1");
        Product product2 = Product.buildWithFeatures("FEATURE_A", "FEATURE_B");
        product2.setName("Product_2");
        allProducts = asList(product1, product2);

        ProductConfiguration productConfiguration1 = new ProductConfiguration();
        productConfiguration1.setProduct(product1);
        productConfiguration1.setName("Product_1_Configuration_1");
        productConfiguration1.active("FEATURE_1");

        ProductConfiguration productConfiguration2 = new ProductConfiguration();
        productConfiguration2.setProduct(product1);
        productConfiguration2.setName("Product_1_Configuration_2");
        productConfiguration2.active("FEATURE_1");
        productConfiguration2.active("FEATURE_2");
        allProductConfigurations = asList(productConfiguration1, productConfiguration2);
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


    public List<ProductConfiguration> getConfigurationsForProduct(String productName) {
        List<ProductConfiguration> productConfigurationList = new ArrayList<ProductConfiguration>();
        for (ProductConfiguration productConfiguration : allProductConfigurations) {
            if (productName.equalsIgnoreCase(productConfiguration.getProduct().getName()))
                productConfigurationList.add(productConfiguration);
        }

        return productConfigurationList;
    }

    public ProductConfiguration getConfigurationWithNameForProduct(String productName, String configurationName) {
        for (ProductConfiguration productConfiguration : allProductConfigurations) {
            if (productConfiguration.getName().equalsIgnoreCase(configurationName) &&
                    productConfiguration.getProduct().getName().equalsIgnoreCase(productName)) {
                return productConfiguration;
            }
        }
        throw new ObjectNotFoundException(productName + "," + configurationName);
    }
}
