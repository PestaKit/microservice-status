package io.pestakit.status.entities;

import io.pestakit.status.api.model.ServiceGet;
import io.pestakit.status.api.model.State;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
public class ServiceEntity implements Serializable
{
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;

   private String uid;

   private String name;
   private String statusAddress;
   private int statusPort;
   private String description;
   private State state;
   private String contact;

   public ServiceEntity()
   {

   }

   public ServiceEntity(ServiceEntity service)
   {
      this.uid = service.getUid();
      this.name = service.getName();
      this.statusAddress = service.getStatusAddress();
      this.statusPort = service.getStatusPort();
      this.description = service.getDescription();
      this.state = service.getState();
      this.contact = service.getContact();
   }

   public String getUid()
   {
      return uid;
   }

   public void setUid(String uid)
   {
      this.uid = uid;
   }

   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   public String getStatusAddress()
   {
      return statusAddress;
   }

   public void setStatusAddress(String statusAddress)
   {
      this.statusAddress = statusAddress;
   }

   public int getStatusPort()
   {
      return statusPort;
   }

   public void setStatusPort(int statusPort)
   {
      this.statusPort = statusPort;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription(String description)
   {
      this.description = description;
   }

   public State getState()
   {
      return state;
   }

   public void setState(State state)
   {
      this.state = state;
   }

   public String getContact()
   {
      return contact;
   }

   public void setContact(String contact)
   {
      this.contact = contact;
   }
}