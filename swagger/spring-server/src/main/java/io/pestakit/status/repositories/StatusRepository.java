package io.pestakit.status.repositories;

import io.pestakit.status.entities.StatusEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Olivier Liechti on 26/07/17.
 */
public interface StatusRepository extends CrudRepository<StatusEntity, Long>{

}
