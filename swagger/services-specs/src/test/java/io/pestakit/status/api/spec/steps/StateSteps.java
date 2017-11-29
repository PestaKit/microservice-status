package io.pestakit.status.api.spec.steps;

import io.pestakit.status.ApiException;
import io.pestakit.status.ApiResponse;
import io.pestakit.status.api.ServicesApi;
import io.pestakit.status.api.dto.ServiceGet;
import io.pestakit.status.api.dto.ServicePost;
import io.pestakit.status.api.spec.helpers.Environment;

import java.util.List;

/**
 * @author Matthieu Chatelant, Lara Chauffoureaux, Alain Hardy
 */
public class StateSteps
{
   private Environment environment;
   private ServicesApi api;

   private ServicePost service;
   private List<ServiceGet> services;

   private ApiResponse lastApiResponse;
   private ApiException lastApiException;
   private boolean lastApiCallThrewException;
   private int lastStatusCode;

   public StateSteps(Environment environment)
   {
      this.environment = environment;
      this.api = environment.getApi();
   }



}
