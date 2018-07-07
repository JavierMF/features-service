package org.javiermf.features.models.evaluation

import org.javiermf.features.models.ProductConfiguration
import org.javiermf.features.models.constraints.FeatureConstraint
import org.springframework.stereotype.Component

@Component
class ConfigurationEvaluator {


    fun evaluateConfiguration(configuration: ProductConfiguration, featureConstraints: Set<FeatureConstraint>): EvaluationResult {
        var result = EvaluationResult()
        result = addEvaluationsToResult(result, configuration, featureConstraints)

        return result
    }

    private fun addEvaluationsToResult(result: EvaluationResult, configuration: ProductConfiguration, featureConstraints: Set<FeatureConstraint>): EvaluationResult {
        var result = result
        for (featureConstraint in featureConstraints) {
            result = featureConstraint.evaluateConfiguration(result, configuration)
        }

        if (result.isValid && !result.derivedFeatures.isEmpty()) {
            for (derivedFeature in result.derivedFeatures) {
                configuration.active(derivedFeature)
                result.derivedFeatures.minus(derivedFeature)
                result = this.addEvaluationsToResult(result, configuration, featureConstraints)
            }

        }
        return result
    }
}
