package com.mezzomo.distributor.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mezzomo.distributor.model.User;


@Repository
public interface UserRepository extends CrudRepository<User,Integer>
{

}
