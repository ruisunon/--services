package com.demo.service;

import com.demo.model.JobCategories;
import com.demo.repository.JobCategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class JobCategoriesService {
    private final String CATEGORIES_CACHE = "CATEGORIES";

    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, JobCategories> hashOperations;

    @Autowired
    private JobCategoriesRepository jobCategoriesRepository;

    @PostConstruct
    private void intializeHashOperations() {
        hashOperations = redisTemplate.opsForHash();
    }

    // Save operation.
    public void save(final JobCategories jobCategories) {
        jobCategoriesRepository.save(jobCategories);
    }

    public void saveToCache(final JobCategories jobCategories) {
        hashOperations.put(CATEGORIES_CACHE, jobCategories.getCategory_id().toString(), jobCategories);
    }

    public JobCategories getCategories(Long id) {
        JobCategories jobCategories = null;
        try {
            jobCategories = this.jobCategoriesRepository.findById(id)
                    .orElseThrow(() -> new Exception("jobCategories Data not found for this id :: " + id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jobCategories;
    }

    // Find by data id operation.
    public JobCategories findById(final String id) {
        JobCategories jobCategories = (JobCategories) hashOperations.get(CATEGORIES_CACHE, id);
        if (jobCategories!=null) return jobCategories;
        else {
            JobCategories jobCategories2 = this.getCategories(Long.valueOf(id));
            saveToCache(jobCategories2);
            return jobCategories2;
        }
    }

    // Find all data operation.
    public Map<String, JobCategories> findAll() {
        List<JobCategories> jobCategories = jobCategoriesRepository.findAll();
        jobCategories.forEach((jobCategories1 -> hashOperations.put(CATEGORIES_CACHE, jobCategories1.getCategory_id().toString(), jobCategories1)));
        return hashOperations.entries(CATEGORIES_CACHE);
    }

    public JobCategories delete(String id){
        hashOperations.delete(CATEGORIES_CACHE, id);
        JobCategories jobCategories = getCategories(Long.parseLong(id));
        this.jobCategoriesRepository.delete(jobCategories);
        return jobCategories;
    }

    // Update by id operation.
    public JobCategories updateCategory(Long id, JobCategories jobCategories){
        JobCategories jobCategoriesEntity = null;
        try {
            jobCategoriesEntity = this.jobCategoriesRepository.findById(id)
                    .orElseThrow(() -> new Exception("JobCategories not found for this id :: " + id));
            jobCategoriesEntity.setCateogryName(jobCategories.getCateogryName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jobCategoriesEntity;
    }
}
