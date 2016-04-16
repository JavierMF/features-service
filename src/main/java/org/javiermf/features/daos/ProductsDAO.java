package org.javiermf.features.daos;

import com.mysema.query.jpa.impl.JPAQuery;
import org.javiermf.features.exceptions.ObjectNotFoundException;
import org.javiermf.features.models.Product;
import org.javiermf.features.models.QProduct;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ProductsDAO {

    @PersistenceContext
    private EntityManager entityManager;

    QProduct qProduct = QProduct.product;

    public List<Product> findAll() {
        JPAQuery query = new JPAQuery(entityManager);

        return query.from(qProduct).list(qProduct);
    }

    public Product findByName(String name) {
        JPAQuery query = new JPAQuery(entityManager);

        query.from(qProduct).where(qProduct.name.eq(name));
        Product product = query.singleResult(qProduct);
        if (product == null) {
            throw new ObjectNotFoundException(name);
        }
        return product;
    }

    @Transactional
    public void deleteByName(String productName) {
        Product product = findByName(productName);
        entityManager.remove(product);

    }

    @Transactional
    public void insert(Product product) {
        entityManager.persist(product);
    }
}
