package com.histograph.server.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public User() {

	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	boolean institution;
	String copyrightInfo;
	String name;
	String password;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public boolean isInstitution() {
		return institution;
	}
	public void setInstitution(boolean institution) {
		this.institution = institution;
	}
	public String getCopyrightInfo() {
		return copyrightInfo;
	}
	public void setCopyrightInfo(String copyrightInfo) {
		this.copyrightInfo = copyrightInfo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
