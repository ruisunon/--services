package com.demo.controllers;

import com.demo.model.Jobs;
import com.demo.model.JobsPage;
import com.demo.model.JobsSearchCriteria;
import com.demo.service.JobsService;
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
@RequestMapping(value = "/api/redis/jobs")
public class JobsController {
    private static final Logger LOG = LoggerFactory.getLogger(JobsController.class);

    @Autowired
    JobsService jobsService;

    // Url - http://localhost:9000/api/redis/jobs
    @PostMapping
    public String save(@RequestBody final Jobs jobs) {
        LOG.info("Saving the new jobs data to the redis.");
        jobsService.save(jobs);
        return "Successfully added. Jobs data with id= " + jobs.getJob_id();
    }

    // Url - http://localhost:9000/api/redis/jobs/getall
    @GetMapping("/getall")
    public Map<String, Jobs> findAll() {
        LOG.info("Fetching all employer data from the redis.");
        final Map<String, Jobs> jobsMap = jobsService.findAll();

        // Todo - If developers like they can sort the map (optional).
        return jobsMap;
    }

    @GetMapping("/serach")
    public ResponseEntity<Page<Jobs>> getFilterJobs(JobsPage jobsPage,
                                                   JobsSearchCriteria jobsSearchCriteria){
        return new ResponseEntity<>(jobsService.getFilterJobs(jobsPage, jobsSearchCriteria),HttpStatus.OK);
    }

    // Url - http://localhost:9000/api/redis/jobs/get/<jobs_id>
    @GetMapping("/get/{id}")
    public Jobs findById(@PathVariable("id") final String id) {
        LOG.info("Fetching Jobs with id= " + id);
        return jobsService.findById(id);
    }

    // Url - http://localhost:9000/api/redis/jobs/<jobs_id>
    @DeleteMapping("/delete/{id}")
    public Map<String, Jobs> delete(@PathVariable("id") final String id) {
        jobsService.delete(id);
        // Returning the all employer data (post the deleted one).
        return findAll();
    }

    // Url - http://localhost:9000/api/redis/jobs/put/<jobs_id>
    @PutMapping("/put/{id}")
    public void updateProduct(@PathVariable(value = "id") Long id, @RequestBody Jobs jobs){
        jobsService.updateCategory(id, jobs);
    }


}
