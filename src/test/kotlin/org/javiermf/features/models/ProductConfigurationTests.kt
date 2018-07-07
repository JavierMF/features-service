package org.javiermf.features.models

import org.hamcrest.CoreMatchers.hasItem
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.hasSize
import org.junit.Test

class ProductConfigurationTests {

    @Test
    fun shouldReturnProductFeaturesAsAvailable() {
        val product = Product.buildWithFeatures("F1", "F2", "F3")
        val productConfiguration = ProductConfiguration("name", product)

        assertThat(productConfiguration.availableFeatures(), hasSize<Any>(3))
        assertThat(productConfiguration.availableFeatures(), hasItem("F1"))
        assertThat(productConfiguration.availableFeatures(), hasItem("F2"))
        assertThat(productConfiguration.availableFeatures(), hasItem("F3"))
    }

    @Test
    @Throws(Exception::class)
    fun shouldReturnFeatureActiveIfActivated() {
        val product = Product.buildWithFeatures("F1", "F2", "F3")
        val productConfiguration = ProductConfiguration("name", product)

        productConfiguration.active(Feature.withName(product, "F1"))

        assertThat(productConfiguration.activedFeatures(), hasSize<Any>(1))
        assertThat(productConfiguration.activedFeatures(), hasItem("F1"))
    }

    @Test
    @Throws(Exception::class)
    fun shouldReturnTwoFeaturesActiveIfActivated() {
        val product = Product.buildWithFeatures("F1", "F2", "F3")
        val productConfiguration = ProductConfiguration("name", product)

        productConfiguration.active(Feature.withName(product, "F1"))
        productConfiguration.active(Feature.withName(product, "F2"))

        assertThat(productConfiguration.activedFeatures(), hasSize<Any>(2))
        assertThat(productConfiguration.activedFeatures(), hasItem("F1"))
        assertThat(productConfiguration.activedFeatures(), hasItem("F2"))
    }

    @Test
    @Throws(Exception::class)
    fun shouldDeactiveFeatureIfPreviouslyActivated() {
        val product = Product.buildWithFeatures("F1", "F2", "F3")
        val productConfiguration = ProductConfiguration("name", product)

        productConfiguration.active(Feature.withName(product, "F1"))
        productConfiguration.active(Feature.withName(product, "F2"))

        productConfiguration.deactive(Feature.withName(product, "F2"))

        assertThat(productConfiguration.activedFeatures(), hasSize<Any>(1))
        assertThat(productConfiguration.activedFeatures(), hasItem("F1"))
    }

}
