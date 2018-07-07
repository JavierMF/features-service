package org.javiermf.features.models


import com.fasterxml.jackson.annotation.JsonProperty
import org.javiermf.features.exceptions.ObjectNotFoundException
import org.javiermf.features.models.constraints.FeatureConstraint
import java.util.*
import javax.persistence.*

@Entity
class Product {

    @Id
    @GeneratedValue
    var id: Long? = null
        internal set

    @Column(nullable = false)
    var name: String

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER, cascade = [(CascadeType.ALL)])
    internal var productFeatures: MutableSet<Feature> = HashSet()

    @OneToMany(mappedBy = "forProduct", fetch = FetchType.EAGER, cascade = [(CascadeType.ALL)])
    internal var productFeatureConstraints: MutableSet<FeatureConstraint> = HashSet()

    constructor(name: String) {
        this.name = name
    }

    @JsonProperty("features")
    fun getProductFeatures(): Set<Feature> {
        return productFeatures
    }

    fun addFeature(feature: Feature) {
        productFeatures.add(feature)
    }

    fun removeFeature(feature: Feature) {
        productFeatures.remove(feature)
    }

    @JsonProperty("constraints")
    fun getProductFeatureConstraints(): Set<FeatureConstraint> {
        return productFeatureConstraints
    }

    fun findProductFeatureByName(featureName: String): Feature {
        for (feature in getProductFeatures()) {
            if (feature.name.equals(featureName, ignoreCase = true)) {
                return feature
            }
        }

        throw ObjectNotFoundException(featureName)
    }

    fun hasFeatureNamed(featureName: String): Boolean {
        for (feature in getProductFeatures()) {
            if (feature.name.equals(featureName, ignoreCase = true)) {
                return true
            }
        }

        return false
    }

    fun addFeatureConstraint(constraint: FeatureConstraint) {
        productFeatureConstraints.add(constraint)
    }

    companion object {

        fun buildWithFeatures(vararg featureNames: String): Product {
            val product = Product("aProduct")
            for (featureName in featureNames) {
                product.addFeature(Feature.withName(product, featureName))
            }

            return product

        }
    }
}
