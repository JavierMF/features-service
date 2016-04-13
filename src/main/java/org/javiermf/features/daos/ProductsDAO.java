package org.javiermf.features.daos;

import com.mysema.query.jpa.impl.JPAQuery;
import org.javiermf.features.models.Product;
import org.javiermf.features.models.QProduct;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ProductsDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Product> findAll() {
        QProduct qProduct = QProduct.product;
        JPAQuery query = new JPAQuery(entityManager);

        return query.from(qProduct).list(qProduct);
    }

    public Product findByName(String name) {
        QProduct qProduct = QProduct.product;
        JPAQuery query = new JPAQuery(entityManager);

        query.from(QProduct.product).where(qProduct.name.eq(name));
        return query.singleResult(qProduct);

    }
}
