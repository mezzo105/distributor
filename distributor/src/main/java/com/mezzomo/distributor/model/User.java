package com.mezzomo.distributor.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class User 
{
	private final static HashSet<String> LEVEL= new HashSet<String>(Arrays.asList("private","business","other"));
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name,surname;
	private String level;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	List<Contract>Contracts= new ArrayList<Contract>(); //child list
	
	
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public List<Contract> getContracts() {
		return Contracts;
	}
	public void setContracts(List<Contract> contracts) {
		Contracts = contracts;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public boolean isValid()
	{
		return LEVEL.contains(level) &&
				name!=null &&
				!name.isBlank() &&
				surname!=null &&
				!surname.isBlank();
	}

}

