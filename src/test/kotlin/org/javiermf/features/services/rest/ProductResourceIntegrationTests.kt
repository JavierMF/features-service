package org.javiermf.features.services.rest

import com.jayway.restassured.RestAssured.`when`
import org.apache.http.HttpStatus
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
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
class ProductResourceIntegrationTests {

    @Autowired
    internal var productsDAO: ProductsDAO? = null

    @Test
    @Throws(Exception::class)
    fun canFetchProducts() {
        val boydResponse = `when`().get("/products").then().statusCode(HttpStatus.SC_OK).and()
                .extract().response().`as`(List::class.java)

        assertThat(boydResponse, hasSize<Any>(2))

    }

    @Test
    @Throws(Exception::class)
    fun canFetchAProduct() {
        `when`().get("/products/Product_1").then().statusCode(HttpStatus.SC_OK).and().body("name", `is`("Product_1")).body("features", hasSize<Any>(3)).body("constraints", hasSize<Any>(1))

    }

    @Test
    @Throws(Exception::class)
    fun canDeleteAProduct() {
        `when`().delete("/products/Product_1").then().statusCode(HttpStatus.SC_NO_CONTENT)

        assertThat(productsDAO!!.findAll(), hasSize<Any>(1))
    }

    @Test
    @Throws(Exception::class)
    fun canAddAProduct() {
        `when`().post("/products/Product_3").then().statusCode(HttpStatus.SC_CREATED)

        assertThat(productsDAO!!.findAll(), hasSize<Any>(3))
    }


}
