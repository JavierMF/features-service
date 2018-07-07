package org.javiermf.features.services.rest

import com.jayway.restassured.RestAssured.`when`
import com.jayway.restassured.RestAssured.given
import org.apache.http.HttpStatus
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.hasSize
import org.javiermf.features.Application
import org.javiermf.features.daos.ProductsDAO
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

@RunWith(SpringJUnit4ClassRunner::class)
@SpringBootTest(classes = [(Application::class)])
@Sql("/empty-db.sql", "/data-test.sql")
class ProductsConstraintsIntegrationTests {

    @Autowired
    internal var productsDAO: ProductsDAO? = null


    @Test
    @Throws(Exception::class)
    fun canAddRequiresConstraint() {
        given().formParam("sourceFeature", "Feature_1").formParam("requiredFeature", "Feature_2").`when`().post("/products/Product_1/constraints/requires").then().statusCode(HttpStatus.SC_CREATED)

        val product = productsDAO!!.findByName("Product_1")

        assertThat(product.getProductFeatureConstraints(), hasSize<Any>(2))

    }

    @Test
    @Throws(Exception::class)
    fun canAddExcludesConstraint() {
        given().formParam("sourceFeature", "Feature_1").formParam("excluded", "Feature_2").`when`().post("/products/Product_1/constraints/excludes").then().statusCode(HttpStatus.SC_CREATED)

        val product = productsDAO!!.findByName("Product_1")

        assertThat(product.getProductFeatureConstraints(), hasSize<Any>(2))

    }

    @Test
    @Throws(Exception::class)
    fun canDeleteAProductConstraint() {
        `when`().delete("/products/Product_1/constraints/1").then().statusCode(HttpStatus.SC_NO_CONTENT)


        val product = productsDAO!!.findByName("Product_1")
        assertThat(product.getProductFeatureConstraints(), hasSize<Any>(0))
    }

}
