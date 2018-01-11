package io.pestakit.status.api.spec.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.pestakit.status.ApiException;
import io.pestakit.status.ApiResponse;
import io.pestakit.status.api.ServiceApi;
import io.pestakit.status.api.dto.State;
import io.pestakit.status.api.spec.helpers.Environment;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;

import static org.junit.Assert.assertTrue;

public class PutPatchSteps
{
   private Environment environment;
   private ServiceApi api;

   private String state;
   private String length;

   public PutPatchSteps(Environment environment)
   {
      this.environment = environment;
      this.api = environment.getServiceApi();
   }

   @Given("^I have a valid status$")
   public void i_have_a_valid_status() throws Throwable
   {
      state = "\"up\"";
      length = "4";
   }

   @Given("^I have an invalid status$")
   public void i_have_an_invalid_status() throws Throwable
   {
      state = "\"toto\"";
      length = "6";
   }

   @When("^I PATCH on /services/\\{uid} endpoint$")
   public void i_PATCH_on_services_uid_endpoint() throws Throwable
   {
      // We don't use API to allow a PATCH with an invalid value and HttpUrlConnection doesn't allow patch

      Socket httpSocket = null;
      DataOutputStream os = null;
      BufferedReader is = null;

      try
      {
         httpSocket = new Socket("127.0.0.1", 8080);
         os = new DataOutputStream(httpSocket.getOutputStream());
         is = new BufferedReader(new InputStreamReader(httpSocket.getInputStream()));
      }
      catch (Exception e)
      {
         System.out.println(e.getMessage());
         assertTrue(false);
      }

      System.out.println("Socket opened");

      if (httpSocket != null && os != null && is != null)
      {
         try
         {
            os.writeBytes("PATCH /api/services/" + environment.getCurrent_uid() + " HTTP/1.1\n");
            os.writeBytes("Host: localhost:8080\n");
            os.writeBytes("User-Agent: Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:57.0) Gecko/20100101 Firefox/57.0");
            os.writeBytes("Accept: */*\n");
            os.writeBytes("Accept-Language: en-US,en;q=0.5\n");
            os.writeBytes("Accept-Encoding: gzip, deflate\n");
            os.writeBytes("Referer: http://localhost:8080/api/swagger-ui.html\n");
            os.writeBytes("Content-Type: application/json\n");
            os.writeBytes("Content-Length: " + length + "\n");
            os.writeBytes("Connection: keep-alive\n");
            os.writeBytes("\n");
            os.writeBytes(state);
            os.flush();

            String responseLine = is.readLine();
            int responseCode = Integer.parseInt(responseLine.substring(responseLine.length() - 4, responseLine.length() - 1));

            os.close();
            is.close();
            httpSocket.close();

            environment.setLastStatusCode(responseCode);
         }
         catch (Exception e)
         {
            assertTrue(false);
         }
      }
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
      assertTrue(("\"" + environment.getRecuperatedServices().get(0).getState().toString() + "\"").equals(state));
   }

   @Then("^The service has changed$")
   public void the_service_has_changed() throws Throwable
   {
      assertTrue(environment.getRecuperatedServices().get(0).getName().equals("Simple test"));
   }
}
