package org.javiermf.features.daos;

import com.mysema.query.jpa.impl.JPAQuery;
import org.javiermf.features.models.ProductConfiguration;
import org.javiermf.features.models.QProduct;
import org.javiermf.features.models.QProductConfiguration;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ProductsConfigurationsDAO {

    @PersistenceContext
    private EntityManager entityManager;


    public List<ProductConfiguration> findByProductName(String productName) {
        QProduct qProduct = QProduct.product;
        QProductConfiguration qProductConfiguration = QProductConfiguration.productConfiguration;

        JPAQuery query = new JPAQuery(entityManager);
        query.from(qProductConfiguration)
                .innerJoin(qProductConfiguration.product, qProduct)
                .where(qProduct.name.eq(productName));
        return query.list(qProductConfiguration);
    }

    public ProductConfiguration findByNameAndProductName(String productName, String configurationName) {
        QProduct qProduct = QProduct.product;
        QProductConfiguration qProductConfiguration = QProductConfiguration.productConfiguration;

        JPAQuery query = new JPAQuery(entityManager);
        query.from(qProductConfiguration)
                .innerJoin(qProductConfiguration.product, qProduct)
                .where(qProduct.name.eq(productName)
                        .and(qProductConfiguration.name.eq(configurationName)));
        return query.singleResult(qProductConfiguration);
    }
}
