package io.prots.app.model.entity;

import java.io.Serializable;
import java.util.Collections;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "departments")
public final class Department implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "department_id", unique = true, nullable = false, precision = 10)
	private Integer departmentId;
	
	@NotBlank(message = "*Must not blank**")
	@Column(name = "department_name", length = 200)
	private String departmentName;
	
	@NotNull(message = "*Must not NULL**")
	@ManyToOne
	@JoinColumn(name = "location_id")
	private Location location;
	
	@JsonIgnore
	@OneToMany(mappedBy = "department", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Employee> employees;
	
	public Department() {
		
	}
	
	@Override
	public String toString() {
		return "Department [departmentId=" + getDepartmentId() + ", departmentName=" + departmentName + ", location="
				+ this.location + "]";
	}
	
	public Integer getDepartmentId() {
		return departmentId;
	}
	
	public void setDepartmentId(final Integer departmentId) {
		this.departmentId = departmentId;
	}
	
	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(final String departmentName) {
		this.departmentName = departmentName;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(final Location location) {
		this.location = location;
	}

	public Set<Employee> getEmployees() {
		return Collections.unmodifiableSet(this.employees);
	}

	public void setEmployees(final Set<Employee> employees) {
		this.employees = employees;
	}
	
	
	
}














