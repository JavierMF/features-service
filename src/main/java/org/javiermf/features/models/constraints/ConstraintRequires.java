package org.javiermf.features.models.constraints;

import org.javiermf.features.models.ProductConfiguration;
import org.javiermf.features.models.evaluation.EvaluationResult;

import javax.persistence.Basic;
import javax.persistence.Entity;

@Entity
public class ConstraintRequires extends FeatureConstraint {

    @Basic
    String sourceFeatureName;

    @Basic
    String requiredFeatureName;

    public ConstraintRequires() {
    }

    public ConstraintRequires(String sourceFeatureName, String requiredFeatureName) {
        this.sourceFeatureName = sourceFeatureName;
        this.requiredFeatureName = requiredFeatureName;
    }

    // If sourceFeature is active, requiredFeature must be active too
    @Override
    public EvaluationResult evaluateConfiguration(EvaluationResult currentResult, ProductConfiguration configuration) {
        if (configuration.hasActiveFeature(sourceFeatureName) &&
                !configuration.hasActiveFeature(requiredFeatureName)) {
            currentResult.derivedFeatures.add(requiredFeatureName);
        }
        return currentResult;
    }
}
