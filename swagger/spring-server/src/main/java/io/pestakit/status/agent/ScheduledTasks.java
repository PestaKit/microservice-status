package io.pestakit.status.agent;

import io.pestakit.status.api.ServicesApi;
import io.pestakit.status.api.model.State;
import io.pestakit.status.entities.ServiceEntity;
import io.pestakit.status.repositories.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.Socket;
import java.util.List;

@Component
public class ScheduledTasks
{
   @Autowired
   ServiceRepository serviceRepository;

   // Interval in ms
   private final int CHECK_INTERVAL = 5 * 1000;
   private Iterable<ServiceEntity> services = null;

   /**
    * Run this task every fixedRate milliseconds
    */
   @Scheduled(fixedRate = CHECK_INTERVAL)
   public void checkServicesStatus()
   {
      System.out.println("Starting service check...");

      services = serviceRepository.findAll();

      for(ServiceEntity service : services)
      {
         // If the service is required to be checked
         if(isCheckable(service))
         {
            boolean alive = isAlive(service);

            ServiceEntity updatedService = new ServiceEntity(service);

            // Set the appropriate state
            if(alive)
               updatedService.setState(State.UP);
            else
               updatedService.setState(State.DOWN);

            // Delete the old service
            serviceRepository.deleteByUid(service.getUid());

            // Restore the same uid for the updated service
            updatedService.setUid(service.getUid());

            // Save the updated service
            serviceRepository.save(updatedService);
         }
      }

   }

   private boolean isCheckable(ServiceEntity serviceEntity)
   {
      boolean addressState = !serviceEntity.getStatusAddress().equals("") && serviceEntity.getStatusAddress() != null;
      return true;
   }

   private boolean isAlive(ServiceEntity serviceEntity)
   {
      System.out.println("Checking service " + serviceEntity.getName());

      Socket socket =
      return true;
   }
}
