package com.mezzomo.distributor.model;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Contract 
{
	private final static HashSet<String> TYPE= new HashSet<String>(Arrays.asList("gas","electricity","gas and electricity"));
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String type;
	@ManyToOne
	@JoinColumn(name="userid") //foreign key
	private User user;
	//data inizio
	private LocalDate startDate;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	
	public boolean isValid()
	{
		return TYPE.contains(type) &&
				startDate!=null;
	}
	
	

}
