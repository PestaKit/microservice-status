package io.pestakit.status.api.spec.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.pestakit.status.ApiException;
import io.pestakit.status.ApiResponse;
import io.pestakit.status.api.ServicesApi;
import io.pestakit.status.api.dto.ServiceGet;
import io.pestakit.status.api.dto.ServicePost;
import io.pestakit.status.api.dto.State;
import io.pestakit.status.api.spec.helpers.Environment;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

/**
 * @author Matthieu Chatelan, Lara Chauffoureaux, Alain Hardy
 */
public class SimpleSteps
{
   private Environment environment;
   private ServicesApi api;

   private int size;

   public SimpleSteps(Environment environment)
   {
      this.environment = environment;
      this.api = environment.getApi();
   }

   @Given("^I have a service payload$")
   public void i_have_a_service_payload() throws Throwable
   {
      ServicePost service = new ServicePost();
      service.setName("Simple test");
      service.setStatusAddress("10.10.10.1");
      service.setState(State.UP);
      service.setContact("Simple Steps");
      service.setDescription("Service posted for simple test");

      environment.setToPostService(service);
   }

   @Given("^I have an invalid service payload \\(address is null\\)$")
   public void i_have_an_invalid_service_payload() throws Throwable
   {
      ServicePost service = new ServicePost();
      service.setName("Invalid test");
      service.setStatusAddress(null);

      environment.setToPostService(service);
   }

   @Given("^I recuperate the actual size of the service list$")
   public void i_recuperate_the_actual_size_of_the_service_list() throws Throwable {
      i_GET_on_services_endpoint();
      size = environment.getRecuperatedServices().size();
   }

   @When("^I GET on /services endpoint$")
   public void i_GET_on_services_endpoint() throws Throwable
   {
      try
      {
         ApiResponse lastApiResponse = api.getServicesWithHttpInfo(null);

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

   @When("^I POST it to the /services endpoint$")
   public void i_POST_it_to_the_services_endpoint() throws Throwable
   {
      try
      {
         ApiResponse lastApiResponse = api.addServiceWithHttpInfo(environment.getToPostService());

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

   @Then("^I receive a (\\d+) status code$")
   public void i_receive_a_status_code(int arg1) throws Throwable
   {
      assertEquals(arg1, environment.getLastStatusCode());
   }

   @Then("^I receive a list of services$")
   public void i_receive_a_list_of_services() throws Throwable
   {
      assertNotNull(environment.getRecuperatedServices());
   }

   @Then("^The size should be greater by one$")
   public void the_size_should_be_greater_by_one() throws Throwable {
      i_GET_on_services_endpoint();
      int new_size = environment.getRecuperatedServices().size();

      assertEquals(size + 1, new_size);
   }
}