package io.prots.app.model.entity;

import java.io.Serializable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "user_credentials", indexes = {@Index(name = "user_credentials_username_IX", columnList = "username", unique = true)})
public final class Credential implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id", unique = true, nullable = false, precision = 10)
	private Integer credentialId;
	
	@Column(unique = true, length = 200)
	private String username;
	
	@Column(name = "password", length = 200)
	private String password;
	
	@Column(name = "enabled")
	private Boolean enabled;
	
	@Column(name = "role", length = 200)
	private String role;
	
	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "employee_id")
	private Employee employee;
	
	public Credential() {
		
	}
	
	public Credential(final String username, final String password, final String role, final Employee employee) {
		this.username = username;
		this.password = password;
		this.enabled = true;
		this.role = role;
		this.employee = employee;
	}
	
	public Credential(final String username, final String password, final Boolean enabled, final String role, final Employee employee) {
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.role = role;
		this.employee = employee;
	}
	
	@Override
	public String toString() {
		return "Credential [credentialId=" + credentialId + ", username=" + username + ", password=" + password
				+ ", enabled=" + enabled + ", role=" + role + ", employeeId=" + employee.getEmployeeId() + "]";
	}
	
	public Integer getCredentialId() {
		return credentialId;
	}

	public void setCredentialId(Integer credentialId) {
		this.credentialId = credentialId;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(final Boolean enabled) {
		this.enabled = enabled;
	}

	public String getRole() {
		return role;
	}

	public void setRole(final String role) {
		this.role = role;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(final Employee employee) {
		this.employee = employee;
	}
	
	
	
}













