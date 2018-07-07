package org.javiermf.features.services.rest

import com.jayway.restassured.RestAssured.`when`
import org.apache.http.HttpStatus
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.javiermf.features.Application
import org.javiermf.features.daos.ProductsConfigurationsDAO
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

@RunWith(SpringJUnit4ClassRunner::class)
@SpringBootTest(classes = [(Application::class)])
@Sql("/empty-db.sql", "/data-test.sql")
class ProductConfigurationsIntegrationTests {

    @Autowired
    internal var productsConfigurationsDAO: ProductsConfigurationsDAO? = null


    @Test
    @Throws(Exception::class)
    fun canFetchConfigurations() {
        val boydResponse = `when`().get("/products/Product_1/configurations").then().statusCode(HttpStatus.SC_OK).and()
                .extract().response().`as`(List::class.java)

        assertThat(boydResponse, hasSize<Any>(2))

    }

    @Test
    @Throws(Exception::class)
    fun canFetchAConfiguration() {
        `when`().get("/products/Product_1/configurations/Product_1_Configuration_2").then().statusCode(HttpStatus.SC_OK).and().body("name", `is`("Product_1_Configuration_2")).body("activedFeatures", hasSize<Any>(1))

    }

    @Test
    @Throws(Exception::class)
    fun canAddProductsConfigurations() {
        `when`().post("/products/Product_1/configurations/newConfig").then().statusCode(HttpStatus.SC_CREATED)

        val configuration = productsConfigurationsDAO!!.findByNameAndProductName("Product_1", "newConfig")

        assertThat(configuration, `is`(notNullValue()))
        assertThat(configuration!!.product.name, `is`(equalTo("Product_1")))
        assertThat(configuration.isValid, `is`(true))
    }

    @Test
    @Throws(Exception::class)
    fun canDeleteAProductConfiguration() {
        `when`().delete("/products/Product_1/configurations/Product_1_Configuration_1").then().statusCode(HttpStatus.SC_NO_CONTENT)

        assertThat(productsConfigurationsDAO!!.findByProductName("Product_1"), hasSize<Any>(1))
    }

    @Test
    @Throws(Exception::class)
    fun canAddAnActivedFeatureToAConfiguration() {
        `when`().post("/products/Product_1/configurations/Product_1_Configuration_2/features/Feature_2").then().statusCode(HttpStatus.SC_CREATED)

        val configuration = productsConfigurationsDAO!!.findByNameAndProductName("Product_1", "Product_1_Configuration_2")

        assertThat(configuration!!.getActivedFeatures(), hasSize<Any>(2))
    }

    @Test
    @Throws(Exception::class)
    fun canNotAddDuplicatedActivedFeatureToAConfiguration() {
        `when`().post("/products/Product_1/configurations/Product_1_Configuration_2/features/Feature_1").then().statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)

        val configuration = productsConfigurationsDAO!!.findByNameAndProductName("Product_1", "Product_1_Configuration_2")

        assertThat(configuration!!.getActivedFeatures(), hasSize<Any>(1))
    }

    @Test
    @Throws(Exception::class)
    fun canNotAddAFeatureOfOtherProductToAConfiguration() {
        `when`().post("/products/Product_1/configurations/Product_1_Configuration_2/features/Feature_B").then().statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
    }


    @Test
    @Throws(Exception::class)
    fun canDeleteAActivedFeatureFromAConfiguration() {
        `when`().delete("/products/Product_1/configurations/Product_1_Configuration_1/features/Feature_1").then().statusCode(HttpStatus.SC_NO_CONTENT)

        val configuration = productsConfigurationsDAO!!.findByNameAndProductName("Product_1", "Product_1_Configuration_1")
        assertThat(configuration!!.getActivedFeatures(), hasSize<Any>(1))

    }

}
