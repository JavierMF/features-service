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
@Sql("/empty-db.sql", "/data-test.sql")
@SpringBootTest(classes = [(Application::class)])
class ConstraintExcludesIntegrationTests {

    @Autowired
    internal var productsConfigurationsDAO: ProductsConfigurationsDAO? = null

    @Test
    @Throws(Exception::class)
    fun requiresExcludesInvalidatesConfiguration() {
        given().`when`().post("/products/Product_2/configurations/Product_2_Configuration_1/features/Feature_C").then().statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)

        val configuration = productsConfigurationsDAO!!.findByNameAndProductName("Product_2", "Product_2_Configuration_1")
        assertThat(configuration!!.activedFeatures(), hasSize<Any>(3))
        assertThat(configuration.isValid, `is`(false))
    }
}
