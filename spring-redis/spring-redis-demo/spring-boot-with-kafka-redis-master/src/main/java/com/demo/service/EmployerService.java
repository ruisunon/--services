package com.demo.service;

import com.demo.model.*;
import com.demo.repository.EmployerCriteriaRepository;
import com.demo.repository.EmployerRepository;
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
public class EmployerService {

    private final String EMPLOYER_CACHE = "EMPLOYER";

    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, Employer> hashOperations;

    @Autowired
    private final EmployerRepository employerRepository;
    private final EmployerCriteriaRepository employerCriteriaRepository;

    public EmployerService(EmployerRepository employerRepository, EmployerCriteriaRepository employerCriteriaRepository) {
        this.employerRepository = employerRepository;
        this.employerCriteriaRepository = employerCriteriaRepository;
    }

    @PostConstruct
    private void intializeHashOperations() {
        hashOperations = redisTemplate.opsForHash();
    }

    // Save operation.
    public void save(final Employer employer) {
        employerRepository.save(employer);
    }

    public void saveToCache(final Employer employer) {
        hashOperations.put(EMPLOYER_CACHE, employer.getEmployer_id().toString(), employer);
    }

    public Employer getEmployer(Long id) {
        Employer employer = null;
        try {
            employer = this.employerRepository.findById(id)
                    .orElseThrow(() -> new Exception("Employer Data not found for this id :: " + id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employer;
    }


    // Find by data id operation.
    public Employer findById(final String id) {
        Employer employer = (Employer) hashOperations.get(EMPLOYER_CACHE, id);
        if (employer!=null) return employer;
        else {
            Employer employer2 = this.getEmployer(Long.valueOf(id));
            saveToCache(employer2);
            return employer2;
        }
    }

    // Find all data operation.
    public Map<String, Employer> findAll() {
        List<Employer> employer = employerRepository.findAll();
        employer.forEach((employer1 -> hashOperations.put(EMPLOYER_CACHE, employer1.getEmployer_id().toString(), employer1)));
        return hashOperations.entries(EMPLOYER_CACHE);
    }

    public Page<Employer> getFilterJobs(EmployerPage jobsPage,
                                        EmployerSearchCriteria jobsSearchCriteria){
        return employerCriteriaRepository.findAllWithFilters(jobsPage, jobsSearchCriteria);
    }

//    // Delete sensor by id operation.
//    public void delete(String id) {
//        hashOperations.delete(EMPLOYER_CACHE, id);
//    }

    public Employer delete(String id){
        hashOperations.delete(EMPLOYER_CACHE, id);
        Employer employer = getEmployer(Long.parseLong(id));
        this.employerRepository.delete(employer);
        return employer;
    }

    public Employer updateCategory(Long id, Employer employer){
        Employer employerEntity = null;
        try {
            employerEntity = this.employerRepository.findById(id)
                    .orElseThrow(() -> new Exception("Employer not found for this id :: " + id));
            employerEntity.setCompanyAddress(employer.getCompanyAddress());
            employerEntity.setCompanyName(employer.getCompanyName());
            employerEntity.setJobs(employer.getJobs());
            employerEntity.setPassword(employer.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employerEntity;
    }
}
