package org.javiermf.features.services.rest

import com.jayway.restassured.RestAssured.`when`
import com.jayway.restassured.RestAssured.given
import org.apache.http.HttpStatus
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.javiermf.features.Application
import org.javiermf.features.daos.ProductsDAO
import org.javiermf.features.models.Feature
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

@RunWith(SpringJUnit4ClassRunner::class)
@SpringBootTest(classes = [(Application::class)])
@Sql("/empty-db.sql", "/data-test.sql")
class ProductsFeaturesIntegrationTests {

    @Autowired
    internal var productsDAO: ProductsDAO? = null

    @Test
    @Throws(Exception::class)
    fun canFetchProductsFeatures() {
        val boydResponse = `when`().get("/products/Product_1/features").then().statusCode(HttpStatus.SC_OK).and()
                .extract().response().`as`(List::class.java)

        assertThat(boydResponse, hasSize<Any>(3))

        val featureObject = boydResponse[0] as Map<String, String>
        assertThat(featureObject["name"], `is`(anyOf(equalTo("Feature_1"), equalTo("Feature_2"), equalTo("Feature_3"))))

    }

    @Test
    @Throws(Exception::class)
    fun canAddProductsFeatures() {
        given().formParam("description", "New Feature Description").`when`().post("/products/Product_1/features/newFeature").then().statusCode(HttpStatus.SC_CREATED)

        val product = productsDAO!!.findByName("Product_1")

        assertThat(product.getProductFeatures(), hasSize<Any>(4))

        val newFeature = product.findProductFeatureByName("newFeature")
        assertThat(newFeature, `is`(notNullValue()))
        assertThat(newFeature.description, `is`(equalTo("New Feature Description")))
    }

    @Test
    @Throws(Exception::class)
    fun canNotAddDuplicatedProductsFeatures() {
        given().formParam("description", "Existing Feature Description").`when`().post("/products/Product_1/features/Feature_1").then().statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)

        val product = productsDAO!!.findByName("Product_1")

        assertThat(product.getProductFeatures(), hasSize<Any>(3))

    }

    @Test
    @Throws(Exception::class)
    fun canUpdateProductsFeatures() {
        val feature = given().formParam("description", "New Feature Description").`when`().put("/products/Product_1/features/Feature_1").then().statusCode(HttpStatus.SC_OK).and()
                .extract().response().`as`(Feature::class.java)

        assertThat(feature.description, `is`(equalTo("New Feature Description")))

        val product = productsDAO!!.findByName("Product_1")
        val updatedFeature = product.findProductFeatureByName("Feature_1")
        assertThat(updatedFeature.description, `is`(equalTo("New Feature Description")))
    }

    @Test
    @Throws(Exception::class)
    fun canDeleteAProductsFeatures() {
        `when`().delete("/products/Product_1/features/Feature_1").then().statusCode(HttpStatus.SC_NO_CONTENT)


        val product = productsDAO!!.findByName("Product_1")
        assertThat(product.getProductFeatures(), hasSize<Any>(2))
    }

}
