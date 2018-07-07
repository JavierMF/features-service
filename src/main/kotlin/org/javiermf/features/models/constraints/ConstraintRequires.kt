package org.javiermf.features.models.constraints

import org.javiermf.features.models.Product
import org.javiermf.features.models.ProductConfiguration
import org.javiermf.features.models.evaluation.EvaluationResult

import javax.persistence.Basic
import javax.persistence.Entity

@Entity
class ConstraintRequires : FeatureConstraint {

    @Basic
    var sourceFeatureName: String

    @Basic
    var requiredFeatureName: String

    override val type: String
        get() = "requires"

    constructor(forProduct: Product, sourceFeatureName: String, requiredFeatureName: String) : super(forProduct) {
        this.sourceFeatureName = sourceFeatureName
        this.requiredFeatureName = requiredFeatureName
    }


    // If sourceFeature is active, requiredFeature must be active too
    override fun evaluateConfiguration(currentResult: EvaluationResult, configuration: ProductConfiguration): EvaluationResult {
        if (configuration.hasActiveFeature(sourceFeatureName) && !configuration.hasActiveFeature(requiredFeatureName)) {
            currentResult.derivedFeatures.plus(requiredFeatureName)
        }
        return currentResult
    }
}
