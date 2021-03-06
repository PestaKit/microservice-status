package io.pestakit.status.api.endpoints;

import io.pestakit.status.api.ServicesApi;
import io.pestakit.status.api.exceptions.NotFoundException;
import io.pestakit.status.api.model.InlineResponse201;
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

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class StatusApiController implements ServicesApi
{
   @Autowired
   ServiceRepository serviceRepository;

   @Autowired
   ServicePostValidator servicePostValidator;

   private final String PASSPHRASE = "ca2f2315-f538-42f7-a68e-7817c9b57911";

   private final String BASE_URL = "http://localhost:8080/api/services/";

   /**
    * Method used to add one/several service(s) when a post is made to the api
    *
    * @param service a service to add to the store
    * @return a response code
    */
   @Override
   public ResponseEntity<InlineResponse201> addService(@ApiParam(value = "", required = true) @Valid @RequestBody ServicePost service)
   {
      // Convert the service to a se ServiceEntity
      ServiceEntity serviceEntity = toServiceEntity(service);

      // Save the service into the DB
      serviceRepository.save(serviceEntity);

      // Return a OK code
      return new ResponseEntity<>(new InlineResponse201().self("http://localhost:8080/api/services/" + serviceEntity.getUid()).uid(serviceEntity.getUid()), HttpStatus.CREATED);
   }

   /**
    * Method used when a get is made with a specific id to get the related service
    *
    * @param serviceUID the service id of the required service
    * @return a response code and the payload
    */
   @Override
   public ResponseEntity<ServiceGet> getOneService(@ApiParam(value = "Numeric ID of the service to get.", required = true) @PathVariable("serviceUID") String serviceUID)
   {
      ServiceEntity serviceEntity = serviceRepository.findByUid(serviceUID);

      // If the service is not found
      if (serviceEntity == null)
      {
         throw new NotFoundException(404, "Service not found");
      }

      ServiceGet service = toService(serviceEntity);

      return new ResponseEntity<ServiceGet>(service, HttpStatus.OK);
   }

   /**
    * Get a list of all services available in the store
    *
    * @param state filter by status, none specified mean all
    * @return a list of services and a response code
    */
   @Override
   public ResponseEntity<List<ServiceGet>> getServices(@ApiParam(value = "Status wanted, none specified mean all", allowableValues = "UP, DOWN, MAINTENANCE") @RequestParam(value = "state", required = false) String state)
   {
      // The list of services to return to the user
      ArrayList<ServiceGet> liste = new ArrayList<>();
      Iterable<ServiceEntity> searchResult = null;

      // If we want a specific status
      if (state != null)
      {
         state = state.toLowerCase();

         // Convert the string state received into a State
         State stateConverted = State.fromValue(state);

         // If the stateConverted is valid
         if (stateConverted != null)
         {
            searchResult = serviceRepository.findByState(stateConverted);
         } else
         {
            // Throw a runtime exception to be catched in exception handler
            throw new IllegalArgumentException("Illegal status");
         }
      }

      // Otherwise return everything
      else
         searchResult = serviceRepository.findAll();

      // Prepare the response content
      for (ServiceEntity s : searchResult)
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
    *
    * @param service
    * @return
    */
   @Override
   public ResponseEntity<Void> servicesServiceUIDPut(@ApiParam(value = "Numeric ID of the service to put.",required=true ) @PathVariable("serviceUID") String serviceUID,
                                                     @ApiParam(value = "" ,required=true ) @Valid @RequestBody ServicePost service)
   {
      // Delete the old service
      long code = serviceRepository.deleteByUid(serviceUID);

      if (code != 1)
         return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);

      // Create the updated service
      ServiceEntity serviceEntity = toServiceEntity(service);
      serviceEntity.setUid(serviceUID);

      // Save the updated service
      serviceRepository.save(serviceEntity);

      return new ResponseEntity<Void>(HttpStatus.OK);
   }

   /**
    * @param serviceUID
    * @return
    */
   @Override
   public ResponseEntity<Void> servicesServiceUIDDelete(@ApiParam(value = "Numeric ID of the service to delete.", required = true) @PathVariable("serviceUID") String serviceUID)
   {
      long code = serviceRepository.deleteByUid(serviceUID);

      if (code == 1)
         return new ResponseEntity<Void>(HttpStatus.OK);
      else
         return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);

   }

   /**
    * @param serviceUID
    * @param state
    * @return
    */
   @Override
   public ResponseEntity<Void> servicesServiceUIDPatch(@ApiParam(value = "Numeric ID of the service to patch.",required=true ) @PathVariable("serviceUID") String serviceUID,
                                                       @ApiParam(value = "The new state of the service" ,required=true ) @RequestBody State state)
   {
      ServiceEntity serviceEntity = serviceRepository.findByUid(serviceUID);

      if(serviceEntity != null) {
         serviceEntity.setState(state);

         serviceRepository.save(serviceEntity);

         return new ResponseEntity<Void>(HttpStatus.OK);
      }
      else
      {
         throw new NotFoundException(404, "Service not found");
      }
   }

   @Override
   public ResponseEntity<Void> deleteServices(@NotNull @ApiParam(value = "Secret passphrase to access this endpoint", required = true) @RequestParam(value = "passphrase", required = true) String passphrase)
   {
      if (passphrase.equals(PASSPHRASE))
      {
         serviceRepository.deleteAll();
         return new ResponseEntity<Void>(HttpStatus.OK);
      } else
      {
         return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
      }
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
      entity.setStatusPort(service.getStatusPort());
      entity.setState(service.getState());
      entity.setName(service.getName());
      entity.setDescription(service.getDescription());
      entity.setContact(service.getContact());

      entity.setUid(UUID.randomUUID().toString());

      return entity;
   }

   /**
    * Private method used to go from a serviceEntity to a Service
    *
    * @param serviceEntity the received serviceEntity
    * @return the new Service
    */
   private ServiceGet toService(ServiceEntity serviceEntity)
   {
      ServiceGet service = new ServiceGet();

      service.setContact(serviceEntity.getContact());
      service.setDescription(serviceEntity.getDescription());
      service.setSelf(BASE_URL + serviceEntity.getUid());
      service.setName(serviceEntity.getName());
      service.setState(serviceEntity.getState());
      service.setStatusAddress(serviceEntity.getStatusAddress());
      service.setStatusPort(serviceEntity.getStatusPort());

      return service;
   }

   // Add a new validator to the service entity we get as an input
   @InitBinder(value = "servicePost")
   public void initBinder(WebDataBinder binder)
   {
      binder.addValidators(servicePostValidator);
   }
}
