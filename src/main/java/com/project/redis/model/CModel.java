package com.project.redis.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class CModel implements Serializable{
	
	
	private static final long serialVersionUID = 1182195261452141107L;
	private int id; 
	private String name; 
	private String staffNumber;
	private String age;
	

}
