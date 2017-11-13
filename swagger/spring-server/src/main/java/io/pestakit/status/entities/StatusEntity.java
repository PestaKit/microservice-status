package io.pestakit.status.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class StatusEntity implements Serializable
{
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private long id;

   private String name;
   private String statusAddress;
   private String description;
   private String state;
   private String contact;

   public long getId()
   {
      return id;
   }

   public void setId(long id)
   {
      this.id = id;
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

   public String getDescription()
   {
      return description;
   }

   public void setDescription(String description)
   {
      this.description = description;
   }

   public String getState()
   {
      return state;
   }

   public void setState(String state)
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