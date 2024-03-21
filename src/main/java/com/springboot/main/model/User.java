package com.springboot.main.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.UniqueConstraint;
@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String emailId;
	@Column(unique=true)
	private String username;
	private String password;
	private String role;
	//generate getters and setters
	public User(int id, String emailId, String username, String password, String role) {
		super();
		this.id = id;
		this.emailId = emailId;
		this.username = username;
		this.password = password;
		this.role = role;
	}
	public User(String emailId, String username, String password, String role) {
		super();
		this.emailId = emailId;
		this.username = username;
		this.password = password;
		this.role = role;
	}
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", emailId=" + emailId + ", username=" + username + ", password=" + password
				+ ", role=" + role + "]";
	}
	


}
