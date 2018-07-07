package org.javiermf.features.models.constraints

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.javiermf.features.models.Product
import org.javiermf.features.models.ProductConfiguration
import org.javiermf.features.models.evaluation.EvaluationResult
import org.junit.Test

class ConstraintRequiresTests {

    @Test
    @Throws(Exception::class)
    fun shouldBeEmptyIfNoAddedFeatures() {
        val SOURCE_FEATURE = "SOURCE_FEATURE"
        val REQUIRED_FEATURE = "REQUIRED_FEATURE"

        val product = Product.buildWithFeatures(SOURCE_FEATURE, REQUIRED_FEATURE)
        val requires = ConstraintRequires(product, SOURCE_FEATURE, REQUIRED_FEATURE)
        product.addFeatureConstraint(requires)

        val configuration = ProductConfiguration("name", product)
        configuration.active(SOURCE_FEATURE)

        val result = EvaluationResult()
        requires.evaluateConfiguration(result, configuration)

        assertThat(result.isValid, `is`(true))
        assertThat(result.derivedFeatures, hasSize<Any>(1))
        assertThat(result.derivedFeatures, contains(REQUIRED_FEATURE))

    }
}
