package org.javiermf.features.services.rest

import com.jayway.restassured.RestAssured.given
import org.apache.http.HttpStatus
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.hasSize
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
class ConstraintRequiresIntegrationTests {

    @Autowired
    internal var productsConfigurationsDAO: ProductsConfigurationsDAO? = null


    @Test
    @Throws(Exception::class)
    fun requiresConstraintAddRequiredFeature() {
        given().`when`().post("/products/Product_1/configurations/Product_1_Configuration_2/features/Feature_3").then().statusCode(HttpStatus.SC_CREATED)

        val configuration = productsConfigurationsDAO!!.findByNameAndProductName("Product_1", "Product_1_Configuration_2")
        assertThat(configuration!!.activedFeatures(), hasSize<Any>(3))
        assertThat(configuration.isValid, `is`(true))
    }
}
