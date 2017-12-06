package io.pestakit.status.api.endpoints;

import io.pestakit.status.api.ServicesApi;
import io.pestakit.status.api.model.ServiceGet;
import io.pestakit.status.api.model.ServicePost;
import io.pestakit.status.api.model.State;
import io.pestakit.status.entities.ServiceEntity;
import io.pestakit.status.repositories.ServiceRepository;
import io.pestakit.status.validators.ServicePostValidator;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class StatusApiController implements ServicesApi
{
   @Autowired
   ServiceRepository serviceRepository;

   private final String BASE_URL = "http://localhost:8080/services/";

   /**
    * Method used to add one/several service(s) when a post is made to the api
    * @param service a service to add to the store
    * @return a response code
    */
   @Override
   public ResponseEntity<Void> addService(@ApiParam(value = "" ,required=true ) @Valid @RequestBody ServicePost service)
   {
      if(checkServicePost(service))
      {
         // Convert the service to a serviceEntity
         ServiceEntity serviceEntity = toServiceEntity(service);

         // Save the service into the DB
         serviceRepository.save(serviceEntity);

         // Return a OK code
         return new ResponseEntity<Void>(HttpStatus.OK);
      }

      // Something went wrong, return a 400 Bad Request
      return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
   }

   /**
    * Method used when a get is made with a specific id to get the related service
    * @param serviceID the service id of the required service
    * @return a response code and the payload
    */
   @Override
   public ResponseEntity<ServiceGet> getOneService(@ApiParam(value = "Numeric ID of the service to get.",required=true ) @PathVariable("serviceID") Integer serviceID,
                                                   @ApiParam(value = "The user token" ,required=true ) @RequestHeader(value="token", required=true) String token)
   {
      return null;
   }

   /**
    * Get a list of all services available in the store
    * @param status filter by status, none specified mean all
    * @return a list of services and a response code
    */
   @Override
   public ResponseEntity<List<ServiceGet>> getServices( @ApiParam(value = "Status wanted, none specified mean all", allowableValues = "UP, DOWN, MAINTENANCE") @RequestParam(value = "status", required = false) String status)
   {
      // The list of services to return to the user
      ArrayList<ServiceGet> liste = new ArrayList<>();
      Iterable<ServiceEntity> searchResult = null;

      // If we want a specific status
      if(status != null)
      {
         status = status.toLowerCase();
         State state = State.fromValue(status);

         if(state != null)
         {
            searchResult = serviceRepository.findByState(state);
         }
         else
         {
            // TO BE DONE WHEN MATTHIEU IS FINISHED
         }
      }

      // Otherwise return everything
      else
         searchResult = serviceRepository.findAll();

      // Prepare the response content
      for(ServiceEntity s : searchResult)
      {
         // Convert the serviceEntity to a serviceGet
         ServiceGet service = toService(s);
         liste.add(service);
      }

      // Return everything to the client
      return new ResponseEntity<List<ServiceGet>>(liste, HttpStatus.OK);
   }

   /**
    * Fix a service with a put request
    * @param serviceS
    * @return
    */
   @Override
   public ResponseEntity<Void> servicesPut(@ApiParam(value = "" ,required=true ) @RequestBody Object serviceS)
   {
      return null;
   }

   /**
    *
    * @param serviceID
    * @return
    */
   @Override
   public ResponseEntity<Void> servicesServiceIDDelete(@ApiParam(value = "Numeric ID of the service to delete.",required=true ) @PathVariable("serviceID") Integer serviceID)
   {
      return null;
   }

   /**
    *
    * @param serviceID
    * @param state
    * @return
    */
   @Override
   public ResponseEntity<Void> servicesServiceIDPatch(@ApiParam(value = "Numeric ID of the service to patch.",required=true ) @PathVariable("serviceID") Integer serviceID,
                                                      @ApiParam(value = "The new state of the service" ,required=true ) @RequestHeader(value="state", required=true) Integer state)
   {
      return null;
   }

   ////////////////////////////////////////////////////////////////////////////////////////////
   ////////////////////// EXCEPTION HANDLING //////////////////////////////////////////////////
   ////////////////////////////////////////////////////////////////////////////////////////////
   @ExceptionHandler(Exception.class)
   @ResponseBody
   public Map<String,String> errorResponse(Exception ex, HttpServletResponse response)
   {
      // Map containing error messages
      Map<String,String> errorMap = new HashMap<>();

      // Insert the error message into the map
      errorMap.put("errorMessage",ex.getMessage());

      StringWriter sw = new StringWriter();
      PrintWriter pw = new PrintWriter(sw);
      ex.printStackTrace(pw);
      String stackTrace = sw.toString();

      // Insert the prepared stack trace message into the map
      errorMap.put("errorStackTrace", stackTrace);

      // Set the response status to the desired error code
      response.setStatus(HttpStatus.BAD_REQUEST.value());

      // Return the prepared error map
      return errorMap;
   }

   ////////////////////////////////////////////////////////////////////////////////////////////
   ///////////////////// PRIVATE METHODS //////////////////////////////////////////////////////
   ////////////////////////////////////////////////////////////////////////////////////////////

   /**
    * Private method used to go from a Service to a ServiceEntity
    *
    * @param service the received service
    * @return the new ServiceEntity
    */
   private ServiceEntity toServiceEntity(ServicePost service)
   {
      ServiceEntity entity = new ServiceEntity();

      entity.setStatusAddress(service.getStatusAddress());
      entity.setState(service.getState());
      entity.setName(service.getName());
      entity.setDescription(service.getDescription());
      entity.setContact(service.getContact());

      return entity;
   }

   /**
    * Private method used to go from a serviceEntity to a Service
    * @param serviceEntity the received serviceEntity
    * @return the new Service
    */
   private ServiceGet toService(ServiceEntity serviceEntity) {
      ServiceGet service = new ServiceGet();

      service.setContact(serviceEntity.getContact());
      service.setDescription(serviceEntity.getDescription());
      service.setSelf(BASE_URL + serviceEntity.getId());
      service.setName(serviceEntity.getName());
      service.setState(serviceEntity.getState());
      service.setStatusAddress(serviceEntity.getStatusAddress());

      return service;
   }

   /**
    * Check whether the service is valid
    * @param service the service to check
    * @return the result of the check. True = success, False = Fail.
    */
   private boolean checkServicePost(ServicePost service)
   {
      return service.getName() != null &&
         service.getStatusAddress() != null;
   }

   @Autowired
   ServicePostValidator servicePostValidator;

   @InitBinder("service")
   public void initBinder(WebDataBinder binder) {
      binder.addValidators(servicePostValidator);
   }
}
