package io.pestakit.status.api.spec.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.pestakit.status.ApiException;
import io.pestakit.status.ApiResponse;
import io.pestakit.status.api.ServiceApi;
import io.pestakit.status.api.dto.ServiceGet;
import io.pestakit.status.api.spec.helpers.Environment;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class SingleServiceSteps
{
   private Environment environment;
   private ServiceApi api;

   public SingleServiceSteps(Environment environment)
   {
      this.environment = environment;
      this.api = environment.getServiceApi();
   }

   @Given("^I have a valid service uid$")
   public void i_have_a_valid_service_uid() throws Throwable
   {
      environment.setCurrent_uid(getUidFromSelf(environment.getApi().getServicesWithHttpInfo(null).getData().get(0).getSelf()));
   }

   @Given("^I have an invalid service uid$")
   public void i_have_an_invalid_service_uid() throws Throwable
   {
      environment.setCurrent_uid("42");
   }

   @When("^I GET on /services/\\{uid} endpoint$")
   public void i_GET_on_services_uid_endpoint() throws Throwable
   {
      try
      {
         ApiResponse lastApiResponse = api.getOneServiceWithHttpInfo(environment.getCurrent_uid());

         environment.setLastApiResponse(lastApiResponse);
         environment.setLastApiException(null);
         environment.setLastStatusCode(lastApiResponse.getStatusCode());

         environment.setRecuperatedServices(Arrays.asList((ServiceGet) lastApiResponse.getData()));
      }
      catch (ApiException e)
      {
         environment.setLastApiResponse(null);
         environment.setLastApiException(e);
         environment.setLastStatusCode(e.getCode());
      }
   }

   @When("^I DELETE on /services/\\{uid} endpoint$")
   public void i_DELETE_on_services_uid_endpoint() throws Throwable
   {
      try
      {
         ApiResponse lastApiResponse = api.servicesServiceUIDDeleteWithHttpInfo(environment.getCurrent_uid());

         environment.setLastApiResponse(lastApiResponse);
         environment.setLastApiException(null);
         environment.setLastStatusCode(lastApiResponse.getStatusCode());
      }
      catch (ApiException e)
      {
         environment.setLastApiResponse(null);
         environment.setLastApiException(e);
         environment.setLastStatusCode(e.getCode());
      }
   }

   @Then("^I receive a services with the correct uid$")
   public void i_receive_a_services_with_the_correct_uid() throws Throwable
   {
      assertEquals(environment.getCurrent_uid(), getUidFromSelf(environment.getRecuperatedServices().get(0).getSelf()));
   }

   private String getUidFromSelf(String self)
   {
      return self.substring(self.lastIndexOf('/') + 1, self.length());
   }
}