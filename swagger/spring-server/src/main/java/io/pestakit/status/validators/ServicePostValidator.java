package io.pestakit.status.validators;

import io.pestakit.status.api.model.ServicePost;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ServicePostValidator implements Validator
{
   @Override
   public boolean supports(Class<?> aClass)
   {
      return ServicePost.class.isAssignableFrom(aClass);
   }

   @Override
   public void validate(Object o, Errors errors)
   {
      // Check des champs
      ServicePost service = (ServicePost) o;

      errors.rejectValue("nom du champ", "status string");
   }
}
