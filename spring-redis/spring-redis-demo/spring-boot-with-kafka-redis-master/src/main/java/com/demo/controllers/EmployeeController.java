package com.demo.controllers;

import com.demo.model.*;
import com.demo.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/redis/employee")
public class EmployeeController {
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    EmployeeService employeeService;

    // Url - http://localhost:9000/api/redis/employee
    @PostMapping
    public String save(@RequestBody final Employee employee) {
        LOG.info("Saving the new employee data to the redis.");
        employeeService.save(employee);
        return "Successfully added. Employee data with id= " + employee.getEmployee_id();
    }

    // Url - http://localhost:9000/api/redis/employee/getall
    @GetMapping("/getall")
    @PreAuthorize("hasAuthority('read:admin-messages')")
    public Map<String, Employee> findAll() {
        LOG.info("Fetching all employee data from the redis.");
        final Map<String, Employee> employeeMap = employeeService.findAll();
        // Todo - If developers like they can sort the map (optional).
        return employeeMap;
    }

    // Url - http://localhost:9000/api/redis/employee/get/<employee_id>
    @GetMapping("/get/{id}")
    public Employee findById(@PathVariable("id") final String id) {
        LOG.info("Fetching Employee with id= " + id);
        return employeeService.findById(id);
    }

    // Url - http://localhost:9000/api/redis/employee/search?
    @GetMapping("/serach")
    public ResponseEntity<Page<Employee>> getFilterJobs(EmployeePage employeePage,
                                                    EmployeeSearchCriteria employeeSearchCriteria){
        return new ResponseEntity<>(employeeService.getFilterJobs(employeePage, employeeSearchCriteria), HttpStatus.OK);
    }

    // Url - http://localhost:9000/api/redis/employee/<employee_id>
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('read:admin-messages')")
    public Map<String, Employee> delete(@PathVariable("id") final String id) {
        employeeService.delete(id);
        // Returning the all employee data (post the deleted one).
        return findAll();
    }

    // Url - http://localhost:9000/api/redis/employee/put/<employee_id>
    @PutMapping("/put/{id}")
    public void updateProduct(@PathVariable(value = "id") Long id, @RequestBody Employee employee){
        employeeService.updateCategory(id, employee);
    }
}
