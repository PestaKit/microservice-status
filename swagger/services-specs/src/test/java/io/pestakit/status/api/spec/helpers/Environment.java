package io.pestakit.status.api.spec.helpers;

import io.pestakit.status.api.ServicesApi;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Olivier Liechti on 24/06/17.
 */
public class Environment {

    private ServicesApi api = new ServicesApi();

    public Environment() throws IOException {
        Properties properties = new Properties();
        properties.load(this.getClass().getClassLoader().getResourceAsStream("environment.properties"));
        String url = properties.getProperty("io.pestakit.status.server.url");
        api.getApiClient().setBasePath(url);

    }

    public ServicesApi getApi() {
        return api;
    }


}
