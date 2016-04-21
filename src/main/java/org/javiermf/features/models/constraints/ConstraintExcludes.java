package org.javiermf.features.models.constraints;

import org.javiermf.features.models.ProductConfiguration;
import org.javiermf.features.models.evaluation.EvaluationResult;

import javax.persistence.Basic;
import javax.persistence.Entity;

@Entity
public class ConstraintExcludes extends FeatureConstraint {

    @Basic
    String sourceFeatureName;

    @Basic
    String excludedFeatureName;

    public ConstraintExcludes() {
    }

    public ConstraintExcludes(String sourceFeatureName, String excludedFeatureName) {
        this.sourceFeatureName = sourceFeatureName;
        this.excludedFeatureName = excludedFeatureName;
    }

    @Override
    public EvaluationResult evaluateConfiguration(EvaluationResult currentResult, ProductConfiguration configuration) {
        if (configuration.hasActiveFeature(sourceFeatureName) &&
                configuration.hasActiveFeature(excludedFeatureName)) {
            currentResult.isValid = false;
            currentResult.evaluationErrorMessages.add(String.format("Feauture %s can not be active when feature %s is active", excludedFeatureName, sourceFeatureName));
        }
        return currentResult;
    }

    // If excludedFeature is active, requiredFeature can not be activated
}
