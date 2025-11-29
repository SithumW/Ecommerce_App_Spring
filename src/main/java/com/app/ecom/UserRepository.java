package com.app.ecom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long>{
//User -> Giving access to methods of User entity type
//Long -> Mention the type of the Primary key (id)

    //This JPARepository already have set of defined methods
    //  save, findById likewise

}
