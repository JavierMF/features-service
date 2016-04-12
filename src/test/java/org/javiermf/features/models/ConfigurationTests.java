package org.javiermf.features.models;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

public class ConfigurationTests {

    @Test
    public void shouldReturnProductFeaturesAsAvailable() throws Exception {
        Product product = Product.buildWithFeatures("F1", "F2", "F3");
        Configuration configuration = new Configuration();
        configuration.product = product;

        assertThat(configuration.availableFeatures(), hasSize(3));
        assertThat(configuration.availableFeatures(), hasItem("F1"));
        assertThat(configuration.availableFeatures(), hasItem("F2"));
        assertThat(configuration.availableFeatures(), hasItem("F3"));
    }

    @Test
    public void shouldReturnFeatureActiveIfActivated() throws Exception {
        Product product = Product.buildWithFeatures("F1", "F2", "F3");
        Configuration configuration = new Configuration();
        configuration.product = product;

        configuration.active(Feature.withName(product, "F1"));

        assertThat(configuration.activedFeatures(), hasSize(1));
        assertThat(configuration.activedFeatures(), hasItem("F1"));
    }

    @Test
    public void shouldReturnTwoFeaturesActiveIfActivated() throws Exception {
        Product product = Product.buildWithFeatures("F1", "F2", "F3");
        Configuration configuration = new Configuration();
        configuration.product = product;

        configuration.active(Feature.withName(product, "F1"));
        configuration.active(Feature.withName(product, "F2"));

        assertThat(configuration.activedFeatures(), hasSize(2));
        assertThat(configuration.activedFeatures(), hasItem("F1"));
        assertThat(configuration.activedFeatures(), hasItem("F2"));
    }

    @Test
    public void shouldDeactiveFeatureIfPreviouslyActivated() throws Exception {
        Product product = Product.buildWithFeatures("F1", "F2", "F3");
        Configuration configuration = new Configuration();
        configuration.product = product;

        configuration.active(Feature.withName(product, "F1"));
        configuration.active(Feature.withName(product, "F2"));

        configuration.deactive(Feature.withName(product, "F2"));

        assertThat(configuration.activedFeatures(), hasSize(1));
        assertThat(configuration.activedFeatures(), hasItem("F1"));
    }

}
