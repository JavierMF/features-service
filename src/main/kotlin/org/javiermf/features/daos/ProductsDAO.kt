package org.javiermf.features.daos

import com.mysema.query.jpa.impl.JPADeleteClause
import com.mysema.query.jpa.impl.JPAQuery
import org.javiermf.features.exceptions.ObjectNotFoundException
import org.javiermf.features.models.Feature
import org.javiermf.features.models.Product
import org.javiermf.features.models.QFeature
import org.javiermf.features.models.QProduct
import org.javiermf.features.models.constraints.FeatureConstraint
import org.javiermf.features.models.constraints.QFeatureConstraint
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Repository
class ProductsDAO {

    @PersistenceContext
    private val entityManager: EntityManager? = null

    internal var qProduct = QProduct.product
    internal var qFeature = QFeature.feature
    internal var qFeatureConstraint = QFeatureConstraint.featureConstraint

    fun findAll(): List<Product> {
        val query = JPAQuery(entityManager!!)

        return query.from(qProduct).list(qProduct)
    }

    fun findByName(name: String): Product {
        val query = JPAQuery(entityManager!!)

        query.from(qProduct).where(qProduct.name.eq(name))
        return query.singleResult(qProduct) ?: throw ObjectNotFoundException(name)
    }

    @Transactional
    fun deleteByName(productName: String) {
        val product = findByName(productName)
        entityManager!!.remove(product)

    }

    @Transactional
    fun deleteConstraintsForProduct(productName: String) {
        val product = findByName(productName)
        for (featureConstraint in product.productFeatureConstraints) {
            entityManager!!.remove(featureConstraint)
        }
        product.productFeatureConstraints.clear()
    }

    @Transactional
    fun insert(product: Product) {
        entityManager!!.persist(product)
    }

    @Transactional
    fun insertFeature(feature: Feature) {
        entityManager!!.persist(feature)
    }

    @Transactional
    fun deleteFeature(feature: Feature) {
        val deleteClause = JPADeleteClause(entityManager!!, qFeature)
        deleteClause.where(qFeature.id.eq(feature.id!!))
        deleteClause.execute()
    }

    @Transactional
    fun insertConstraint(constraint: FeatureConstraint) {
        entityManager!!.persist(constraint)
    }

    @Transactional
    fun deleteConstraintForProduct(productName: String, constraintId: Long?) {
        val deleteClause = JPADeleteClause(entityManager!!, qFeatureConstraint)
        deleteClause.where(qFeatureConstraint.id.eq(constraintId!!))
        deleteClause.execute()
    }
}
