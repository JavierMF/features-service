package org.javiermf.features.services

import org.javiermf.features.daos.ProductsConfigurationsDAO
import org.javiermf.features.daos.ProductsDAO
import org.javiermf.features.exceptions.DuplicatedObjectException
import org.javiermf.features.models.Feature
import org.javiermf.features.models.Product
import org.javiermf.features.models.constraints.ConstraintExcludes
import org.javiermf.features.models.constraints.ConstraintRequires
import org.javiermf.features.models.constraints.FeatureConstraint
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class ProductsService {

    @Autowired
    internal var productsDAO: ProductsDAO? = null

    @Autowired
    internal var productsConfigurationsDAO: ProductsConfigurationsDAO? = null


    val allProductNames: List<String>
        get() {
            val allProducts = ArrayList<String>()

            for (product in productsDAO!!.findAll()) {
                allProducts.add(product.name)
            }

            return allProducts
        }

    fun findByName(productName: String): Product {
        return productsDAO!!.findByName(productName)
    }

    fun deleteByName(productName: String) {
        productsConfigurationsDAO!!.deleteConfigurationsForProduct(productName)
        productsDAO!!.deleteConstraintsForProduct(productName)
        productsDAO!!.deleteByName(productName)
    }

    fun add(productName: String) {
        val product = Product(productName)
        productsDAO!!.insert(product)
    }

    fun getFeaturesForProduct(productName: String): Set<Feature> {
        val product = findByName(productName)
        return product.productFeatures
    }

    fun addFeatureToProduct(productName: String, featureName: String, featureDescription: String) {
        val product = findByName(productName)
        if (product.hasFeatureNamed(featureName)) {
            throw DuplicatedObjectException(featureName)
        }
        val feature = Feature.withName(product, featureName)
        feature.description = featureDescription
        productsDAO!!.insertFeature(feature)
    }

    @Transactional
    fun updateFeatureOfProduct(productName: String, featureName: String, featureDescription: String): Feature {
        val product = productsDAO!!.findByName(productName)
        val feature = product.findProductFeatureByName(featureName)
        feature.description = featureDescription
        return feature
    }

    @Transactional
    fun deleteFeatureOfProduct(productName: String, featureName: String) {
        val product = productsDAO!!.findByName(productName)
        val feature = product.findProductFeatureByName(featureName)

        for (productConfiguration in productsConfigurationsDAO!!.findConfigurationsWithFeatureActive(feature)) {
            productConfiguration.activedFeatures.remove(feature)
        }

        productsDAO!!.deleteFeature(feature)
    }

    fun addRequiresConstraintToProduct(productName: String, sourceFeatureName: String, requiredFeatureName: String): FeatureConstraint {
        val product = productsDAO!!.findByName(productName)

        val constraintRequires = ConstraintRequires(product, sourceFeatureName, requiredFeatureName)
        productsDAO!!.insertConstraint(constraintRequires)

        return constraintRequires
    }

    fun deleteConstraintFromProduct(productName: String, constraintId: Long?) {
        productsDAO!!.deleteConstraintForProduct(productName, constraintId)
    }

    fun addExcludesConstraintToProduct(productName: String, sourceFeatureName: String, excludedFeatureName: String): FeatureConstraint {
        val product = productsDAO!!.findByName(productName)

        val constraintExcludes = ConstraintExcludes(product, sourceFeatureName, excludedFeatureName)
        productsDAO!!.insertConstraint(constraintExcludes)

        return constraintExcludes
    }
}
