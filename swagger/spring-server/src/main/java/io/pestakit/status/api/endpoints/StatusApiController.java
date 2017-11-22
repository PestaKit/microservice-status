package io.pestakit.status.api.endpoints;

import io.pestakit.status.api.ServicesApi;
import io.pestakit.status.api.model.ServiceGet;
import io.pestakit.status.api.model.ServicePost;
import io.pestakit.status.entities.ServiceEntity;
import io.pestakit.status.repositories.ServiceRepository;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class StatusApiController implements ServicesApi
{
   @Autowired
   ServiceRepository serviceRepository;

   private final String BASE_URL = "http://localhost:8080/services/";

   /**
    * Method used to add one/several service(s) when a post is made to the api
    * @param serviceS a list of service(s) to add to the store
    * @return a response code
    */
   @Override
   public ResponseEntity<Void> addService(@ApiParam(value = "" ,required=true ) @RequestBody List<ServicePost> serviceS)
   {
      // For every service we got in the body
      for (ServicePost s : serviceS)
      {
         // Check whether the service is valid. If so, include it to the serviceRepository
         if(checkServicePost(s))
         {
            ServiceEntity serviceEntity = toServiceEntity(s);

            // save the service
            serviceRepository.save(serviceEntity);
         }
      }

      // TODO error response code according to result to deal
      return new ResponseEntity<Void>(HttpStatus.OK);
   }

   /**
    * Method used when a get is made with a specific id to get the related service
    * @param serviceID the service id of the required service
    * @return a response code and the payload
    */
   @Override
   public ResponseEntity<ServiceGet> getOneService(@ApiParam(value = "Numeric ID of the service to get.",required=true ) @PathVariable("serviceID") Integer serviceID)
   {
      return null;
   }

   /**
    * Get a list of all services available in the store
    * @param status filter by status, none specified mean all
    * @return a list of services and a response code
    */
   @Override // TODO
   public ResponseEntity<List<ServiceGet>> getServices( @ApiParam(value = "Status wanted, none specified mean all") @RequestParam(value = "status", required = false) String status)
   {
      // The list of services to return to the user
      ArrayList<ServiceGet> liste = new ArrayList<>();

      System.out.println("Status : " + status);

      // If we want a specific status
      if(status != null)
      {
         for(ServiceEntity s : serviceRepository.findAll())
         {
            ServiceGet service = toService(s);

            if(service.getState().equals(status))
            {
               System.out.println(service.toString());
               liste.add(service);
            }
         }
      }
      // Otherwise return everything
      else
      {
         for(ServiceEntity s : serviceRepository.findAll())
         {
            ServiceGet service = toService(s);

            System.out.println(service.toString());
            liste.add(service);
         }
      }

      return new ResponseEntity<List<ServiceGet>>(liste, HttpStatus.OK);
   }

   /**
    * Fix a service with a put request
    * @param serviceS
    * @return
    */
   @Override
   public ResponseEntity<Void> servicesPut(@ApiParam(value = "" ,required=true ) @RequestBody List<ServicePost> serviceS)
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
}
