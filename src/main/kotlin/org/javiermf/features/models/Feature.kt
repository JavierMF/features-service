package org.javiermf.features.models


import com.fasterxml.jackson.annotation.JsonIgnore

import javax.persistence.*

@Entity
class Feature {

    @Id
    @GeneratedValue
    var id: Long
        internal set

    @Column(nullable = false)
    var name: String

    @Column
    var description: String

    @JsonIgnore
    @ManyToOne
    var product: Product

    @JsonIgnore
    @ManyToMany(mappedBy = "activedFeatures")
    internal var inConfigurations: Set<ProductConfiguration>? = null

    constructor(id: Long, name: String, description: String, product: Product) {
        this.id = id
        this.name = name
        this.description = description
        this.product = product
    }


    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o !is Feature) return false

        val feature = o as Feature?

        return if (name != feature!!.name) false else product == feature.product

    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + product.hashCode()
        return result
    }

    companion object {

        fun withName(product: Product, featureName: String): Feature {
            return Feature(1, featureName, featureName, product)
        }
    }
}
