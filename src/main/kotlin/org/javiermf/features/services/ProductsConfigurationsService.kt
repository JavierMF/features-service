package org.javiermf.features.services

import org.javiermf.features.daos.ProductsConfigurationsDAO
import org.javiermf.features.exceptions.DuplicatedObjectException
import org.javiermf.features.models.ProductConfiguration
import org.javiermf.features.models.evaluation.ConfigurationEvaluator
import org.javiermf.features.models.evaluation.EvaluationResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class ProductsConfigurationsService {

    @Autowired
    internal var productsConfigurationsDAO: ProductsConfigurationsDAO? = null

    @Autowired
    internal var productsService: ProductsService? = null

    @Autowired
    internal var configurationEvaluator: ConfigurationEvaluator? = null

    fun getConfigurationsNamesForProduct(productName: String): List<String> {
        val configurationsForProduct = ArrayList<String>()

        for (productConfiguration in productsConfigurationsDAO!!.findByProductName(productName)) {
            configurationsForProduct.add(productConfiguration.name)
        }

        return configurationsForProduct
    }

    fun findByNameAndProductName(productName: String, configurationName: String): ProductConfiguration? {
        return productsConfigurationsDAO!!.findByNameAndProductName(productName, configurationName)
    }

    fun getConfigurationActivedFeaturesNames(productName: String, configurationName: String): List<String> {
        val productConfiguration = productsConfigurationsDAO!!.findByNameAndProductName(productName, configurationName)
        val featureNames = ArrayList<String>()
        for (feature in productConfiguration!!.activedFeatures) {
            featureNames.add(feature.name)
        }
        return featureNames
    }


    fun add(productName: String, configurationName: String) {
        val product = productsService!!.findByName(productName)

        val configuration = ProductConfiguration(configurationName, product)
        productsConfigurationsDAO!!.insert(configuration)
    }

    fun deleteByName(productName: String, configurationName: String) {
        productsConfigurationsDAO!!.deleteConfigurationForProduct(productName, configurationName)

    }

    @Transactional
    fun removeFeatureFromConfiguration(productName: String, configurationName: String, featureName: String): EvaluationResult {
        val configuration = productsConfigurationsDAO!!.findByNameAndProductName(productName, configurationName)
        configuration!!.deactive(featureName)

        return evaluateAndUpdateConfiguration(configuration)
    }


    @Transactional
    fun addFeatureFromConfiguration(productName: String, configurationName: String, featureName: String): EvaluationResult {
        val configuration = productsConfigurationsDAO!!.findByNameAndProductName(productName, configurationName)
        if (configuration!!.hasActiveFeature(featureName)) {
            throw DuplicatedObjectException(featureName)
        }
        configuration.active(featureName)

        return evaluateAndUpdateConfiguration(configuration)
    }

    private fun evaluateAndUpdateConfiguration(configuration: ProductConfiguration): EvaluationResult {
        val product = configuration.product
        val evaluationResult = configurationEvaluator!!.evaluateConfiguration(configuration, product.productFeatureConstraints)
        configuration.isValid = evaluationResult.isValid
        return evaluationResult
    }
}
