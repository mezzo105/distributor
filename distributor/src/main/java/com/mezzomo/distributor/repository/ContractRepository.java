package com.mezzomo.distributor.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mezzomo.distributor.model.Contract;

@Repository
public interface ContractRepository extends CrudRepository<Contract,Integer>
{
	/*2)search contracts with the possibility to search by:
	a. Costumer’s name
	b. Date of start contract.
	c. Type of contract (gas |electricity | gas and electricity)
	d. Type of user (private | business)
	All filters can be combined
	*/
	List<Contract> findByUser_Name(@Param("customerName") String customerName);

    List<Contract> findByStartDate(@Param("startDate") LocalDate startDate);

    List<Contract> findByType(@Param("contractType") String contractType);

    List<Contract> findByUser_Level(@Param("userType") String userType);
    
    
    //3)retrieves the contracts of one user
    List<Contract> findByUser_Id(@Param("userId") int userId);

}
