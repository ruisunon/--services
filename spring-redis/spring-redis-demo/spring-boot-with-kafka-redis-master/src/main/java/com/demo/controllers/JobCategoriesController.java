package com.demo.controllers;

import com.demo.model.JobCategories;
import com.demo.service.JobCategoriesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/redis/category")
public class JobCategoriesController {
    private static final Logger LOG = LoggerFactory.getLogger(JobCategoriesController.class);

    @Autowired
    JobCategoriesService jobCategoriesService;

    // Url - http://localhost:9000/api/redis/category
    @PostMapping
    @PreAuthorize("hasAuthority('read:admin-messages')")
    public String save(@RequestBody final JobCategories jobCategories) {
        LOG.info("Saving the new jobCategories data to the redis.");
        jobCategoriesService.save(jobCategories);
        return "Successfully added. JobCategories data with id= " + jobCategories.getCategory_id();
    }

    // Url - http://localhost:9000/api/redis/category/getall
    @GetMapping("/getall")
    public Map<String, JobCategories> findAll() {
        LOG.info("Fetching all jobCategories data from the redis.");
        final Map<String, JobCategories> jobCategoriesMap = jobCategoriesService.findAll();
        // Todo - If developers like they can sort the map (optional).
        return jobCategoriesMap;
    }

    // Url - http://localhost:9000/api/redis/category/get/<JobCategories_id>
    @GetMapping("/get/{id}")
    public JobCategories findById(@PathVariable("id") final String id) {
        LOG.info("Fetching JobCategories with id= " + id);
        return jobCategoriesService.findById(id);
    }

    // Url - http://localhost:9000/api/redis/category/delete/<JobCategories_id>
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('read:admin-messages')")
    public Map<String, JobCategories> delete(@PathVariable("id") final String id) {
        jobCategoriesService.delete(id);
        // Returning the all employer data (post the deleted one).
        return findAll();
    }

    // Url - http://localhost:9000/api/redis/category/put/<JobCategories_id>
    @PutMapping("/put/{id}")
    @PreAuthorize("hasAuthority('read:admin-messages')")
    public void updateProduct(@PathVariable(value = "id") Long id, @RequestBody JobCategories jobCategories){
        jobCategoriesService.updateCategory(id, jobCategories);
    }
}
