package io.pestakit.status.agent;

import io.pestakit.status.api.model.State;
import io.pestakit.status.entities.ServiceEntity;
import io.pestakit.status.repositories.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.Socket;

@Component
public class ScheduledTasks
{
   @Autowired
   ServiceRepository serviceRepository;

   // Interval in ms
   private final int CHECK_INTERVAL = 20 * 1000;
   private Iterable<ServiceEntity> services = null;

   /**
    * Run this task every fixedRate milliseconds
    */
   @Scheduled(fixedRate = CHECK_INTERVAL)
   public void checkServicesStatus()
   {
      System.out.println("Starting service check...");

      // Retrieve all services
      services = serviceRepository.findAll();

      // Check all services
      for(ServiceEntity service : services)
      {
         // If the service is required to be checked
         if(isCheckable(service))
         {
            // Check if the service is alive
            boolean alive = isAlive(service);

            // Update the service
            updateService(service, alive);
         }
      }

      System.out.println("End of the services check");
   }

   private boolean isCheckable(ServiceEntity serviceEntity)
   {
      boolean address = !serviceEntity.getStatusAddress().equals("") && serviceEntity.getStatusAddress() != null;
      boolean port = serviceEntity.getStatusPort() != null;

      return port && address;
   }

   private boolean isAlive(ServiceEntity service)
   {
      System.out.println("Checking service " + service.getName());

      try (Socket s = new Socket(service.getStatusAddress(), service.getStatusPort())) {
         return true;
      } catch (IOException ex) {

      }
      return false;
   }

   private void updateService(ServiceEntity oldService, boolean alive) {

      System.out.println("Updating service " + oldService.getName());

      // Create an updated service
      ServiceEntity updatedService = new ServiceEntity(oldService);

      // Set all the appropriate fields and update the required ones
      updatedService.setUid(oldService.getUid());

      // Set the appropriate state
      if(alive)
         updatedService.setState(State.UP);
      else
         updatedService.setState(State.DOWN);

      // Delete the old service
      serviceRepository.deleteByUid(oldService.getUid());

      // Save the updated service
      serviceRepository.save(updatedService);
   }
}
