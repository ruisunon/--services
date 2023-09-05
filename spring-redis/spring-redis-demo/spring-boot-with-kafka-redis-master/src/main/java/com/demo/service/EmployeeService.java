package com.demo.service;

import com.demo.model.*;
import com.demo.repository.EmployeeCriteriaRepository;
import com.demo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class EmployeeService {
    private final String EMPLOYEE_CACHE = "EMPLOYEE";

    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, Employee> hashOperations;

    @Autowired
    private final EmployeeRepository employeeRepository;

    private final EmployeeCriteriaRepository employeeCriteriaRepository;

    public EmployeeService(EmployeeRepository employeeRepository, EmployeeCriteriaRepository employeeCriteriaRepository) {
        this.employeeRepository = employeeRepository;
        this.employeeCriteriaRepository = employeeCriteriaRepository;
    }

    @PostConstruct
    private void intializeHashOperations() {
        hashOperations = redisTemplate.opsForHash();
    }

    // Save operation.
    public void save(final Employee employee) {
        employeeRepository.save(employee);
    }

    public void saveToCache(final Employee employee) {
        hashOperations.put(EMPLOYEE_CACHE, employee.getEmployee_id().toString(), employee);
    }

    public Employee getEmployee(Long id) {
        Employee employee = null;
        try {
            employee = this.employeeRepository.findById(id)
                    .orElseThrow(() -> new Exception("Employee Data not found for this id :: " + id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employee;
    }

    // Find by data id operation.
    public Employee findById(final String id) {
        Employee employee = (Employee) hashOperations.get(EMPLOYEE_CACHE, id);
        if (employee!=null) return employee;
        else {
            Employee employee2 = this.getEmployee(Long.valueOf(id));
            saveToCache(employee2);
            return employee2;
        }
    }

    // Find all data operation.
    public Map<String, Employee> findAll() {
        List<Employee> employee = employeeRepository.findAll();
        employee.forEach((employee1 -> hashOperations.put(EMPLOYEE_CACHE, employee1.getEmployee_id().toString(), employee1)));
        return hashOperations.entries(EMPLOYEE_CACHE);
    }

    public Page<Employee> getFilterJobs(EmployeePage jobsPage,
                                        EmployeeSearchCriteria jobsSearchCriteria){
        return employeeCriteriaRepository.findAllWithFilters(jobsPage, jobsSearchCriteria);
    }

//    public void delete(String id) {
//        hashOperations.delete(EMPLOYEE_CACHE, id);
//    }

    public Employee delete(String id){
        hashOperations.delete(EMPLOYEE_CACHE, id);
        Employee employee = getEmployee(Long.parseLong(id));
        this.employeeRepository.delete(employee);
        return employee;
    }

    public Employee updateCategory(Long id, Employee employee){
        Employee employeeEntity = null;
        try {
            employeeEntity = this.employeeRepository.findById(id)
                    .orElseThrow(() -> new Exception("Employee not found for this id :: " + id));
            employeeEntity.setJobs(employee.getJobs());
            employeeEntity.setPassword(employee.getPassword());
            employeeEntity.setDob(employee.getDob());
            employeeEntity.setPhoneNumber(employee.getPhoneNumber());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employeeEntity;
    }
}
