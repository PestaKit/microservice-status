package io.pestakit.status.api.spec.helpers;

import io.pestakit.status.ApiException;
import io.pestakit.status.ApiResponse;
import io.pestakit.status.api.ServiceApi;
import io.pestakit.status.api.ServicesApi;
import io.pestakit.status.api.dto.ServiceGet;
import io.pestakit.status.api.dto.ServicePost;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * Created by Olivier Liechti on 24/06/17.
 */
public class Environment {

    private ServicesApi api = new ServicesApi();
    private ServiceApi serviceApi = new ServiceApi();
    private ApiException lastApiException;
    private ApiResponse lastApiResponse;
    private int lastStatusCode;
    private ServicePost toPostService;
    private List<ServiceGet> recuperatedServices;
    private String current_uid;

    public Environment() throws IOException {
        Properties properties = new Properties();
        properties.load(this.getClass().getClassLoader().getResourceAsStream("environment.properties"));
        String url = properties.getProperty("io.pestakit.status.server.url");
        api.getApiClient().setBasePath(url);
        serviceApi.getApiClient().setBasePath(url);
    }

    public ServicesApi getApi() {
        return api;
    }

    public ServiceApi getServiceApi()
    {
        return serviceApi;
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

    public List<ServiceGet> getRecuperatedServices()
    {
        return recuperatedServices;
    }

    public void setRecuperatedServices(List<ServiceGet> recuperatedServices)
    {
        this.recuperatedServices = recuperatedServices;
    }

    public String getCurrent_uid()
    {
        return current_uid;
    }

    public void setCurrent_uid(String current_uid)
    {
        this.current_uid = current_uid;
    }
}
