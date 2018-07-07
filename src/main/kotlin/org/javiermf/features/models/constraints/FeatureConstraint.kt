package org.javiermf.features.models.constraints


import org.javiermf.features.models.Product
import org.javiermf.features.models.ProductConfiguration
import org.javiermf.features.models.evaluation.EvaluationResult

import javax.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
abstract class FeatureConstraint {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Long? = null

    @ManyToOne
    internal var forProduct: Product

    abstract val type: String

    constructor(forProduct: Product) {
        this.forProduct = forProduct
    }

    abstract fun evaluateConfiguration(currentResult: EvaluationResult, configuration: ProductConfiguration): EvaluationResult

    fun setForProduct(forProduct: Product) {
        this.forProduct = forProduct
    }
}
