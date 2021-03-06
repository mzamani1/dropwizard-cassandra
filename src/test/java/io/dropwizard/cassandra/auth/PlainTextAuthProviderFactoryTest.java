package io.dropwizard.cassandra.auth;

import com.datastax.driver.core.PlainTextAuthProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Resources;
import io.dropwizard.configuration.ConfigurationException;
import io.dropwizard.configuration.YamlConfigurationFactory;
import io.dropwizard.jackson.DiscoverableSubtypeResolver;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.jersey.validation.Validators;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.validation.Validator;

import static org.assertj.core.api.Assertions.assertThat;

public class PlainTextAuthProviderFactoryTest {
    private final ObjectMapper objectMapper = Jackson.newObjectMapper();
    private final Validator validator = Validators.newValidator();
    private final YamlConfigurationFactory<AuthProviderFactory> factory =
            new YamlConfigurationFactory<>(AuthProviderFactory.class, validator, objectMapper, "dw");

    @Test
    public void isDiscoverable() throws Exception {
        assertThat(new DiscoverableSubtypeResolver().getDiscoveredSubtypes())
                .contains(PlainTextAuthProviderFactory.class);
    }

    @Test
    public void shouldBuildAPlainTextAuthProvider() throws URISyntaxException, IOException, ConfigurationException {
        final File yaml = new File(Resources.getResource("smoke/auth/plain-text.yaml").toURI());
        final AuthProviderFactory factory = this.factory.build(yaml);
        assertThat(factory).isInstanceOf(AuthProviderFactory.class);
        final PlainTextAuthProviderFactory authProviderFactory = (PlainTextAuthProviderFactory) factory;
        assertThat(authProviderFactory.getUsername()).isEqualTo("admin");
        assertThat(authProviderFactory.getPassword()).isEqualTo("hunter2");

        assertThat(factory.build()).isInstanceOf(PlainTextAuthProvider.class);
    }
}
