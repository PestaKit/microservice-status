package io.pestakit.status.api.spec.helpers;

import io.pestakit.status.ApiException;
import io.pestakit.status.ApiResponse;
import io.pestakit.status.api.ServicesApi;
import io.pestakit.status.api.dto.ServicePost;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Olivier Liechti on 24/06/17.
 */
public class Environment {

    private ServicesApi api = new ServicesApi();
    private ApiException lastApiException;
    private ApiResponse lastApiResponse;
    private int lastStatusCode;
    private ServicePost toPostService;

    public Environment() throws IOException {
        Properties properties = new Properties();
        properties.load(this.getClass().getClassLoader().getResourceAsStream("environment.properties"));
        String url = properties.getProperty("io.pestakit.status.server.url");
        api.getApiClient().setBasePath(url);
    }

    public ServicesApi getApi() {
        return api;
    }

    public void setLastApiException(ApiException lastApiException)
    {
        this.lastApiException = lastApiException;
    }

    public void setLastApiResponse(ApiResponse lastApiResponse)
    {
        this.lastApiResponse = lastApiResponse;
    }

    public int getLastStatusCode()
    {
        return lastStatusCode;
    }

    public void setLastStatusCode(int lastStatusCode)
    {
        this.lastStatusCode = lastStatusCode;
    }

    public ServicePost getToPostService()
    {
        return toPostService;
    }

    public void setToPostService(ServicePost toPostService)
    {
        this.toPostService = toPostService;
    }
}
