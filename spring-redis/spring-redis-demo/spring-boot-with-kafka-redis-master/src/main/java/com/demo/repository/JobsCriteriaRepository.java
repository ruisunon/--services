package com.demo.repository;

import com.demo.model.Jobs;
import com.demo.model.JobsPage;
import com.demo.model.JobsSearchCriteria;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class JobsCriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public JobsCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<Jobs> findAllWithFilters(JobsPage jobsPage,
                                         JobsSearchCriteria jobsSearchCriteria){
        CriteriaQuery<Jobs> criteriaQuery = criteriaBuilder.createQuery(Jobs.class);
        Root<Jobs> jobsRoot = criteriaQuery.from(Jobs.class);
        Predicate predicate = getPredicate(jobsSearchCriteria, jobsRoot);
        criteriaQuery.where(predicate);
        setOrder(jobsPage, criteriaQuery, jobsRoot);

        TypedQuery<Jobs> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(jobsPage.getPageNumber() * jobsPage.getPageSize());
        typedQuery.setMaxResults(jobsPage.getPageSize());

        Pageable pageable = getPageable(jobsPage);
        long employeesCount = getJobsCount(predicate);

        return new PageImpl<>(typedQuery.getResultList(), pageable, employeesCount);
    }


    private Predicate getPredicate (JobsSearchCriteria jobsSearchCriteria, Root<Jobs> jobsRoot){
        List<Predicate> predicates = new ArrayList<>();
        if(Objects.nonNull(jobsSearchCriteria.getDistrict())){
            predicates.add(criteriaBuilder.like(jobsRoot.get("district"),
                    "%" + jobsSearchCriteria.getDistrict() + "%"));
        }

        if(Objects.nonNull(jobsSearchCriteria.getPosition())){
            predicates.add(criteriaBuilder.like(jobsRoot.get("position"),
                    "%" + jobsSearchCriteria.getPosition() + "%"));
        }

        if(Objects.nonNull(jobsSearchCriteria.getPosition())){
            predicates.add(criteriaBuilder.like(jobsRoot.get("companyName"),
                    "%" + jobsSearchCriteria.getCompanyName() + "%"));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private void setOrder(JobsPage jobsPage, CriteriaQuery<Jobs> criteriaQuery, Root<Jobs> jobsRoot) {
        if(jobsPage.getSortDirection().equals(Sort.Direction.ASC)){
            criteriaQuery.orderBy(criteriaBuilder.asc(jobsRoot.get(jobsPage.getSortBy())));
        }else{
            criteriaQuery.orderBy(criteriaBuilder.desc(jobsRoot.get(jobsPage.getSortBy())));
        }
    }

    private Pageable getPageable(JobsPage jobsPage) {
        Sort sort = Sort.by(jobsPage.getSortDirection(), jobsPage.getSortBy());
        return PageRequest.of(jobsPage.getPageNumber(),jobsPage.getPageSize(), sort);
    }

    private long getJobsCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Jobs> countRoot = countQuery.from(Jobs.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }

}