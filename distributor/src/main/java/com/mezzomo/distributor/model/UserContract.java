package com.mezzomo.distributor.model;

import java.util.ArrayList;

public class UserContract 
{
	private ArrayList<Contract> contracts= new ArrayList<>();
	private User user;
	public ArrayList<Contract> getContracts() {
		return contracts;
	}
	public void setContracts(ArrayList<Contract> contracts) {
		this.contracts = contracts;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	

}
