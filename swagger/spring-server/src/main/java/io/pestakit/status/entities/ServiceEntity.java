package io.pestakit.status.entities;

import io.pestakit.status.api.model.ServiceGet;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
public class ServiceEntity implements Serializable
{
   public enum State {

      UP("up"),
      DOWN("down"),
      MAINTENANCE("maintenance");

      private String value;

      State(String value) {
         this.value = value;
      }

      @Override
      public String toString() {
         return String.valueOf(value);
      }

      public static State fromValue(String text) {
         for (State b : State.values()) {
            if (String.valueOf(b.value).equals(text)) {
               return b;
            }
         }
         return null;
      }
   }

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;

   private String name;
   private String statusAddress;
   private String description;
   @NotNull
   private State state;
   private String contact;

   public Integer getId()
   {
      return id;
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