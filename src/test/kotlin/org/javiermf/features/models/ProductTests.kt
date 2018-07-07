package org.javiermf.features.models

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.Test

class ProductTests {

    @Test
    @Throws(Exception::class)
    fun shouldBeEmptyIfNoAddedFeatures() {
        val product = Product("name")

        assertThat(product.getProductFeatures(), `is`(empty<Any>()))

    }

    @Test
    @Throws(Exception::class)
    fun shouldHaveOneFeatureIfAdded() {
        val product = Product("name")
        val feature = Feature.withName(product, "FEATURE1")
        product.addFeature(feature)

        assertThat(product.getProductFeatures(), hasSize<Any>(1))
        assertThat(product.getProductFeatures(), hasItem(feature))

    }

    @Test
    @Throws(Exception::class)
    fun shouldHaveTwoFeatureIfAdded() {
        val product = Product("name")
        val feature1 = Feature.withName(product, "FEATURE1")
        product.addFeature(feature1)
        val feature2 = Feature.withName(product, "FEATURE2")
        product.addFeature(feature2)

        assertThat(product.getProductFeatures(), hasSize<Any>(2))
        assertThat(product.getProductFeatures(), hasItem(feature1))
        assertThat(product.getProductFeatures(), hasItem(feature2))

    }

    @Test
    @Throws(Exception::class)
    fun shouldHaveOneFeatureIfAddedTwice() {
        val product = Product("name")
        val feature1 = Feature.withName(product, "FEATURE1")
        product.addFeature(feature1)
        product.addFeature(feature1)

        assertThat(product.getProductFeatures(), hasSize<Any>(1))
        assertThat(product.getProductFeatures(), hasItem(feature1))

    }

    @Test
    @Throws(Exception::class)
    fun shouldHaveOneFeatureIfAddedTwoWithSameName() {
        val product = Product("name")
        val feature1 = Feature.withName(product, "FEATURE1")
        product.addFeature(feature1)
        val feature2 = Feature.withName(product, "FEATURE1")
        product.addFeature(feature2)

        assertThat(product.getProductFeatures(), hasSize<Any>(1))
        assertThat(product.getProductFeatures(), hasItem(feature1))

    }

    @Test
    @Throws(Exception::class)
    fun shouldRemoveFeatures() {
        val product = Product("name")
        val feature1 = Feature.withName(product, "FEATURE1")
        product.addFeature(feature1)
        val feature2 = Feature.withName(product, "FEATURE2")
        product.addFeature(feature2)

        product.removeFeature(feature1)

        assertThat(product.getProductFeatures(), hasSize<Any>(1))
        assertThat(product.getProductFeatures(), hasItem(feature2))

    }
}
