package io.pestakit.status.api.endpoints;

import io.pestakit.status.api.ServicesApi;
import io.pestakit.status.api.model.Service;
import io.pestakit.status.repositories.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class StatusApiController implements ServicesApi
{
   @Autowired
   StatusRepository statusRepository;

   @Override
   public ResponseEntity<Void> addService(List<Service> serviceS)
   {
      return null;
   }

   @Override
   public ResponseEntity<Service> getOneService(Integer serviceID)
   {
      return null;
   }

   @Override
   public ResponseEntity<List<Service>> getServices(String status)
   {
      return null;
   }

   @Override
   public ResponseEntity<Void> servicesPut(List<Service> serviceS)
   {
      return null;
   }

   @Override
   public ResponseEntity<Void> servicesServiceIDDelete(Integer serviceID)
   {
      return null;
   }

   @Override
   public ResponseEntity<Void> servicesServiceIDPatch(Integer serviceID, Integer state)
   {
      return null;
   }
}
