package net.copicomp.backendcursojava.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.copicomp.backendcursojava.entities.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long>{
    
    UserEntity findByEmail(String email);    

    

}
