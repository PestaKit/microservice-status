package io.pestakit.status.api.spec.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.pestakit.status.ApiException;
import io.pestakit.status.ApiResponse;
import io.pestakit.status.api.ServicesApi;
import io.pestakit.status.api.dto.ServiceGet;
import io.pestakit.status.api.dto.ServicePost;
import io.pestakit.status.api.spec.helpers.Environment;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class StateSteps
{
   private Environment environment;
   private ServicesApi api;

   public StateSteps(Environment environment)
   {
      this.environment = environment;
      this.api = environment.getApi();
   }

   @Given("^I have an invalid service with an invalid state parameter$")
   public void i_have_an_invalid_service_with_an_invalid_state_parameter() throws Throwable
   {
      ServicePost service = new ServicePost();
      service.setName("Invalid State");
      service.setStatusAddress("0.0.0.0");
      service.setState(null);
      service.setContact("State Steps");
      service.setDescription("Service with an invalid state parameter");

      environment.setToPostService(service);
   }

   @When("^I GET on /services\\?state=(.*) endpoint$")
   public void i_GET_on_services_with_state_endpoint(String state) throws Throwable
   {
      try
      {
         ApiResponse lastApiResponse = api.getServicesWithHttpInfo(state);

         environment.setLastApiResponse(lastApiResponse);
         environment.setLastApiException(null);
         environment.setLastStatusCode(lastApiResponse.getStatusCode());

         environment.setRecuperatedServices((List<ServiceGet>) lastApiResponse.getData());
      }
      catch (ApiException e)
      {
         environment.setLastApiResponse(null);
         environment.setLastApiException(e);
         environment.setLastStatusCode(e.getCode());
      }
   }

   @Then("^I receive a list of services with state (.*)$")
   public void i_receive_a_list_of_services_with_wanted_state(String state) throws Throwable
   {
      boolean servicesHaveTheCorrectState = true;
      List<ServiceGet> services = environment.getRecuperatedServices();

      for(ServiceGet service : services) {
         if(! service.getState().toString().equals(state)) {
            servicesHaveTheCorrectState = false;
            break;
         }
      }

      assertTrue(servicesHaveTheCorrectState);
   }
}
