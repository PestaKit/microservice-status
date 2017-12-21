package io.pestakit.status.api.spec.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.pestakit.status.ApiException;
import io.pestakit.status.ApiResponse;
import io.pestakit.status.api.ServiceApi;
import io.pestakit.status.api.dto.State;
import io.pestakit.status.api.spec.helpers.Environment;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.Assert.assertTrue;

public class PutPatchSteps
{
   private Environment environment;
   private ServiceApi api;

   private State state;

   public PutPatchSteps(Environment environment)
   {
      this.environment = environment;
      this.api = environment.getServiceApi();
   }

   @Given("^I have a valid status$")
   public void i_have_a_valid_status() throws Throwable
   {
      state = State.DOWN;
   }

   @Given("^I have an invalid status$")
   public void i_have_an_invalid_status() throws Throwable
   {
      state = null;
   }

   @When("^I PATCH on /services/\\{uid} endpoint$")
   public void i_PATCH_on_services_uid_endpoint() throws Throwable
   {
      try
      {
         ApiResponse lastApiResponse = api.servicesServiceUIDPatchWithHttpInfo(environment.getCurrent_uid(), state);

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

      /*
      HttpURLConnection connection = null;

      try
      {
         //Create connection
         URL url = new URL("http://localhost:8080/api/services/" + environment.getCurrent_uid());
         connection = (HttpURLConnection) url.openConnection();
         connection.setRequestMethod("PATCH");
         connection.setRequestProperty("Accept", "");

         connection.setUseCaches(false);
         connection.setDoOutput(true);

         //Send request
         DataOutputStream wr = new DataOutputStream(
            connection.getOutputStream());
         wr.writeBytes(state);
         wr.close();

         //Get Response
         environment.setLastStatusCode(connection.getResponseCode());
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
      */
   }

   @When("^I PUT on /services/\\{uid} endpoint$")
   public void i_PUT_on_services_uid_endpoint() throws Throwable
   {
      try
      {
         ApiResponse lastApiResponse = api.servicesServiceUIDPutWithHttpInfo(environment.getCurrent_uid(), environment.getToPostService());

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

   @Then("^The status has changed$")
   public void the_status_has_changed() throws Throwable
   {
      assertTrue(environment.getRecuperatedServices().get(0).getState().toString().equals(state.toString()));
   }

   @Then("^The service has changed$")
   public void the_service_has_changed() throws Throwable
   {
      assertTrue(environment.getRecuperatedServices().get(0).getName().equals("Simple test"));
   }
}
