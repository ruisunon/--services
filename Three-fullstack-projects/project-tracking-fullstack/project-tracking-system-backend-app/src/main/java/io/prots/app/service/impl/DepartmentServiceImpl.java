package io.prots.app.service.impl;

import java.util.Collections;

import jakarta.transaction.Transactional;

import io.prots.app.exception.custom.ObjectNotFoundException;
import io.prots.app.repository.DepartmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.prots.app.model.dto.collection.DtoCollection;
import io.prots.app.model.entity.Department;
import io.prots.app.service.DepartmentService;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {
	
	private final DepartmentRepository rep;
	private static final Logger logger = LoggerFactory.getLogger(DepartmentServiceImpl.class);
	
	static {
		logger.info("************ entering " + DepartmentServiceImpl.class.getName() + " ************");
	}
	
	@Autowired
	public DepartmentServiceImpl(final DepartmentRepository rep) {
		this.rep = rep;
	}
	
	@Override
	public DtoCollection<Department> findAll() {
		return new DtoCollection<>(Collections.unmodifiableList(this.rep.findAll()));
	}
	
	@Override
	public Department findById(final Integer departmentId) {
		return this.rep.findById(departmentId).orElseThrow(() -> new ObjectNotFoundException("###### NO Department object FOUND! ######"));
	}
	
	@Override
	public Department save(final Department department) {
		return this.rep.save(department);
	}
	
	@Override
	public Department update(final Department department) {
		return this.rep.save(department);
	}
	
	@Override
	public void deleteById(final Integer departmentId) {
		this.rep.delete(this.findById(departmentId));
	}
	
	
	
}










