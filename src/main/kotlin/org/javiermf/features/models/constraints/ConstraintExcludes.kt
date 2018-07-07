package org.javiermf.features.models.constraints

import org.javiermf.features.models.Product
import org.javiermf.features.models.ProductConfiguration
import org.javiermf.features.models.evaluation.EvaluationResult

import javax.persistence.Basic
import javax.persistence.Entity

@Entity
class ConstraintExcludes : FeatureConstraint {

    @Basic
    var sourceFeatureName: String

    @Basic
    var excludedFeatureName: String

    override val type: String
        get() = "excludes"

    constructor(forProduct: Product, sourceFeatureName: String, excludedFeatureName: String) : super(forProduct) {
        this.sourceFeatureName = sourceFeatureName
        this.excludedFeatureName = excludedFeatureName
    }


    override fun evaluateConfiguration(currentResult: EvaluationResult, configuration: ProductConfiguration): EvaluationResult {
        if (configuration.hasActiveFeature(sourceFeatureName) && configuration.hasActiveFeature(excludedFeatureName)) {
            currentResult.isValid = false
            currentResult.evaluationErrorMessages.plus(String.format("Feauture %s can not be active when feature %s is active", excludedFeatureName, sourceFeatureName))
        }
        return currentResult
    }
}
