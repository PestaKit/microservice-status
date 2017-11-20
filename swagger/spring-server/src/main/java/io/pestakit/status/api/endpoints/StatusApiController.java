package io.pestakit.status.api.endpoints;

import io.pestakit.status.api.ServicesApi;
import io.pestakit.status.api.model.Service;
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

   @Override // TODO
   public ResponseEntity<Void> addService(@ApiParam(value = "" ,required=true ) @RequestBody List<Service> serviceS)
   {
      int numberOfServiceAdded = 0;

      // For every service we got
      for (Service s : serviceS)
      {
         // Check whether the service is valid. If so, include it to the serviceRepository
         if(checkService(s))
         {
            numberOfServiceAdded += 1;
            ServiceEntity serviceEntity = toServiceEntity(s);

            // save the service
            serviceRepository.save(serviceEntity);
         }
      }

      System.out.println("Number of services added : " + numberOfServiceAdded);

      // TODO error response code according to result to deal
      return new ResponseEntity<Void>(HttpStatus.OK);
   }

   @Override
   public ResponseEntity<Service> getOneService(@ApiParam(value = "Numeric ID of the service to get.",required=true ) @PathVariable("serviceID") Integer serviceID)
   {
      return null;
   }

   /**
    * Get a list of all services available
    * @param status status wanted, none specified mean all
    * @return a list of services
    */
   @Override // TODO
   public ResponseEntity<List<Service>> getServices( @ApiParam(value = "Status wanted, none specified mean all") @RequestParam(value = "status", required = false) String status)
   {
      // The list of services to return to the user
      ArrayList<Service> liste = new ArrayList<>();

      System.out.println("Status : " + status);

      // If we want a specific status
      if(status != null)
      {
         for(ServiceEntity s : serviceRepository.findAll())
         {
            Service service = toService(s);

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
            Service service = toService(s);

            System.out.println(service.toString());
            liste.add(service);
         }
      }

      return new ResponseEntity<List<Service>>(liste, HttpStatus.OK);
   }

   @Override
   public ResponseEntity<Void> servicesPut(@ApiParam(value = "" ,required=true ) @RequestBody List<Service> serviceS)
   {
      return null;
   }

   @Override
   public ResponseEntity<Void> servicesServiceIDDelete(@ApiParam(value = "Numeric ID of the service to delete.",required=true ) @PathVariable("serviceID") Integer serviceID)
   {
      return null;
   }

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
   private ServiceEntity toServiceEntity(Service service)
   {
      ServiceEntity entity = new ServiceEntity();

      entity.setId(service.getId());
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
   private Service toService(ServiceEntity serviceEntity) {
      Service service = new Service();

      service.setContact(serviceEntity.getContact());
      service.setDescription(serviceEntity.getDescription());
      service.setId(serviceEntity.getId());
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
   private boolean checkService(Service service)
   {
      return service.getContact() != null &&
         service.getDescription() != null &&
         service.getName() != null &&
         service.getState() != null &&
         service.getStatusAddress() != null &&
         service.getId() != null;
   }
}
