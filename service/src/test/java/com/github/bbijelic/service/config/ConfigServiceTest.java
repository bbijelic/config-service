package com.github.bbijelic.service.config;

import com.github.bbijelic.service.config.application.api.Application;
import com.github.bbijelic.service.config.config.ServiceConfiguration;
import com.github.bbijelic.service.config.environment.api.Environment;
import com.github.bbijelic.service.config.region.api.Region;
import com.google.common.io.BaseEncoding;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Base64;

/**
 * Config Service integration test
 */
public class ConfigServiceTest {

    @ClassRule
    public static final DropwizardAppRule<ServiceConfiguration> SERVICE_RULE
            = new DropwizardAppRule<ServiceConfiguration>(
            ConfigService.class, ResourceHelpers.resourceFilePath("config.yml"));

    @Test
    public void testNotFound(){

        final Client client = new JerseyClientBuilder().build();

        final Response response =
                client.target(String.format("http://localhost:%d/file-config", SERVICE_RULE.getLocalPort()))
                        .queryParam("name", "config.yml")
                        .queryParam("environment", "dev")
                        .queryParam("region", "EU")
                        .queryParam("application", "dropwizard")
                        .request("asd")
                        .get(Response.class);

        Assert.assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void testOk(){

        // Client
        final Client client = new JerseyClientBuilder().build();

        // Make application
        final Application application = new Application();
        application.setName("dropwizard");
        application.setDescription("dropwizard application");

        // Create application
        final Response applicationPostResponse =
                client.target(String.format("http://localhost:%d/application", SERVICE_RULE.getLocalPort()))
                        .request().post(Entity.entity(application, MediaType.APPLICATION_JSON_TYPE));

        Assert.assertEquals(Response.Status.CREATED.getStatusCode(), applicationPostResponse.getStatus());

        // Make environment
        final Environment environment = new Environment();
        environment.setName("dev");
        environment.setDescription("dev environment");

        // Create application
        final Response environmentPostResponse =
                client.target(String.format("http://localhost:%d/environment", SERVICE_RULE.getLocalPort()))
                        .request().post(Entity.entity(environment, MediaType.APPLICATION_JSON_TYPE));

        Assert.assertEquals(Response.Status.CREATED.getStatusCode(), environmentPostResponse.getStatus());

        // Make region
        final Region region = new Region();
        region.setName("eu");
        region.setDescription("eu region");

        // Create application
        final Response regionPostResponse =
                client.target(String.format("http://localhost:%d/region", SERVICE_RULE.getLocalPort()))
                        .request().post(Entity.entity(region, MediaType.APPLICATION_JSON_TYPE));

        Assert.assertEquals(Response.Status.CREATED.getStatusCode(), regionPostResponse.getStatus());

        // Request
        final Response response =
                client.target(String.format("http://localhost:%d/file-configuration", SERVICE_RULE.getLocalPort()))
                        .queryParam("name", "config.yml")
                        .queryParam("environment", environment.getName())
                        .queryParam("region", region.getName())
                        .queryParam("application", application.getName())
                        .request().post(Entity.entity("test".getBytes(), MediaType.APPLICATION_OCTET_STREAM_TYPE));

        Assert.assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());

        // Search for submitted configuration
        final Response findResponse =
                client.target(String.format("http://localhost:%d/file-configuration", SERVICE_RULE.getLocalPort()))
                        .queryParam("name", "config.yml")
                        .queryParam("environment", environment.getName())
                        .queryParam("region", region.getName())
                        .queryParam("application", application.getName())
                        .request().get();

        Assert.assertEquals(Response.Status.OK.getStatusCode(), findResponse.getStatus());
        Assert.assertEquals("test", new String(Base64.getDecoder().decode(findResponse.readEntity(byte[].class))));
    }

}
