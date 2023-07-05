package io.prots.app.service;

import io.prots.app.model.dto.collection.DtoCollection;
import io.prots.app.model.entity.Department;

public interface DepartmentService {
	
	DtoCollection<Department> findAll();
	Department findById(final Integer departmentId);
	Department save(final Department department);
	Department update(final Department department);
	void deleteById(final Integer departmentId);
	
}
