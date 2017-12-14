package io.pestakit.status.api.spec.steps;

import io.pestakit.status.api.ServicesApi;
import io.pestakit.status.api.dto.ServiceGet;
import io.pestakit.status.api.spec.helpers.Environment;

import java.util.List;

public class SingleServiceSteps
{
   private Environment environment;
   private ServicesApi api;

   private String id;

   public SingleServiceSteps(Environment environment)
   {
      this.environment = environment;
      this.api = environment.getApi();
   }
}
