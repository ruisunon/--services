package com.demo.service;

import com.demo.model.Jobs;
import com.demo.model.JobsPage;
import com.demo.model.JobsSearchCriteria;
import com.demo.repository.JobsCriteriaRepository;
import com.demo.repository.JobsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class JobsService {
    private final String JOBS_CACHE = "JOBS";

    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, Jobs> hashOperations;

    @Autowired
    private final JobsRepository jobsRepository;
    private final JobsCriteriaRepository jobsCriteriaRepository;

    public JobsService(JobsRepository jobsRepository, JobsCriteriaRepository jobsCriteriaRepository) {
        this.jobsRepository = jobsRepository;
        this.jobsCriteriaRepository = jobsCriteriaRepository;
    }

    @PostConstruct
    private void intializeHashOperations() {
        hashOperations = redisTemplate.opsForHash();
    }

    // Save operation.
    public void save(final Jobs jobs) {
        jobsRepository.save(jobs);
    }

    public void saveToCache(final Jobs jobs) {
        hashOperations.put(JOBS_CACHE, jobs.getJob_id().toString(), jobs);
    }

    public Jobs getJobs(Long id) {
        Jobs jobs = null;
        try {
            jobs = this.jobsRepository.findById(id)
                    .orElseThrow(() -> new Exception("Jobs Data not found for this id :: " + id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jobs;
    }

    // Find by data id operation.
    public Jobs findById(final String id) {
        Jobs jobs = (Jobs) hashOperations.get(JOBS_CACHE, id);
        if (jobs!=null) return jobs;
        else {
            Jobs jobs2 = this.getJobs(Long.valueOf(id));
            saveToCache(jobs2);
            return jobs2;
        }
    }

    // Find all data operation.
    public Map<String, Jobs> findAll() {
        List<Jobs> jobs = jobsRepository.findAll();
        jobs.forEach((jobs1 -> hashOperations.put(JOBS_CACHE, jobs1.getJob_id().toString(), jobs1)));
        return hashOperations.entries(JOBS_CACHE);
    }

    public Page<Jobs> getFilterJobs(JobsPage jobsPage,
                                   JobsSearchCriteria jobsSearchCriteria){
        return jobsCriteriaRepository.findAllWithFilters(jobsPage, jobsSearchCriteria);
    }

    public Jobs delete(String id){
        hashOperations.delete(JOBS_CACHE, id);
        Jobs jobs = getJobs(Long.parseLong(id));
        this.jobsRepository.delete(jobs);
        return jobs;
    }

    // Update by id operation.
    public Jobs updateCategory(Long id, Jobs jobs){
        Jobs jobsEntity = null;
        try {
            jobsEntity = this.jobsRepository.findById(id)
                    .orElseThrow(() -> new Exception("Jobs not found for this id :: " + id));
            jobsEntity.setDescription(jobs.getDescription());
            jobsEntity.setDistrict(jobs.getDistrict());
            jobsEntity.setJobCategories(jobs.getJobCategories());
            jobsEntity.setEmployee(jobs.getEmployee());
            jobsEntity.setHire(jobs.getHire());
            jobsEntity.setPosition(jobs.getPosition());
            jobsEntity.setCompanyName(jobs.getCompanyName());
            jobsEntity.setCompanyAddress(jobs.getCompanyAddress());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jobsEntity;
    }
}
