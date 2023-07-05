package io.prots.app.model.entity;

import java.io.Serializable;
import java.util.Collections;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "locations")
public final class Location implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "location_id", unique = true, nullable = false, precision = 10)
	private Integer locationId;
	
	@NotBlank(message = "*Must Not blank**")
	@Column(name = "adr", length = 200)
	private String adr;
	
	@NotBlank(message = "*Must Not blank**")
	@Column(name = "postal_code", length = 200)
	private String postalCode;
	
	@NotBlank(message = "*Must Not blank**")
	@Column(name = "city", length = 200)
	private String city;
	
	@JsonIgnore
	@OneToMany(mappedBy = "location", cascade = CascadeType.ALL)
	private Set<Department> departments;
	
	public Location() {
		
	}
	
	@Override
	public String toString() {
		return "Location [locationId=" + getLocationId() + ", adr=" + adr + ", postalCode=" + postalCode + ", city=" + city
				+ "]";
	}
	
	public Integer getLocationId() {
		return locationId;
	}
	
	public void setLocationId(final Integer locationId) {
		this.locationId = locationId;
	}
	
	public String getAdr() {
		return adr;
	}

	public void setAdr(final String adr) {
		this.adr = adr;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(final String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(final String city) {
		this.city = city;
	}

	public Set<Department> getDepartments() {
		return Collections.unmodifiableSet(this.departments);
	}

	public void setDepartments(final Set<Department> departments) {
		this.departments = departments;
	}
	
	
	
}
















