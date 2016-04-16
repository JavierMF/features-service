package org.javiermf.features.services.rest;

import org.apache.http.HttpStatus;
import org.javiermf.features.Application;
import org.javiermf.features.daos.ProductsDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;

import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.util.MatcherAssertionErrors.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@Sql({"/empty-db.sql", "/data.sql"})
@WebIntegrationTest
public class ProductsFeaturesIntegrationTests {

    @Autowired
    ProductsDAO productsDAO;

    @Test
    public void canFetchProductsFeatures() throws Exception {
        List boydResponse =
                when().
                        get("/products/Product_1/features").
                        then().
                        statusCode(HttpStatus.SC_OK).
                        and()
                        .extract().response().as(List.class);

        assertThat(boydResponse, hasSize(2));

        Map<String, String> featureObject = (Map<String, String>) boydResponse.get(0);
        assertThat(featureObject.get("name"), is(anyOf(equalTo("Feature_1"), equalTo("Feature_2"))));

    }

}
