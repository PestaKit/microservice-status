package io.pestakit.status.api.spec.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import io.pestakit.status.ApiException;
import io.pestakit.status.ApiResponse;
import io.pestakit.status.api.ServicesApi;
import io.pestakit.status.api.dto.ServicePost;
import io.pestakit.status.api.dto.State;
import io.pestakit.status.api.spec.helpers.Environment;

import static org.junit.Assert.assertNotNull;

public class InitializationSteps
{
   private Environment environment;
   private ServicesApi api;

   public InitializationSteps(Environment environment)
   {
      this.environment = environment;
      this.api = environment.getApi();
   }

   @Given("^There is a Services server$")
   public void there_is_a_Services_server() throws Throwable
   {
      assertNotNull(api);
   }

   @Given("^I have a flushed database$")
   public void flush_database() throws Throwable
   {
      try
      {
         ApiResponse lastApiResponse = api.deleteServicesWithHttpInfo();

         environment.setLastApiResponse(lastApiResponse);
         environment.setLastApiException(null);
      }
      catch (ApiException e)
      {
         environment.setLastApiResponse(null);
         environment.setLastApiException(e);
      }
   }

   @Then("^I populate it with some services$")
   public void populate_database() throws Throwable
   {
      for(int i = 0; i < 3; i++)
      {
         ServicePost service = new ServicePost();
         service.setName("Service " + i);
         service.setStatusAddress("127.0.0." + i);
         service.setState(State.values()[i % State.values().length]);
         service.setContact("Initialization Steps");
         service.setDescription("Populating service no." + i);

         try
         {
            ApiResponse lastApiResponse = api.addServiceWithHttpInfo(service);

            environment.setLastApiResponse(lastApiResponse);
            environment.setLastApiException(null);
         }
         catch (ApiException e)
         {
            environment.setLastApiResponse(null);
            environment.setLastApiException(e);
         }
      }
   }
}
