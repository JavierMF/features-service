package org.javiermf.features.models.evaluation

import java.util.*


class EvaluationResult {

    var isValid = true

    var derivedFeatures: Set<String> = HashSet()

    var evaluationErrorMessages: Set<String> = HashSet()


}
