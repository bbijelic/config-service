package com.github.bbijelic.service.config;

import com.github.bbijelic.service.config.config.ServiceConfiguration;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.xml.ws.Response;

public class ConfigServiceTest {

    @ClassRule
    public static final DropwizardAppRule<ServiceConfiguration> RULE
            = new DropwizardAppRule<ServiceConfiguration>(
            ConfigService.class, ResourceHelpers.resourceFilePath("config.yml"));

    @Test
    public void test(){



    }

}
