package com.demo.controllers;


import com.demo.model.*;
import com.demo.service.EmployerService;
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
@RequestMapping(value = "/api/redis/employer")
public class EmployerController {
    private static final Logger LOG = LoggerFactory.getLogger(EmployerController.class);

    @Autowired
    EmployerService employerService;

    // Url - http://localhost:9000/api/redis/employer
    @PostMapping
    public String save(@RequestBody final Employer employer) {
        LOG.info("Saving the new employer data to the redis.");
        employerService.save(employer);
        return "Successfully added. Employer data with id= " + employer.getEmployer_id();
    }

    // Url - http://localhost:9000/api/redis/employer/getall
    @GetMapping("/getall")
    @PreAuthorize("hasAuthority('read:admin-messages')")
    public Map<String, Employer> findAll() {
        LOG.info("Fetching all employer data from the redis.");
        final Map<String, Employer> employerMap = employerService.findAll();
        // Todo - If developers like they can sort the map (optional).
        return employerMap;
    }

    // Url - http://localhost:9000/api/redis/employer/get/<employer_id>
    @GetMapping("/get/{id}")
    public Employer findById(@PathVariable("id") final String id) {
        LOG.info("Fetching Employer with id= " + id);
        return employerService.findById(id);
    }

    @GetMapping("/serach")
    public ResponseEntity<Page<Employer>> getFilterJobs(EmployerPage employerPage,
                                                    EmployerSearchCriteria employerSearchCriteria){
        return new ResponseEntity<>(employerService.getFilterJobs(employerPage, employerSearchCriteria), HttpStatus.OK);
    }

    // Url - http://localhost:9000/api/redis/employer/<employer_id>
    @DeleteMapping("/delete/{id}")
    public Map<String, Employer> delete(@PathVariable("id") final String id) {
        employerService.delete(id);
        // Returning the all employer data (post the deleted one).
        return findAll();
    }

    // Url - http://localhost:9000/api/redis/employer/put/<employer_id>
    @PutMapping("/put/{id}")
    public void updateProduct(@PathVariable(value = "id") Long id, @RequestBody Employer employer){
        employerService.updateCategory(id, employer);
    }
}
