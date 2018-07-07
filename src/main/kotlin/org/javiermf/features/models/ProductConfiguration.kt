package org.javiermf.features.models

import com.fasterxml.jackson.annotation.JsonIgnore
import java.util.*
import javax.persistence.*

@Entity
class ProductConfiguration {

    @Id
    @GeneratedValue
    internal var id: Long? = null

    @Column(nullable = false)
    var name: String

    @Column
    var isValid: Boolean? = true

    @JsonIgnore
    @ManyToOne
    var product: Product

    @ManyToMany(fetch = FetchType.EAGER)
    internal var activedFeatures: MutableSet<Feature> = HashSet()

    constructor(name: String, product: Product) {
        this.name = name
        this.product = product
    }

    fun getActivedFeatures(): Set<Feature> {
        return activedFeatures
    }

    fun availableFeatures(): Set<String> {
        return collectFeatureNames(product.getProductFeatures())
    }

    private fun collectFeatureNames(featuresSet: Set<Feature>): Set<String> {
        val features = HashSet<String>()
        for (feature in featuresSet) {
            features.add(feature.name)
        }

        return features
    }

    fun activedFeatures(): Set<String> {
        return collectFeatureNames(activedFeatures)
    }

    fun active(feature: Feature) {
        activedFeatures.add(feature)

    }

    fun deactive(feature: Feature) {
        activedFeatures.remove(feature)
    }

    fun active(featureName: String) {
        val feature = product.findProductFeatureByName(featureName)
        active(feature)
    }

    fun deactive(featureName: String) {
        val feature = product.findProductFeatureByName(featureName)
        deactive(feature)
    }

    fun hasActiveFeature(featureName: String): Boolean {
        return activedFeatures().contains(featureName)
    }
}
