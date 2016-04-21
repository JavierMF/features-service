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
        return currentResult;
    }

    // If excludedFeature is active, requiredFeature can not be activated
}
