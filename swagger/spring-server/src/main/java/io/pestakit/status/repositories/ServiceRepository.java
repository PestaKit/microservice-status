package io.pestakit.status.repositories;

import io.pestakit.status.jpa.ServiceJPA;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Olivier Liechti on 26/07/17.
 */
public interface ServiceRepository extends CrudRepository<ServiceJPA, Long>{

   public List<ServiceJPA> findByState(String state);

}
