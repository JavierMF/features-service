package org.javiermf.features.models.constraints

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.hasSize
import org.javiermf.features.models.Product
import org.javiermf.features.models.ProductConfiguration
import org.javiermf.features.models.evaluation.EvaluationResult
import org.junit.Test

class ConstraintExcludesTests {

    @Test
    @Throws(Exception::class)
    fun shouldBeEmptyIfNoAddedFeatures() {
        val SOURCE_FEATURE = "SOURCE_FEATURE"
        val EXCLUDED_FEATURE = "EXCLUDED_FEATURE"

        val product = Product.buildWithFeatures(SOURCE_FEATURE, EXCLUDED_FEATURE)
        val excludes = ConstraintExcludes(product, SOURCE_FEATURE, EXCLUDED_FEATURE)

        product.addFeatureConstraint(excludes)

        val configuration = ProductConfiguration("name", product)
        configuration.active(SOURCE_FEATURE)
        configuration.active(EXCLUDED_FEATURE)

        val result = EvaluationResult()
        excludes.evaluateConfiguration(result, configuration)

        assertThat(result.isValid, `is`(false))
        assertThat(result.evaluationErrorMessages, hasSize<Any>(1))

    }
}
