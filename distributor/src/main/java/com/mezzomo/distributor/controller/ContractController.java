package com.mezzomo.distributor.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mezzomo.distributor.model.Contract;
import com.mezzomo.distributor.model.User;
import com.mezzomo.distributor.model.UserContract;
import com.mezzomo.distributor.repository.ContractRepository;
import com.mezzomo.distributor.repository.UserRepository;

@RestController
@RequestMapping("/distributor")
public class ContractController 
{
	@Autowired
	ContractRepository repo; //repository Contract
	
	@Autowired
	UserRepository userRepo; //repository User
	
	
	//1)insert new contracts
	@PostMapping("/contracts")
    public ResponseEntity<List<Contract>> addContracts(@RequestBody UserContract userContract) 
	{
		//User
		User u= userRepo.findById(userContract.getUser().getId()).get();
		
		//Check whether the user can enter into a new contract according to specifications
		 if (!u.getLevel().equalsIgnoreCase("private") && !u.getLevel().equalsIgnoreCase("business")) 
         {
			 return new ResponseEntity<>(HttpStatus.FORBIDDEN);  
         } 
		 
         for (Contract contract : userContract.getContracts()) 
         {
        	//full processing
        	if(!contract.isValid())
        		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
         }
         
         try
         {
        	 repo.saveAll(userContract.getContracts());
        	 
         }catch (Exception e) 
 		
		 {
		    System.err.println("error while saving contracts: " + e.getMessage());
		 }
        return new ResponseEntity<>(userContract.getContracts(), HttpStatus.CREATED);
    }
	
	/*2)search contracts with the possibility to search by:
	a. Costumer’s name
	b. Date of start contract.
	c. Type of contract (gas |electricity | gas and electricity)
	d. Type of user (private | business)
	All filters can be combined
	*/
	@GetMapping("/contracts/search")
    public Iterable<Contract> searchContracts(
            @RequestParam(name = "Name", required = false) String customerName,
            @RequestParam(name = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(name = "type", required = false) String contractType,
            @RequestParam(name = "level", required = false) String userType)
	{
		
		Iterable<Contract> result = repo.findAll(); // recover all contracts
		
		//check if there are contracts
		if(((List<Contract>) result).isEmpty())
			System.err.println("No contract present");
		
		try
		{
			
	        if (customerName != null)
	        {
	            ((List<Contract>) result).retainAll(repo.findByUser_Name(customerName));
	        }
	
	        if (startDate != null) 
	        {
	            ((List<Contract>) result).retainAll(repo.findByStartDate(startDate));
	        }
	
	        if (contractType != null)
	        {
	            ((List<Contract>) result).retainAll(repo.findByType(contractType));
	        }
	
	        if (userType != null) 
	        {
	            ((List<Contract>) result).retainAll(repo.findByUser_Level(userType));
	        }
	        
		} catch (Exception e) 
		
		{
	        System.err.println("error while searching for contracts: " + e.getMessage());
	        }
	        return result;
	
	}
	
	//3)retrieves the contracts of one user
	@GetMapping("/contracts/user/{userId}")
	public ResponseEntity<List<Contract>> getContractsByUser(@PathVariable("userId") int userId) 
	{
	    List<Contract> userContracts = repo.findByUser_Id(userId);

	    if (userContracts.isEmpty()) 
	    {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    } 
	    else 
	    {
	        return new ResponseEntity<>(userContracts, HttpStatus.OK);
	    }
	}


}
