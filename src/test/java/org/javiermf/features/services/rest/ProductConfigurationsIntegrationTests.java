package org.javiermf.features.services.rest;

import org.apache.http.HttpStatus;
import org.javiermf.features.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.util.MatcherAssertionErrors.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
public class ProductConfigurationsIntegrationTests {

    @Test
    public void canFetchConfigurations() throws Exception {
        List boydResponse =
                when().
                        get("/products/Product_1/configurations").
                        then().
                        statusCode(HttpStatus.SC_OK).
                        and()
                        .extract().response().as(List.class);

        assertThat(boydResponse, hasSize(2));

    }

    @Test
    public void canFetchAConfiguration() throws Exception {
        when().
                get("/products/Product_1/configurations/Product_1_Configuration_2").
                then().
                statusCode(HttpStatus.SC_OK).
                and().
                body("name", is("Product_1_Configuration_2")).
                body("activedFeatures", hasSize(1));

    }

    @Test
    public void canFetchAConfigurationActivedFeatures() throws Exception {
        List boydResponse =
                when().
                        get("/products/Product_1/configurations/Product_1_Configuration_2/features").
                        then().
                        statusCode(HttpStatus.SC_OK).
                        and()
                        .extract().response().as(List.class);

        assertThat(boydResponse, hasSize(1));

    }

}
