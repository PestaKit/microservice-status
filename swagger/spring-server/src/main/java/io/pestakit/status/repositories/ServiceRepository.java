package io.pestakit.status.repositories;

import io.pestakit.status.api.model.State;
import io.pestakit.status.entities.ServiceEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Olivier Liechti on 26/07/17.
 */
public interface ServiceRepository extends CrudRepository<ServiceEntity, Long>{

   public List<ServiceEntity> findByState(State state);

   @Transactional
   public ServiceEntity findByUid(String uid);

   @Transactional
   public long deleteByUid(String uid);
}
