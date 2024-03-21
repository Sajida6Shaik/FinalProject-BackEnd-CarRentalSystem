package com.springboot.main.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
@Entity
public class Host {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	
	private int hostId; 
	private String hostEmail;	
	private String hostName;
	private String hostContact;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;
	//Generate getters and setters
	public Host() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Host(int hostId, String hostEmail, String hostName, String hostContact, User user) {
		super();
		this.hostId = hostId;
		this.hostEmail = hostEmail;
		this.hostName = hostName;
		this.hostContact = hostContact;
		this.user = user;
	}
	public int getHostId() {
		return hostId;
	}
	public void setHostId(int hostId) {
		this.hostId = hostId;
	}
	public String getHostEmail() {
		return hostEmail;
	}
	public void setHostEmail(String hostEmail) {
		this.hostEmail = hostEmail;
	}
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public String getHostContact() {
		return hostContact;
	}
	public void setHostContact(String hostContact) {
		this.hostContact = hostContact;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Override
	public String toString() {
		return "Host [hostId=" + hostId + ", hostEmail=" + hostEmail + ", hostName=" + hostName + ", hostContact="
				+ hostContact + ", user=" + user + "]";
	}
	 
	

}
