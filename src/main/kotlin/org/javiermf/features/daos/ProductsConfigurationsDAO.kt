package org.javiermf.features.daos

import com.mysema.query.jpa.impl.JPAQuery
import org.javiermf.features.models.*
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Repository
class ProductsConfigurationsDAO {

    @PersistenceContext
    private val entityManager: EntityManager? = null

    internal var qProductConfiguration = QProductConfiguration.productConfiguration
    internal var qProduct = QProduct.product

    fun findByProductName(productName: String): List<ProductConfiguration> {


        val query = JPAQuery(entityManager!!)
        query.from(qProductConfiguration)
                .innerJoin<Product>(qProductConfiguration.product, qProduct)
                .where(qProduct.name.eq(productName))
        return query.list(qProductConfiguration)
    }

    fun findByNameAndProductName(productName: String, configurationName: String): ProductConfiguration? {
        val qProduct = QProduct.product


        val query = JPAQuery(entityManager!!)
        query.from(qProductConfiguration)
                .innerJoin<Product>(qProductConfiguration.product, qProduct)
                .where(qProduct.name.eq(productName)
                        .and(qProductConfiguration.name.eq(configurationName)))
        return query.singleResult(qProductConfiguration)
    }

    @Transactional
    fun deleteConfigurationsForProduct(productName: String) {
        for (productConfiguration in findByProductName(productName)) {
            entityManager!!.remove(productConfiguration)
        }

    }

    fun findConfigurationsWithFeatureActive(feature: Feature): List<ProductConfiguration> {
        val query = JPAQuery(entityManager!!)
        query.from(qProductConfiguration)
                .where(qProductConfiguration.activedFeatures.contains(feature))

        return query.list(qProductConfiguration)
    }

    @Transactional
    fun insert(configuration: ProductConfiguration) {
        entityManager!!.persist(configuration)

    }

    @Transactional
    fun deleteConfigurationForProduct(productName: String, configurationName: String) {
        val configuration = findByNameAndProductName(productName, configurationName)
        entityManager!!.remove(configuration)
    }
}
