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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Matthieu Chatelant, Lara Chauffoureaux, Alain Hardy
 */
public class CreationSteps
{
   private Environment environment;
   private ServicesApi api;

   // Apiexception.getMessage -> désérialiser avec gson dans un object Error.

   private ServicePost service;
   private List<ServiceGet> services;
   private int size;

   private ApiResponse lastApiResponse;
   private ApiException lastApiException;
   private boolean lastApiCallThrewException;
   private int lastStatusCode;

   public CreationSteps(Environment environment)
   {
      this.environment = environment;
      this.api = environment.getApi();
   }

   @Given("^there is a Services server$")
   public void there_is_a_Services_server() throws Throwable
   {
      assertNotNull(api);
   }

   @Given("^I have a service payload$")
   public void i_have_a_service_payload() throws Throwable
   {
      service = new ServicePost();
      service.setName("Test");
      service.setStatusAddress("10.10.10.3");
   }

   @Given("^I have an invalid service payload$")
   public void i_have_an_invalid_service_payload() throws Throwable
   {
      service = new ServicePost();
      service.setName("Invalid test");
      service.setStatusAddress(null);
   }

   @Given("^I have an invalid service with an invalid state parameter$")
   public void i_have_an_invalid_service_with_an_invalid_state_parameter() throws Throwable
   {
      service = new ServicePost();
      service.setName("Invalid state");
      service.setStatusAddress("0.0.0.0");
      service.setState("invalid");
   }

   @Given("^I recuperate the actual size of the service list$")
   public void i_recuperate_the_actual_size_of_the_service_list() throws Throwable {
      i_GET_on_services_endpoint();
      size = services.size();
   }

   @When("^I POST it to the /services endpoint$")
   public void i_POST_it_to_the_services_endpoint() throws Throwable
   {
      try
      {
         lastApiResponse = api.addServiceWithHttpInfo(service);
         lastApiCallThrewException = false;
         lastApiException = null;
         lastStatusCode = lastApiResponse.getStatusCode();
      }
      catch (ApiException e)
      {
         lastApiCallThrewException = true;
         lastApiResponse = null;
         lastApiException = e;
         lastStatusCode = lastApiException.getCode();
      }
   }

   @When("^I GET on /services endpoint$")
   public void i_GET_on_services_endpoint() throws Throwable
   {
      try
      {
         lastApiResponse = api.getServicesWithHttpInfo(null);
         lastApiCallThrewException = false;
         lastApiException = null;
         lastStatusCode = lastApiResponse.getStatusCode();
         services = (List<ServiceGet>) lastApiResponse.getData();
      }
      catch (ApiException e)
      {
         lastApiCallThrewException = true;
         lastApiResponse = null;
         lastApiException = e;
         lastStatusCode = lastApiException.getCode();
      }
   }

   @When("^I GET on /services\\?state=(.*) endpoint$")
   public void i_GET_on_services_with_state_endpoint(String state) throws Throwable
   {
      try
      {
         lastApiResponse = api.getServicesWithHttpInfo(state);
         lastApiCallThrewException = false;
         lastApiException = null;
         lastStatusCode = lastApiResponse.getStatusCode();
         services = (List<ServiceGet>) lastApiResponse.getData();
      }
      catch (ApiException e)
      {
         lastApiCallThrewException = true;
         lastApiResponse = null;
         lastApiException = e;
         lastStatusCode = lastApiException.getCode();
      }
   }

   @Then("^I receive a (\\d+) status code$")
   public void i_receive_a_status_code(int arg1) throws Throwable
   {
      assertEquals(arg1, lastStatusCode);
   }

   @Then("^I receive a list of services$")
   public void i_receive_a_list_of_services() throws Throwable
   {
      assertNotNull(services);
   }

   @Then("^The size should be greater by one$")
   public void the_size_should_be_greater_by_one() throws Throwable {
      i_GET_on_services_endpoint();
      int new_size = services.size();

      assertEquals(size + 1, new_size);
   }

   @Then("^I receive a list of services with state (.*)$")
   public void i_receive_a_list_of_services_with_wanted_state(String state) throws Throwable
   {
      System.out.println("size " + services.size());
      boolean servicesHaveTheCorrectState = true;

      for(ServiceGet service : services) {
         if(! service.getState().equals(state)) {
            servicesHaveTheCorrectState = false;
            break;
         }
      }

      assertTrue(servicesHaveTheCorrectState);
   }
}
