package com.example.demo.dao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity

public class User {
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
private Integer id;
	
	@Column(unique=true,nullable=false,length=40)
private String email;
	
	@Column(unique=true,nullable=false,length=40)
private String password;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
private Role role;
	
	

	public User() {
		
	}

	public User( String email, String password, Role role) {
		super();
		
		this.email = email;
		this.password = password;
		this.role = role;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	
}