package io.prots.app.service;

import java.util.List;

import io.prots.app.model.dto.EmployeeAssignedProjectDto;
import io.prots.app.model.dto.collection.DtoCollection;
import io.prots.app.model.entity.Employee;

public interface EmployeeService {
	
	DtoCollection<Employee> findAll();
	Employee findById(final Integer employeeId);
	Employee save(final Employee employee);
	Employee update(final Employee employee);
	void deleteById(final Integer employeeId);
	List<Employee> findByDepartmentId(final Integer departmentId);
	List<Employee> findByManagerId(final Integer managerId);
	List<EmployeeAssignedProjectDto> findByManagerIdAndProjectId(final Integer managerId, final Integer projectId);
	List<Employee> findAllManagers();
	Employee findByUsername(final String username);
	void deleteByUsername(final String username);
	
}
