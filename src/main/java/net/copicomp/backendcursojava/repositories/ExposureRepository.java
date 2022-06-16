package net.copicomp.backendcursojava.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.copicomp.backendcursojava.entities.ExposureEntity;

@Repository
public interface ExposureRepository extends CrudRepository<ExposureEntity, Long>{
     
    ExposureEntity findById(long id);    


}
